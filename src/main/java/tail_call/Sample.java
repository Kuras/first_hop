package tail_call;

import java.util.stream.Streams;
/**
 * Class description goes here.
 * @author  : PK
 */
@FunctionalInterface
interface TailCall {
  TailCall get();
  default boolean terminated() { return false; }
}

class TailCallTerminate implements TailCall {
  public TailCall get() { throw new Error("Don't call"); }
  public boolean terminated() { return true; }
}

public class Sample {
  public static TailCall squareAndPrint(int number, int max) {
    System.out.println(number * number);
    if(max > number) {
      return () -> squareAndPrint(number + 1, max);
    } else {
      return new TailCallTerminate();
    }
  }

  public static void main(String[] args) throws Exception {
    int max = Integer.parseInt(args[0]);

    Streams.iterate(1, number -> number + 1)
    .map(number -> number * number)
    .limit(25)
    .forEach(number -> System.out.print(number + " "));

    System.out.println("=================================");

    Streams.iterate(squareAndPrint(1, max), TailCall::get)
    .filter(TailCall::terminated)
    .findFirst();
  }
}
