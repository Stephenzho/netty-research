package study.common;

import cn.shuyiio.wire.transport.msg.MessageHeader;

public class RequestMessage extends Message<Operation>{
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationClazz();
    }

    public RequestMessage(){}

    public RequestMessage(long msgId, Operation operation){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMsgId(msgId);
        messageHeader.setOpCode(OperationType.fromOperation(operation).getOpCode());
        this.setMessageHeader(messageHeader);
        this.setMessageBody(operation);
    }

}
