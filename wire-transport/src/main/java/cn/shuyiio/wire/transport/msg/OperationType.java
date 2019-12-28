package cn.shuyiio.wire.transport.msg;

import lombok.Getter;
import cn.shuyiio.wire.transport.msg.order.OrderOperation;
import cn.shuyiio.wire.transport.msg.order.OrderOperationResult;
import cn.shuyiio.wire.transport.msg.auth.AuthOperation;
import cn.shuyiio.wire.transport.msg.auth.AuthOperationResult;
import cn.shuyiio.wire.transport.msg.keepalive.KeepaliveOperation;
import cn.shuyiio.wire.transport.msg.keepalive.KeepaliveOperationResult;

import java.util.function.Predicate;

@Getter
public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE( 2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> responseClass) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = responseClass;
    }



    public static OperationType fromOpCode(int type){
        return getOperationType(requestType -> requestType.opCode == type);
    }

    public static OperationType fromOperation(Operation operation){
        return getOperationType(requestType -> requestType.operationClazz.equals(operation.getClass()));
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate){
        OperationType[] values = values();
        for (OperationType operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        throw new AssertionError("no found type");
    }

}
