package com.youxiang.zookeeper.create_node;

import com.youxiang.zookeeper.ZooKeeperApi;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class AsyncCreateNode implements Watcher {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(ZooKeeperApi.CONNETION_STRING, ZooKeeperApi.SESSION_TIMEOUT, new AsyncCreateNode());
            latch.await();

            zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");
            zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");
            zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context.");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }

    static class IStringCallback implements AsyncCallback.StringCallback {
        public void processResult(int i, String s, Object o, String s1) {
            System.out.println("Create path result:[" + i + ", " + s + ", " + o + ", real path name:" + s1);
        }
    }
}
