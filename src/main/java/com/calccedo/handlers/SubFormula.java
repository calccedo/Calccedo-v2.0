
/*
 *   Calccedo Framework Library
 *   hint this code under ApacheLicense
 */

package com.calccedo.handlers;


import com.calccedo.configuration.Angel;
import com.calccedo.configuration.Configuration;
import com.calccedo.configuration.Structure;
import com.calccedo.configuration.Symbols;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class SubFormula extends CalccedoHandler{
    
    Quote quote;   
    
    // load basic operators from Symbols Class
    ArrayList<Character> basicOperatorsList=Symbols.basicOperatorsList;
    // load +-  from Symbols Class 
    ArrayList<Character> plus_minus_operatorsList=Symbols.plus_minus_OperatorsList;
   // load semantics from Structure Class
    ArrayList<String> semanticsNameList=Structure.semanticsNameList;
    // load functions from Function Class
    ArrayList<String> functionNameList=Structure.functionsNameList;
    
    HashMap<String, Object> semanticsNameMap=SemanticInjector.semanticsNameMap;
    HashMap<String, Function> injectedFunctionsMap=FunctionInjector.injectedFunctionsMap;
  
    StringBuilder subformula;
    
    
    
    public SubFormula(Quote quote){
        this.quote=quote;       
        subformula=new StringBuilder(quote.subformula);
    }

  
    
    public QuoteResult getQuoteResult(){
      
       if (validational(subformula)==null){
           return null;
       }
       
       String subformula_str = subformula.toString();
       
      // detect standard operads (<>), if yes processing it      
       if(subformula_str.contains("<")){             
          subformula=processSqrt(subformula);
          if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
      System.out.println("<> Subformula "+subformula);
     // detect standard operads (^), if yes processing it      
       if(subformula_str.contains("^")){         
         subformula=processPriority(subformula,'^');
         if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
        System.out.println("^ Subformula "+subformula);
        // detect Reminder operads, if yes processing it     
       if(subformula_str.contains("%")){
          subformula=processPriority(subformula,'%'); 
          if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
       System.out.println("% Subformula "+subformula);
       
      // detect divison operads, if yes processing it     
       if(subformula_str.contains("/")){
           subformula=processPriority(subformula,'/');
           if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
       System.out.println("/ Subformula "+subformula);
      // detect multiplication operads, if yes processing it      
       if(subformula_str.contains("*")){
           subformula=processPriority(subformula,'*');
           if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
        System.out.println("* Subformula "+subformula);
       
          // detect standard operads (+,-), if yes processing it      
       if(subformula_str.contains("+")||subformula_str.contains("-")){
          subformula=processStandard(subformula,plus_minus_operatorsList);
          if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
      System.out.println("+- Subformula "+subformula);
       
      // final result of inside quote
       stringBuilderReplacer("(", "", subformula);
        stringBuilderReplacer(")", "", subformula);

        String finalQuoteNumber = subformula.toString();
        Number result = 0;

      // check if this is semantic and calculate it
      if(semanticsNameList.contains(quote.tokenName)){
          
          try{
          result= processSemantic(quote.tokenName,finalQuoteNumber);
          if(result !=null){
          return  new QuoteResult(quote.offsetA-quote.tokenName.length(), quote.offsetZ,result);
          }
          if(functionNameList.contains(quote.tokenName)){
                try{
          result= processInjectedFunction(quote.tokenName,finalQuoteNumber);
          return  new QuoteResult(quote.offsetA-quote.tokenName.length(), quote.offsetZ,result);
          }
          catch(Exception ex){
              System.err.println(ex);
              return null;
          }
          }
          }
          catch(SemanticException ex){
              return null;
          }
          catch (RuntimeException ex) {
                System.err.println(ex);
                return null;
            }

      }
        // check if this is injectedFumction and calculate it
      else if(functionNameList.contains(quote.tokenName)){
          
          try{
          result= processInjectedFunction(quote.tokenName,finalQuoteNumber);
          return  new QuoteResult(quote.offsetA-quote.tokenName.length(), quote.offsetZ,result);
          }
          catch(Exception ex){
              System.err.println(ex);
              return null;
          }
      }
     else {
            try {
                result = Integer.parseInt(optemizeNumeric(finalQuoteNumber));
            } catch (Exception exp) {
                try {
                result = Double.parseDouble(finalQuoteNumber);
            } catch (Exception ex) {
                return null;
            }
            }
        }
       if(Configuration.deepTracing)
        System.out.println("Result >>>>>>>>>>>>>>>>>>>>>>>>>>>>"+result);
      
      return  new QuoteResult(quote.offsetA, quote.offsetZ,result);
    }
    
    
    
   
    public Object validational(StringBuilder subformula){
         
           if(subformula.charAt(1)=='/'|| subformula.charAt(1)=='*'||subformula.charAt(1)=='^'){
             return null;      // not legal to attach operator after open partethis eg (/121*3) 
            }
    
           if(basicOperatorsList.contains(subformula.charAt(subformula.length()-2))){
               return null;      // not legal to attach operator before closing partethis eg (121*3/) 
     }
           return "okay"; // just dummy output
    }


     
   
    
    public StringBuilder processPriority(StringBuilder subformula,char operand){
     uncoverQuote(subformula);
        if (isNumber(subformula)) {
            return subformula;
        }

     
      coverQuote(subformula);
      optimizeFormula(subformula);

         
        StringBuilder leftTemp = new StringBuilder();
        StringBuilder rightTemp = new StringBuilder();
        double result = 0;
        String infinityTester = "";
        int QLPointer = 0;
        int QRPointer = 0;
        Eshortuct eshortuct = new Eshortuct();
  
               
           for(int i=0;i<subformula.length();i++){
               
               // detect operand, now let's stop on it's index and take left and right numbers
               if(subformula.charAt(i)==operand){
                 
                   
         // every iterate check if inside quote number to return it without iterate
              uncoverQuote(subformula); 
              if(isNumber(subformula)){
                    return subformula;
                }
               coverQuote(subformula);
                   //obtain left number
                   for(int j=i-1;j>=0;j--){
                        
                       // detect E sympol which mean e^10
                        if(subformula.charAt(j)=='E'){
                            eshortuct=eshortuct.process(subformula, j);
                            leftTemp=eshortuct.leftTemp;
                            operand=eshortuct.operandType;
                            i=eshortuct.rightOperandIndex;   
                            QLPointer=eshortuct.leftOperandIndex;
                         break;
                        }
                       
                       if(basicOperatorsList.contains(subformula.charAt(j))){
                          if(subformula.charAt(j)=='-'&&operand!='^'){
                               if(j>0&&subformula.charAt(j-1)=='E'){
                           continue;
                           }
                            leftTemp.insert(0, subformula.charAt(j)); // append operand to left number
                            QLPointer = j - 1;
                            break;
                          }
                          else{
                           QLPointer=j;  // pointer to last number
                           break;    
                          }
                       }
                       else{
                           
                           leftTemp.insert(0, subformula.charAt(j));
                
                       }
                   }
                  
                   //obtain right number
                   for(int jj=i+1;jj<subformula.length();jj++){
                       
                         // detect E sympol which mean e^10
                        if(subformula.charAt(jj)=='E'){
                            eshortuct=eshortuct.process(subformula, jj);
                            rightTemp=eshortuct.leftTemp;
                            QRPointer=eshortuct.rightOperandIndex;
                         break;
                        }
                       
                       else if(basicOperatorsList.contains(subformula.charAt(jj))&& basicOperatorsList.contains(subformula.charAt(jj-1))){
                            rightTemp.append(subformula.charAt(jj));
                            QRPointer=jj+1; // stop pointer after right number to check there is another operand                                        
                          
                        }
                        else if(basicOperatorsList.contains(subformula.charAt(jj))){
                              if(subformula.charAt(jj)=='-'&&operand!='^'){
                               if(subformula.charAt(jj-1)=='E'){
                           continue;
                           }
                              }
                            QRPointer=jj;
                            break;
                       }
                       else{
                           rightTemp.append(subformula.charAt(jj));
                          
                       }
                   }
           
              
                   try{
                   double number1=Double.parseDouble(leftTemp.toString());
                   double number2=Double.parseDouble(rightTemp.toString());     
                   
                    switch (operand) {
                        case '^':
                            result=Math.pow(number1,number2);
                            break;
                        case '%':
                            result=number1%number2;
                            break;
                        case '/':
                            result=number1/number2;
                            infinityTester=result+"";
                            if(infinityTester.equals("Infinity")||infinityTester.equals("-Infinity")||infinityTester.equals("NaN")){
                                 return new StringBuilder("error");
                            }     
                            break;
                        case '*':
                            result=number1*number2;
                            break;
                        default:
                            break;
                    }
                   
                  
                   }
                   catch(Exception ex){
                       System.err.println("Priority Exception"+ex);
                       return new StringBuilder("error");
                       
                   }
                   if(Configuration.deepTracing){
                   System.out.println("Operand "+operand+"    "+result);
                   System.out.println("QLPointer   "+QLPointer);
                   System.out.println("QRPointer   "+QRPointer); 
                   }
                 
                String subformula_str = subformula.toString();
                String result_str =  result+"";
                
                      if(QLPointer==0 && QRPointer==0){
                               return new StringBuilder("(" + result_str + ")");
                          } 
                          else if(subformula.charAt(QLPointer)!='('){
                                   subformula.replace(QLPointer + 1, QRPointer, result_str);
                                   optimizeFormula(subformula);
                                   i = 0;
                            }
                           else if(subformula.charAt(QLPointer)=='('&&QRPointer>0 ){
                                subformula.insert(0, "(").insert(1,result_str).replace(1+result_str.length(),QRPointer+1+result_str.length(),"");
                                i = 0;
                            }
                           else if(!subformula_str.contains("/")&&!subformula_str.contains("*")&&!subformula_str.contains("+")&&!subformula_str.contains("-")&&!subformula_str.contains("^")){
                                 if(Configuration.deepTracing)  
                                    return subformula; 
                                 
                            }
                          
                   
              subformula_str = null;  
             result_str = null;   
                   
                   }
                 leftTemp.setLength(0);
            leftTemp.trimToSize();

            rightTemp.setLength(0);
            rightTemp.trimToSize();

           }  
      
      return subformula;
      
      
    }
    
    
    
    
    
     
   public StringBuilder processStandard(StringBuilder subformula,ArrayList<Character> clist){
     uncoverQuote(subformula);
        if (isNumber(subformula)) {
            return subformula;
        }

        coverQuote(subformula);
        optimizeFormula(subformula);

       
        StringBuilder leftTemp = new StringBuilder();
        StringBuilder rightTemp = new StringBuilder();
        double result = 0;
        int QLPointer = 0;
        int QRPointer = 0;
        char operandType;
        Eshortuct eshortuct = new Eshortuct();
       
       if(Configuration.deepTracing)
       System.out.println("Standard SubFormula   "+subformula);
   
           
           for(int i=0;i<subformula.length();i++){
               
               
            // every iterate check if inside quote number to return it without iterate
             uncoverQuote(subformula);  
            
            if (isNumber(subformula)) {
                return subformula;
            }
            coverQuote(subformula);
               
               // detect +- operand, now let's stop on it's index and take left and right numbers
               if(clist.contains(subformula.charAt(i))) {
                  
                 operandType=subformula.charAt(i);
               
                   
                   
             // detect there is operand in first subformula      
                   if(i<=1){
                    for(i=i+1;i<subformula.length();i++){
                            if(clist.contains(subformula.charAt(i))) {
                                operandType=subformula.charAt(i);                            
                                break; 
                            }
                        }
                   }
                       
                   
                   //obtain left number
                   for(int j=i-1;j>=0;j--){
                        
                        // detect E sympol which mean e^10
                        if(subformula.charAt(j)=='E'){
                            eshortuct=eshortuct.process(subformula, j);
                            leftTemp=eshortuct.leftTemp;
                            operandType=eshortuct.operandType;
                            i=eshortuct.rightOperandIndex;   
                            QLPointer=eshortuct.leftOperandIndex;
                         break;
                        }
                       
                        else if(basicOperatorsList.contains(subformula.charAt(j))){
                          if(subformula.charAt(j)=='-'){
                               if(j>0&&subformula.charAt(j-1)=='E'){
                                     continue;
                                  }
                            leftTemp.insert(0, subformula.charAt(j)); // append operand to left number
                            QLPointer = j - 1;
                            break;
                          }
                          else{
                           QLPointer=j;  // pointer to last number
                           break;    
                          }
                       }
                       else{
                           
                            leftTemp.insert(0, subformula.charAt(j));
              }
                   }
                          
                   //obtain right number
                   for(int jj=i+1;jj<subformula.length();jj++){
                       
                       
                      // detect E sympol which mean e^10
                        if(subformula.charAt(jj)=='E'){
                            eshortuct=eshortuct.process(subformula, jj);
                            rightTemp=eshortuct.leftTemp;
                            QRPointer=eshortuct.rightOperandIndex;
                         break;
                        }
   
                        else if(basicOperatorsList.contains(subformula.charAt(jj))){
                              if(subformula.charAt(jj)=='-'){
                               if(subformula.charAt(jj-1)=='E'){
                           continue;
                           }
                              }
                            QRPointer=jj; // sopt pointer after right number to check there is another operand                     
                            break;
                       }
                       else{
                            rightTemp.append(subformula.charAt(jj));  
                       }
                   }
           
              
                   try{
                   double number1=Double.parseDouble(leftTemp.toString());
                   double number2=Double.parseDouble(rightTemp.toString());     
                   
                      
                   
                   if(operandType=='+'){
                      result=number1+number2;
                      }                      
                  else if(operandType=='-'){
                      if(rightTemp.indexOf("-")==0){
                          number2=number2*-1;
                      }
                      result=number1-number2;
                      }
                    
                    
                   }
                   catch(Exception ex){
                       System.err.println("Standard Exception"+ex);
                       return null;
                       
                   }
                   
                    if(Configuration.deepTracing){
                   System.out.println("Standard Result   "+result);
                   System.out.println("QLPointer   "+QLPointer);
                   System.out.println("QRPointer   "+QRPointer); 
                    }
                      
                      String subformula_str = subformula.toString();
                String result_str =  result+"";

                        if(QLPointer==0 && QRPointer==0){
                              return new StringBuilder("(" + result_str + ")");
                          }                 
                         else if((subformula.charAt(QLPointer) != '(' && subformula.charAt(QLPointer-1) == '(') || (subformula.charAt(QLPointer) != '(' && subformula.charAt(QLPointer - 1) != '(')){   
                                 subformula.replace(QLPointer + 1, QRPointer, result_str);
                                 optimizeFormula(subformula);
                                 i = 0;
                            }
                        
                           else if(subformula.charAt(QLPointer)=='('){
                                subformula.insert(0, "(").insert(1,result_str).replace(1+result_str.length(),QRPointer+1+result_str.length(),"");
                                i = 0;
                            }
                           else if(!subformula_str.contains("/")&&!subformula_str.contains("*")&&!subformula_str.contains("+")&&!subformula_str.contains("-")&&!subformula_str.contains("^")){
                              
                                    return subformula; 
                                 
                            }
                           else{
                                 return new StringBuilder("(" + result_str + ")");
                           }
                       if(Configuration.deepTracing)  {  
                       System.out.println("Standard SubFormula   "+subformula);
                       }
                       
                   subformula_str = null;  
              result_str = null;  
                   }
                leftTemp.setLength(0);
            leftTemp.trimToSize();

            rightTemp.setLength(0);
            rightTemp.trimToSize();

           }
            
          
         
      
     
         
      
      return subformula;
      
      
    }
    

    
    public Number processSemantic(String semanticName,String finalQuoteNumber){           
        if(semanticsNameMap.get(semanticName+finalQuoteNumber)!=null && angel_type == Angel.DEGREES){
            if(semanticsNameMap.get(semanticName+finalQuoteNumber) == Infinity.class || 
                   semanticsNameMap.get(semanticName+finalQuoteNumber) == Error.class ){
              throw new SemanticException(semanticName+"("+finalQuoteNumber+")");  
            }
            Semantic obj=(Semantic)semanticsNameMap.get(semanticName+finalQuoteNumber);
            return obj.value;
        }
        
        Double number=Double.parseDouble(finalQuoteNumber);
        
        switch(semanticName){
            
            case "sin":
                if(angel_type == Angel.RADIANS){
                return Math.sin(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                return Math.sin(Math.toRadians(number));
                }
                
            case "cos":
                if(angel_type == Angel.RADIANS){
                return Math.cos(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.cos(Math.toRadians(number));
                }
            case "tan":
                if(angel_type == Angel.RADIANS){
                return Math.tan(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.tan(Math.toRadians(number));
                }
            case "log":
                return Math.log10(number);
                
            case "sinh":
                if(angel_type == Angel.RADIANS){
                return Math.sinh(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.sinh(Math.toRadians(number));
                }
                
            case "cosh":
                if(angel_type == Angel.RADIANS){
                return Math.cosh(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.cosh(Math.toRadians(number));
                }

            case "tanh":
                if(angel_type == Angel.RADIANS){
                return Math.tanh(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.tanh(Math.toRadians(number));
                }  
                
            case "asin":
                if(angel_type == Angel.RADIANS){
                return Math.asin(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.asin(Math.toRadians(number));
                }  
                
            case "acos":
                 if(angel_type == Angel.RADIANS){
                return Math.acos(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.acos(Math.toRadians(number));
                }   
             
            case "atan":
                 if(angel_type == Angel.RADIANS){
                return Math.atan(Math.toDegrees(number)*Math.PI/180);
                }
                else if(angel_type == Angel.DEGREES){
                 return Math.atan(Math.toRadians(number));
                }   
                
            case "in":
                return Math.log(number);    
                
             default:
                 return null;
                
        }
            
            
        }
        
    
      public double processInjectedFunction(String functionName,String argumentsStream)throws Exception{
          Function function=(Function)injectedFunctionsMap.get(functionName);
          FunctionHandler functionHandler=new FunctionHandler(function);
          double result=Double.parseDouble(functionHandler.calculateFunction(argumentsStream));
          return result;
      }
    
    
        
    
    
        public StringBuilder processSqrt(StringBuilder subformula){
            StringBuilder temp = new StringBuilder();
            double sqrtResult=0;
            // iterate inside formula untill find '<>'
            for(int i=0;i<subformula.length();i++){   
                if(subformula.charAt(i)=='<'){  
                    
                    for(int j=i+1;j<subformula.length();j++){                      
                        if(subformula.charAt(j)=='>'){
                            try{
                            sqrtResult=Math.sqrt(Double.parseDouble(temp.toString()));
                            }
                            catch(Exception ex){
                                   subformula.setLength(0);
                            subformula.trimToSize();
                            subformula.insert(0, "error");
                                break;
                            }
                        stringBuilderReplacer("<" + temp.toString() + ">", "" + sqrtResult, subformula);
                        temp.setLength(0);
                        temp.trimToSize();
                        break;
                        }
                        else{
                           temp.append(subformula.charAt(j));                    
                        }
                    }
                                        
                }
                
            }
            
            return subformula;
            
        }


     public void uncoverQuote(StringBuilder subformula) {
        stringBuilderReplacer("(", "", subformula);
        stringBuilderReplacer(")", "", subformula);
    }

    public void coverQuote(StringBuilder subformula) {
        subformula.insert(0, "(");
        subformula.insert(subformula.length(), ")");
    }
     
     
        public void stringBuilderReplacer(String oldStr, String newStr, StringBuilder strB) {
        try {
            strB.replace(strB.indexOf(oldStr), strB.indexOf(oldStr) + oldStr.length(), newStr);
        } catch (Exception ex) {
            // just not-handled caughted Exception 
        }
    }
     
   @Override
    public StringBuilder optimizeFormula(StringBuilder subformula) {
        stringBuilderReplacer("-+", "-", subformula);
        stringBuilderReplacer("+-", "-", subformula);
        stringBuilderReplacer("--", "+", subformula);
        return subformula;
    }   
       
      public String optemizeNumeric(String string_number) {
        if (string_number.endsWith(".0")) {
            return string_number.substring(0, string_number.length() - 2);
        }
        return string_number;
    }
    
}
