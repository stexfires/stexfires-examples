package stexfires.examples.data;

import stexfires.data.BooleanDataTypeFormatter;
import stexfires.data.BooleanDataTypeParser;
import stexfires.data.CharsetDataTypeFormatter;
import stexfires.data.CharsetDataTypeParser;
import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.LocaleDataTypeFormatter;
import stexfires.data.LocaleDataTypeParser;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesMiscDataType {

    private ExamplesMiscDataType() {
    }

    private static void testParseBoolean(String source, BooleanDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseLocale(String source, LocaleDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + localeAsString(parser.parse(source)));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatBoolean(Boolean source, BooleanDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatLocale(Locale source, LocaleDataTypeFormatter formatter) {
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

    private static void testParseCharset(String source, CharsetDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatCharset(Charset source, CharsetDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {

        System.out.println("---BooleanDataTypeFormatter");
        testFormatBoolean(null, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", null));
        testFormatBoolean(null, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));
        testFormatBoolean(Boolean.TRUE, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));
        testFormatBoolean(Boolean.FALSE, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));

        System.out.println("---BooleanDataTypeParser");
        Set<String> trueValues = Set.of("true", "TRUE");
        Set<String> falseValues = Set.of("false", "FALSE");
        testParseBoolean(null, new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean(null, new BooleanDataTypeParser(trueValues, falseValues, () -> Boolean.TRUE, null));
        testParseBoolean("", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("", new BooleanDataTypeParser(trueValues, falseValues, null, () -> Boolean.TRUE));
        testParseBoolean("true", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("TRUE", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("false", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("FALSE", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("test", new BooleanDataTypeParser(trueValues, falseValues, null, null));

        System.out.println("---LocaleDataTypeFormatter");
        testFormatLocale(null, new LocaleDataTypeFormatter(null));
        testFormatLocale(null, new LocaleDataTypeFormatter(() -> Locale.getDefault().toLanguageTag()));
        testFormatLocale(Locale.getDefault(), new LocaleDataTypeFormatter(null));
        testFormatLocale(Locale.GERMAN, new LocaleDataTypeFormatter(null));
        testFormatLocale(Locale.GERMANY, new LocaleDataTypeFormatter(null));
        testFormatLocale(Locale.ENGLISH, new LocaleDataTypeFormatter(null));
        testFormatLocale(Locale.US, new LocaleDataTypeFormatter(null));
        testFormatLocale(Locale.JAPAN, new LocaleDataTypeFormatter(null));
        testFormatLocale(Locale.forLanguageTag("und"), new LocaleDataTypeFormatter(null));

        System.out.println("---LocaleDataTypeParser");
        testParseLocale(null, new LocaleDataTypeParser(null, null));
        testParseLocale(null, new LocaleDataTypeParser(Locale::getDefault, null));
        testParseLocale("", new LocaleDataTypeParser(null, null));
        testParseLocale("", new LocaleDataTypeParser(null, Locale::getDefault));
        testParseLocale("de", new LocaleDataTypeParser(null, null));
        testParseLocale("de_DE", new LocaleDataTypeParser(null, null));
        testParseLocale("de-DE", new LocaleDataTypeParser(null, null));
        testParseLocale("de-POSIX-x-URP-lvariant-Abc-Def", new LocaleDataTypeParser(null, null));
        testParseLocale("ja-JP", new LocaleDataTypeParser(null, null));
        testParseLocale("ja-JP-x-lvariant-JP", new LocaleDataTypeParser(null, null));
        testParseLocale("ja-JP-u-ca-japanese-x-lvariant-JP", new LocaleDataTypeParser(null, null));
        testParseLocale("und", new LocaleDataTypeParser(null, null));

        System.out.println("---CharsetDataTypeFormatter");
        testFormatCharset(null, new CharsetDataTypeFormatter(null));
        testFormatCharset(null, new CharsetDataTypeFormatter(StandardCharsets.ISO_8859_1::name));
        testFormatCharset(StandardCharsets.ISO_8859_1, new CharsetDataTypeFormatter(null));

        System.out.println("---CharsetDataTypeParser");
        testParseCharset(null, new CharsetDataTypeParser(null, null));
        testParseCharset(null, new CharsetDataTypeParser(() -> StandardCharsets.ISO_8859_1, null));
        testParseCharset("", new CharsetDataTypeParser(null, null));
        testParseCharset("", new CharsetDataTypeParser(null, () -> StandardCharsets.ISO_8859_1));
        testParseCharset("ISO-8859-1", new CharsetDataTypeParser(null, null));
        testParseCharset("test", new CharsetDataTypeParser(null, null));
    }

}
