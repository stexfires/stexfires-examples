package stexfires.examples.javatest;

import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "ResultOfMethodCallIgnored"})
public final class StreamTest {

    private StreamTest() {
    }

    private static Stream<String> generateTestStream() {
        return Stream.of("This ", "is ", "a ", "test ", "stream.");
    }

    private static void showIntStreamRange() {
        System.out.println("-showIntStreamRange---");
        System.out.println("range[10,15[");
        IntStream.range(10, 15).forEachOrdered(System.out::println);
        System.out.println("range[10,15]");
        IntStream.rangeClosed(10, 15).forEachOrdered(System.out::println);
    }

    private static void showStreamIterate() {
        System.out.println("-showStreamIterate---");
        System.out.println("n+1");
        Stream.iterate(1, n -> n + 1).limit(3L).forEachOrdered(System.out::println);
        System.out.println("n*2");
        Stream.iterate(2, n -> n * 2).limit(3L).forEachOrdered(System.out::println);
    }

    private static void showStreamGenerate() {
        System.out.println("-showStreamGenerate---");
        System.out.println("constant");
        Stream.generate(() -> 1).limit(3L).forEachOrdered(System.out::println);
        System.out.println("random");
        Stream.generate(Math::random).limit(3L).forEachOrdered(System.out::println);
        System.out.println("Atomic increment");
        Stream.generate(new AtomicInteger()::getAndIncrement).limit(3L).forEachOrdered(System.out::println);
    }

    private static void showPeekAndParallel() {
        System.out.println("-showPeekAndParallel---");
        System.out.println("peek: ");
        generateTestStream().peek(System.out::print).count();
        System.out.println();
        System.out.println("parallel peek: ");
        generateTestStream().parallel().peek(System.out::print).count();
        System.out.println();
        System.out.println("many peeks");
        Stream.of("AA", "B", "AA", "CC")
              .peek(p -> System.out.println("1. " + p))
              .distinct()
              .peek(p -> System.out.println("2. " + p))
              .filter(v -> v.length() > 1)
              .peek(p -> System.out.println("3. " + p))
              .forEachOrdered(v -> System.out.println("End: " + v));
    }

    private static void showCount() {
        System.out.println("-showCount---");
        System.out.println("Count stream elements: " + generateTestStream().count());
    }

    private static void showFind() {
        System.out.println("-showFind---");
        System.out.println("findFirst: " + generateTestStream().findFirst());
        System.out.println("parallel findFirst: " + generateTestStream().parallel().findFirst());
        System.out.println("findAny: " + generateTestStream().findAny());
        System.out.println("parallel findAny: " + generateTestStream().parallel().findAny());
    }

    private static void showBuilder() {
        System.out.println("-showBuilder---");
        Stream.builder().add("This ").add("is ").add("a ").add("test ").add("stream.").build().forEachOrdered(System.out::print);
        System.out.println();
    }

    private static void showJoining() {
        System.out.println("-showJoining---");
        System.out.println(generateTestStream().collect(Collectors.joining()));
        System.out.println(generateTestStream().collect(Collectors.joining("-")));
    }

    private static void showReduce() {
        System.out.println("-showReduce---");
        System.out.println(new SplittableRandom().ints(10L, 1, 10)
                                                 .reduce((r, e) -> r + e).orElse(0));
    }

    private static void showSplittableRandom() {
        System.out.println("-showSplittableRandom---");

        System.out.println("-- ints");
        new SplittableRandom().ints(5L, 1, 4)
                              .forEachOrdered(System.out::println);
        System.out.println("-- longs");
        new SplittableRandom().longs(5L, Long.MAX_VALUE / 2L, Long.MAX_VALUE)
                              .forEachOrdered(System.out::println);
        System.out.println("-- doubles");
        new SplittableRandom().doubles(5L, 0.0d, 1.0d)
                              .forEachOrdered(System.out::println);
        System.out.println("-- ints boxed");
        System.out.println(new SplittableRandom().ints(5L, 1, 4).boxed()
                                                 .collect(Collectors.toList()));
    }

    private static void showConcat() {
        System.out.println("-showConcat---");
        Stream.concat(generateTestStream(), generateTestStream())
              .forEachOrdered(System.out::println);
    }

    public static void main(String[] args) {
        showIntStreamRange();
        showStreamIterate();
        showStreamGenerate();
        showPeekAndParallel();
        showCount();
        showFind();
        showBuilder();
        showJoining();
        showReduce();
        showSplittableRandom();
        showConcat();
    }

}
