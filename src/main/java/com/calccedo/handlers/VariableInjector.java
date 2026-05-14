
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;

import java.util.HashMap;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
public class VariableInjector {

    /**
     * variableNameMap is a HashMap to hold all variables name
     */
    public static HashMap<String, Double> variableNameMap=new HashMap<String,Double>();;

    /**
     * constructor of VariableInjector
     */
    public VariableInjector() {     
    }
 
    /**
     *  add variable with it's value into HashMap
     * @param variableName String
     * @param variableValue double
     */
    public void add(String variableName,double variableValue) {
        initialValidation(variableName);
        redundancyValidation(variableName);
        variableNameMap.put(variableName,variableValue);
    }
    
    /**
     * to remove variable from HashMap
     * @param variableName String
     */
    public void remove(String variableName) {
        variableNameMap.remove(variableName);
    }
    
    /**
     * to check this variable already exists into HashMap or not
     * @param variableName String
     * @return true
     */
    public boolean checkVariableExists(String variableName){
        if(variableNameMap.containsKey(variableName)){
          return true;   
        }
         else{
           System.err.println("you want to remove variable called "+variableName +" and "+ variableName +" not exists, please ensure that you register this variable name >>> "+variableName);
           return false;
       }
    }
    
    /**
     * to update added variable with new value
     * @param variableName String
     * @param newValue double
     */
    public void updateVariableTo(String variableName, double newValue){
         variableNameMap.put(variableName, newValue);
     }
    


    /**
     * validate variableName to accept only alphabetic characters
     * if variabeleName not valid, so calccedo will exit from JVM,  
     * calccedo not allow variableName to contains other than alphabetic characters 
     * @param variableName String
     */

    private void initialValidation(String variableName) {
        for (int i = 0; i < variableName.length(); i++) {
            if (Character.isLetter(variableName.charAt(i))) {
                continue;
            } else {
                System.err.println("Exception: Calccedo refuse " + variableName + " as a constant \nmust contain only Letters \n\nPI is valid which mean 3.14 \n\nbut P1I not valid ");
                System.exit(0);
            }
        }
    }



    /**
     * if variableName already exists into HashMap, so calccedo will exit from JVM,  
     * calccedo Engine refuse to repeat something already exists
     * @param variableName String
     */

     private void redundancyValidation(String variableName) {
        if (variableNameMap.containsKey(variableName)) {
            System.err.println("Exception: " + variableName + " already added!");
            System.exit(0);
        }
    }
     
     
}


