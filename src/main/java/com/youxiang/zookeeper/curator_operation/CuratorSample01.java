package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class CuratorSample01 {

    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, 3000, retryPolicy);
        client.start();
    }
}
