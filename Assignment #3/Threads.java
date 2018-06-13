package com.company;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

/*
 CISC3320 Operation Systems
 Asssignment #3
 Khomasuridze, Natia
*/

/*
A Runnable is basically a type of class (Runnable is an Interface, thats why we use key word implements)
that can be put into a thread, describing what the thread is supposed to do.
If you did not have the Runnable interface, the Thread class, which is responsible to execute your stuff in the other thread,
would not have the promise to find a run() method in your class. That is why you need to implement the interface.
*/

/*
in the run method ()
request a process identifier,
sleeps for some time (randomly time)
release the pid to be re-used by other threads
*/

/*
Each thread requests an identifier.
If all identifiers are used by other threads, it should wait for another one that becomes available.
Once the thread gets the pid, it sleeps for some time which is randomly generated and during that time thread compeletes
its task. After the thread wakes up it releases the pid to be used by another thread.
*/

/*
In computer science, a lock or mutex (from mutual exclusion) is a synchronization mechanism for enforcing
limits on access to a resource in an environment where there are many threads of execution. A lock is designed
to enforce a mutual exclusion concurrency control policy.
*/

/*
There can be only one lock on a mutex at any given time. If another thread wishes to gain control,
it must wait for the first to unlock it. This mutual exclusion is the primary goal of the mutex,
and indeed the origin of the name. Attempting to lock an already locked mutex is called contention.
 */

public class Threads implements Runnable {

    public int threadID;
    private int threadSleepTime;
    private PID_MAP pids;

    Threads(int threadID, int threadSleepTime, PID_MAP pids) {
        this.threadID = threadID;
        this.threadSleepTime = threadSleepTime;
        this.pids = pids;
        System.out.println("Creating Thread- " + threadID);
    }

    @Override
    public void run() {

        Integer new_pid;
        System.out.println("Running Thread is " + currentThread().getName()); //show running thread

        //created two reentrant lock objects
        Lock lock_1 = new ReentrantLock();
        Lock lock_2 = new ReentrantLock();

        // we need lock to ensure that only one thread executes allocate_pid() function at a time.
        // It is surrounded with critical section with "try" and "finally" block just
        // to make sure the lock is released in cases any exceptions occur in the map.


        lock_1.lock();
        try {
            new_pid = pids.allocate_pid(); //assigned our pid object from the Main class to the new_pid variable

            while (new_pid == -1) {   // to make sure that each process created gets its own PID
                System.out.println("All PIDs are used");
                new_pid = pids.allocate_pid();
            }
        } finally {
            lock_1.unlock(); //lock_1 becomes unlocked
        }

        currentThread().setName(new_pid.toString()); //PID  was assigned to the thread
        System.out.println("PID " + new_pid + " is successfully allocated and is ready to use");


        try {

            Thread.sleep(threadSleepTime); //Thread sleeps for a while - time is random

        } catch (InterruptedException e) {
            System.out.println("Thread " + currentThread().getName() + " is interrupted.");
        }

        Integer pid_to_release = Integer.valueOf(currentThread().getName()); //current thread is assigned to the new variable pid_to_release which will be released later
        System.out.println("pid " + pid_to_release);

        //It will be the same for the lock_2
        // We need lock to ensure that only one thread executes allocate_pid() function at a time.
        // It is surrounded with critical section with "try" and "finally" block just
        // to make sure the lock is released in cases any exceptions occur in the map.


        lock_2.lock();
        try {
            pids.release_pid(pid_to_release); //pids object called the release_pid method from the Main class to get released
        } finally {
            lock_2.unlock(); //lock_2 becomes unlocked
        }

        System.out.println("PID " + pid_to_release + " is released");
        System.out.println("Thread " + currentThread().getName() + " already exists"); //prints out the existing current thread
    }

}


     /*
      The thread pool is primarily used to reduce the number of application threads
      and provide management of the worker threads.
     */

    class ThreadPoolTest { // removed static and public after second hw

        public void main(String args[]) { //removed public after second hw


            PID_MAP pids = new PID_MAP();

            //double checks if map is allocated and initialized

            if (pids.allocate_map() == 1) {
                System.out.println("Map of PIDs were successfully allocated");
            } else {
                System.out.println("Map of PIDs were not successfully allocated - FAIL");
            }

            //Creating 2 pools with 50 threads for each,  100 in total.

            //ExecutorService that creates a thread pool of fixed number of threads.
            //newFixedThreadPool() method is used where we specify the number of threads in the pool.
            // To execute the thread, we can use either execute() method or submit(), where both of them take Runnable as a parameter.


            ExecutorService pool1 = Executors.newFixedThreadPool(50);
            for (int i = 1; i < 51; i++) {
                Threads task1 = new Threads(i, (int) (Math.random() * 50 + 1), pids);
                // execute() is a method provided my the thread class, takes one argument.
                // We use it to add a new Runnable object to the work queue.
                pool1.execute(task1);
            }

            ExecutorService pool2 = Executors.newFixedThreadPool(50);
            for (int i = 1; i < 51; i++) {
                Threads task2 = new Threads(i, (int) (Math.random() * 50 + 1), pids);
                pool2.execute(task2);
            }

            //To close down the ExecutorService we use shutdown() method,
            // in which the submitted tasks are executed before
            // the shutting down but new tasks can not be accepted.

            pool1.shutdown();
            pool2.shutdown();


            while (!pool1.isTerminated()) {
            }
            System.out.println("Finished all threads in Pool-1");

            while (!pool2.isTerminated()) {
            }
            System.out.println("Finished all threads in Pool-2");
        }

    }



