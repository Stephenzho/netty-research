package io.netty.example.study.client.codec.dispatcher;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.example.study.common.RequestMessage;
import io.netty.example.study.common.keepalive.KeepaliveOperation;
import io.netty.example.study.util.IdUtil;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * keepalive包
 *
 * @author zhoushuyi
 */
@Slf4j
public class KeepaliveHandler extends ChannelDuplexHandler {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {

            log.info("write idle happen, so need to send keepalive to keep connextion not closed by server");

            KeepaliveOperation keepaliveOperation = new KeepaliveOperation();
            RequestMessage rm = new RequestMessage(IdUtil.nextId(), keepaliveOperation);
            ctx.writeAndFlush(rm);
        }

        super.userEventTriggered(ctx, evt);
    }
}