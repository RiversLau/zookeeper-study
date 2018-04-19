package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: Rivers
 * @date: 2018/4/19
 */
public class CuratorSample15 {

    static String BARRIER_PATH = "/curator_recipes_barrier_path";
    static DistributedBarrier barrier;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder()
                                .connectString(ZKApi.CONNETION_STRING)
                                .sessionTimeoutMs(ZKApi.SESSION_TIMEOUT)
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                        client.start();
                        barrier = new DistributedBarrier(client, BARRIER_PATH);
                        System.out.println(Thread.currentThread().getName() + "好Barrier设置完毕");
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        System.out.println("启动......");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        try {
            barrier.removeBarrier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
