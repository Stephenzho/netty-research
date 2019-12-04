package io.netty.example.study.client.codec.dispatcher;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * Idle监测
 *
 * @author zhoushuyi
 */
public class ClientIdleCheckHanlder extends IdleStateHandler {

    public ClientIdleCheckHanlder() {
        super(0, 5, 0);
    }

}
