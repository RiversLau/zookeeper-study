package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: Rivers
 * @date: 2018/4/19
 */
public class CuratorSample16 {

    static String BARRIER_PATH = "/curator_recipes_barrier_path";

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder()
                                .connectString(ZKApi.CONNETION_STRING)
                                .sessionTimeoutMs(ZKApi.SESSION_TIMEOUT)
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                        client.start();

                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, BARRIER_PATH, 5);
                        Thread.sleep(Math.round(Math.random() * 3000));
                        System.out.println(Thread.currentThread().getName() + "号Barrier设置完毕");

                        barrier.enter();
                        System.out.println("启动。。。");
                        Thread.sleep(Math.round(Math.random() * 3000));
                        barrier.leave();
                        System.out.println("退出。。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
