package cn.shuyiio.wire.transport.msg.keepalive;

import cn.shuyiio.wire.transport.msg.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
