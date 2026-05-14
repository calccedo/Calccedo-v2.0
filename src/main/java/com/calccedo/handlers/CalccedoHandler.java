
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;

import com.calccedo.Calccedo;
import com.calccedo.configuration.Symbols;
import com.calccedo.configuration.Configuration;
import com.calccedo.configuration.Structure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * 
 * @author Ibrahim Abdsaid Hanna 
 *         ibrahim.seniore@gmail.com
 */
 public class CalccedoHandler extends Calccedo implements Handler {
    /**
      * postfixSymbolsList is a type of ArrayList that hold all postfix symbols from Symbols class  
      */
    private ArrayList<Character> postfixSymbolsList=Symbols.postfixSymbolsList;
    /**
     *  prefixSymbolsList is a type of ArrayList that hold all prefix symbols from Symbols class  
     */
    private ArrayList<Character> prefixSymbolsList=Symbols.prefixSymbolsList;
    /**
     *  constantsNameMap is a type of ArrayList that hold all constants name from ConstantInjector class  
     */
    private HashMap<String, Double> constantsNameMap=ConstantInjector.constantsNameMap;
    /**
     *  variablesNameMap is a type of ArrayList that hold all variables name from VariableInjector class   
     */
    private HashMap<String, Double> variablesNameMap=VariableInjector.variableNameMap;
    
    /**
     * constructor of CalccedoHandler
     */
    public CalccedoHandler(){
    }

     
  
     private Quote parsePartethis(String formula) {
     
           int offsetA=0;
           int offsetZ=0;
           String token="";
           
           for(int i=formula.length()-1;i>=0;i--){
               
                if(formula.charAt(i)=='('){
                         offsetA=i;
                    for(int j=i;j<formula.length();j++){
                         if(formula.charAt(j)==')'){
                          offsetZ=j;
                          i=-1;
                          break;
                        }                 
                }                    
           }     
         
           }
           
           if(offsetA==0){
              return new Quote(formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
           }
           else{
               try{
                   
                   for(int t=offsetA-1;t>=0;t--){
                       if(isNumber(formula.charAt(t)) || Symbols.basicOperatorsList.contains(formula.charAt(t))){ 
                           break;
                       }
                       else{
                           token=formula.charAt(t)+token;
                       }
                   }
                   
                  
               if(Structure.semanticsNameList.contains(token) || Structure.functionsNameList.contains(token)){
                   return new Quote(token, formula.substring(offsetA, offsetZ+1),offsetA, offsetZ+1);
               }
                else{
                    return new Quote(formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
                }
                   }
               catch(Exception ex){
                   return new Quote(formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
               }
              
           }
          
           
       
    }
 
    /**
     * return true if char is Number or false if char is not number 
     * @param c char
     * @return boolean
     */
    @Override
   public boolean isNumber(char c){
       if(c=='.'){
           return true;
       }
       try{
           Integer.parseInt(""+c);
           return true;
       }
       
       catch(Exception ex){
           
           return false;
       }
       
   }

    /**
     * to optimize formula before start calculate process 
     * @param formula StringBuilder
     * @return StringBuilder
     */
    @Override
    public StringBuilder optimizeFormula(StringBuilder formula) {
      StringBuilder newformula=new StringBuilder();   
 
        for(int i=0;i<formula.length();i++){
             
            if(postfixSymbolsList.contains(formula.charAt(i)) && i>0){
                if(prefixSymbolsList.contains(formula.charAt(i-1)) && i>0){
                     if(formula.charAt(i)=='<'){
                      newformula.append("*").append(formula.charAt(i)).append("(");
                    }
                     else{
                    newformula.append("*").append(formula.charAt(i));
                     }
                }
                else if(formula.charAt(i)=='<'){
                    newformula.append(formula.charAt(i)).append("(");
                }
                else if(isNumber(formula.charAt(i-1))){
                    newformula.append("*").append(formula.charAt(i));
                }
                else{
                    newformula.append(formula.charAt(i));
                }
            }
            
            
            
             else if(formula.charAt(i)=='<'){
                  newformula.append(formula.charAt(i)).append("(");
            }
             else if(formula.charAt(i)=='>'){
                  newformula.append(")").append(formula.charAt(i));
            }
            else if(isNumber(formula.charAt(i))){
                if(i>0&&(formula.charAt(i-1)==')'||formula.charAt(i-1)=='>')){
                  newformula.append("*").append(formula.charAt(i));
                }
                else{
                  newformula.append(formula.charAt(i));
                }
            }
            else{
                newformula.append(formula.charAt(i));
            }
            
          
        }

         if(Configuration.deepTracing)
        System.out.println("Optimization is >>>>>>>>>>>"+newformula);

        
     return newformula;
    }

    /** 
     * to optimize formula from constants, if constants founded in formula
     * @param formula StringBuilder
     * @return StringBuilder
     */
    @Override
    public StringBuilder optimizeFormulaFromConstants(StringBuilder formula) {
        StringBuilder newformula=formula;   
         Map.Entry pair;
         String constantName;
         Iterator iterator= constantsNameMap.entrySet().iterator();
         while(iterator.hasNext()){
            pair=(Map.Entry)iterator.next();
            constantName=pair.getKey().toString() ;
            while(newformula.indexOf(constantName) != -1){
                int startIndex=newformula.indexOf(constantName);
                int lastIndex= newformula.indexOf(constantName)+constantName.length();
                 if ((lastIndex<newformula.length() && isNumber(newformula.charAt(lastIndex)))|| (lastIndex<newformula.length() && postfixSymbolsList.contains(newformula.charAt(lastIndex)))) {
                    newformula.insert(lastIndex, "*");
                 }
                 newformula.replace(startIndex , lastIndex, constantsNameMap.get(constantName)+""); 
            }
         }
         
           if(Configuration.deepTracing)
        System.out.println("optinmization from constants is >>>>>>>>>>>"+newformula);
      
         
         return newformula;
    }

    /**
     * to optimize formula from variables, if variables founded in formula 
     * @param formula StringBuilder
     * @return StringBuilder
     */
    @Override
    public StringBuilder optimizeFormulaFromVariables(StringBuilder formula) {
        StringBuilder newformula=formula;   
         Map.Entry pair;
         String variableName;
         Iterator iterator= variablesNameMap.entrySet().iterator();
         while(iterator.hasNext()){
            pair=(Map.Entry)iterator.next();
            variableName=pair.getKey().toString() ;
            while(newformula.indexOf(variableName) != -1){
                int startIndex=newformula.indexOf(variableName);
                int lastIndex= newformula.indexOf(variableName)+variableName.length();
                 if ((lastIndex<newformula.length() && isNumber(newformula.charAt(lastIndex)))|| (lastIndex<newformula.length() && postfixSymbolsList.contains(newformula.charAt(lastIndex)))) {
                    newformula.insert(lastIndex, "*");
                 }
                 newformula.replace(startIndex , lastIndex, variablesNameMap.get(variableName)+""); 
            }
         }
         
           if(Configuration.deepTracing)
        System.out.println("optinmization from variables is >>>>>>>>>>>"+newformula+"\n");
      
         
         return newformula;
    }

    /**
     * to validate if this is valid formula or not before start calculate process 
     * @param formula String
     * @return boolean
     */
    @Override 
    public boolean initValidation(String formula) {
       
        if(!formula.contains("(")&&!formula.contains("<")&&!formula.contains(">")&&!formula.contains(")")){
            return true;
        }
        
         int openedPartethis=0;      
         int openedRoot=0;
        
       
         for(int i=0;i<formula.length();i++){
          
             if(formula.charAt(i)=='('){
           
                 openedPartethis++;
             }
             
               if(formula.charAt(i)==')'){
                 openedPartethis--;
             }
               if(openedPartethis<0){
                   return false;  
               }
                if(formula.charAt(i)=='<'){
                 openedRoot++;
             }
                
                if(formula.charAt(i)=='>'){
                 openedRoot--;
             }
                 if(openedRoot<0){
                   return false;  
               }
         }
       
        return openedPartethis==0 && openedRoot==0 ;
    }
   
    /**
     * this is the main method of calccedo to calculate formula
     * @param formula String
     * @return String
     */
    @Override
    public String calculate(String formula) {    
       // validate formula
       if(!initValidation(formula)) return  "Error";
       
        StringBuilder formu = new StringBuilder(formula); 
       // optimize formula
        formu=optimizeFormula(formu);
        
        // optimize from constants
        formu=optimizeFormulaFromConstants(formu);
        
        // optimize from variables
        formu=optimizeFormulaFromVariables(formu);
        
        // calculate inside partethis
        String firstProcess= calculatePartethis(formu.toString());
        if(firstProcess.equals("error")){
            return "Error";
        }
        
        
        // include final formula inside partetehis to process it 
        // second peocess is the final process in calccedo, just because conatins only +,-
        
        String secondProcess= calculatePartethis("("+firstProcess+")");
        if(secondProcess.equals("error")){
            return "Error";
        }
        
        // detect final result contain fractions, if not return result as Integer value 
        if(secondProcess.endsWith(".0")){
            secondProcess=secondProcess.substring(0,secondProcess.lastIndexOf(".0"));
        }
        
        return secondProcess;
}

   


    
    private String calculatePartethis(String formula){
             Quote quote;
             SubFormula subFormula;
             QuoteResult quoteResult;
             while(formula.contains("(")){
                 quote=parsePartethis(formula);
                 subFormula=new SubFormula(quote);
                 quoteResult=subFormula.getQuoteResult();
                 if(quoteResult==null){
                     return "error";
                 }
                 else{
                     formula=formula.substring(0,quoteResult.offsetA)+quoteResult.result+formula.substring(quoteResult.offsetZ,formula.length());
                 }
                        
             }
              if(Configuration.deepTracing)
             System.out.println("formula after parsing partethis"+formula);
            return formula;
         }

    /**
     * to check if this StringBuilder quote able to parse to Number or not
     * @param quote StringBuilder
     * @return boolean
     */
    @Override
    public boolean isNumber(StringBuilder quote) {
        
        try{
           Double.parseDouble(quote.toString());
           return true;
       }
       
       catch(Exception ex){
            
            if(Configuration.deepTracing){
              //  System.err.println(ex+"\n just dummy exception do in behind while validating Numbers\n");
               System.out.println("\nCalccedo Info: this is just info to help developers how Calccedo Library work");
               System.out.println("Info quote "+quote+", cannot wrapped to Double, so it will complete loop until finishing operations \n"); 
            }
            return false;
       }
    }

    /**
     * This is the backbone handler class of calccedo, 
     * It receives formula and handle all cycles to bring result
     * @return String
     */
    @Override
    public String toString() {
        return "This is the backbone handler class of calccedo, it recieves formula and handle all cycles to bring result";
    }

     
    
    
}


  

