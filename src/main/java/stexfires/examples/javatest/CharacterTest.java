package stexfires.examples.javatest;

import stexfires.core.TextRecord;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.filter.RecordFilter;
import stexfires.core.filter.ValueFilter;
import stexfires.core.impl.StandardRecord;
import stexfires.io.RecordIOStreams;
import stexfires.io.markdown.table.MarkdownTableConsumer;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.util.Alignment;
import stexfires.util.LineSeparator;
import stexfires.util.StringComparisonType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class CharacterTest {

    private static final char MIN_CHAR = 0;
    private static final char MAX_CHAR_8_BIT = 255;
    private static final char MAX_CHAR = 65535;

    private CharacterTest() {
    }

    public static String getCharacterTypeAsString(int characterType) {
        String type;
        if (characterType == Character.COMBINING_SPACING_MARK) type = "COMBINING_SPACING_MARK";
        else if (characterType == Character.CONNECTOR_PUNCTUATION) type = "CONNECTOR_PUNCTUATION";
        else if (characterType == Character.CONTROL) type = "CONTROL";
        else if (characterType == Character.CURRENCY_SYMBOL) type = "CURRENCY_SYMBOL";
        else if (characterType == Character.DASH_PUNCTUATION) type = "DASH_PUNCTUATION";
        else if (characterType == Character.DECIMAL_DIGIT_NUMBER) type = "DECIMAL_DIGIT_NUMBER";
        else if (characterType == Character.ENCLOSING_MARK) type = "ENCLOSING_MARK";
        else if (characterType == Character.END_PUNCTUATION) type = "END_PUNCTUATION";
        else if (characterType == Character.FINAL_QUOTE_PUNCTUATION) type = "FINAL_QUOTE_PUNCTUATION";
        else if (characterType == Character.FORMAT) type = "FORMAT";
        else if (characterType == Character.INITIAL_QUOTE_PUNCTUATION) type = "INITIAL_QUOTE_PUNCTUATION";
        else if (characterType == Character.LETTER_NUMBER) type = "LETTER_NUMBER";
        else if (characterType == Character.LINE_SEPARATOR) type = "LINE_SEPARATOR";
        else if (characterType == Character.LOWERCASE_LETTER) type = "LOWERCASE_LETTER";
        else if (characterType == Character.MATH_SYMBOL) type = "MATH_SYMBOL";
        else if (characterType == Character.MODIFIER_LETTER) type = "MODIFIER_LETTER";
        else if (characterType == Character.MODIFIER_SYMBOL) type = "MODIFIER_SYMBOL";
        else if (characterType == Character.NON_SPACING_MARK) type = "NON_SPACING_MARK";
        else if (characterType == Character.OTHER_LETTER) type = "OTHER_LETTER";
        else if (characterType == Character.OTHER_NUMBER) type = "OTHER_NUMBER";
        else if (characterType == Character.OTHER_PUNCTUATION) type = "OTHER_PUNCTUATION";
        else if (characterType == Character.OTHER_SYMBOL) type = "OTHER_SYMBOL";
        else if (characterType == Character.PARAGRAPH_SEPARATOR) type = "PARAGRAPH_SEPARATOR";
        else if (characterType == Character.PRIVATE_USE) type = "PRIVATE_USE";
        else if (characterType == Character.SPACE_SEPARATOR) type = "SPACE_SEPARATOR";
        else if (characterType == Character.START_PUNCTUATION) type = "START_PUNCTUATION";
        else if (characterType == Character.SURROGATE) type = "SURROGATE";
        else if (characterType == Character.TITLECASE_LETTER) type = "TITLECASE_LETTER";
        else if (characterType == Character.UNASSIGNED) type = "UNASSIGNED";
        else if (characterType == Character.UPPERCASE_LETTER) type = "UPPERCASE_LETTER";
        else type = "---";
        return type;
    }

    public static String getDirectionality(int codePoint) {
        String directionality;
        byte unicodeDirectionality = Character.getDirectionality(codePoint);
        if (unicodeDirectionality == Character.DIRECTIONALITY_UNDEFINED) directionality = "DIRECTIONALITY_UNDEFINED";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT)
            directionality = "DIRECTIONALITY_LEFT_TO_RIGHT";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT)
            directionality = "DIRECTIONALITY_RIGHT_TO_LEFT";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC)
            directionality = "DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_EUROPEAN_NUMBER)
            directionality = "DIRECTIONALITY_EUROPEAN_NUMBER";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR)
            directionality = "DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR)
            directionality = "DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_ARABIC_NUMBER)
            directionality = "DIRECTIONALITY_ARABIC_NUMBER";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR)
            directionality = "DIRECTIONALITY_COMMON_NUMBER_SEPARATOR";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_NONSPACING_MARK)
            directionality = "DIRECTIONALITY_NONSPACING_MARK";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_BOUNDARY_NEUTRAL)
            directionality = "DIRECTIONALITY_BOUNDARY_NEUTRAL";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR)
            directionality = "DIRECTIONALITY_PARAGRAPH_SEPARATOR";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_SEGMENT_SEPARATOR)
            directionality = "DIRECTIONALITY_SEGMENT_SEPARATOR";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_WHITESPACE)
            directionality = "DIRECTIONALITY_WHITESPACE";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_OTHER_NEUTRALS)
            directionality = "DIRECTIONALITY_OTHER_NEUTRALS";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING)
            directionality = "DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE)
            directionality = "DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING)
            directionality = "DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE)
            directionality = "DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT)
            directionality = "DIRECTIONALITY_POP_DIRECTIONAL_FORMAT";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE)
            directionality = "DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE)
            directionality = "DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_FIRST_STRONG_ISOLATE)
            directionality = "DIRECTIONALITY_FIRST_STRONG_ISOLATE";
        else if (unicodeDirectionality == Character.DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE)
            directionality = "DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE";
        else directionality = "---";
        return directionality;
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static TextRecord generateCharacterRecord(Integer characterId) {
        Objects.requireNonNull(characterId);

        // character
        char ch = (char) characterId.intValue();
        String characterAsString = String.valueOf(ch);

        // characterId
        Long characterIdAsLong = characterId.longValue();
        String characterIdAsDecString = characterId.toString();
        String characterIdAsHexString = Integer.toHexString(characterId);

        // additional character infos
        int characterType = Character.getType(ch);
        String printChar = (characterType == Character.CONTROL
                || characterType == Character.SURROGATE
                || characterType == Character.UNASSIGNED
                || characterType == Character.PRIVATE_USE
        ) ? "" : characterAsString;
        String type = getCharacterTypeAsString(characterType);
        UnicodeBlock unicodeBlock = UnicodeBlock.of(characterId);
        String block = (unicodeBlock == null) ? "---" : unicodeBlock.toString();
        String name = Character.getName(characterId);
        String directionality = getDirectionality(characterId);

        return new StandardRecord(characterAsString, characterIdAsLong,
                characterIdAsDecString,
                characterIdAsHexString,
                printChar,
                type,
                block,
                name,
                directionality
        );
    }

    public static Stream<TextRecord> generateCharacterRecordStream(int firstChar, int lastChar) {
        return IntStream.rangeClosed(firstChar, lastChar)
                        .boxed()
                        .map(CharacterTest::generateCharacterRecord);
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void writeMarkdownTableFile(File outputFile, Stream<TextRecord> recordStream)
            throws ConsumerException, IOException {
        Objects.requireNonNull(outputFile);
        Objects.requireNonNull(recordStream);

        List<MarkdownTableFieldSpec> fieldSpecsConsumer = new ArrayList<>();
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Decimal code point", 5, Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Hexadecimal code point", 5, Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Char", 5, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Type", 5, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Block", 5, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Name", 5, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Directionality", 5, Alignment.START));

        MarkdownTableFileSpec consumerFileSpec = MarkdownTableFileSpec.write(
                StandardCharsets.UTF_8,
                fieldSpecsConsumer,
                LineSeparator.CR_LF);

        try (MarkdownTableConsumer consumer = consumerFileSpec.consumer(new FileOutputStream(outputFile))) {
            RecordIOStreams.write(recordStream, consumer);
        }
    }

    private static void writeFilteredFile(File outputMarkdownFile, RecordFilter<TextRecord> recordFilter)
            throws ConsumerException, IOException {
        Objects.requireNonNull(outputMarkdownFile);
        Objects.requireNonNull(recordFilter);

        System.out.println("Generate MarkdownTable file: " + outputMarkdownFile);

        writeMarkdownTableFile(outputMarkdownFile,
                generateCharacterRecordStream(MIN_CHAR, MAX_CHAR).filter(recordFilter.asPredicate()));
    }

    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            // LETTER
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_LETTER.md"),
                    ValueFilter.containedIn(3, List.of(
                            "LOWERCASE_LETTER",
                            "MODIFIER_LETTER",
                            "OTHER_LETTER",
                            "TITLECASE_LETTER",
                            "UPPERCASE_LETTER"
                    )));

            // NUMBER_SYMBOL
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_NUMBER_SYMBOL.md"),
                    ValueFilter.containedIn(3, List.of(
                            "DECIMAL_DIGIT_NUMBER",
                            "LETTER_NUMBER",
                            "OTHER_NUMBER",
                            "CURRENCY_SYMBOL",
                            "MATH_SYMBOL",
                            "MODIFIER_SYMBOL",
                            "OTHER_SYMBOL"
                    )));

            // MISC
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_MISC.md"),
                    ValueFilter.containedIn(3, List.of(
                            "LINE_SEPARATOR",
                            "PARAGRAPH_SEPARATOR",
                            "SPACE_SEPARATOR",
                            "CONNECTOR_PUNCTUATION",
                            "DASH_PUNCTUATION",
                            "END_PUNCTUATION",
                            "FINAL_QUOTE_PUNCTUATION",
                            "INITIAL_QUOTE_PUNCTUATION",
                            "OTHER_PUNCTUATION",
                            "START_PUNCTUATION",
                            "COMBINING_SPACING_MARK",
                            "ENCLOSING_MARK",
                            "FORMAT",
                            "NON_SPACING_MARK"
                    )));

            // NOT_PRINTABLE
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_NOT_PRINTABLE.md"),
                    ValueFilter.containedIn(3, List.of(
                            "CONTROL",
                            "PRIVATE_USE",
                            "SURROGATE",
                            "UNASSIGNED"
                    )));

            // Block_LATIN
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_Block_LATIN.md"),
                    ValueFilter.compare(4, StringComparisonType.CONTAINS,
                            "LATIN"
                    ));
        } catch (ConsumerException | IOException e) {
            System.out.println("Cannot generate MarkdownTable file! " + e.getMessage());
        }
    }

}
