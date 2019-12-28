package cn.shuyiio.wire.transport.codec.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import study.common.Operation;
import study.common.OperationResult;
import study.common.RequestMessage;
import study.common.ResponseMessage;
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
