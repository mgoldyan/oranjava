package main.java.com.oranjava.tries;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * MIT license , Copyright (C) 2018 mgoldyan
 *
 * The Oranjava Project : https://github.com/mgoldyan/oranjava
 *
 * Purpose :
 *  We use the try-catch so much in our code (if not then you should!), but...
 *  The problem with using it becomes visible as it makes our code pretty verbose.
 *  It can even be more painful when a code must be wrapped because of potential checked exceptions.
 *
 *  To make your code (way) more readable, you may want to adopt the following suggested pattern instead : try-orElse
 *
 * For usages and further details, please follow the documentation of the methods :
 *  tryOrElse(...)
 *  unchecked(...)
 *
 * BETA version (0.2).
 */
public class Try {

    /**
     * Wraps automatically a supplied chunk of code with a try-catch (Throwable).
     *
     * Useful when one wants to simply write a concise chunk of code
     *  with the ability to react to any thrown exception - if such is raised.
     *
     * Note that the code which reacts to a raised exception may itself throw an exception.
     *  In such a case, the inner exception will be rethrown as a RuntimeException.
     *
     * Another advantage is the freedom of the need to wrap code which may throw checked exceptions.
     *
     * The following code :
     *
     *  int result;
     *  try {
     *      result = divide(dividend, divisor);
     *  } catch (Exception e1) {
     *      try {
     *          result = conquer(dividend); // The method conquer may also throw a checked exception
     *      } catch (Exception e2) {
     *          // You may want to rethrow as RuntimeException - if the conquer method may throw a checked exception
     *      }
     *  }
     *
     * can be shortened to a single line of code :
     *
     * int result = Try.tryOrElse(() -> divide(dividend, divisor), () -> conquer(dividend));
     *
     * So 10 lines of code can be replaced with a single readable line of code.
     *
     * @param code         Line of code (which should be wrapped inside a try-catch).
     *                     Usage for a single line of code :    () -> yourMethod(...)
     *                     Usage for a block of code :          () -> { several lines of code, returning a V }
     * @param codeIfThrown Code to be run whenever an exception (Throwable) is thrown.
     *                     Usage for a single line of code :    ex -> yourMethod(...)
     *                     Usage for a block of code :          ex -> { several lines of code, returning a V }
     * @param <V>          Expected return type.
     * @return  Result of the provided "code".
     *          When an exception is thrown during the call to "code", the result of "codeIfThrown" will be returned.
     *          A RuntimeException will be thrown whenever "codeIfThrown" itself throws an exception.
     */
    public static <V> V tryOrElse(Callable<V> code, Function<Throwable, V> codeIfThrown) {
        try {
            return code.call();
        } catch (Throwable throwable1) {
            try {
                return codeIfThrown.apply(throwable1);
            } catch (Throwable throwable2) {
                throw new RuntimeException(throwable2);
            }
        }
    }

    /**
     * Wraps automatically a supplied chunk of code with a try-catch (Throwable).
     *
     * Useful when one wants to simply write a concise chunk of code
     *  with the ability to react to any thrown exception - if such is raised.
     *
     * Note that the code which reacts to a raised exception may itself throw an exception.
     * In such a case, the inner exception will be rethrown as a RuntimeException.
     *
     * Another advantage is the freedom of the need to wrap code which may throw checked exceptions.
     *
     * The following code :
     *
     *  try {
     *      computeAndPrint(n);
     *  } catch (Exception e1) {
     *      try {
     *          computeAndPrint(n-1); // The method conquer
     *      } catch (Exception e2) {
     *          // You may want to rethrow as RuntimeException - if the "retry" method may also throw a checked exception
     *      }
     *  }
     *
     * can be shortened to a single line of code :
     *
     * Try.tryOrElse(() -> computeAndPrint(n), ex -> computeAndPrint(n-1));
     *
     * So here 9 lines of code can be collapsed to a single readable line of code.
     *
     * @param code         Line of code (which should be wrapped inside a try-catch).
     *                     Usage for a single line of code :    () -> yourMethod(...)
     *                     Usage for a block of code :          () -> { several lines of code, returning a V }
     * @param codeIfThrown Code to be run whenever an exception (Throwable) is thrown.
     *                     Usage for a single line of code :    ex -> yourMethod(...)
     *                     Usage for a block of code :          ex -> { several lines of code, returning a V }
     *
     *  When an exception is thrown during the call to "code", "codeIfThrown" will be called.
     *  A RuntimeException will be thrown whenever "codeIfThrown" itself throws an exception.
     */
    public static void tryOrElse(Runnable code, Consumer<Throwable> codeIfThrown) {
        try {
            code.run();
        } catch (Throwable throwable1) {
            try {
                codeIfThrown.accept(throwable1);
            } catch (Throwable throwable2) {
                throw new RuntimeException(throwable2);
            }
        }
    }


