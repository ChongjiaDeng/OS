package com.company;

import java.util.HashMap;

/*
 CISC3320 Operation Systems
 Asssignment #1
 Khomasuridze, Natela
 */

/*
 dip manager manages process identifiers, which are unique.
 Several active processes can not have same pid.
 It creates unique pid, which is assigned to ti.
 When process completes execution pid is returned to pdd manager.
 pdd manager reassigns this pid.

--first method:
  creates and initializes the map in order to maintain the list of available pids.
--second method:
  finds next avialable pid and assigns them to active processes
--third method:
  releases old identifiers by making them available again for new processes.

 */

public class Main {

    /*
    Variables that will specify the range of pid values
    basically it says that process identifiers are constant
    integers (final key word) between 300 and 500.
    */

    public static final Integer MIN_PID = 300;
    public static final Integer MAX_PID = 5000;


    /*
    variables that identify availability of a particular process identifier
    with 0 being available and 1 being currently in use
    */

    public Integer available = 0;
    public Integer notAvailable = 1;


    /*
    I decided to use hash map data structure named PID_map to represent the availability of process identifiers with Key/Value principle
     */

    public HashMap <Integer, Integer> PID_map;

    /*
     int allocate_map(void) - Creates and initializes a data structure for representing pids; returns -1 if unsuccessful and 1 if successful
     This method allocates a hash map of possible pid-s.
     The map has Key/Value principle.
     Key is an Integer, Value is "available (0) /not available (1)" for allocation to an active process.
     */

    public int allocate_map(){
        //allocated map for certain capacity
        PID_map = new HashMap(MAX_PID - MIN_PID + 1);    //checks if system has enough resources to allocate a map of the capacity mentioned above
        if(true) {
            for (int i = MIN_PID; i <= MAX_PID; i++) {
                PID_map.put(i, available);                           //values for all the keys are set to 0, because non of the process will be active if I do not allocate the map first.
            }
        }
        else {
            return -1;                                            //if returns integer "-1" means hash map did not created, initialized and allocated successfully.
        }
        return 1;                                                //if returns integer "1" means hash map successfully created, initialized and allocated.
    }

    /*
    int allocate_pid(void) - Allocates and returns a pid; returns -1 if if unable to allocate a pid (all pids are in use)
     */

    public int allocate_pid(){
        for (Integer i = MIN_PID; i <= MAX_PID; i++){      //traverses through the map to find available pid
            if (PID_map.get(i).equals(available)){        //once the available process identifier is found
                PID_map.put(i,notAvailable);             //the process identifier is updated from avialeble to unavialable
                return i;                               //returns the "new unavailable pid"
            }
        }
        return -1;                                    //returs -1 if all process identifiers are in use.
    }

    /*
   	void release_pid(int_pid) - Releases a pid.
     */

    public void release_pid(Integer k){                         // method releases used process identifier which is passes as parameter-Integer K
        if(k > MAX_PID || k < MIN_PID){                        //double checks if Pid is valid
            System.out.println("Error, invalid identifier");  //if not system notifies that its invalid process identifier
        }
        PID_map.put(k,available);                            //if it is valid pid, it becomes released and the pid can be used by another process. It is set to available (0)
    }

}