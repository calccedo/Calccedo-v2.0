
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.configuration;

import java.util.ArrayList;



/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */


   /**
    * Structure is global class in calccedo 
   */
public  class Structure {
     

    /**
     *  semanticsNameList is arrayList of String contains all default semantics 
     *                    and custom semantics that added by developers
     */

    public static ArrayList<String> semanticsNameList=new ArrayList<String>();

    /**
     * functionNameList is arrayList of String contains all functions 
     *                    that added by developers as a plugins into calccedo Engine
     */
    public static ArrayList<String> functionsNameList=new ArrayList<String>();

    /**
     *  constantsNameList is arrayList of String contains contains all default constants  
     *                    that added by developers
     */
    public static ArrayList<String> constantsNameList=new ArrayList<String>();

    /**
     * variableNameList is arrayList of String contains all variables 
     *                    that added by developers
     */
    public static ArrayList<String> variableNameList=new ArrayList<String>();
    
    /** 
     * to add Semantic into calccedo Engine
     * @param semanticName String
     */
     static void addSemantic(String semanticName){
        semanticsNameList.add(semanticName);
    }
     
    /**
     * to remove Semantic from calccedo Engine
     * @param semanticName String
     */
     static void removeSemantic(String semanticName){
        semanticsNameList.remove(semanticName);
    }
      
    /**
     * to inject function into calccedo Engine
     * @param functionName String
     */
     static void addFunction(String functionName){
        functionsNameList.add(functionName);
    }
    
    /**
     * to remove injected function from calccedo Engine
     * @param functionName String
     */
     static void removeFunction(String functionName){
        functionsNameList.remove(functionName);
    }
    
    /** 
     * to add Constant into calccedo Engine
     * @param constantName String
     */
     static void addConstant(String constantName){
        constantsNameList.add(constantName);
    }
    
    /** 
     * to remove Constant from calccedo Engine
     * @param constantName String
     */
     static void removeConstant(String constantName){
        constantsNameList.remove(constantName);
    }

    /**
     * to add Variable into calccedo Engine
     * @param variableName String
     */
     static void addVariable(String variableName){
        variableNameList.add(variableName);
    } 
     
    /** 
     * to remove Variable from calccedo Engine
     * @param variableName String
     */
     static void removeVariable(String variableName){
        variableNameList.remove(variableName);
    }
     
    /**
     * to configure semantic using it's name by adding it into Semantic ArrayList
     */
     static void configureSemantics(){
        addSemantic("log");
        addSemantic("sinh");
        addSemantic("cosh");
        addSemantic("tanh");
        addSemantic("asin");
        addSemantic("acos");
        addSemantic("atan");
        addSemantic("in");
    }
    
    
    
}
