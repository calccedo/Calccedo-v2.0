
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */
package com.calccedo.handlers;

import java.util.HashMap;

/**
 *
 * @author Ibrahim Abdsaid Hanna ibrahim.seniore@gmail.com
 */
public class SemanticInjector {

    /**
     *  semanticsNameMap is a HashMap of to hold semantics name that added by you 
     */
    public static HashMap<String, Object> semanticsNameMap = new HashMap<String, Object>();

    /**
     * constructor of SemanticInjector
     */
    public SemanticInjector() {
       
    }

    /**
     * to add semantic with it's return value into HashMap
     * @param semanticName String
     * @param quoteNumber int
     * @param value double 
     */
    public void add(String semanticName, int quoteNumber, double value) {
        initialValidation(semanticName);
        redundancyValidation(semanticName, quoteNumber);
        semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, value));
    }

    /**
     * to add semantic with it's return value into HashMap
     * @param semanticName String
     * @param quoteNumber double
     * @param value double
     */
    public void add(String semanticName, double quoteNumber, double value) {
        initialValidation(semanticName);
        redundancyValidation(semanticName, quoteNumber);
        semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, value));
    }

    /**
     * to add semantic as an Infinity semantic or Error semantic into HashMap
     * @param semanticName String
     * @param quoteNumber int
     * @param infinityOrError Object
     */
    public void add(String semanticName, int quoteNumber, Object infinityOrError) {
        initialValidation(semanticName);
        redundancyValidation(semanticName, quoteNumber);
        semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
    }

    /**
     * to add semantic as an Infinity semantic or Error semantic into HashMap
     * @param semanticName String
     * @param quoteNumber double
     * @param infinityOrError Object
     */
    public void add(String semanticName, double quoteNumber, Object infinityOrError) {
        initialValidation(semanticName);
        redundancyValidation(semanticName, quoteNumber);
        semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
    }

    /**
     * to return semanticsNameMap
     * @return HashMap
     */
    public HashMap<String, Object> getsemanticNameMap() {
        return semanticsNameMap;
    }

    /**
     * to remove added semantic from HashMap
     * @param semanticName String
     * @param quoteNumber int
     */
    public void remove(String semanticName, int quoteNumber) {
        semanticsNameMap.remove(semanticName + quoteNumber);
    }

    /**
     * to remove added semantic from HashMap
     * @param semanticName String
     * @param quoteNumber double
     */
    public void remove(String semanticName, double quoteNumber) {
        semanticsNameMap.remove(semanticName + quoteNumber);
    }

    /**
     * to update semantic with new value
     * @param semanticName String
     * @param quoteNumber int
     * @param new_value double
     */
    public void updateSemanticTo(String semanticName, int quoteNumber, double new_value) {
        semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, new_value));
    }

    /**
     * to update semantic with new value
     * @param semanticName String
     * @param quoteNumber doube
     * @param new_value double
     */
    public void updateSemanticTo(String semanticName, double quoteNumber, double new_value) {
        semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, new_value));
    }

    /**
     * to update semantic to be Infinity | Error
     * @param semanticName String
     * @param quoteNumber int
     * @param infinityOrError Object
     */
    public void updateSemanticTo(String semanticName, int quoteNumber, Object infinityOrError) {
        semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
    }

    /**
     * to update semantic to be Infinity | Error
     * @param semanticName String
     * @param quoteNumber double
     * @param infinityOrError Object
     */
    public void updateSemanticTo(String semanticName, double quoteNumber, Object infinityOrError) {
        semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
    }

    /**
     * to check if this semantic already exists into HashMap or not
     * @param semanticName String
     * @param quoteNumber Number
     * @return true
     */
    public boolean checkSemanticExists(String semanticName, Number quoteNumber) {
        if (semanticsNameMap.containsKey(semanticName + quoteNumber)) {
            return true;
        } else {
            System.err.println("you want to remove semantic called " + semanticName + " and " + semanticName + " not exists, please ensure that you register this semantic name >>> " + semanticName);
            return false;
        }
    }
     

    /**
     * validate semanticName to accept only alphabetic characters
     * if semanticName not valid, so calccedo will exit from JVM,  
     * calccedo not allow semanticName to contains other than alphabetic characters 
     * @param semanticName String
     */

    private void initialValidation(String semanticName) {
        for (int i = 0; i < semanticName.length(); i++) {
            if (Character.isLetter(semanticName.charAt(i))) {
                continue;
            } else {
                System.err.println("Exception: Calccedo refuse " + semanticName + "() as a semantic \nmust contain only Letters \n\nSin(30) valid \n\nbut Si2n(30) not valid ");
                System.exit(0);
            }
        }
    }



    /**
     * if semanticName already exists into HashMap, so calccedo will exit from JVM,  
     * calccedo Engine refuse to repeat something already exists
     * @param semanticName String
     * @param quoteNumber int
     */

     private void redundancyValidation(String semanticName, int quoteNumber) {
        if (semanticsNameMap.containsKey(semanticName + quoteNumber)) {
            System.err.println("Exception: " + semanticName + "(" + quoteNumber + ")" + " already added!");
            System.exit(0);
        }
    }

    /**
     * if semanticName already exists into HashMap, so calccedo will exit from JVM,  
     * calccedo Engine refuse to repeat something already exists
     * @param semanticName String
     * @param quoteNumber double
     */
    private  void redundancyValidation(String semanticName, double quoteNumber) {
        if (semanticsNameMap.containsKey(semanticName + quoteNumber)) {
            System.err.println("Exception: " + semanticName + "(" + quoteNumber + ")" + " already added!");
            System.exit(0);
        }
    }

}
