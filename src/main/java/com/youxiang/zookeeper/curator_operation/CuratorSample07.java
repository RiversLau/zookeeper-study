package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Rivers
 * @date: 2018/4/19
 */
public class CuratorSample07 {

    static String path = "/zk-book";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKApi.CONNETION_STRING).sessionTimeoutMs(ZKApi.SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
    static CountDownLatch latch = new CountDownLatch(2);
    static ExecutorService tp = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {
        client.start();
        System.out.println("Main thread:" + Thread.currentThread().getName());

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("event[code:" + curatorEvent.getResultCode() + ", type:" + curatorEvent.getType() + "]");
                System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                latch.countDown();
            }
        }, tp).forPath(path, "init".getBytes());

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("event[code:" + curatorEvent.getResultCode() + ", type:" + curatorEvent.getType() + "]");
                System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                latch.countDown();
            }
        }).forPath(path, "init".getBytes());

        latch.await();
        tp.shutdown();
    }
}
