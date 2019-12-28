package study.common;

import lombok.Data;
import lombok.Getter;
import study.common.order.OrderOperation;
import study.common.order.OrderOperationResult;
import study.common.auth.AuthOperation;
import study.common.auth.AuthOperationResult;
import study.common.keepalive.KeepaliveOperation;
import study.common.keepalive.KeepaliveOperationResult;

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



    public static OperationType fromOpCode(byte type){
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
