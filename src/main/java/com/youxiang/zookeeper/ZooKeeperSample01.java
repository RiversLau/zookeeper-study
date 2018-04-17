package com.youxiang.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/17
 */
public class ZooKeeperSample01 implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper keeper = new ZooKeeper("120.77.177.184:2181", 5000, new ZooKeeperSample01());
            System.out.println(keeper.getState());
            latch.await();
            System.out.println("ZooKeeper connection extablished!");
            keeper.close();
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
