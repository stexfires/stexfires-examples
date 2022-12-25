package stexfires.examples.data;

import stexfires.data.BooleanDataTypeFormatter;
import stexfires.data.BooleanDataTypeParser;
import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.EnumDataTypeFormatter;
import stexfires.data.EnumDataTypeParser;
import stexfires.data.LocaleDataTypeFormatter;
import stexfires.data.LocaleDataTypeParser;
import stexfires.util.Alignment;
import stexfires.util.CommonCharsetNames;
import stexfires.util.function.StringUnaryOperators;

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

    private static void testParseAlignment(String source, EnumDataTypeParser<Alignment> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseCommonCharsetNames(String source, EnumDataTypeParser<CommonCharsetNames> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatAlignment(Alignment source, EnumDataTypeFormatter<Alignment> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatCommonCharsetNames(CommonCharsetNames source, EnumDataTypeFormatter<CommonCharsetNames> formatter) {
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

        System.out.println("---EnumDataTypeFormatter");
        testFormatAlignment(null, new EnumDataTypeFormatter<>(null));
        testFormatAlignment(null, new EnumDataTypeFormatter<>(Alignment.CENTER::name));
        testFormatAlignment(Alignment.CENTER, new EnumDataTypeFormatter<>(null));

        testFormatCommonCharsetNames(null, new EnumDataTypeFormatter<>(null));
        testFormatCommonCharsetNames(null, new EnumDataTypeFormatter<>(CommonCharsetNames.ISO_8859_1::name));
        testFormatCommonCharsetNames(CommonCharsetNames.ISO_8859_1, new EnumDataTypeFormatter<>(null));

        System.out.println("---EnumDataTypeParser");
        testParseAlignment(null, new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseAlignment(null, new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), () -> Alignment.CENTER, null));
        testParseAlignment("", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseAlignment("", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, () -> Alignment.CENTER));
        testParseAlignment("CENTER", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseAlignment("center", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseAlignment("test", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));

        testParseCommonCharsetNames(null, new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseCommonCharsetNames(null, new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), () -> CommonCharsetNames.ISO_8859_1, null));
        testParseCommonCharsetNames("", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseCommonCharsetNames("", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, () -> CommonCharsetNames.ISO_8859_1));
        testParseCommonCharsetNames("ISO_8859_1", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseCommonCharsetNames("iso_8859_1", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParseCommonCharsetNames("test", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
    }

}
