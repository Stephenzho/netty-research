package study.common;

import cn.shuyiio.wire.transport.msg.MessageBody;

public abstract class Operation extends MessageBody {

    public abstract OperationResult execute();

}
