package com.youxiang.zookeeper.acl_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class AuthSample3 {

    public static void main(String[] args) throws Exception {

        String path = "/zk-book-auth-test";

        ZooKeeper zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(path, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zk2 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk2.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(zk2.getData(path, false, null));

        ZooKeeper zk3 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk3.addAuthInfo("digest", "foo:false".getBytes());
        zk3.getData(path, false, null);
    }
}
