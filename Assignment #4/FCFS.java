package com.company;
import java.util.*;
import static java.lang.System.err;


//CISC 3320 Assignment 4
//Natela Khomasuridze

//The Class FCFS is created to represent how FCFS disk-scheduling algorithm works
//There is a disk with 5000 cylinders numbered from 0 to 4999 that generates 1000 requests and services them based
//on FSCS algorithm
//FCFS is the simplest of all the Disk Scheduling Algorithms.
//In FCFS, the requests are addressed in the order they arrive in the disk queue.


public class FCFS {
    public static final Integer MAX = 4999;
    public static final Integer LIMIT = 1000;
    static Integer headPosition;
    static int headMoves=0;
    static Random rand = new Random();


    public static void main (String args []){

        //checks if command line argument exists. if not reports an issue.
        if (args.length == 0){
            err.println("Error-No head!");
        }
        else{
            headPosition = new Integer(args[0]);
            System.out.println("Head is " + headPosition);
        }


        int []requests =new int [LIMIT]; //declares array of 1000 requests

        for(int i=0; i<LIMIT; i++){
            requests[i]=(rand.nextInt(MAX)); // put random requests in array
            System.out.println("Requests: " + requests[i]);
        }

        // The I/O requests are served or processes according to their arrival.
        // The request arrives first will be accessed and served first

        for(int i=0; i<LIMIT; i++){
            requests[i]=requests[i]-requests[i++];
            if (requests[i]<0){
                requests[i]=requests[i]*-1;
            }
        }

        //counts and prints out headmovements
        for(int i=0; i<LIMIT; i++){
            headMoves=headMoves+requests[i];
        }

        int r=requests[0]-headPosition;
        if (r<0){
            r=r*-1;
        }

        headMoves=headMoves+r;
        System.out.println("Servicing cylinder request: " + headMoves);

    }

}


