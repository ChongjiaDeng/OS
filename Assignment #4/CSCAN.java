package com.company;

import java.util.Random;

import static java.lang.System.err;

/**
 * Created by Natia on 5/14/18.
 */

//The Class CSCAN is created to represent how CSCAN disk-scheduling algorithm works
//There is a disk with 5000 cylinders numbered from 0 to 4999 that generates 1000 requests and services them based
//on CSAN algorithm

//This algorithm is a modified version of the SCAN algorithm. C-SCAN sweeps the disk from end-to-end,
//but as soon it reaches one of the end tracks it then moves to the other end track without servicing
//any requesting location. As soon as it reaches the other end track it then starts servicing and grants
// requests headed to its direction. This algorithm improves the unfair situation of the end tracks against
// the middle tracks

public class CSCAN {

    public static final Integer MAX = 4999;
    public static final Integer LIMIT = 1000;
    static Integer headPosition;
    static int headMoves=0;
    static Random rand = new Random();

    public static void main (String args []){

        System.out.println("CLOOK DISK SHEDULING ALGORITHM");

        //checks if command line argument exists. if not reports an issue.
        if (args.length == 0){
            err.println("Error-No head!");
        }
        else{
            headPosition = new Integer(args[0]);
            System.out.println("Head is " + headPosition);
        }

        int i, temp;
        int anarray[]=new int [LIMIT]; // declares array of 1000 requests
        anarray[0] = 0;
        for(i=1; i<LIMIT; i++){
            anarray[i]=(rand.nextInt(MAX)); // put random requests in array
            System.out.println("Requests: " + anarray[i]);
        }

        System.out.println("CLOOK Computation");
        anarray [i] = 199;
        i++;
        anarray[i]=headPosition;
        for (i=0;i<LIMIT+2; i++){
            for (int j=0;j<LIMIT+2-i; j++){
                if(anarray[j]>anarray[j+1]){
                    temp=anarray[j];
                    anarray[j]=anarray[j+1];
                    anarray[j+1]=temp;
                }

            }

        }
        for (i=0;i<LIMIT+2; i++){
            headPosition=anarray[i]-anarray[i+1];
            if (headPosition<0){
                headPosition=headPosition*(-1);
            }
            headMoves=headMoves+headPosition;
            if (anarray[i]==headPosition){
                temp=anarray[i]-anarray[i-1];
                headMoves=headMoves-temp;
            }
        }

        headMoves=headMoves+anarray[i];
        System.out.println("Total Head Movements " + headMoves);

    }

}
