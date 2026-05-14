
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;

import java.util.HashMap;

/**
 *
 * @author Ibrahim Abdsaid Hanna 
            ibrahim.seniore@gmail.com
 */

public class ConstantInjector {
   
    /**
     * constantsNameMap is a HashMap that hold constants name that added by you
     */
    public static HashMap<String, Double> constantsNameMap=new HashMap<String,Double>();;

    /**
     * constructor of ConstantInjector
     */
    public ConstantInjector() {     
    }
 
    /**
     * to put constant and it's value in HashMap
     * @param consatntName  String
     * @param constantValue double
     */
    public void add(String consatntName, double constantValue) {
        initialValidation(consatntName);
        redundancyValidation(consatntName);
        constantsNameMap.put(consatntName,constantValue);
    }
    
    /**
     * to remove constant from HashMap
     * @param consatntName String
     */
    public void remove(String consatntName) {
        constantsNameMap.remove(consatntName);
    }
    
    /**
     * to check if this constant exists in HashMap or not
     * @param constantName String 
     * @return true
     */
    public boolean checkConstantExists(String constantName){
        if(constantsNameMap.containsKey(constantName)){
            return true;
        }
        else{
           System.err.println("you want to remove constant name called "+constantName +" and "+ constantName +" not exists, please ensure that you register this constant name >>> "+constantName); 
           return false;
        }
    }
    
    /**
     * validate constantName to accept only alphabetic characters
     * if constantName not valid, so calccedo will exit from JVM, 
     * calccedo not allow constantName to contains other than alphabetic characters 
     * @param constantName String
     */

     private void initialValidation(String constantName) {
        for (int i = 0; i < constantName.length(); i++) {
            if (Character.isLetter(constantName.charAt(i))) {
                continue;
            } else {
                System.err.println("Exception: Calccedo refuse " + constantName + " as a constant \nmust contain only Letters \n\nPI is valid which mean 3.14 \n\nbut P1I not valid ");
                System.exit(0);
            }
        }
    }

    
    /**
     * validate constant before putting it into HashMap, 
     * if constantName already exists into HashMap, so calccedo will exit from JVM, 
     * calccedo Engine refuse to repeat something already exists
     * @param constantName String
     */

     private void redundancyValidation(String constantName) {
        if (constantsNameMap.containsKey(constantName)) {
            System.err.println("Exception: " + constantName + " already added!");
            System.exit(0);
        }
    }
     
     
}
