
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;


/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class Function {
    
     /*
     declare Function variables
     functionName:
                  set when developer need to use custome function name, not method name
                  like AVG() not average() as a method
     
     fullClassName:
                  used in reflection class to call initialized methods
     
     methodName: 
                  which method declared will call this function
     
     methodArgumentsType:
                  array of Class that conatin all arguments type that needed in this method   
      
     */
     
     
     String functionName;
     Class operationalClass;
     String methodName;

     
     
   /*
      First Constructor to let developers use only method name to call function
      Like: average(5,6,10,20);
     */
    public Function(Class operationalClass, String methodName) {
        this.operationalClass = operationalClass;
        this.methodName = methodName;
    }
    
    
    /*
     Second Constructor to let developers use custom function name and link it to method name
    Like: AVG(5,6,10,20) >>>>>>>>> Call >>>>>>>>> average(5,6,10,20);
    */
    
    public Function(String functionName, Class operationalClass, String methodName) {
        this.functionName = functionName;
        this.operationalClass = operationalClass;
        this.methodName = methodName;
    }

    
    
    
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Class getOperationalClass() {
        return operationalClass;
    }

    public void setOperationalClass(Class operationalClass) {
        this.operationalClass = operationalClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

 
}
