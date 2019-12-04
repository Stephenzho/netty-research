package io.netty.example.study.common.keepalive;


import io.netty.example.study.common.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoushuyi
 */
@Data
@Slf4j
public class KeepaliveOperation extends Operation {

    private long time ;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public KeepaliveOperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
