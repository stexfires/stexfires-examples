package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.IntegerDataTypeFormatter;
import stexfires.data.IntegerDataTypeParser;

import java.text.NumberFormat;
import java.util.Locale;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberDataType {

    private ExamplesNumberDataType() {
    }

    private static void testParse(String source, IntegerDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(Integer source, IntegerDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        System.out.println("---IntegerDataTypeFormatter");
        testFormat(null, new IntegerDataTypeFormatter(NumberFormat.getIntegerInstance(Locale.GERMANY), null));
        testFormat(null, new IntegerDataTypeFormatter(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> "NULL"));
        testFormat(1, new IntegerDataTypeFormatter(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> "NULL"));
        testFormat(-1, new IntegerDataTypeFormatter(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> "NULL"));
        testFormat(Integer.MAX_VALUE, new IntegerDataTypeFormatter(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> "NULL"));
        testFormat(Integer.MIN_VALUE, new IntegerDataTypeFormatter(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> "NULL"));
        testFormat(Integer.MIN_VALUE, new IntegerDataTypeFormatter(NumberFormat.getPercentInstance(Locale.GERMANY), () -> "NULL"));

        System.out.println("---IntegerDataTypeParser");
        testParse(null, new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse(null, new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> -1, null));
        testParse("", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse("", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, () -> -1));
        testParse("123", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse("a123", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse("12a3", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse("1,23", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse("1,23", new IntegerDataTypeParser(NumberFormat.getNumberInstance(Locale.GERMANY), null, null));
        testParse("-123.456", new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse(String.valueOf(Integer.MAX_VALUE), new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse(String.valueOf(10L + Integer.MAX_VALUE), new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
        testParse(String.valueOf(Long.MAX_VALUE), new IntegerDataTypeParser(NumberFormat.getIntegerInstance(Locale.GERMANY), null, null));
    }

}
