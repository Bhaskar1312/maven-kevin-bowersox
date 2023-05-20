package com.company.maven;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide a numeric string");
        if(isNumeric(scanner.nextLine())) {
            System.out.println("The provided string is numeric");
        } else {
            System.out.println("The provided string is invalid");
        }
        scanner.close();
    }

    private static boolean isNumeric(String s) {
        // return false;
        return StringUtils.isNumeric(s);
    }
}
