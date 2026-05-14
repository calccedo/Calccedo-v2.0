
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
 class Quote {
    
    
    String tokenName;
    String subformula;
    int offsetA;
    int offsetZ;

    
    
    
    public Quote(String subformula, int offsetA, int offsetZ) {
        this.subformula = subformula;
        this.offsetA=offsetA;
        this.offsetZ=offsetZ;
    }
    
    
    public Quote(String tokenName, String subformula, int offsetA, int offsetZ) {
        this.tokenName = tokenName;
        this.subformula = subformula;
        this.offsetA=offsetA;
        this.offsetZ=offsetZ;
    }
    


}
