package cn.shuyiio.wire.client;

import cn.shuyiio.common.unit.IdUtil;
import cn.shuyiio.wire.client.codec.OperationToRequestMessageEncoder;
import cn.shuyiio.wire.client.codec.dispatcher.OperationResultFuture;
import cn.shuyiio.wire.client.codec.dispatcher.RequestPendingCenter;
import cn.shuyiio.wire.client.codec.dispatcher.ResponseDispatcherHandler;
import cn.shuyiio.wire.transport.codec.OrderFrameDecoder;
import cn.shuyiio.wire.transport.codec.OrderFrameEncoder;
import cn.shuyiio.wire.transport.codec.OrderProtocolDecoder;
import cn.shuyiio.wire.transport.codec.OrderProtocolEncoder;
import cn.shuyiio.wire.transport.msg.RequestMessage;
import cn.shuyiio.wire.transport.msg.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

/**
 * @author zhoushuyi
 */
public class ClientV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {


        RequestPendingCenter rpc = new RequestPendingCenter();


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);

        // 连接超时等待 10s
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());

                pipeline.addLast(new OperationToRequestMessageEncoder());

                pipeline.addLast(new ResponseDispatcherHandler(rpc));
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                Object object = future.get();

                System.out.println("成功了" + object);
            } else {
                System.out.println("失败了");
            }
        });
        channelFuture.sync();


        OperationResultFuture future = new OperationResultFuture();


        Long id = IdUtil.nextId();
        OrderOperation orderOperation = new OrderOperation(1001, "org.shuyi.App#getxx");
        RequestMessage rm = new RequestMessage(id, orderOperation);

        rpc.add(id, future);

        channelFuture.channel().writeAndFlush(rm);


        System.out.println(future.get());


        channelFuture.channel().closeFuture().get();

    }

}
