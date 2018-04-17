package com.youxiang.zookeeper.create_node;

import com.youxiang.zookeeper.ZooKeeperApi;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class SyncCreateNode implements Watcher {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper keeper = new ZooKeeper(ZooKeeperApi.CONNETION_STRING, ZooKeeperApi.SESSION_TIMEOUT, new SyncCreateNode());
            latch.await();
            String path = keeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("Success create znode:" + path);

            String path2 = keeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("Success create znode:" + path2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }
}
