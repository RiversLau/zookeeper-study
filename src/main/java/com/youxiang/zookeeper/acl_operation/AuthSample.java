package com.youxiang.zookeeper.acl_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: Rivers
 * @date: 2018/4/18
 */
public class AuthSample {

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create("/zk-book-auth-test", "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
