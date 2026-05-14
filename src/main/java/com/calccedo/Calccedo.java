
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

 package com.calccedo;

import com.calccedo.configuration.Configuration;




/**
 * @author Ibrahim Abdsaid Hanna 
 *         ibrahim.seniore@gmail.com
 */
  public abstract class Calccedo extends Configuration{

    /**
     * constructor of Calccedo which execute init() method in Configuration class while calling
     */   
    public Calccedo() {
        super.init();
    }

     /**
     * This method to help in tracing calccedo step by step if it set to true 
     * @param enabled true | false
     */
      @Override
      protected void enableDeepTrace(boolean enabled) {
          super.enableDeepTrace(enabled);
      }

    /**
     * Calccedo Library is calculation engine Framework library designed to help developers for building applications that need complicated Mathematical equations,"
     * Calccedo is not just library, it's work as Interpreter and act as AI engines
     * @return String
     */
    @Override
    public String toString() {
        return " Calccedo Library is calculation engine Framework library designed to help developers for building applications that need complecated Mathematical equations,"
                + "\n  Calccedo is not just library, it's work as Interpreter and act as AI engines";
    }
    
   
}
