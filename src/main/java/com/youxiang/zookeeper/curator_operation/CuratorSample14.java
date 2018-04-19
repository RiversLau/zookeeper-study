package com.youxiang.zookeeper.curator_operation;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Rivers
 * @date: 2018/4/19
 */
public class CuratorSample14 {

    static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.execute(new Thread(new Runner("No.0001")));
        pool.execute(new Thread(new Runner("No.0002")));
        pool.execute(new Thread(new Runner("No.0003")));
        pool.shutdown();
    }

    static class Runner implements Runnable {

        private String name;
        public Runner(String name) {
            this.name = name;
        }

        public void run() {
            System.out.println(this.name + " is ready!");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(this.name + " fly");
        }
    }
}
