package io.netty.example.study.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.study.server.codec.OrderFrameDecoder;
import io.netty.example.study.server.codec.OrderFrameEncoder;
import io.netty.example.study.server.codec.OrderProtocolDecoder;
import io.netty.example.study.server.codec.OrderProtocolEncoder;
import io.netty.example.study.server.codec.handler.MetricHandler;
import io.netty.example.study.server.codec.handler.OrderServerProcessHandler;
import io.netty.example.study.server.codec.handler.ServerIdleCheckHandler;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

import java.util.concurrent.ExecutionException;

/**
 * @author zhoushuyi
 */
public class Server {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup(0, new DefaultThreadFactory("Boss"));
        NioEventLoopGroup worker = new NioEventLoopGroup(0, new DefaultThreadFactory("Work"));
        serverBootstrap.group(boss, worker);

        serverBootstrap.channel(NioServerSocketChannel.class);


        // 调参
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(NioChannelOption.SO_BACKLOG, 1024);


        MetricHandler metricHandler = new MetricHandler();
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));

        // 业务处理线程池
        EventExecutorGroup business = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));
        // 限流
        GlobalTrafficShapingHandler trafficShaping = new GlobalTrafficShapingHandler(new NioEventLoopGroup(), 10 * 1024 * 1024, 10 * 1024 * 1024);

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {

            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                pipeline.addLast("TSHandler", trafficShaping);
                pipeline.addLast("idleCheck", new ServerIdleCheckHandler());

                pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                pipeline.addLast("frameEncoder", new OrderFrameEncoder());
                pipeline.addLast("protocolEncoder", new OrderProtocolEncoder());
                pipeline.addLast("protocolDecoder", new OrderProtocolDecoder());

                pipeline.addLast("metricHandler", metricHandler);
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                // 异步flush，可提高qps
                pipeline.addLast("flushEnbance", new FlushConsolidationHandler(5, true));

                pipeline.addLast(business, new OrderServerProcessHandler());
            }
        });


        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();

        channelFuture.channel().closeFuture().get();



    }

}
