/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */
package com.calccedo.configuration;

import com.calccedo.handlers.ConstantInjector;
import com.calccedo.handlers.FunctionInjector;
import com.calccedo.handlers.SemanticInjector;
import com.calccedo.handlers.Infinity;
import com.calccedo.handlers.VariableInjector;

/**
 *
 * @author Ibrahim Abdsaid Hanna 
 *         ibrahim.seniore@gmail.com
 */
public abstract class Configuration {

    /**
     * deepTracing is a boolean to enable or disable tracing calccedo steps 
     */
    public static boolean deepTracing = false;

    /**
     * closeGate is a boolean to call init() method only one time in Calccedo constructor
     */
    public static boolean closeGate = false;

    /**
     * closeCompiler is a boolean to avoid conflict and repetition when injecting function in calccedo
     */
    public static boolean closeCompiler = false;

    /**
     * angel_type is an enum type to hold angel type DEGREES | REDIANS
     */
    public static Angel angel_type = Angel.RADIANS;
    
    /**
     * semanticInjector is an instance of SemanticInjector class to addSemantics
     */
    SemanticInjector semanticInjector = new SemanticInjector();
    
    /**
     * functionInjector is an instance of FunctionInjector class to addFunctions
     */
    FunctionInjector functionInjector = new FunctionInjector();
    
    /**
     * constantInjector is an instance of ConstantInjector class to addConstants
     */
    ConstantInjector constantInjector=new ConstantInjector();
    
    /**
     * variableInjector is an instance of VariableInjector class to addVariables
     */
    VariableInjector variableInjector=new VariableInjector();
    
    /**
     * setter is an instance of Setter class to update semantics values update variables value , update functions reference methods
     */
    Setter setter = new Setter();
     
    
    /**
     * is a startup method in calccedo to config all calccedo needs plus that adding some semantics and constants,
     * like: addSemantic("sin",30, 0.5);
     *       addSemantic("sin",90, 1);
     *       addSemantic("cos",90.0, 0);
     *       addConstant("pi", 3.14);
     */
    protected void init() {
        if(!closeGate){ 
        addSemantic("sin",30, 0.5);
        addSemantic("sin",30.0, 0.5);
        addSemantic("sin",90, 1);
        addSemantic("sin",90.0, 1);
        addSemantic("sin",150, 0.5);
        addSemantic("sin",150.0, 0.5);
        addSemantic("sin",180, 0);
        addSemantic("sin",180.0, 0);
        addSemantic("sin",210, -0.5);
        addSemantic("sin",210.0, -0.5);
        addSemantic("sin",270, -1);
        addSemantic("sin",270.0, -1);
        addSemantic("sin",330, -0.5);
        addSemantic("sin",330.0, -0.5);
        addSemantic("sin",360, 0);
        addSemantic("sin",360.0, 0);
        
        addSemantic("cos",60, 0.5);
        addSemantic("cos",60.0, 0.5);
        addSemantic("cos",90, 0);
        addSemantic("cos",90.0, 0);
        addSemantic("cos",180,-1) ;
        addSemantic("cos",180.0,-1);
        addSemantic("cos",240,-0.5) ;
        addSemantic("cos",240.0, -0.5);
        addSemantic("cos",270,0) ;
        addSemantic("cos",270.0,0);
        addSemantic("cos",300,0.5) ;
        addSemantic("cos",300.0,0.5);
        addSemantic("cos",360,1) ;
        addSemantic("cos",360.0,1);
        
        addSemantic("tan",45,1);
        addSemantic("tan",45.0,1);
        addSemantic("tan",180,0);
        addSemantic("tan",180.0,0);
        addSemantic("tan",225,1);
        addSemantic("tan",225.0,1);
        addSemantic("tan",360,0);
        addSemantic("tan",360.0,0);
        addSemantic("tan",90,Infinity.class);
        addSemantic("tan",90.0,Infinity.class); 
        addSemantic("tan",270,Infinity.class);
        addSemantic("tan",270.0,Infinity.class);
         addSemantic("tan",315,Infinity.class);
        addSemantic("tan",315.0,Infinity.class);

        addConstant("pi", 3.14);
        addConstant("PI", 3.14);
        
        Symbols.configurePostfixSymbols();
        Symbols.configurePrefixSymbols();
        Symbols.configureBasicOperators();
        Symbols.configurePlusMinusOperators();
      
        Structure.configureSemantics();
        
        closeGate=true; 
        
        }

    }

    /**
     * to help in tracing calccedo step by step if it set to true
     * like: enableDeepTrace(true);
     * @param enabled true | false
     */
    protected void enableDeepTrace(boolean enabled) {
        deepTracing = enabled;
    }

    /**
     * is a method to set angel (REDIANS | DEGREES) before calccedo start processing
     * like: configAngel( Angel.DEGREES );
     * @param angel_type REDIANS | DEGREES
     */
    protected void configAngel(Angel angel_type) {
        this.angel_type = angel_type;
    }

