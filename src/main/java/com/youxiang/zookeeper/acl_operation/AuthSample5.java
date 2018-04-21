package com.youxiang.zookeeper.acl_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: Rivers
 * @date: 2018/4/21
 */
public class AuthSample5 {

    private static final String PATH = "/zk-book";

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk.addAuthInfo("digest", "foo:true".getBytes());
        zk.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

//        ZooKeeper zk1 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
//        zk1.addAuthInfo("digest", "foo:false".getBytes());
//        zk1.getData(PATH, true, null);

        ZooKeeper zk2 = new ZooKeeper(ZKApi.CONNETION_STRING, ZKApi.SESSION_TIMEOUT, null);
        zk2.addAuthInfo("digest2", "foo:true".getBytes());
        zk2.getData(PATH, true, null);
    }
}
