package cn.shuyiio.wire.transport.msg.auth;

import cn.shuyiio.wire.transport.msg.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
