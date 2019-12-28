package cn.shuyiio.wire.client;

import cn.shuyiio.common.unit.IdUtil;
import cn.shuyiio.wire.client.codec.dispatcher.ClientIdleCheckHanlder;
import cn.shuyiio.wire.client.codec.dispatcher.KeepaliveHandler;
import cn.shuyiio.wire.transport.codec.OrderFrameDecoder;
import cn.shuyiio.wire.transport.codec.OrderFrameEncoder;
import cn.shuyiio.wire.transport.codec.OrderProtocolDecoder;
import cn.shuyiio.wire.transport.codec.OrderProtocolEncoder;
import cn.shuyiio.wire.transport.msg.RequestMessage;
import cn.shuyiio.wire.transport.msg.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class Client {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);

        KeepaliveHandler keepaliveHandler = new KeepaliveHandler();

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {

            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new ClientIdleCheckHanlder());

                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());

                pipeline.addLast(keepaliveHandler);
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });


        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();

        RequestMessage rm =  new RequestMessage(IdUtil.nextId(), new OrderOperation(1001,"org.shuyi.App#getxx"));
        channelFuture.channel().writeAndFlush(rm);


        channelFuture.channel().closeFuture().get();
    }

}
