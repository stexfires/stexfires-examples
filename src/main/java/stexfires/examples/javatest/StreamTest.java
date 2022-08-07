package stexfires.examples.javatest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "ResultOfMethodCallIgnored", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
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
                                                 .reduce(Integer::sum).orElse(0));
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

    private static void showModification() {
        System.out.println("-showModification---");
        String strA = "a";
        String strB = "b";
        String strC = "c";
        String[] array = new String[]{strA, strB};

        List<String> list1 = new ArrayList<>(1);
        list1.add(strA);

        // create streams
        Stream<String> listStream = list1.stream();
        Stream<String> arrayStream1 = Arrays.stream(array);
        Stream<String> arrayStream2 = Stream.of(array);

        // modify streams
        list1.add(strC);
        array[1] = strC;

        // print streams
        System.out.print("Modified list stream:    ");
        listStream.forEachOrdered(System.out::print);
        System.out.println();
        System.out.print("Modified array stream 1: ");
        arrayStream1.forEachOrdered(System.out::print);
        System.out.println();
        System.out.print("Modified array stream 2: ");
        arrayStream2.forEachOrdered(System.out::print);
        System.out.println();
    }

    private static void showCharacteristics() {
        System.out.println("-showCharacteristics---");
        String strA = "a";
        String strB = "b";

        // List
        List<String> list1 = new ArrayList<>(1);
        list1.add(strA);
        Stream<String> streamList1 = list1.stream();
        Stream<String> streamList2 = List.copyOf(list1).stream();

        // Single
        Stream<String> streamSingle1 = Stream.of(strA);

        // Array
        String[] array = new String[]{strA, strB};
        Stream<String> streamArray1 = Arrays.stream(array);
        Stream<String> streamArray2 = Stream.of(strA, strB);
        Stream<String> streamArray3 = Stream.of(array);
        Stream<String> streamArray4 = Stream.of();

        // Empty
        Stream<String> streamEmpty1 = Stream.empty();

        // Spliterator
        Spliterator<String> spList1 = streamList1.spliterator();
        Spliterator<String> spList2 = streamList2.spliterator();

        Spliterator<String> spSingle1 = streamSingle1.spliterator();

        Spliterator<String> spArray1 = streamArray1.spliterator();
        Spliterator<String> spArray2 = streamArray2.spliterator();
        Spliterator<String> spArray3 = streamArray3.spliterator();
        Spliterator<String> spArray4 = streamArray4.spliterator();

        Spliterator<String> spEmpty1 = streamEmpty1.spliterator();

        Map<String, Spliterator<String>> spliterators = new TreeMap<>();
        spliterators.put("spList1  ", spList1);
        spliterators.put("spList2  ", spList2);
        spliterators.put("spSingle1", spSingle1);
        spliterators.put("spArray1 ", spArray1);
        spliterators.put("spArray2 ", spArray2);
        spliterators.put("spArray3 ", spArray3);
        spliterators.put("spArray4 ", spArray4);
        spliterators.put("spEmpty1 ", spEmpty1);

        System.out.println("Spliterator Overview");
        System.out.println("spList1   " + spList1.characteristics() + " " + spList1.estimateSize() + " " + spList1.getExactSizeIfKnown() + " " + spList1);
        System.out.println("spList2   " + spList2.characteristics() + " " + spList2.estimateSize() + " " + spList2.getExactSizeIfKnown() + " " + spList2);
        System.out.println("spSingle1 " + spSingle1.characteristics() + " " + spSingle1.estimateSize() + " " + spSingle1.getExactSizeIfKnown() + " " + spSingle1);
        System.out.println("spArray1  " + spArray1.characteristics() + " " + spArray1.estimateSize() + " " + spArray1.getExactSizeIfKnown() + " " + spArray1);
        System.out.println("spArray2  " + spArray2.characteristics() + " " + spArray2.estimateSize() + " " + spArray2.getExactSizeIfKnown() + " " + spArray2);
        System.out.println("spArray3  " + spArray3.characteristics() + " " + spArray3.estimateSize() + " " + spArray3.getExactSizeIfKnown() + " " + spArray3);
        System.out.println("spArray4  " + spArray4.characteristics() + " " + spArray4.estimateSize() + " " + spArray4.getExactSizeIfKnown() + " " + spArray4);
        System.out.println("spEmpty1  " + spEmpty1.characteristics() + " " + spEmpty1.estimateSize() + " " + spEmpty1.getExactSizeIfKnown() + " " + spEmpty1);

        System.out.println();

        System.out.println("Spliterator Characteristics");
        System.out.println("CONCURRENT");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.CONCURRENT)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("DISTINCT");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.DISTINCT)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("IMMUTABLE");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.IMMUTABLE)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("NONNULL");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.NONNULL)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("ORDERED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.ORDERED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("SIZED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.SIZED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("SORTED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.SORTED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("SUBSIZED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.SUBSIZED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);
    }

    public static void main(String... args) {
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
        showModification();
        showCharacteristics();
    }

}
