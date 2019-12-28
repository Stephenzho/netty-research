package cn.shuyiio.wire.transport.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import study.common.RequestMessage;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author zhoushuyi
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {

        RequestMessage rm = new RequestMessage();
        rm.decode(msg);

        out.add(rm);
    }


}
