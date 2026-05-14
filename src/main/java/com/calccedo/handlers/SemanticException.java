
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;

import com.calccedo.configuration.Configuration;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class SemanticException extends RuntimeException{

    String breakPoint;
    public SemanticException(String breakPoint){
        this.breakPoint=breakPoint;
    }
    
    @Override
    public String toString() {
         if(Configuration.deepTracing){
             System.out.println("\nCalccedo Info:");
             System.out.println("Computer calculation different than simple calculator just because,");
             System.out.println("Computer RAM size bigger than simple calculator, so you can find some equations should return infinity result, but in computer return value");
             System.out.println("don't worry, Calccedo Library help you to custome any semantic  that has constant value");
             System.out.println("like: addSemantic(\"Tan\",90,Infinity.class\")");
             System.out.println("or there is predefind semantic as an Error like akw(101) >> Error.class");
             System.out.println("please read Calccedo documentation or contact me on www.github.com/ibrahim1hero1/calccedo \n");
         }

        System.err.println("Exception:");    
        return "Calccedo Library find "+breakPoint+" defined as infinty or Error custom operand";
    }
    
    
}
