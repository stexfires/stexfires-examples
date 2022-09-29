package stexfires.examples.util;

import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.RandomStringSupplier;
import stexfires.util.supplier.SequenceStringSupplier;
import stexfires.util.supplier.ThreadNameStringSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
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

    @SuppressWarnings("CharUsedInArithmeticContext")
    private static void showRandomStringSupplier() {
        System.out.println("-showRandomStringSupplier---");

        RandomGenerator random = new Random();

        printStream("uuid",
                Stream.generate(
                        RandomStringSupplier.uuid()));

        printStream("stringSelection List 3",
                Stream.generate(
                        RandomStringSupplier.stringSelection(random, List.of("Aaa", "Bbb", "Ccc"))));
        printStream("stringSelection Array 3",
                Stream.generate(
                        RandomStringSupplier.stringSelection(random, "Aaa", "Bbb", null, "Ccc")));
        printStream("stringSelection Array 1",
                Stream.generate(
                        RandomStringSupplier.stringSelection(random, "Aaa")));

        printStream("codePointConcatenation Boundary A-z isAlphabetic",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20), 'A', 'z',
                                Character::isAlphabetic
                        )));
        printStream("codePointConcatenation Boundary 0-255 isLetterOrDigit",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20), 0, 255,
                                Character::isLetterOrDigit
                        )));
        printStream("codePointConcatenation Boundary 32-255 DASH_PUNCTUATION || CURRENCY_SYMBOL",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20), 32, 255,
                                c -> {
                                    var type = Character.getType(c);
                                    return (type == Character.DASH_PUNCTUATION) || (type == Character.CURRENCY_SYMBOL);
                                }
                        )));
        printStream("codePointConcatenation Boundary 128512-128515 smileys",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20), 128512, 128515,
                                codePoint -> true
                        )));

        printStream("codePointConcatenation Boundary large codePoints isLetterOrDigit",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20), Character.MAX_VALUE + 1, Integer.MAX_VALUE - 1,
                                codePoint -> Character.isValidCodePoint(codePoint) && Character.isDefined(codePoint) && Character.isLetterOrDigit(codePoint)
                        )));

        printStream("codePointConcatenation List 3",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20),
                                List.of(65, "€".codePointAt(0), 128512))));
        printStream("codePointConcatenation Array 3",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20),
                                65, "€".codePointAt(0), 128512)));
        printStream("codePointConcatenation String",
                Stream.generate(
                        RandomStringSupplier.codePointConcatenation(random, () -> random.nextInt(5, 20),
                                "Hello world! \uD83D\uDE00")));

        printStream("characterConcatenation List 3",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, () -> random.nextInt(5, 20),
                                List.of('A', 'B', 'C'))));

        List<Character> charactersWithNull = new ArrayList<>(3);
        charactersWithNull.add('A');
        charactersWithNull.add(null);
        charactersWithNull.add('C');
        printStream("characterConcatenation List 3 with null",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, () -> random.nextInt(5, 20),
                                charactersWithNull)));

        printStream("characterConcatenation Array 3",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, () -> random.nextInt(5, 20),
                                'a', 'b', 'c')));

        printStream("characterConcatenation Array 3 with null",
                Stream.generate(
                        RandomStringSupplier.characterConcatenation(random, () -> random.nextInt(5, 20),
                                'a', null, 'c')));

        printStream("stringCutting",
                Stream.generate(
                        RandomStringSupplier.stringCutting(() -> 4 * random.nextInt(0, 5), () -> 4,
                                "0123ABCD4567abcdXYZ")));
    }

    public static void main(String... args) {
        showLocalTimeStringSupplier();
        showSequenceStringSupplier();
        showThreadNameStringSupplier();
        showRandomStringSupplier();
    }

}
