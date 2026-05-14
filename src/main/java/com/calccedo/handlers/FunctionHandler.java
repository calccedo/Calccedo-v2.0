
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;

import com.calccedo.configuration.Configuration;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

/**
 *
 * @author Ibrahim Abdsaid Hanna 
 *         ibrahim.seniore@gmail.com
 */
class FunctionHandler {

    Function function;
    Class operationalClass;
    String methodName;

    public FunctionHandler(Function function) {
        this.function = function;
        this.operationalClass = function.getOperationalClass();
        this.methodName = function.getMethodName();
    }

    public String calculateFunction(String argumentsStream) throws Exception {
        String calculateResult = "";

        // get method reference from operatioalClass that passed from SubFormula Class
        Method method = operationalClass.getDeclaredMethod(methodName,Number[].class);
        // Close Compile may be instantiate the same class that extends CalccedoHandler
        Configuration.closeCompiler=true;
       // instantiate object from operationalClass
        Object obj = operationalClass.newInstance();

        calculateResult=method.invoke(obj,new Object[]{fetchArgumentsValue(argumentsStream)}).toString();

        return calculateResult;
    }

    /* tokenize arguments value quote into array of Object
     (1,2,3.5,10,20) >>>>> Number[]{1,2,3.5,10,20}
     */
    public Number[] fetchArgumentsValue(String stream) {
       int counter = 0;
        Number[] argumentsValue=null ;
        StringTokenizer tokenizer;
     if(stream.contains(",")){ 
         tokenizer=new StringTokenizer(stream, ",");

      while (tokenizer.hasMoreElements()) {
            counter++;
            tokenizer.nextToken(",");
          }
       argumentsValue= new Number[counter];
        counter = 0;
        tokenizer = new StringTokenizer(stream, ",");
        while (tokenizer.hasMoreTokens()) {
            argumentsValue[counter] = Double.parseDouble(tokenizer.nextToken(","));
            counter++;
             
        }
     }
     else{
          argumentsValue= new Number[1];
          argumentsValue[0] = Double.parseDouble(stream);
     }
        return argumentsValue;
    }
    
}
