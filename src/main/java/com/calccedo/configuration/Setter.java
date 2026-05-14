/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */
package com.calccedo.configuration;

import com.calccedo.handlers.ConstantInjector;
import com.calccedo.handlers.FunctionInjector;
import com.calccedo.handlers.SemanticInjector;
import com.calccedo.handlers.VariableInjector;

/**
 *
 * @author Ibrahim Abdsaid Hanna 
 *         ibrahim.seniore@gmail.com
 */
 class Setter {
    
    
    ConstantInjector constantInjector = new ConstantInjector();
    VariableInjector variableInjector = new VariableInjector();
    SemanticInjector semanticInjector =new SemanticInjector();
    FunctionInjector functionInjector = new FunctionInjector();
    
    
   /*
        update variable with new value
    */

    /**
     *
     * @param variableName
     * @param new_value
     */

    public void set(String variableName, double new_value){
         if(constantInjector.checkConstantExists(variableName)){
             System.err.println("Error in set("+variableName+","+new_value+") >>> "+variableName+" defined as a constant");
             System.exit(0);
         }
         else if(!variableInjector.checkVariableExists(variableName)){
             System.err.println("Variable Name you want to update >>> "+variableName +" not exists");
             System.exit(0);
         }
         else{
             variableInjector.updateVariableTo(variableName, new_value);
         }
    }
    
     /*
        update semantic with new value
    */

    /**
     *
     * @param semanticName
     * @param quoteNumber
     * @param new_value
     */

    public void set(String semanticName, int quoteNumber, double new_value){
        if(!semanticInjector.checkSemanticExists(semanticName,quoteNumber)){
              System.err.println("Semantic Name you want to update >>> "+semanticName+"("+quoteNumber+") not exists");
              System.exit(0);
        }
         else{
             semanticInjector.updateSemanticTo(semanticName, quoteNumber, new_value );
         }
    }
    
     /*
        update semantic with new value
    */

    /**
     *
     * @param semanticName
     * @param quoteNumber
     * @param new_value
     */

     public void set(String semanticName, double quoteNumber, double new_value){
        if(!semanticInjector.checkSemanticExists(semanticName,quoteNumber)){
              System.err.println("Semantic Name you want to update >>> "+semanticName+"("+quoteNumber+") not exists");
              System.exit(0);
        }
         else{
             semanticInjector.updateSemanticTo(semanticName, quoteNumber, new_value );
         }
    }
    
      /*
        update semantic with new value
    */

    /**
     *
     * @param semanticName
     * @param quoteNumber
     * @param infinityOrError
     */

    public void set(String semanticName, int quoteNumber, Object infinityOrError){
        if(!semanticInjector.checkSemanticExists(semanticName,quoteNumber)){
              System.err.println("Semantic Name you want to update >>> "+semanticName+"("+quoteNumber+") not exists");
              System.exit(0);
        }
         else{
             semanticInjector.updateSemanticTo(semanticName, quoteNumber, infinityOrError );
         }
    }
    
     /*
        update semantic with new value
    */

    /**
     *
     * @param semanticName
     * @param quoteNumber
     * @param infinityOrError
     */

     public void set(String semanticName, double quoteNumber, Object infinityOrError){
        if(!semanticInjector.checkSemanticExists(semanticName,quoteNumber)){
              System.err.println("Semantic Name you want to update >>> "+semanticName+"("+quoteNumber+") not exists");
              System.exit(0);
        }
         else{
             semanticInjector.updateSemanticTo(semanticName, quoteNumber, infinityOrError );
         }
    } 
     
      /*
        update function with new value
    */

    /**
     *
     * @param functionName
     * @param operationalClass
     * @param newMethodName
     */

    public void set(String functionName, Class operationalClass, String newMethodName) {
         if(!functionInjector.checkFunctionExists(functionName)){
              System.err.println("Function Name you want to update >>> "+functionName+"(Number... numbers) not exists");
              System.exit(0);
         }
         else{
             functionInjector.updateFunctionTo(functionName, operationalClass, newMethodName);
         }
       }
    
    
    
}
