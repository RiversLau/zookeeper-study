package com.youxiang.zookeeper.acl_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class AuthSample4 {

    private static final String PATH = "/zk-book-auth-test";
    private static final String PATH2 = "/zk-book-auth-test/child";

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zk.create(PATH2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        try {
            ZooKeeper zk2 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
            zk2.delete(PATH2, -1);
        } catch (Exception e) {
            System.out.println("删除节点失败：" + e.getMessage());
        }

        ZooKeeper zk3 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk3.addAuthInfo("digest", "foo:true".getBytes());
        zk3.delete(PATH2, -1);
        System.out.println("成功删除节点：" + PATH2);

        ZooKeeper zk4 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk4.delete(PATH, -1);
        System.out.println("成功删除节点：" + PATH);
    }
}
