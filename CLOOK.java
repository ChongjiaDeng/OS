package com.company;

import java.util.Random;

import static java.lang.System.err;

/**
 * Created by Natia on 5/14/18.
 */

//Circular LOOK is like a C-SCAN which uses a return sweep before processing a set of disk requests.
// It does not reach the end of the tracks unless there is a request, either read or write on such disk
// location similar with the LOOK algorithm.

//The Class CLOOK is created to represent how CLOOK disk-scheduling algorithm works
//There is a disk with 5000 cylinders numbered from 0 to 4999 that generates 1000 requests and services them based
//on CLOOK algorithm

public class CLOOK {
    public static final Integer MAX = 4999;
    public static final Integer LIMIT = 1000;
    static Integer headPosition;
    static int headMoves=0;
    static Random rand = new Random();


    public static void main (String args []){
        int k=0, i, d=1, hm=0;
        System.out.println("CLOOK DISK SHEDULING ALGORITHM");

        //checks if command line argument exists. if not reports an issue.
        if (args.length == 0){
            err.println("Error-No head!");
        }
        else{
            headPosition = new Integer(args[0]);
            System.out.println("Head is " + headPosition);
        }

        int anarray[]=new int [LIMIT]; // declares array of 1000 requests
        for(i=0; i<LIMIT; i++){
            anarray[i]=(rand.nextInt(MAX)); // put random requests in array
            System.out.println("Requests: " + anarray[i]);
        }

        System.out.println("CLOOK Computation");

        anarray[i]=headPosition;
        for (i=0; i<LIMIT-1; i++){
            for (int j=0; j<LIMIT-1; j++){
                if (anarray[j]>anarray[j+1]){
                    int temp=anarray[j];
                    anarray[j]=anarray[j+1];
                    anarray[j+1]=temp;
                }

            }

        }

        for(i=0;i<LIMIT; i++){
            if(anarray[i]==headPosition){
                k=i;
            }
        }

        for(i=k;i<LIMIT;i++){
            headPosition=anarray[i]-anarray[i+1];
            if (headPosition<0){
                headPosition=headPosition*(-1);
            }
            System.out.println("|" + anarray[i] + "-" + anarray[i+1] + "|" + "+");
            headMoves=headMoves+headPosition;
        }

        headPosition=anarray[i]-anarray[0];
        headMoves=headMoves+headPosition;
        System.out.println("|" + anarray[i] + "-" + anarray[0] + "|");

        for (i=0; i<k-1; i++){
            headPosition=anarray[i]-anarray[i+1];
            if(headPosition<0)
            {
                headPosition=headPosition*(-1);
            }
            if (i<k-1){
                System.out.println("+");
            }
            headMoves=headMoves+headPosition;
            System.out.println("|" + anarray[i] + "-" + anarray[i+1] + "|");
        }

       System.out.println("=" + headMoves);
        System.out.print("Total Head Moveents equal to " + headMoves);

    }

}

