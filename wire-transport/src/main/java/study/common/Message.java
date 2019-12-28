package study.common;

import cn.shuyiio.wire.transport.msg.MessageBody;
import cn.shuyiio.wire.transport.msg.MessageHeader;
import io.netty.buffer.ByteBuf;
import study.util.JsonUtil;
import lombok.Data;

import java.nio.charset.Charset;

@Data
public abstract class Message<T extends MessageBody> {

    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getMsgId());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setVersion(version);
        messageHeader.setOpCode(opCode);
        messageHeader.setMsgId(streamId);
        this.messageHeader = messageHeader;

        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        T body = JsonUtil.fromJson(msg.toString(Charset.forName("UTF-8")), bodyClazz);
        this.messageBody = body;
    }

}
