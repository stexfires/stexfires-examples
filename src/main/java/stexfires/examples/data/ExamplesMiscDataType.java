package stexfires.examples.data;

import stexfires.data.BooleanDataTypeFormatter;
import stexfires.data.BooleanDataTypeParser;
import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.LocaleDataTypeFormatter;
import stexfires.data.LocaleDataTypeParser;

import java.util.Locale;
import java.util.Set;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesMiscDataType {

    private ExamplesMiscDataType() {
    }

    private static void testParse(String source, BooleanDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParse(String source, LocaleDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + localeAsString(parser.parse(source)));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(Boolean source, BooleanDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(Locale source, LocaleDataTypeFormatter formatter) {
        try {
            System.out.println("Format: " + localeAsString(source) + ". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: " + localeAsString(source) + ". Error: " + e.getMessage());
        }
    }

    private static String localeAsString(Locale locale) {
        if (locale == null) {
            return "<NULL>";
        }
        return "\"" + locale.toLanguageTag() + "\" (" + locale + " - " + locale.hashCode() + ")";
    }

    public static void main(String... args) {

        System.out.println("---BooleanDataTypeFormatter");
        testFormat(null, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", null));
        testFormat(null, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));
        testFormat(Boolean.TRUE, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));
        testFormat(Boolean.FALSE, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));

        System.out.println("---BooleanDataTypeParser");
        Set<String> trueValues = Set.of("true", "TRUE");
        Set<String> falseValues = Set.of("false", "FALSE");
        testParse(null, new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParse(null, new BooleanDataTypeParser(trueValues, falseValues, () -> Boolean.TRUE, null));
        testParse("", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParse("", new BooleanDataTypeParser(trueValues, falseValues, null, () -> Boolean.TRUE));
        testParse("true", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParse("TRUE", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParse("false", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParse("FALSE", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParse("test", new BooleanDataTypeParser(trueValues, falseValues, null, null));

        System.out.println("---LocaleDataTypeFormatter");
        testFormat(null, new LocaleDataTypeFormatter(null));
        testFormat(null, new LocaleDataTypeFormatter(() -> Locale.getDefault().toLanguageTag()));
        testFormat(Locale.getDefault(), new LocaleDataTypeFormatter(null));
        testFormat(Locale.GERMAN, new LocaleDataTypeFormatter(null));
        testFormat(Locale.GERMANY, new LocaleDataTypeFormatter(null));
        testFormat(Locale.ENGLISH, new LocaleDataTypeFormatter(null));
        testFormat(Locale.US, new LocaleDataTypeFormatter(null));
        testFormat(Locale.JAPAN, new LocaleDataTypeFormatter(null));
        testFormat(Locale.forLanguageTag("und"), new LocaleDataTypeFormatter(null));

        System.out.println("---LocaleDataTypeParser");
        testParse(null, new LocaleDataTypeParser(null, null));
        testParse(null, new LocaleDataTypeParser(Locale::getDefault, null));
        testParse("", new LocaleDataTypeParser(null, null));
        testParse("", new LocaleDataTypeParser(null, Locale::getDefault));
        testParse("de", new LocaleDataTypeParser(null, null));
        testParse("de_DE", new LocaleDataTypeParser(null, null));
        testParse("de-DE", new LocaleDataTypeParser(null, null));
        testParse("de-POSIX-x-URP-lvariant-Abc-Def", new LocaleDataTypeParser(null, null));
        testParse("ja-JP", new LocaleDataTypeParser(null, null));
        testParse("ja-JP-x-lvariant-JP", new LocaleDataTypeParser(null, null));
        testParse("ja-JP-u-ca-japanese-x-lvariant-JP", new LocaleDataTypeParser(null, null));
        testParse("und", new LocaleDataTypeParser(null, null));
    }

}