    /** 
     * to addSemantic into calccedo engine like >> sin(30) >> 0.5
     * Hint:
     * this method take three arguments (String,int,double)
     * like: addSemantic("sin", 30 , 0.5 )
     *       addSemantic("akw", 10 , 50 );          
     * @param semanticName String
     * @param quoteNumber int
     * @param value double
     */
    protected void addSemantic(String semanticName, int quoteNumber, double value) {
        if(!closeCompiler){
        semanticInjector.add(semanticName, quoteNumber, value);
        Symbols.addPostfixSymbol(semanticName.charAt(0));
        Structure.addSemantic(semanticName);
        }
    }

    /**
     * to addSemantic into calccedo engine like >> sin(30.0) >> 0.5
     * Hint:
     * this method take three arguments (String,double,double)
     * like: addSemantic("sin", 30.0 , 0.5 )
     *       addSemantic("akw", 10.0 , 50 );
     * @param semanticName String
     * @param quoteNumber double
     * @param value double
     */
    protected void addSemantic(String semanticName, double quoteNumber, double value) {
         if(!closeCompiler){
        semanticInjector.add(semanticName, quoteNumber, value);
        Symbols.addPostfixSymbol(semanticName.charAt(0));
        Structure.addSemantic(semanticName);
         }
    }

    /**
     * to addSemantic into calccedo engine like >> tan(90) >> ERROR >> Infinity.class
     * Hint:
     * this method take three arguments (String,double,Infinity.class)
     * like: addSemantic("tan", 90 , Infinity.class )
     *       addSemantic("akw", 0 , Error.class );
     * @param semanticName String
     * @param quoteNumber int
     * @param infinityOrError Infinity.class | Error.class
     */
    protected void addSemantic(String semanticName, int quoteNumber, Object infinityOrError) {
         if(!closeCompiler){
        semanticInjector.add(semanticName, quoteNumber, infinityOrError);
        Symbols.addPostfixSymbol(semanticName.charAt(0));
        Structure.addSemantic(semanticName);
         }
    }

    /**
     * to addSemantic into calccedo engine like >> sin(30.0) >> Infinity.class
     * Hint:
     * this method take three arguments (String,double,Infinity.class)
     * like: addSemantic("sin", 30.0 , Infinity.class )
     *       addSemantic("akw", 10.0 , Error.class );
     * @param semanticName String
     * @param quoteNumber double
     * @param infinityOrError Infinity.class | Error.class
     */
    protected void addSemantic(String semanticName, double quoteNumber, Object infinityOrError) {
         if(!closeCompiler){
        semanticInjector.add(semanticName, quoteNumber, infinityOrError);
        Symbols.addPostfixSymbol(semanticName.charAt(0));
        Structure.addSemantic(semanticName);
         }
    }

    /** 
     * to inject function into calccedo Engine
     * like: add( Mathematics.class , "deviation" )
     * @param operationalClass Class.class
     * @param methodName String  
     */
    protected void injectFunction(Class operationalClass, String methodName) {
         if(!closeCompiler){
        functionInjector.add(operationalClass, methodName);
        Symbols.addPostfixSymbol(methodName.charAt(0));
        Structure.addFunction(methodName);
         }
    }

    /**
     * to inject function into calccedo Engine
     * like: add("dev" , Mathematics.class , "deviation" );
     * @param functionName String
     * @param operationalClass Class.class
     * @param methodName String
     */
    protected void injectFunction(String functionName, Class operationalClass, String methodName) {
         if(!closeCompiler){
        functionInjector.add(functionName, operationalClass, methodName);
        Symbols.addPostfixSymbol(functionName.charAt(0));
        Structure.addFunction(functionName);
         }
    }

    /**
     * to add constants into calccedo Engine
     * like: addConstant("saturday" , 1 );
     *       addConstant("sunday" , 2 );
     *       addConstant("monday" , 3 );
     *       addConstant("tuesday" , 4 );
     *       addConstant("wedensday" , 5 );
     *       addConstant("thursday" , 6 );
     *       addConstant("friday" , 7 );
     * @param constantName String
     * @param constantValue double
     */
    protected void addConstant(String constantName, double constantValue) {
       if(!closeCompiler){
        constantInjector.add(constantName,constantValue);
        Symbols.addPostfixSymbol(constantName.charAt(0));
        Structure.addConstant(constantName);
        }
    }
    
    /**
     * to add variables into calccedo Engine
     * like: addVariable(x,5);
     *       addVariable(y,10);
     *       addVariable(z,15); 
     * @param variableName String
     * @param variableValue double
     */
    protected void addVariable(String variableName, double variableValue) {
       if(!closeCompiler){
        variableInjector.add(variableName,variableValue);
        Symbols.addPostfixSymbol(variableName.charAt(0));
        Structure.addVariable(variableName);
        }
    }
    
