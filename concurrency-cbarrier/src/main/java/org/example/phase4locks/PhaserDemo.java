package org.example.phase4locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserDemo {
    //Similar to CyclicBarrier except Phaser has a dynamic participant range which can alter during the workflow,
    //while the CyclicBarrier can only have a set point of range, Phaser can register, and deregister participants at any point
    //and introduce/release them from phases, introducing different responsibilities and etc...
    public static void main(String[] args) {

        //Starting participants = 2
        Phaser phaser = new Phaser(2);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Worker(phaser, "Worker 1", false));
        executor.submit(new Worker(phaser, "Worker 2", true));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n=== Worker 3 joins ===");

        //Add a participant from 2->3 in this example, which will join a future phase that is not yet completed.
        phaser.register();

        executor.submit(new Worker(phaser, "Worker 3", false));

        executor.shutdown();
    }
}

//Worker class for simulating deregistering after phase2
class Worker implements Runnable {

    private final Phaser phaser;
    private final String name;
    private final boolean leaveAfterPhase2;

    public Worker(Phaser phaser, String name, boolean leaveAfterPhase2) {

        this.phaser = phaser;
        this.name = name;
        this.leaveAfterPhase2 = leaveAfterPhase2;
    }

    @Override
    public void run() {

        System.out.println(name + " Phase 1");

        sleep();

        System.out.println(name + " waiting at phase 1 barrier");

        //Worker finishes a phase1 and wait for other workers
        phaser.arriveAndAwaitAdvance();

        System.out.println(name + " Phase 2");

        sleep();

        if (leaveAfterPhase2) {
            System.out.println(name + " leaving permanently");

            //Worker finishes a phase and leaves permanently informing others not to wait for him for future phases.
            //For example there were 3 participants now there is going to be 2.
            phaser.arriveAndDeregister();

            return;
        }

        System.out.println(name + " waiting at phase 2 barrier");

        //Worker finishes a phase2 and wait for other workers
        phaser.arriveAndAwaitAdvance();

        System.out.println(name + " Phase 3");

        sleep();

        //Worker finishes a phase3 thus completing all phases and leaving permanently
        phaser.arriveAndDeregister();

        System.out.println(name + " finished completely");
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
