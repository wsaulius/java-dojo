package org.example;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptibleBankExample {

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
        try {
            System.out.println(Thread.currentThread().getName() + " TRYING to lock " + from.getName());
            from.lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " LOCKED " + from.getName());

            try {
                sleep(100);

                System.out.println(Thread.currentThread().getName() + " TRYING to lock " + to.getName());
                to.lock.lockInterruptibly(); // can be interrupted
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

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " INTERRUPTED while waiting → ESCAPING DEADLOCK");
        }
    }

    public static void main(String[] args) throws Exception {
        Account a = new Account("Account-A", 1000);
        Account b = new Account("Account-B", 1000);

        Thread t1 = new Thread(() -> transfer(a, b, 100), "T1");
        Thread t2 = new Thread(() -> transfer(b, a, 200), "T2");

        t1.start();
        t2.start();

        Thread.sleep(500);
        System.out.println("MAIN: Interrupting T2 to resolve deadlock...");
        t2.interrupt();
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}