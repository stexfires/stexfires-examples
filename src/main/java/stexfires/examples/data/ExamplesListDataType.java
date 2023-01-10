package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;
import stexfires.data.ListDataTypeFormatter;
import stexfires.data.ListDataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.util.Strings;
import stexfires.util.function.Suppliers;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber", "UnnecessaryUnicodeEscape", "ReuseOfLocalVariable", "ConstantValue"})
public final class ExamplesListDataType {

    private ExamplesListDataType() {
    }

    private static <T> void testFormat(List<T> source, ListDataTypeFormatter<T> formatter) {
        try {
            String formatResult = formatter.format(source);
            if (source == null) {
                System.out.println("Format: NULL. Result: " + formatResult);
            } else if (source.isEmpty()) {
                System.out.println("Format: EMPTY. Result: " + formatResult);
            } else {
                System.out.println("Format: " + source.size() + " " + source + ". Result: " + formatResult);
            }
        } catch (DataTypeFormatException e) {
            System.out.println("Format: " + source + ". Error: " + e.getMessage());
        }
    }

    private static <T> void testParse(String source, ListDataTypeParser<T> parser) {
        try {
            List<T> parseResult = parser.parse(source);
            if (parseResult == null) {
                System.out.println("Parse: \"" + source + "\". Result: List is NULL");
            } else if (parseResult.isEmpty()) {
                System.out.println("Parse: \"" + source + "\". Result: List is EMPTY: " + parseResult.getClass().getSimpleName());
            } else {
                System.out.println("Parse: \"" + source + "\". Result: " + parseResult.size() + " " + parseResult);
            }
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        System.out.println("---ListDataTypeFormatter withDelimiter stringDelimitedList");
        List<String> stringDelimitedList = null;
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        stringDelimitedList = new ArrayList<>();
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        stringDelimitedList.add("a");
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        stringDelimitedList.add("b");
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        stringDelimitedList.add("c");
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        stringDelimitedList.add("");
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        stringDelimitedList.add(null);
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", StringDataTypeFormatter.newIdentityFormatter(), null));
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", new StringDataTypeFormatter(null, null), null));
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", new StringDataTypeFormatter(null, Suppliers.constant("<null>")), null));
        stringDelimitedList.add(", ");
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter("[", "]", ", ", new StringDataTypeFormatter(null, Suppliers.constant("<null>")), null));
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter(null, null, "|", new StringDataTypeFormatter(null, Suppliers.constant("<null>")), null));
        stringDelimitedList.add("g");
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter(null, null, "|", new StringDataTypeFormatter(null, Suppliers.constant("<null>")), null));
        testFormat(stringDelimitedList, ListDataTypeFormatter.withDelimiter(null, ";", ";", new StringDataTypeFormatter(null, Suppliers.constant("<null>")), null));

        System.out.println("---ListDataTypeFormatter withDelimiter integerDelimitedList");
        List<Integer> integerDelimitedList = new ArrayList<>();
        integerDelimitedList.add(42);
        testFormat(integerDelimitedList, ListDataTypeFormatter.withDelimiter(null, null, "; ", GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));
        integerDelimitedList.add(7);
        testFormat(integerDelimitedList, ListDataTypeFormatter.withDelimiter(null, null, "; ", GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));
        integerDelimitedList.add(23);
        testFormat(integerDelimitedList, ListDataTypeFormatter.withDelimiter(null, null, "; ", GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));
        integerDelimitedList.add(null);
        testFormat(integerDelimitedList, ListDataTypeFormatter.withDelimiter(null, null, "; ", GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));

        System.out.println("---ListDataTypeFormatter withValueLength stringLengthList");
        List<String> stringLengthList = new ArrayList<>();
        stringLengthList.add("He");
        stringLengthList.add("ll");
        stringLengthList.add("o ");
        stringLengthList.add("wo");
        stringLengthList.add("rl");
        stringLengthList.add("d!");
        testFormat(stringLengthList, ListDataTypeFormatter.withValueLength("\"", "\"", 2, StringDataTypeFormatter.newIdentityFormatter(), null));
        stringLengthList.add("!");
        testFormat(stringLengthList, ListDataTypeFormatter.withValueLength("\"", "\"", 2, StringDataTypeFormatter.newIdentityFormatter(), null));

        System.out.println("---ListDataTypeFormatter withValueLength stringBitLengthList");
        List<String> stringBitLengthList = Strings.splitTextByLength("1001101100", 1).toList();
        testFormat(stringBitLengthList, ListDataTypeFormatter.withValueLength(null, null, 1, StringDataTypeFormatter.newIdentityFormatter(), null));

        System.out.println("---ListDataTypeFormatter withValueLength integerLengthList");
        List<Integer> integerLengthList = new ArrayList<>();
        integerLengthList.add(2);
        testFormat(integerLengthList, ListDataTypeFormatter.withValueLength(null, null, 1, GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));
        integerLengthList.add(8);
        integerLengthList.add(1);
        integerLengthList.add(9);
        testFormat(integerLengthList, ListDataTypeFormatter.withValueLength("(", ")", 1, GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));
        integerLengthList.add(99);
        testFormat(integerLengthList, ListDataTypeFormatter.withValueLength("(", ")", 1, GenericDataTypeFormatter.newIntegerRadixDataType(10, null), null));

        System.out.println("---ListDataTypeParser withDelimiter String");
        testParse(null, ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse(" ", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[ ]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[a]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[a, b]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[a, b, c]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[a, b, c, ]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[a, b, c, , e]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[a, b, c, , e, ]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));

        testParse("[, ]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[, , ]", ListDataTypeParser.withDelimiter("[", "]", ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));

        testParse("", ListDataTypeParser.withDelimiter(null, null, ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse(" ", ListDataTypeParser.withDelimiter(null, null, ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a", ListDataTypeParser.withDelimiter(null, null, ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a, b", ListDataTypeParser.withDelimiter(null, null, ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a, b, c", ListDataTypeParser.withDelimiter(null, null, ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a, b, c, ", ListDataTypeParser.withDelimiter(null, null, ", ", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));

        testParse("", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse(".", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("..", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a.", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse(".a", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse(".a.", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a.b", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("a", ListDataTypeParser.withDelimiter(null, null, ".", StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));

        System.out.println("---ListDataTypeParser withDelimiter Integer");
        testParse("", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse(" ", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("; ", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7; 23", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7; 23;", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7; 23; ", ListDataTypeParser.withDelimiter(null, null, "; ", GenericDataTypeParser.newIntegerRadixDataType(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));

        System.out.println("---ListDataTypeParser withValueLength String");
        testParse("", ListDataTypeParser.withValueLength(null, null, 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("[]", ListDataTypeParser.withValueLength("[", "]", 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("H", ListDataTypeParser.withValueLength(null, null, 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("He", ListDataTypeParser.withValueLength(null, null, 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("Hel", ListDataTypeParser.withValueLength(null, null, 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("Hello world", ListDataTypeParser.withValueLength(null, null, 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
        testParse("Hello world!", ListDataTypeParser.withValueLength(null, null, 2, StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));

        System.out.println("---ListDataTypeParser String splitTextByCodePointsFunction");
        testParse("aä\uD83D\uDE00o\u0308", new ListDataTypeParser<>(null, null, Strings.splitTextByCodePointsFunction(), StringDataTypeParser.newIdentityParser(), null, Suppliers.constantNull()));
    }

}
