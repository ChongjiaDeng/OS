package com.company;

import java.util.*;

import static java.lang.System.err;
    //CISC 3320 Assignment 4
   //Natela Khomasuridze

    //The Class SCAN is created to represent how Scan disk-scheduling algorithm works
    //There is a disk with 5000 cylinders numbered from 0 to 4999 that generates 1000 requests and services them based
    //on SCAN algorithm

    //In SCAN algorithm the disk arm moves into a particular direction and services the requests coming in its path and
    //after reaching the end of disk, it reverses its direction and again services the request arriving in its path.
    //So, this algorithm works like an elevator and hence also known as elevator algorithm.
    //As a result, the requests at the midrange are serviced more and those arriving behind the disk arm will have to wait.


public class SCAN {
    public static final Integer MAX = 4999;
    public static final Integer LIMIT = 1000;

    static Set<Integer> requests;
    static TreeSet<Integer> partition1 = new TreeSet(Collections.reverseOrder());
    static TreeSet<Integer> partition2 = new TreeSet();
    static Random rand = new Random();
    static Integer headPosition;
    static int headMoves;

    //Thi method "Requests" will create 1000 random requests.
    //All request numbers will be unique. The lowest request number can be 0 and highest - 4999
    //The method accepts two parameters 'max' and 'limit' and returns set of the cylinder requests that will be serviced later.

    public static Set<Integer> Requests(int max, int limit){
        Set<Integer> s = new HashSet();
        while(s.size()!= limit){
            s.add(rand.nextInt(max));
        }
        return s;
    }

    //The method 'serviceRequests' - SCAN SCHEDULING ALGORITHM
    //includes following parameter:
    //TreeSet<Integer> set_less - set of requests that are less or equal to the head position, will be sorted in descending order.
    //TreeSet<Integer> set_more - set of requests that are greater than head position, will be sorted in ascending order.
    //Integer head - head position. command line argument.
    //This method will return total number of head movements.

    //TreeSet gives me order



    public static int serviceRequests (TreeSet<Integer> set_less, TreeSet<Integer> set_more, Integer head){
        int count = 0;
        int newHead = head;

        for(Integer i : set_less) {
            count += newHead - i;
            System.out.println("Servicing cylinder request: " + i);
            newHead = i;
        }
        count += newHead;
        count += head;
        newHead = head;

        for(Integer k : set_more){
            count += k - newHead;
            System.out.println("Servicing cylinder request: " + k);
            newHead = k;
        }

        return count;
    }

    public static void main(String[] args) {
        //checks if command line arugment exists. if not reports an issue.
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

        headMoves = serviceRequests(partition1,partition2,headPosition); //calls the serviceRequest method to process SCAN algorithm.
        System.out.println("The total number of head movements is " + headMoves); //prints out amount of head movements

    }
}
