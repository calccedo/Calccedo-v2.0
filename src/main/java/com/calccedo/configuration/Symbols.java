


package com.calccedo.configuration;

import java.util.ArrayList;


/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */

public class Symbols {
    
        /**
         * Symbols is global class in calccedo 
        */

    /**
     * postfixSymbols is arrayList of character contains all default symbols of semantics 
     *                    and custom symbol semantics that added by developers
     * postfixSymbol mean the start character of each semantic added
     */

    
    public static ArrayList<Character> postfixSymbolsList=new ArrayList<Character>();

    /**
     * prefixSymbols is arrayList of closed symbols like > , ) and numbers 
     *          
     */
    public static ArrayList<Character> prefixSymbolsList=new ArrayList<Character>();

    /**
     * basicSymbols contains all general operators Math like:
     *    /, % , * , + , - , ^ , ( , )
     */
    public static ArrayList<Character> basicOperatorsList=new ArrayList<Character>();

    /**
     *  plus_minus_Symbols contains only + , -
     */
    public static ArrayList<Character> plus_minus_OperatorsList=new ArrayList<Character>();
    
    /**
     * to add postfix symbol
     * @param c char
     * @return true
     */
     static boolean addPostfixSymbol(char c){
        
        if(!postfixSymbolsList.contains(c)){
            postfixSymbolsList.add(c);
            return true;
        }

        return false;
    }
    
    /**
     * to remove postfix symbol from ArrayList
     * @param c char
     * @return boolean
     */
     static boolean removePostfixSymbol(char c){
           postfixSymbolsList.remove(postfixSymbolsList.indexOf(c));
           return true;
     }
    
    /**
     * to add prefix symbol
     * @param c
     * @return
     */
     static boolean addPrefixSymbol(char c){
        if(!prefixSymbolsList.contains(c)){
            prefixSymbolsList.add(c);
            return true;
        }

        return false;
    }
    
    /**
     * to remove prefix symbol from ArrayList
     * @param c char
     * @return true
     */
     static boolean removePrefixSymbol(char c){
           prefixSymbolsList.remove(prefixSymbolsList.indexOf(c));
           return true;
     }
     
    /**
     * to add all needed postfix symbols  
     */
     static void configurePostfixSymbols(){
          addPostfixSymbol('l');
          addPostfixSymbol('<');
          addPostfixSymbol('(');
      }
     
    /**
     * to add all needed prefix symbols  
     */
     static void configurePrefixSymbols(){
        addPrefixSymbol(')');
        addPrefixSymbol('>');
        addPrefixSymbol('.');
        addPrefixSymbol('0');
        addPrefixSymbol('1');
        addPrefixSymbol('2');
        addPrefixSymbol('3');
        addPrefixSymbol('4');
        addPrefixSymbol('5');
        addPrefixSymbol('6');
        addPrefixSymbol('7');
        addPrefixSymbol('8');
        addPrefixSymbol('9');
                
      }
     
    /** 
     * to add all basic operators
     */
     static void configureBasicOperators(){
        basicOperatorsList.add('/');
        basicOperatorsList.add('%');
        basicOperatorsList.add('*');
        basicOperatorsList.add('+');
        basicOperatorsList.add('-');
        basicOperatorsList.add('^');
        basicOperatorsList.add('(');
        basicOperatorsList.add(')');
        basicOperatorsList.add(',');
     }
     
    /**
     * configurePlusMinusOperators to add + - operators
     */
     static void configurePlusMinusOperators(){
        plus_minus_OperatorsList.add('+');
        plus_minus_OperatorsList.add('-');
     }
     
     
}


