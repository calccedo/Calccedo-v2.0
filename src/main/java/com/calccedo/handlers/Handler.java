
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
 interface Handler {
            
    String calculate(String formula);
    boolean isNumber(char c);
    boolean isNumber(StringBuilder quote);
    boolean initValidation(String formula);
    StringBuilder optimizeFormula(StringBuilder formula);
    StringBuilder optimizeFormulaFromConstants(StringBuilder formula);
    StringBuilder optimizeFormulaFromVariables(StringBuilder formula); 
 }
      
    
    
  

