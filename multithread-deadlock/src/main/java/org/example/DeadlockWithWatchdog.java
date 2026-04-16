package org.example;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.management.*;
import java.util.*;

public class DeadlockWithWatchdog {

    static Map<Long, Thread> threadMap = new HashMap<>();

    static class Account {
        private final String name;
        private int balance;
        private final ReentrantLock lock = new ReentrantLock();

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public String getName() { return name; }
        public void deposit(int amount) { balance += amount; }
        public void withdraw(int amount) { balance -= amount; }
    }

    public static void transfer(Account from, Account to, int amount) {
        boolean fromLocked = false;
        boolean toLocked = false;
        try {
            System.out.println(Thread.currentThread().getName() + " TRYING to lock " + from.getName());
            from.lock.lockInterruptibly();
            fromLocked = true;
            System.out.println(Thread.currentThread().getName() + " LOCKED " + from.getName());

            sleep(100);

            System.out.println(Thread.currentThread().getName() + " TRYING to lock " + to.getName());
            to.lock.lockInterruptibly();
            toLocked = true;
            System.out.println(Thread.currentThread().getName() + " LOCKED " + to.getName());
            from.withdraw(amount);
            to.deposit(amount);

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " INTERRUPTED → releasing locks and exiting");
        } finally {
            if (toLocked) {
                to.lock.unlock();
            }
            if (fromLocked) {
                from.lock.unlock();
            }
        }
    }

    // Watchdog that resolves deadlock after delay
    public static void startWatchdog(long delayMs) {
        Thread watchdog = new Thread(() -> {
            sleep(delayMs);

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            long[] deadlocked = bean.findDeadlockedThreads();

            if (deadlocked != null) {
                System.out.println("\nDEADLOCK DETECTED → resolving...");

                ThreadInfo[] infos = bean.getThreadInfo(deadlocked);

                for (ThreadInfo info : infos) {
                    System.out.println("Thread: " + info.getThreadName());
                    System.out.println("  Waiting on: " + info.getLockName());
                    System.out.println("  Owned by: " + info.getLockOwnerName());
                }

                // Interrupt ONE thread to break the cycle
                long victimId = deadlocked[0];
                Thread victim = threadMap.get(victimId);

                if (victim != null) {
                    System.out.println("\nWatchdog interrupting: " + victim.getName());
                    victim.interrupt();
                }
            } else {
                System.out.println("No deadlock detected.");
            }
        });

        watchdog.setDaemon(true);
        watchdog.start();
    }

    public static void main(String[] args) {

        Account a = new Account("Account-A", 1000);
        Account b = new Account("Account-B", 1000);

        Thread t1 = new Thread(() -> transfer(a, b, 100), "T1");
        Thread t2 = new Thread(() -> transfer(b, a, 200), "T2");

        t1.start();
        t2.start();

        // 🧠 register threads so watchdog can access them
        threadMap.put(t1.getId(), t1);
        threadMap.put(t2.getId(), t2);

        startWatchdog(6000); // ⏱️ change to 60000 for 1 minute
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}