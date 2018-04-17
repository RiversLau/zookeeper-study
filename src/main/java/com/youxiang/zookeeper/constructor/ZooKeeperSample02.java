package com.youxiang.zookeeper.constructor;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/17
 */
public class ZooKeeperSample02 implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper keeper = new ZooKeeper("ip:2181", 5000, new ZooKeeperSample01());
            System.out.println(keeper.getState());
            latch.await();
            long sessionId = keeper.getSessionId();
            byte[] sessionPwd = keeper.getSessionPasswd();
            keeper = new ZooKeeper("ip:2181", 5000, new ZooKeeperSample02(), 1l, "test".getBytes());
            keeper = new ZooKeeper("ip.2181", 5000, new ZooKeeperSample02(), sessionId, sessionPwd);
//            keeper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            System.out.println("ZooKeeper connection establish failed");
        }
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Received watch event: " + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            latch.countDown();
        }
    }
}