    /**
     * to remove constant from calccedo Engine
     * like: removeConstant("saturday");
     *       removeConstant("sunday");
     *       removeConstant("monday");
     *       removeConstant("tuesday");
     *       removeConstant("wedensdasy");
     *       removeConstant(thusday");
     *       removeConstant("friday");
     * @param constantName String
     */
    protected void removeConstant(String constantName) {
       boolean constantExists = constantInjector.checkConstantExists(constantName);
       if(constantExists && !closeCompiler){
        constantInjector.remove(constantName);
        Symbols.removePostfixSymbol(constantName.charAt(0));
        Structure.removeConstant(constantName);
       }
    }  
      
    /**
     * to remove variable from calccedo Engine
     * like: removeVariable("x");
     * like: removeVariable("y");
     * like: removeVariable("z");
     * @param variableName String
     */
    protected void removeVariable(String variableName) {
       boolean variableExists = variableInjector.checkVariableExists(variableName);  
       if(variableExists && !closeCompiler){
        variableInjector.remove(variableName);
        Symbols.removePostfixSymbol(variableName.charAt(0));
        Structure.removeVariable(variableName);
        }     
    }
    
    /** 
     * remove semantic from calccedo Engine
     * like: removeSemantic("sin",30);
     * like: removeSemantic("cos",60);
     * @param semanticName String
     * @param quoteNumber int
     */
    protected void removeSemantic(String semanticName, int quoteNumber) {
       boolean semanticExists = semanticInjector.checkSemanticExists(semanticName,quoteNumber);
       if(semanticExists && !closeCompiler){
        semanticInjector.remove(semanticName,quoteNumber);
        Symbols.removePostfixSymbol(semanticName.charAt(0));
        Structure.removeSemantic(semanticName);
        }
    }
     
    /**
     * to remove semantic from calccedo Engine
     * like: removeSemantic("sin",30.0);
     *       removeSemantic("cos",60.0)
     * @param semanticName String
     * @param quoteNumber double
     */
    protected void removeSemantic(String semanticName, double quoteNumber) {
      boolean semanticExists = semanticInjector.checkSemanticExists(semanticName,quoteNumber); 
       if(semanticExists && !closeCompiler){
        semanticInjector.remove(semanticName,quoteNumber);
        Symbols.removePostfixSymbol(semanticName.charAt(0));
        Structure.removeSemantic(semanticName);
        }
    }
    
    /**
     * to remove function from calccedo Engine
     * removeFunction("deviation");
     * removeFunction("dev");
     * @param functionName String
     */
    protected void removeFunction(String functionName) {
    boolean functionExists = functionInjector.checkFunctionExists(functionName);         
       if(functionExists && !closeCompiler){
        functionInjector.remove(functionName);
        Symbols.removePostfixSymbol(functionName.charAt(0));
        Structure.removeFunction(functionName);
        }  
   }
   
    /**
     * to edit and update value of added Variable
     * like:
     *      if we call addVariable("x",50);   >> x = 50
     *      then
     *                 set("x",60);           >> x = 60
     * @param variableName String
     * @param new_value double
     */
    protected void set(String variableName, double new_value){
         setter.set(variableName, new_value);
    }
    
    /**
     * to edit and update value of added semantic
     * like: set("sin", 30 , 0.0000005 );
     *       set("cos", 60 , 0.0000005);
     * @param semanticName String
     * @param quoteNumber int
     * @param new_value double
     */
    protected void set(String semanticName, int quoteNumber, double new_value){
         setter.set(semanticName, quoteNumber, new_value);
    }
    
    /**
     * to edit and update value of added semantic
     * like: set("sin", 30.0 , 0.0000005 );
     *       set("cos", 60.0 , 0.0000005); 
     * @param semanticName String
     * @param quoteNumber double
     * @param new_value double 
     */
    protected void set(String semanticName, double quoteNumber, double new_value){
         setter.set(semanticName, quoteNumber, new_value);
    }
   
    /**
     * to edit and update value of added semantic
     * like: set("sin", 115 , Infinity.class);
     *       set("akw", 101 , Error.class);
     * @param semanticName String
     * @param quoteNumber int
     * @param infinityOrError Infinity.class | Error.class
     */
    protected void set(String semanticName, int quoteNumber, Object infinityOrError){
         setter.set(semanticName, quoteNumber, infinityOrError);
    }
   
    /**
     * to edit and update value of added semantic
     * like: set("sin", 30.0004 , Infinity.class);
     *       set("akw", 101.0 , Error.class);
     * @param semanticName String
     * @param quoteNumber double
     * @param infinityOrError Infinity.class | Error.class
     */
    protected void set(String semanticName, double quoteNumber, Object infinityOrError){
         setter.set(semanticName, quoteNumber, infinityOrError);
    }
   
    /**
     * to edit and update reference method of added function
     * like set("dev" , Matematics.class , deviation_extra)
     * @param functionName String
     * @param operationalClass Class.class
     * @param newMethodName String
     */
    protected void set(String functionName, Class operationalClass, String newMethodName) {
        setter.set(functionName, operationalClass, newMethodName);
    } 
   
    
   
}


