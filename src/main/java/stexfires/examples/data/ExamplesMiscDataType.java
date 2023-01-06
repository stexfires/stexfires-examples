package stexfires.examples.data;

import stexfires.data.BooleanDataTypeFormatter;
import stexfires.data.BooleanDataTypeParser;
import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;

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

    private static void testParseLocale(String source, GenericDataTypeParser<Locale> parser) {
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

    private static void testFormatLocale(Locale source, GenericDataTypeFormatter<Locale> formatter) {
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

    private static void testParseCharset(String source, GenericDataTypeParser<Charset> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatCharset(Charset source, GenericDataTypeFormatter<Charset> formatter) {
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

        System.out.println("---GenericDataTypeFormatter Locale");
        testFormatLocale(null, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(null, GenericDataTypeFormatter.newLocaleDataTypeFormatter(null));
        testFormatLocale(null, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(() -> Locale.getDefault().toLanguageTag()));
        testFormatLocale(null, GenericDataTypeFormatter.newLocaleDataTypeFormatter(Locale.getDefault().toLanguageTag()));
        testFormatLocale(Locale.getDefault(), GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(Locale.GERMAN, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(Locale.GERMANY, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(Locale.ENGLISH, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(Locale.US, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(Locale.JAPAN, GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));
        testFormatLocale(Locale.forLanguageTag("und"), GenericDataTypeFormatter.newLocaleDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Locale");
        testParseLocale(null, GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale(null, GenericDataTypeParser.newLocaleDataTypeParser(null));
        testParseLocale(null, GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(Locale::getDefault, null));
        testParseLocale("", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("", GenericDataTypeParser.newLocaleDataTypeParser(null));
        testParseLocale("", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, Locale::getDefault));
        testParseLocale("de", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("de_DE", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("de-DE", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("de-POSIX-x-URP-lvariant-Abc-Def", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("ja-JP", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("ja-JP-x-lvariant-JP", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("ja-JP-u-ca-japanese-x-lvariant-JP", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));
        testParseLocale("und", GenericDataTypeParser.newLocaleDataTypeParserWithSuppliers(null, null));

        System.out.println("---GenericDataTypeFormatter Charset");
        testFormatCharset(null, GenericDataTypeFormatter.newCharsetDataTypeFormatterWithSupplier(null));
        testFormatCharset(null, GenericDataTypeFormatter.newCharsetDataTypeFormatter(null));
        testFormatCharset(null, GenericDataTypeFormatter.newCharsetDataTypeFormatterWithSupplier(StandardCharsets.ISO_8859_1::name));
        testFormatCharset(null, GenericDataTypeFormatter.newCharsetDataTypeFormatter(StandardCharsets.ISO_8859_1.name()));
        testFormatCharset(StandardCharsets.ISO_8859_1, GenericDataTypeFormatter.newCharsetDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Charset");
        testParseCharset(null, GenericDataTypeParser.newCharsetDataTypeParserWithSuppliers(null, null));
        testParseCharset(null, GenericDataTypeParser.newCharsetDataTypeParser(null));
        testParseCharset(null, GenericDataTypeParser.newCharsetDataTypeParserWithSuppliers(() -> StandardCharsets.ISO_8859_1, null));
        testParseCharset(null, GenericDataTypeParser.newCharsetDataTypeParser(StandardCharsets.ISO_8859_1));
        testParseCharset("", GenericDataTypeParser.newCharsetDataTypeParserWithSuppliers(null, null));
        testParseCharset("", GenericDataTypeParser.newCharsetDataTypeParser(null));
        testParseCharset("", GenericDataTypeParser.newCharsetDataTypeParserWithSuppliers(null, () -> StandardCharsets.ISO_8859_1));
        testParseCharset("", GenericDataTypeParser.newCharsetDataTypeParser(StandardCharsets.ISO_8859_1));
        testParseCharset("ISO-8859-1", GenericDataTypeParser.newCharsetDataTypeParser(null));
        testParseCharset("test", GenericDataTypeParser.newCharsetDataTypeParser(null));
    }

}
