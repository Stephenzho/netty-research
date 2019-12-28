package cn.shuyiio.wire.transport.msg.order;

import cn.shuyiio.wire.transport.msg.OperationResult;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

}
