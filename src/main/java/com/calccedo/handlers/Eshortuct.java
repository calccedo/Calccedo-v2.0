
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;

import com.calccedo.configuration.Configuration;
import java.util.ArrayList;




/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class Eshortuct extends CalccedoHandler{
    
    
     StringBuilder leftTemp;
     char operandType;
     int leftOperandIndex;
     int rightOperandIndex;
     ArrayList<Character> list=new ArrayList<Character>();
     
        public Eshortuct(){
        }
     
        public Eshortuct(StringBuilder leftTemp,char operandType,int leftOperandIndex,int rightOperandIndex){
        this.leftTemp=leftTemp;
        this.operandType=operandType;
        this.leftOperandIndex=leftOperandIndex;
        this.rightOperandIndex=rightOperandIndex;       
    }
 
    // process E shortuct to not conflict with - and + operand     
     public Eshortuct process(StringBuilder subformula,int Eindex){
        
         StringBuilder leftTemp=new StringBuilder("E");
         char operandType = 0;
         int leftOperandIndex = 0;
         int rightOperandIndex = 0;
        
         boolean ignore=false;
         list.clear();
         list.add('/');
         list.add('%');
         list.add('*');
         list.add('^');
         list.add('(');
         list.add(')'); 
         
           for(int i=Eindex;i>=0;i--){
                if(!isNumber(subformula.charAt(i))){
                   if(isMinus(subformula.charAt(i))&&ignore==false){
                        if(i == 1){  // check if minus operand is after opend partetis direct at index 1 like -1E14
                            leftOperandIndex=0;
                       }
                       else{
                            leftOperandIndex=i;
                       }
                       leftTemp.insert(0, subformula.charAt(i));
                       break;
                   }
                  else if(isPlus(subformula.charAt(i))){
                      leftOperandIndex=i;
                      break;
                   }
                    else if(list.contains(subformula.charAt(i))){
                      leftOperandIndex=i;
                      break;
                    }
               }
               else{
                   leftTemp.insert(0,subformula.charAt(i));
               }
           }
           
           
           
           for(int j=Eindex;j<subformula.length();j++){
               
                if(!isNumber(subformula.charAt(j))){
                   if(isMinus(subformula.charAt(j))&&subformula.charAt(j-1)=='E'&&ignore==false){
                       leftTemp.append('-');
                       ignore=true;
                   }
                  else if(isPlus(subformula.charAt(j))){
                      operandType='+';
                      rightOperandIndex=j;
                      break;
                   }
                  else if(ignore==true&&(isMinus(subformula.charAt(j))||isPlus(subformula.charAt(j)))){
                       operandType=subformula.charAt(j);
                       rightOperandIndex=j;
                       break;
                  }
                 
                 else if(isMinus(subformula.charAt(j))||isPlus(subformula.charAt(j))){
                       operandType=subformula.charAt(j);
                       rightOperandIndex=j;
                       break;
                  }
                 
                   else if(list.contains(subformula.charAt(j))){
                       operandType=subformula.charAt(j);
                       rightOperandIndex=j;
                       break;
                  }
               }
               else{
                  leftTemp.append(subformula.charAt(j));
               }
           }
       if(Configuration.deepTracing)
         System.out.println("leftTemp:"+leftTemp+"\noperandType:"+operandType+"\nleftOperandIndex"+leftOperandIndex+"\nrightOperandIndex"+rightOperandIndex+"\n");
      
       return new Eshortuct(optemizeNumeric(leftTemp), operandType,leftOperandIndex,rightOperandIndex);
    }
    
    
    
    public boolean isMinus(char c){
        return c=='-';
    }
    
    
    public boolean isPlus(char c){
        return c=='+';
    }
    
 public StringBuilder optemizeNumeric(StringBuilder string_number){
        if(string_number.substring(string_number.length()-2,string_number.length()).equals(".0")){
         string_number.replace(string_number.length()-2,string_number.length(),"");
        }
        return string_number;
    }
    
    
}
 