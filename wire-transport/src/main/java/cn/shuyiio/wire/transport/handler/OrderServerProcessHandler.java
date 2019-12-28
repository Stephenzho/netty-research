package cn.shuyiio.wire.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import cn.shuyiio.wire.transport.msg.Operation;
import cn.shuyiio.wire.transport.msg.OperationResult;
import cn.shuyiio.wire.transport.msg.RequestMessage;
import cn.shuyiio.wire.transport.msg.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoushuyi
 */
@Slf4j
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) {
        Operation operation = msg.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(msg.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
            ctx.writeAndFlush(responseMessage);
        } else {
            log.error("message dropped");
        }
    }


}
