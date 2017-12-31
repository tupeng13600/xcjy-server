package com.xcjy.web.service;

import java.util.concurrent.atomic.AtomicInteger;

public class TestLock extends Thread {
    private static volatile Integer i = 0;
    static TestLock lock = new TestLock();

    @Override
    public void run() {
        for(int j = 0;j<100000;j++){
            synchronized (i){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new TestLock());
        Thread t2 = new Thread(new TestLock());
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.print(lock.i);
    }
}
