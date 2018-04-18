package com.youxiang.zookeeper.zkclient_operation;

import com.youxiang.zookeeper.ZKApi;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class ZkclientSample01 {

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT);
        System.out.println("ZooKeeper connection established");

        zkClient.createPersistent("/zk-book/zkclient", true);
        Thread.sleep(10000);
        // 删除指定节点以及所有子节点
        boolean flag = zkClient.deleteRecursive("/zk-book");
        if (flag) {
            System.out.println("Delete succeed!");
        }
    }
}
