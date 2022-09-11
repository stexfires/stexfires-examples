package stexfires.examples.util;

import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.RandomStringSupplier;
import stexfires.util.supplier.SequenceStringSupplier;
import stexfires.util.supplier.ThreadNameStringSupplier;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesStringSupplier {

    private ExamplesStringSupplier() {
    }

    private static void printStream(String title, Stream<String> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showLocalTimeStringSupplier() {
        System.out.println("-showLocalTimeStringSupplier---");

        printStream("constructor",
                Stream.generate(
                        new LocalTimeStringSupplier()));
    }

    private static void showSequenceStringSupplier() {
        System.out.println("-showSequenceStringSupplier---");

        printStream("constructor 1.000",
                Stream.generate(
                        new SequenceStringSupplier(1_000L)));
    }

    private static void showThreadNameStringSupplier() {
        System.out.println("-showThreadNameStringSupplier---");

        printStream("constructor",
                Stream.generate(
                        new ThreadNameStringSupplier()));
    }

    private static void showRandomStringSupplier() {
        System.out.println("-showRandomStringSupplier---");

        RandomGenerator random = new Random();

        printStream("uuid",
                Stream.generate(
                        RandomStringSupplier.uuid()));
        printStream("stringSelection List",
                Stream.generate(
                        RandomStringSupplier.stringSelection(random, List.of("Aaa", "Bbb", "Ccc"))));
        printStream("stringSelection Array",
                Stream.generate(
                        RandomStringSupplier.stringSelection(random, "Aaa", "Bbb", "Ccc")));
        printStream("stringSelection Array",
                Stream.generate(
                        RandomStringSupplier.stringSelection(random, "Aaa")));
        printStream("characterConcatenation Boundary A-z isAlphabetic",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, 'A', 'z',
                                Character::isAlphabetic, () -> random.nextLong(5, 20)
                        )));
        printStream("characterConcatenation Boundary 32-255 isLetterOrDigit",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, 32, 255,
                                Character::isLetterOrDigit, () -> random.nextLong(5, 20)
                        )));
        printStream("characterConcatenation Boundary 32-255 DASH_PUNCTUATION || CURRENCY_SYMBOL",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, 32, 255,
                                c -> {
                                    var type = Character.getType(c);
                                    return type == Character.DASH_PUNCTUATION || type == Character.CURRENCY_SYMBOL;
                                }, () -> random.nextLong(5, 20)
                        )));
        printStream("characterConcatenation sourceCharacters",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, "Hello world!",
                                () -> random.nextLong(5, 20))));
    }

    public static void main(String... args) {
        showLocalTimeStringSupplier();
        showSequenceStringSupplier();
        showThreadNameStringSupplier();
        showRandomStringSupplier();
    }

}
