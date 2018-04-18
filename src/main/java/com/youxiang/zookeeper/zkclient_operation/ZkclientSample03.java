package com.youxiang.zookeeper.zkclient_operation;

import com.youxiang.zookeeper.ZKApi;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class ZkclientSample03 {

    public static void main(String[] args) throws InterruptedException {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT);
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("Node " + s + " changed, new data:" + o);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("Node " + s + " deleted");
            }
        });
        zkClient.createEphemeral(path, "123");

        System.out.println(zkClient.readData(path));
        Thread.sleep(1000);
        zkClient.writeData(path, "456");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
