# oranjava
Simple and useful additions to the Java programming language

Some quick examples :

1) You may sometimes want to avoid the unnecessary clutter of checked exceptions :
      
        Class<?> stringCls;
        try {
            stringCls = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            // You know for sure that java.lang.String must be present...
        }
        
Now, it can be shortened to a single readable line of code :
        
        Class<?> stringCls = Try.unchecked(() -> Class.forName("java.lang.String"));
        
        
2) Moreover, you may even want to react to a raised exception (when such occurs) :
     
       int result;
       try {
           result = divide(dividend, divisor);
       } catch (Exception e1) {
           try {
               result = conquer(dividend); // The method conquer
           } catch (Exception e2) {
               // You may want to rethrow as RuntimeException - if the conquer method may throw a checked exception
           }
       }
     
Now it can be shortened to a single readable line of code :
     
      int result = Try.tryOrElse(() -> divide(dividend, divisor), () -> conquer(dividend));
      
So 10 lines of code can be replaced with a single readable line of code.

Cool, ha?

Be prepared for more goodies to come :-)

Enjoy & tell me what you think
