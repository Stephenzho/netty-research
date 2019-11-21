package io.netty.example.study.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 处理占包半包
 *
 * @author zhoushuyi
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {


    public OrderFrameDecoder() {
        // 最大长度， 长度字段的位移，length长度，补偿调整length字段，删除编码中的length长度
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
