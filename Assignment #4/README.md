# OS

# Assignment#1
### Task:
An operating system’s pid manager is responsible for managing process identifiers. When a process is first created, ti is assigned a unique pid by the pid manager. The pid is returned to the pdd manager when the process completes execution, and the manager may later reassign this pid. Process identifiers must be unique; no two active processes may have the same pid. 
Use the following constants to identify the range of possible pid values:
#define MIN_PID 300
#define MAX_PID 5000
You may use any data structure of your choice to represent the availability of process identifiers. One strategy is to adopt what Linux has done and use a bitmap in which a value of 0 at position i  indicates that a process id of value i is available and a value of 1 indicates that the process id is currently in use.
Implement the following API for obtaining and releasing a pid:
<br> •	int allocate_map(void) – Creates and initializes a data structure for representing pids; returns -1 if unsuccessful and 1 if successful
<br> •	int allocate_pid(void) – Allocates and returns a pid; returns -1 if if unable to allocate a pid (all pids are in use)
<br> •	void release_pid(int_pid) – Releases a pid.
NOTE: This programming problem will be modified later in Chapters 4 and 5. 

### Documentation:
 Dip manager manages process identifiers, which are unique.
 Several active processes can not have same pid.
 It creates unique pid, which is assigned to ti.
 When process completes execution pid is returned to pdd manager.
 pdd manager reassigns this pid.

<br> -- first method:
  creates and initializes the map in order to maintain the list of available pids.
<br> -- second method:
  finds next avialable pid and assigns them to active processes
<br> -- third method:
  releases old identifiers by making them available again for new processes.



# Assignment#2
### Task:
Modify HW#1.
Write a multithreaded program that tests your solution to HW#1. You will create several threads – for example, 100 – and each thread will request a pid, sleep for a random period of time, and then release the pid. (Sleeping for a random period approximates the typical pid usage in which a pid is assigned to a new process, the process executes and terminates, and the pid is released on the process’ termination).On UNIX and Linux systems, sleeping is accomplished through the sleep() function, which is passed an integer value representing the number of seconds to sleep. 

### Documentation:
The goal of the assignment is to imitate the actual work of process identifiers and threads. For the second assignment we need to create multiple threads that represent processes to test PID Management API. Each thread requests an identifier. If all identifiers are used by other threads, it should wait for another one that becomes available. Once the thread gets the pid, it sleeps for some time which is randomly generated and during that time thread compeletes its task. After the thread wakes up it releases the pid to be used by another thread. The thread pool is primarily used to reduce the number of application threads and provide management of the worker threads.

# Assignment#3
### Task:
Programming Exercise 3.20 required you to design a PID manager that allocated a unique process identifier to each process. Exercise 4.20 required you to modify your solution to Exercise 3.20 by writing a program that created a number of threads that requested and released process identifiers. Now modify your solution to Exercise 4.20 by ensuring that the data structure used to represent the availability of process identifiers is safe from race conditions. Use Pthreads mutex locks.
Please note. If you used mutex locks in your solution to HW #2, please resubmit with this revised problem description to get credit for HW#3. 
### Documentation:
LOCKS: A lock is a synchronization mechanism for enforcing limits to access resource in an environment where there are many threads of execution. A lock enforces a mutual exclusion concurrency control policy.
How It Works: There can be only one lock on a mutex at any given time. If another thread wants to control, it must wait for the first one to unlock it. The main goal of mutex is exclusion.
In order to protect the data in my PID MAP, I want to make sure that while Im working on a variable no other thread touches it. So I declare variable and it will be surrounded by lock and unlock.
for example: 

void function{
lock a
manipulate a
unlock a
}

* So “a” is locked and no other thread can touch it while I’m manipulating it.

# Assignment#4
* SCAN
This algorithm is performed by moving the R/W head back-and-forth to the innermost and outermost track. As it scans the tracks from end to end, it process all the requests found in the direction it is headed. This will ensure that all track requests, whether in the outermost, middle or innermost location, will be traversed by the access arm thereby finding all the requests. This is also known as the Elevator algorithm. 

* LOOK
This algorithm is similar to SCAN algorithm except for the end-to-end reach of each sweep. The R/W head is only tasked to go the farthest location in need of servicing. This is also a directional algorithm, as soon as it is done with the last request in one direction it then sweeps in the other direction.

* FCFS
It is the simplest form of disk scheduling algorithms. The I/O requests are served or processes according to their arrival. The request arrives first will be accessed and served first. Since it follows the order of arrival, it causes the wild swings from the innermost to the outermost tracks of the disk and vice versa . The farther the location of the request being serviced by the read/write head from its current location, the higher the seek time will be.

* CLOOK
Circular LOOK is like a C-SCAN which uses a return sweep before processing a set of disk requests. It does not reach the end of the tracks unless there is a request, either read or write on such disk location similar with the LOOK algorithm.

* CSCAN
This algorithm is a modified version of the SCAN algorithm. C-SCAN sweeps the disk from end-to-end,but as soon it reaches one of the end tracks it then moves to the other end track without servicing any requesting location. As soon as it reaches the other end track it then starts servicing and grants requests headed to its direction. This algorithm improves the unfair situation of the end tracks against the middle tracks
