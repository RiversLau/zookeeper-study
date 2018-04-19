package com.youxiang.zookeeper.curator_operation;

import com.youxiang.zookeeper.ZKApi;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: Rivers
 * @date: 2018/4/19
 */
public class CuratorSample17 {

    static String ZKPATH = "/curator_recipes_zkpath";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKApi.CONNETION_STRING)
            .sessionTimeoutMs(ZKApi.SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    public static void main(String[] args) throws Exception {
        client.start();
        ZooKeeper zk = client.getZookeeperClient().getZooKeeper();

        System.out.println(ZKPaths.fixForNamespace(ZKPATH, "/sub"));
        System.out.println(ZKPaths.makePath(ZKPATH, "/sub"));

        System.out.println(ZKPaths.getNodeFromPath("/curator_recipes_zkpath/sub1"));

        ZKPaths.PathAndNode pn = ZKPaths.getPathAndNode("/curator_recipes_zkpath/sub1");
        System.out.println(pn.getPath() + ":" + pn.getNode());

        String dir1 = ZKPATH + "/child1";
        String dir2 = ZKPATH + "/child2";
        ZKPaths.mkdirs(zk, dir1);
        ZKPaths.mkdirs(zk, dir2);
        System.out.println(ZKPaths.getSortedChildren(zk, ZKPATH));

        ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), ZKPATH, true);
    }
}
