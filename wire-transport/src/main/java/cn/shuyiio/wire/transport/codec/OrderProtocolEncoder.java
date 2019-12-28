package cn.shuyiio.wire.transport.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cn.shuyiio.wire.transport.msg.ResponseMessage;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessage msg, List<Object> out) {

        ByteBuf buffer = ctx.alloc().buffer();

        msg.encode(buffer);

        out.add(buffer);
    }

}
