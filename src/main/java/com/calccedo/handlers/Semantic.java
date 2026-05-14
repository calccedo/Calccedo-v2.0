
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
 class Semantic {
    
     String name;
     double value;

    public Semantic(String operandName, double value) {
        this.name = operandName;
        this.value = value;
    }

    public String getOperandName() {
        return name;
    }

    public void setOperandName(String operandName) {
        this.name = operandName;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


       
    
    
    
    
}
