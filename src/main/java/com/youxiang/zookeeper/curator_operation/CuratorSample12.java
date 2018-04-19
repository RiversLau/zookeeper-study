package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/19
 */
public class CuratorSample12 {

    static String LOCK_PATH = "/curator_recipes_lock_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKApi.CONNETION_STRING)
            .sessionTimeoutMs(ZKApi.SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    public static void main(String[] args) {
        client.start();
        final InterProcessMutex lock = new InterProcessMutex(client, LOCK_PATH);
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        latch.await();
                        lock.acquire();
                    } catch (Exception e) {
                        // do nothing
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.out.println("OrderNo is " + orderNo);

                    try {
                        lock.release();
                    } catch (Exception e) {
                        //do nothing
                    }
                }
            }).start();
        }
        latch.countDown();
    }
}
