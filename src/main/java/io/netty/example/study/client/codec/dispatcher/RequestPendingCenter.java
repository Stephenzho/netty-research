package io.netty.example.study.client.codec.dispatcher;

import io.netty.example.study.common.OperationResult;

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
