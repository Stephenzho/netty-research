package cn.shuyiio.wire.transport.msg;

public abstract class Operation extends MessageBody {

    public abstract OperationResult execute();

}
