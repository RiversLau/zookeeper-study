package com.youxiang.zookeeper.zkclient_operation;

import com.youxiang.zookeeper.ZKApi;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class ZkclientSample02 {

    public static void main(String[] args) throws InterruptedException {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT);
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + " 's child changed, currenetChilds:" + currentChilds);
            }
        });

        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);

        zkClient.createPersistent(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
