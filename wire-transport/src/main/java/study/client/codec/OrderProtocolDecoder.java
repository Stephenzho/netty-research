package study.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import study.common.ResponseMessage;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author zhoushuyi
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {

        ResponseMessage rm = new ResponseMessage();
        rm.decode(msg);

        out.add(rm);
    }


}
