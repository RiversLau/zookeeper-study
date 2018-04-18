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
public class GetDataAsync implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) {
        String path = "/zk-book";
        try {
            zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, new GetDataAsync());
            latch.await();

            zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zk.getData(path, true, new IDataCallback(), null);
            zk.setData(path, "123".getBytes(), -1);
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
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                zk.getData(event.getPath(), true, new IDataCallback(), null);
            }
        }
    }

    static class IDataCallback implements AsyncCallback.DataCallback {
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println(rc + ", " + path + ", " + new String(data));
            System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());
        }
    }
}
