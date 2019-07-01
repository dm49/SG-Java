/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.ui;

import java.util.Scanner;

/**
 *
 * @author mrder
 */
public class UserIOConsoleImpl implements UserIO {
    Scanner sc = new Scanner(System.in);
    private double readDouble;
    private float readFloat;
    private int readInt;
    private long readLong;
    private String readString;
    private String message;

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        String userReadDouble = sc.nextLine();
        readDouble = Integer.parseInt(userReadDouble);
        return readDouble;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        do {
            System.out.println(prompt + min + " and " + max + " .");
            String userReadDouble = sc.nextLine();
            readDouble = Integer.parseInt(userReadDouble);
        if (readDouble < min || readDouble > max) {
            System.out.println(prompt + min + " and " + max + " .");
        } 
        }while (readDouble < min || readDouble > max); 
        return readDouble;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        String userReadFloat = sc.nextLine();
        readFloat = Integer.parseInt(userReadFloat);
        return readFloat;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        do {
            System.out.println(prompt + min + " and " + max + " .");
            String userReadFloat = sc.nextLine();
            readFloat = Integer.parseInt(userReadFloat);
        if (readFloat < min || readFloat > max) {
            System.out.println(prompt + min + " and " + max + " .");
        } 
        }while (readFloat < min || readFloat > max); 
        return readFloat;
    }

    @Override
    public int readInt(String prompt) {
        readInt = sc.nextInt();
        return readInt;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        String userReadInt = sc.nextLine();
        readInt = Integer.parseInt(userReadInt);
        return readInt;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        String userReadLong = sc.nextLine();
        readLong = Integer.parseInt(userReadLong);
        return readLong;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        do {
            System.out.println(prompt + min + " and " + max + " .");
            String userReadLong = sc.nextLine();
            readLong = Integer.parseInt(userReadLong);
        if (readLong < min || readLong > max) {
            System.out.println(prompt + min + " and " + max + " .");
        } 
        }while (readLong < min || readLong > max); 
        return readLong;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        readString = sc.nextLine();
        return readString;
    }
}
