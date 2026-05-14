
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */
package com.calccedo.handlers;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 *
 * @author Ibrahim Abdsaid Hanna ibrahim.seniore@gmail.com
 */
public class FunctionInjector {

    /**
     * injectedFunctionsMap is a HashMap to hold all functions name and it's function reference that injected by you
     */
    public static HashMap<String, Function> injectedFunctionsMap = new HashMap<String, Function>();

    /**
     * constructor of FunctionInjector
     */
    public FunctionInjector() {

    }

    /**
     * to put method name with it's operationalClass into HashMap
     * @param operationalClass Class
     * @param methodName String
     */
    public void add(Class operationalClass, String methodName) {
        initialValidation(methodName);
        redundancyValidation(methodName, operationalClass);
        restrictionValidation(operationalClass, methodName);
        injectedFunctionsMap.put(methodName, new Function(operationalClass, methodName));
    }

    /** 
     * to put function name with it's operationalClass into HashMap
     * @param functionName String
     * @param operationalClass Class
     * @param methodName String
     */
    public void add(String functionName, Class operationalClass, String methodName) {
        initialValidation(functionName);
        redundancyValidation(functionName, operationalClass);
        restrictionValidation(operationalClass, methodName);
        injectedFunctionsMap.put(functionName, new Function(operationalClass, methodName));
    }

    /**
     * to remove injected function from HashMap
     * @param functionName String
     */
    public void remove(String functionName) {
        injectedFunctionsMap.remove(functionName);
    }

    /**
     * to replace injected function with another function 
     * @param functionName String
     * @param operationalClass Class
     * @param methodName String
     */
    public void updateFunctionTo(String functionName, Class operationalClass, String methodName) {
        injectedFunctionsMap.put(functionName, new Function(operationalClass, methodName));
    }

    /**
     * to check if this function already exists in HashMap
     * @param functionName String
     * @return boolean
     */
    public boolean checkFunctionExists(String functionName) {
        if (injectedFunctionsMap.containsKey(functionName)) {
            return true;
        } else {
            System.err.println("you want to remove function called " + functionName + " and " + functionName + " not exists, please ensure that you register this function name >>> " + functionName);
            return false;
        }
    }

    

    /**
     * validate functionName to accept only alphabetic characters
     * if functionName not valid, so calccedo will exit from JVM, 
     * calccedo not allow functionName to contains other than alphabetic characters 
     * @param functionName String
     */

     private void initialValidation(String functionName) {
        for (int i = 0; i < functionName.length(); i++) {
            if (Character.isLetter(functionName.charAt(i))) {
                continue;
            } else {
                System.err.println("Exception: Calccedo refuse injected function " + functionName + "() \nmust contain only Letters \n\nAVG() valid  \n\nbut AV2G() not valid ");
                System.exit(0);
            }
        }
    }

   
    /**
     * if functionName already exists into HashMap, so calccedo will exit from JVM,  
     * calccedo Engine refuse to repeat something already exists
     * @param functionOrMethodName String
     * @param operationalClass class
     */

     private void redundancyValidation(String functionOrMethodName, Class operationalClass) {

        if (injectedFunctionsMap.containsKey(functionOrMethodName)) {
            System.err.println("Exception: " + functionOrMethodName + "(Number... numbers) already defined!");
            System.exit(0);
        }
    }



    /**
     * calccedo Engine accept only injected function that return Number, if not calccedo will exit from JVM
     * @param operationalClass class
     * @param methodName String
     */
    private void restrictionValidation(Class operationalClass, String methodName) {
        Method method = null;
        try {
            // get method reference from operatioalClass that passed from SubFormula Class
            method = operationalClass.getDeclaredMethod(methodName, Number[].class);
        } catch (NoSuchMethodException ex) {
            System.err.println("Exception: you want to inject " + methodName + "(...) into Calccedo and not defined in class " + operationalClass.getName());
            System.out.println("Injected Function must declare as below:");
            System.out.println("Like: public int " + methodName + "(Number... numbers){");
            System.out.println("\treturn 5;");
            System.out.println("}");
            System.exit(0);
        } catch (SecurityException ex) {
            System.err.println("Exception: compiler refuse " + methodName + "(Number... numbers) in class " + operationalClass.getName() + " for security reasons!");
            System.exit(0);
        }

        if (!(method.getReturnType().isPrimitive()
                || method.getReturnType() == Short.class
                || method.getReturnType() == Integer.class
                || method.getReturnType() == Long.class
                || method.getReturnType() == Double.class
                || method.getReturnType() == Float.class)) {
            System.err.println("Exception: you want to inject " + methodName + "(...) in class " + operationalClass.getName());
            System.err.println("Injected Function must return Number");
            System.err.println("Like: public int " + methodName + "(Number... numbers){");
            System.err.println("\treturn 5;");
            System.err.println("}");
            System.exit(0);
        }
    }

}