    /**
     * Wraps automatically a supplied chunk of code with a try-catch (Throwable).
     *
     * Useful when one wants to simply write a concise chunk of code, which potentially throws a checked exception.
     * When a checked exception is raised - it is rethrown as a RuntimeException.
     *
     * Note : usually it's better to use tryOrElse(...) , as you have the flexibility to react to a thrown exception
     *  (i.e. you may want to log it or recompute / return a different value instead).
     *
     * The following code :
     *
     *  Person person;
     *  try {
     *      person = parse(id);
     *  } catch (ParseException ex) {
     *      // You may want to rethrow as RuntimeException - to eliminate the need to add a throws statement (signature)
     *  }
     *
     * can be shortened to a single line of code :
     *
     * Person person = Try.unchecked(() -> parse(id));
     *
     * So here 6 lines of code can be replaced with a single readable line of code.
     *
     * @param code         Line of code (which should be wrapped inside a try-catch).
     *                     Usage for a single line of code :    () -> yourMethod(...)
     *                     Usage for a block of code :          () -> { several lines of code, returning a V }
     * @param <V>          Expected return type.
     * @return  Result of the provided "code".
     *          A RuntimeException will be thrown whenever "code" throws an exception.
     */
    public static <V> V unchecked(Callable<V> code) {
        try {
            return code.call();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    /**
     * Wraps automatically a supplied chunk of code with a try-catch (Throwable).
     *
     * Useful when one wants to simply write a concise chunk of code, which potentially throws a checked exception.
     * When a checked exception is raised - it is rethrown as a RuntimeException.
     *
     * Note : usually it's better to use tryOrElse(...) , as you have the flexibility to react to a thrown exception
     *  (i.e. you may want to handle the thrown exception by logging it or do some other computation).
     *
     * The following code :
     *
     *  try {
     *      findAndAppend(path, newValue);
     *  } catch (NoSuchElementException ex) {
     *      // You may want to rethrow as RuntimeException - to eliminate the need to add a throws statement (signature)
     *  }
     *
     * can be shortened to a single line of code :
     *
     * Try.unchecked(() -> findAndAppend(path, newValue));
     *
     * So here 5 lines of code can be replaced with a single readable line of code.
     *
     * @param code         Line of code (which should be wrapped inside a try-catch).
     *                     Usage for a single line of code :    () -> yourMethod(...)
     *                     Usage for a block of code :          () -> { several lines of code, returning a V }
     *
     *  A RuntimeException will be thrown whenever "code" throws an exception.
     */
    public static void unchecked(Runnable code) {
        try {
            code.run();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }



    public static void main(String[] args) {
        // Simple examples to demonstrate the usage of tryOrElse(...)

        int divisor = 0;

        int result = Try.tryOrElse(() -> Integer.divideUnsigned(1, divisor), ex -> -1);
        System.out.println(result); // Prints -1


        Class<?> stringCls = Try.unchecked(() -> Class.forName("java.lang.String"));
        System.out.println(stringCls.getSimpleName()); // Prints "String"

        Try.tryOrElse(
                () -> System.out.println(Integer.divideUnsigned(1, divisor)),
                ex -> System.err.println(ex.getMessage()));
        // An ERR message "/ by zero" is printed to the console
    }

}
