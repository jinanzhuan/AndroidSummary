package com.example.javatest;

/**
 * 关于同步锁有如下总结：
 * 一、同步方法与同步代码块
 * 同步方法默认使用this或者当前类做为锁。
 * 同步代码块可以选择以什么来加锁，比同步方法更精确。
 * 二、关于同步代码块的一些结论
 *      1、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
 *      2、然而，当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
 *      3、尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
 * 三、Synchronized修饰静态方法和普通方法的结论
 * <pre class="prettyprint">
 * Class A {
 *     public synchronized methodA() {//对当前对象加锁
 *
 *     }
 *
 *     public  methodB() {
 *                synchronized(this){}//对当前对象加锁，与methodA用法相同
 *     }
 *
 *     public static synchronized methodC() {}//对类加锁，即对所有此类的对象加锁
 *
 *     public methodD(){
 *                 synchronized(A.class){}//对类加锁，即对所有此类的对象加锁
 *     }
 * }
 * </pre>
 * synchronize用法关键是搞清楚对谁加锁，methodA,和methodB都是对当前对象加锁，即如果有两个线程同时访问同一个对象的methoA和methodB则会发生竞争
 * ，必须等待其中一个执行完成后另一个才会执行。如果两个线程访问的是不同对象的methodA和methodB则不会竞争。
 * methodC和methodD是对类的class对象加锁，methodC和methodD的加锁对象一样，效果也一样。如果两个线程同时访问同一个对象的methodC和methodD是会发生竞争的，
 * 两个线程同时访问不同对象的methodC和methodD是也是会发生竞争的，如果两个线程同时访问methodA/B 和methodC/D则不会发生竞争，因为锁对象不同。
 */
public class SynchronizedTest {
    private int ticketNum = 20;
    private EmContact currentUser;

    public static void main(String[] args) {
        SynchronizedTest test = new SynchronizedTest();
        test.currentUser = new EmContact("som");
        for(int i = 0; i < 20; i++) {
            if(i == 10) {
                Thread thread = new Thread(new MyRunnable2(test));
                thread.start();
                continue;
            }
            Thread thread = new Thread(new MyRunnable(test, 2));
            thread.start();
        }
    }

    private synchronized String getCurrentUsername() {
//        if(currentUser == null
//                || currentUser.username == null
//                || currentUser.username.equals("")) {
//            return "not have username";
//        }
        if(currentUser == null) {
            return "not have username 1";
        }
        try {
            Thread.sleep((long) (Math.random() * 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(currentUser.username == null) {
            return "not have username 2";
        }
        try {
            Thread.sleep((long) (Math.random() * 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(currentUser.username.equals("")) {
                return "not have username 3";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "not have username 4";
        }
        try {
            Thread.sleep((long) (Math.random() * 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentUser.username;
    }

    private int printBookResult() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return --ticketNum;
    }

    private static class MyRunnable implements Runnable {
        private SynchronizedTest test;
        private long sleepTime;

        public MyRunnable(SynchronizedTest test, long sleepTime) {
            this.test = test;
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String currentUsername = this.test.getCurrentUsername();
            System.out.println("current username:"+currentUsername);
        }
    }

    private static class MyRunnable2 implements Runnable {
        private SynchronizedTest test;

        public MyRunnable2(SynchronizedTest test) {
            this.test = test;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.currentUser = null;
            System.out.println("make user is null");
        }
    }
    private static class EmContact {
        private String username;

        public EmContact(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}