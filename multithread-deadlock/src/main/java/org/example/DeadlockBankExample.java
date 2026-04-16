package org.example;
import lombok.Getter;

import java.util.concurrent.locks.ReentrantLock;

public class DeadlockBankExample {

    static class Account {
        @Getter
        private final String name;
        private int balance;
        private final ReentrantLock lock = new ReentrantLock();

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public void deposit(int amount) { balance += amount; }
        public void withdraw(int amount) { balance -= amount; }
    }

    public static void transfer(Account from, Account to, int amount) {
        System.out.println(Thread.currentThread().getName() + " TRYING to lock " + from.getName());
        from.lock.lock();
        System.out.println(Thread.currentThread().getName() + " LOCKED " + from.getName());

        try {
            sleep(100);

            System.out.println(Thread.currentThread().getName() + " TRYING to lock " + to.getName());
            to.lock.lock(); // DEADLOCK HAPPENS HERE
            System.out.println(Thread.currentThread().getName() + " LOCKED " + to.getName());

            try {
                System.out.println(Thread.currentThread().getName() + " DOING TRANSFER");
                from.withdraw(amount);
                to.deposit(amount);
            } finally {
                System.out.println(Thread.currentThread().getName() + " UNLOCKING " + to.getName());
                to.lock.unlock();
            }

        } finally {
            System.out.println(Thread.currentThread().getName() + " UNLOCKING " + from.getName());
            from.lock.unlock();
        }
    }

    public static void main(String[] args) {
        Account a = new Account("Account-A", 1000);
        Account b = new Account("Account-B", 1000);

        new Thread(() -> transfer(a, b, 100), "T1").start();
        new Thread(() -> transfer(b, a, 200), "T2").start();
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}