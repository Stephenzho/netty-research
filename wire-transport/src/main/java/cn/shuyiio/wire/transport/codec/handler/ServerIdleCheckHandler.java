package cn.shuyiio.wire.transport.codec.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * 空闲连接检查，10s后并关闭
 *
 * @author zhoushuyi
 */
@Slf4j
public class ServerIdleCheckHandler extends IdleStateHandler {

    public ServerIdleCheckHandler() {
        super(10, 0, 0, TimeUnit.SECONDS);
    }


    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {

        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            ctx.close();
            log.info("idle check happen, so close the connection");
            return;
        }

        super.channelIdle(ctx, evt);
    }
}
