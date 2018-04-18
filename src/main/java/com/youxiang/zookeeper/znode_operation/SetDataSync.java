package com.youxiang.zookeeper.znode_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class SetDataSync implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) {
        String path = "/zk-book";
        try {
            zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, new SetDataSync());
            latch.await();
            zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zk.getData(path, true, null);

            Stat stat = zk.setData(path, "456".getBytes(), -1);
            System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());

            Stat stat2 = zk.setData(path, "456".getBytes(), stat.getVersion());
            System.out.println(stat2.getCzxid() + ", " + stat2.getMzxid() + ", " + stat2.getVersion());

            try {
                zk.setData(path, "456".getBytes(), stat.getVersion());
            } catch (KeeperException e) {
                System.out.println("Error: " + e.code() + ", " + e.getMessage());
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                latch.countDown();
            }
        }
    }
}
