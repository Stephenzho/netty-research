package cn.shuyiio.wire.client.codec;

import cn.shuyiio.common.unit.IdUtil;
import cn.shuyiio.wire.transport.msg.Operation;
import cn.shuyiio.wire.transport.msg.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author zhoushuyi
 */
public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Operation msg, List<Object> out) throws Exception {

        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), msg);

        out.add(requestMessage);

    }
}
