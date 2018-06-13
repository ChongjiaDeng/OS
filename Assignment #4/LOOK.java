package com.company;

// CISC 3320 Assignment 4
//Natela Khomasuridze

import java.util.*;

import static java.lang.System.err;

 //The Class LOOK is created to represent how LOOK disk-scheduling algorithm works
 //There is a disk with 5000 cylinders numbered from 0 to 4999 that generates 1000 requests and services them based on LOOK algorithm

 //how LOOK works: The disk arm goes only to the last request to be serviced in front of the head and then reverses its
 //direction from there only. Thus it prevents the extra delay which occurred due to unnecessary traversal to the end of the disk.
 //scanning starts from the head, that will be passed as comm line arg in main() - String[] args
 //Also method serviceRequest reports the total number of head movements

public class LOOK {

    public static final Integer MAX = 4999;
    public static final Integer LIMIT = 1000;

    static Set<Integer> requests;
    static TreeSet<Integer> partition1 = new TreeSet(Collections.reverseOrder()); //desc order
    static TreeSet<Integer> partition2 = new TreeSet();  //asc order
    static Random rand = new Random();
    static Integer headPosition;
    static int headMoves;

    //Thi method "Requests" will create 1000 random requests.
    //All request numbers will be unique. The lowest request number can be 0 and highest - 4999
    //The method accepts two parameters 'max' and 'limit' and returns set of the cylinder requests that will be serviced later.


    public static Set<Integer> Requests(int max, int limit){
        Set <Integer> s = new HashSet();
        while(s.size()!= limit){
            s.add(rand.nextInt(max));
        }
        return s;
    }


     //The method 'serviceRequests' - LOOK SCHEDULING ALGORITHM
     //includes following parameter:
     //TreeSet<Integer> set_less - set of requests that are less or equal to the head position, will be sorted in descending order.
     //TreeSet<Integer> set_more - set of requests that are greater than head position, will be sorted in ascending order.
     //Integer head - head position. command line argument.
     //This method will return total number of head movements.

    //TreeSet gives me order

    public static int serviceRequests (TreeSet<Integer> set_less, TreeSet<Integer> set_more, Integer head){
        int count = 0;
        int newHead = head;

        for(Integer i : set_less){ //iterates through set without iterator
            count += newHead - i;
            System.out.println("Servicing cylinder request: " + i);
            newHead = i;
        }

        count += head - newHead;
        newHead = head;

        for(Integer i : set_more){ //iterates through set without iterator
            count += i - newHead;
            System.out.println("Servicing cylinder request: " + i);
            newHead = i;
        }

        return count;
    }


    public static void main(String[] args) {
        //checks if command line argument exists. if not reports an issue.
        if (args.length == 0){
            err.println("Error-No head!");
        }
        else{
            headPosition = new Integer(args[0]);
            System.out.println("Head is " + headPosition);
        }


        requests = Requests(MAX, LIMIT);   //creates a set of 1000 requests. call the method requests which returns set s of requests.
        System.out.println("requests: "); //prints out the requests that will be serviced.
        for (Integer i : requests){
            System.out.println(i + " ");
        }


        for(Integer i : requests){                          //separate requests into 2 tree sets
            if(headPosition.intValue() >= i.intValue()){   //one is less or equal to the head position.
                partition1.add(i);
            }
            else{
                partition2.add(i);                      //the other one is greater than head position.
            }
        }

        headMoves = serviceRequests(partition1,partition2,headPosition); //calls the serviceRequest method to process LOOK algorithm.
        System.out.println("The total number of head movements is " + headMoves); //prints out amount of head movements

    }
}

