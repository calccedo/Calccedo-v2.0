
<h1> Build Calculation using Java </h1>

<h3> Introduction </h3>

Calccedo-V2 is the next revolution of calccedo, it support many rich features like addSemantic(), like addVariable(), like addConstant(), like injectFunction()


<br/>


1- <a style="font-weight: bold;"> Setup using jar and add it to project class path:</a>


<a href="https://www.dropbox.com/scl/fi/kvfuxwd8y21pxslwbd7hd/calccedo-2.0.0.zip?rlkey=5oz9uw6qyoduyvtlq9xkydv9m&st=8qjf7yg3&dl=0">
<img src="https://raw.githubusercontent.com/ibrahim1hero1/calccedo/Calccedo-v2/readme/images/dropbox-logo.png" data-canonical-src="https://raw.githubusercontent.com/ibrahim1hero1/calccedo/Calccedo-v2/readme/images/dropbox-logo.png" width="800" height="200" />
</a>



(https://www.dropbox.com/scl/fi/kvfuxwd8y21pxslwbd7hd/calccedo-2.0.0.zip?rlkey=5oz9uw6qyoduyvtlq9xkydv9m&st=8qjf7yg3&dl=0)

<br/>
<xmp>
      <repositories>
          <repository>
              <id>jitpack.io</id>
              <url>https://jitpack.io</url>
          </repository>
      </repositories>

          <dependency>
              <groupId>com.github.calccedo</groupId>
              <artifactId>Calccedo-v2.0</artifactId>
              <version>2.0.0</version>
          </dependency>

 </xmp>
<a style="font-weight: bold;"> Features: </a>
<pre>
add semantics like Sin(30) = 0.5
add variables like x=10
add constants like PI = 3.14
inject functions like f(x)=x^2+2x+1
you can build large complicated mathematical application using Calccedo-V2

  </pre>


<br/>

<h1>Development</h1>
<h3>Calculate simple operation</h3>

<br/>
<pre>
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */   
    public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(true);
      super.configAngel(Angel.DEGREES);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "1+5+sin(30)+20+70+80+90";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
</pre>

<h3>Adding Sin(30) to calccedo as a semantic Sin(30)=0.5</h3>
     <pre>    
     addSemantic(String semanticName,int quoteNumber,double value);
     addSemantic(String semanticName,double quoteNumber,double value);
     addSemantic(String semanticName,int quoteNumber,Object object);
     addSemantic(String semanticName,double quoteNumber,Object object);
    </pre>
    <pre>
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */   
    public class TestCalccedo extends CalccedoHandler{
    
    
    public TestCalccedo(){
      super.enableDeepTrace(false);
      super.addSemantic("Sin",30,0.5);
      super.addSemantic("Sin",30.0,0.5);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "Sin(30)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
    </pre>

<h3>Adding Tan(90) to calccedo as an Illegal custom operand Tan(90)=Error</h3>
<pre>
import org.calccedo.handlers.CalccedoHandler;
import org.calccedo.handlers.Infinity;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */
public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(false);
      super.addSemantic("Tan",90,Infinity.class);
      super.addSemantic("Tan",90.0,Infinity.class);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "Tan(90)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}

</pre>

<h3>Calculate complicated operations</h3>
<pre>
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */   
    public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(true);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "(5(1+2)+3sin(30cos(30+30))+(5*7+10%2)+(10^21+5E10)/5)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
  
</pre>

<h3>Adding Variable</h3>
<pre>
  
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */
public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(true);
      super.addVariable("x", 3);
      super.addVariable("y", 3);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "x+y";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
</pre>

<h3>Adding Constant</h3>

<pre>
  import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */
public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(true);
      super.addConstant("sat", 1);
      super.addConstant("sun", 2);
      super.addConstant("mon", 3);
      super.addConstant("tue", 4);
      super.addConstant("wed", 5);
      super.addConstant("thu", 6);
      super.addConstant("fri", 7);
     
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "sat + sun + mon + tue + wed + thu + fri";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}

</pre>


<h3>Inject Function</h3>
<h5>calccedo so easy to calculate function formula like f(x) = x^2+2x+1 calccedo make it easy</h5>
<h6></h6>first step: we will create class that implement method that do function formula</h6>
<pre>
  
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */
public class Equations {

    /* implement f(x) = x^2+2x+1
       important! each time you want to implement formula, you should follow this standard steps in calccedo 
       which mean you should declare method as it is methodName(Number... numbers) for every formula you want to inject
    */
    
    
   public double f(Number...  numbers){
      double result=0; 
      result = numbers[0]^2 + 2*numbers[0] + 1;  
      return result;
    }    
    
}

</pre>

<h6></h6>second step: create class that inject function in previous class</h6>
<pre>
  import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */
public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
       super.enableDeepTrace(true);
       super.injectFunction(Equations.class,"f");
       
       /*
          you can also rename injectedFunction to any name you want like this: 
       */
       super.injectFunction("fn",Equations.class,"f");
    }
    
    public static void main(String [] args){
    
     TestCalccedo calccedoHandler = new TestCalccedo();
     
      String formula = "f(50)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
      
      
      // you will notice that calccedo handle fn(50) as f(50) because they are reference to same function
      formula = "fn(50)";
      String result = calccedoHandler.calculate(formula);
      
      
      
      System.out.println(result);
    }
}

  
</pre>

<pre>
  All operations that calccedo can do

#	Operation name	Example	Result
+	Addition	1+2	3
-	Subtraction	80-10	70
*	Multiplication	5*5	25
/	Deviation	27/3	9
%	Reminder	5393%3	2
^	Power	2^5	32
<>	Sqrt	<9>	3
( )	Parenthesis	(1+1(5*5))	26
Sin	sin	sin(30)	0.5
Cos	cos	cos(60)	0.5
Tan	tan	tan(45)	1
Log	log	log(10)	1
E	Power 10	5E10	50000000000
pi	constant of (pi)	2pi	2 * 3.14
PI	constant of (PI)	3PI	3 * 3.14
</pre>

<br/>

<h1>Contact</h1>
<img src="https://raw.githubusercontent.com/ibrahim1hero1/calccedo/Calccedo-v2/readme/images/ibrahim.jpg" data-canonical-src="https://raw.githubusercontent.com/ibrahim1hero1/calccedo/Calccedo-v2/readme/images/ibrahim.jpg" width="400" height="300" />



Author: Ibrahim Abdsaid Hanna

Email: ibrahim.seniore@gmail.com

Blog: https://ibrahim1hero1-calccedo.blogspot.com.eg/

