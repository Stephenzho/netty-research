package study.client.codec;

import io.netty.channel.ChannelHandlerContext;
import study.common.Operation;
import study.common.RequestMessage;
import study.util.IdUtil;
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
