package com.youxiang.zookeeper.znode_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class GetChildrenAsync implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) {
        String path = "/zk-book";
        try {
            zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, new GetChildrenAsync());
            latch.await();
            zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            zk.getChildren(path, true, new IChildren2Callback(), null);

            zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
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
            } else if (Event.EventType.NodeChildrenChanged == event.getType()) {
                try {
                    System.out.println("ReGet child:" + zk.getChildren(event.getPath(), true));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class IChildren2Callback implements AsyncCallback.Children2Callback {
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("Get children znode result:[response code:" + rc + ", param path:" + path
                            + ", ctx:" + ctx + ", children list: " + children + ", stat: " + stat);
        }
    }
}
