package org.example;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockBankExample {

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
        while (true) {
            boolean gotFrom = false;
            boolean gotTo = false;

            try {
                System.out.println(Thread.currentThread().getName() + " TRYING to lock " + from.getName());
                gotFrom = from.lock.tryLock();

                System.out.println(Thread.currentThread().getName() + " TRYING to lock " + to.getName());
                gotTo = to.lock.tryLock();

                if (gotFrom && gotTo) {
                    System.out.println(Thread.currentThread().getName() + " LOCKED BOTH → DOING TRANSFER");
                    from.withdraw(amount);
                    to.deposit(amount);
                    return;
                }

                System.out.println(Thread.currentThread().getName() + " FAILED to get both locks → RETRYING");

            } finally {
                if (gotFrom) {
                    System.out.println(Thread.currentThread().getName() + " UNLOCKING " + from.getName());
                    from.lock.unlock();
                }
                if (gotTo) {
                    System.out.println(Thread.currentThread().getName() + " UNLOCKING " + to.getName());
                    to.lock.unlock();
                }
            }

            sleep(50);
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