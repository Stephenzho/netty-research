package cn.shuyiio.wire.transport.msg;

import lombok.Data;

@Data
public class MessageHeader {

    private int version = 1;
    private int opCode;
    private long msgId;

}
