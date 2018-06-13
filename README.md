# OS

# Assignment#1
Documentation: <Br>
The goal of the assignment is to imitate the actual work of process identifiers and threads. For the second assignment we need to create multiple threads that represent processes to test PID Management API. Each thread requests an identifier. If all identifiers are used by other threads, it should wait for another one that becomes available. Once the thread gets the pid, it sleeps for some time which is randomly generated and during that time thread compeletes its task. After the thread wakes up it releases the pid to be used by another thread. The thread pool is primarily used to reduce the number of application threads and provide management of the worker threads.
