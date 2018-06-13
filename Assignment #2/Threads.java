package com.company;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.Thread.currentThread;

/*
 CISC3320 Operation Systems
 Asssignment #2
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
        System.out.println("Running Thread-" + currentThread().getName()); //show running thread
        new_pid = pids.allocate_pid();                                    //assigned our pid object from the Main class to the new_pid variable

        while (new_pid == -1) {                                         // to make sure that each process created gets its own PID
            System.out.println("All PIDs are in use");
            new_pid = pids.allocate_pid();
        }


        currentThread().setName(new_pid.toString());                       //PID  was assigned to the thread
        System.out.println("PID-" + new_pid + " was successfully allocated");


        try {
            Thread.sleep(threadSleepTime);                                 //Thread sleeps for a while - time is random

        } catch (InterruptedException e) {
            System.out.println("Thread " + currentThread().getName() + " interrupted.");
        }

        Integer pid_to_release = Integer.valueOf(currentThread().getName()); //current thread is assigned to the new variable pid_to_release which will be released later
        System.out.println("pid " + pid_to_release);

        pids.release_pid(pid_to_release);                                   //pids object called the release_pid method from the Main class to get released

        System.out.println("PID-" + pid_to_release + " was successfully released");
        System.out.println("Thread " + currentThread().getName() + " exiting."); //prints out the existing current thread
    }


    /*
      The thread pool is primarily used to reduce the number of application threads
      and provide management of the worker threads.
     */

    public static class ThreadPoolTest {

        public static void main(String args[]) {


            PID_MAP pids = new PID_MAP();

            //double checks if map is allocated and initialized

            if (pids.allocate_map() == 1) {
                System.out.println("Successfully allocated and initialized a map of PIDs");
            } else {
                System.out.println("Failed to allocate and initialize a map of PIDs");
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

}

