package cn.shuyiio.wire.client.codec.dispatcher;

import cn.shuyiio.wire.transport.msg.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoushuyi
 */
public class RequestPendingCenter {

    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();



    public void add(Long streamId, OperationResultFuture future) {
        map.put(streamId, future);
    }


    public void set(Long streamId, OperationResult result) {

        OperationResultFuture future = map.get(streamId);
        if (future != null) {
            future.setSuccess(result);
            map.remove(streamId);
        }
    }



}
