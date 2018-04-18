package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class CuratorSample03 {

    static String path = "/zk-book";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKApi.CONNETION_STRING).sessionTimeoutMs(ZKApi.SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());
    }
}
