package stexfires.examples.data;

import stexfires.data.BooleanDataTypeFormatter;
import stexfires.data.BooleanDataTypeParser;
import stexfires.data.ConvertingDataTypeFormatter;
import stexfires.data.ConvertingDataTypeParser;
import stexfires.data.DataTypeConverterException;
import stexfires.data.DataTypeFormatter;
import stexfires.data.DataTypeParser;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;
import stexfires.util.function.ByteArrayFunctions;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static stexfires.data.DataTypeConverters.URI_TO_URL;
import static stexfires.data.DataTypeConverters.URL_TO_URI;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection", "MagicNumber", "CallToPrintStackTrace"})
public final class ExamplesMiscDataType {

    private ExamplesMiscDataType() {
    }

    private static void testParseBoolean(String source, BooleanDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseLocale(String source, GenericDataTypeParser<Locale> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + localeAsString(parser.parse(source)));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatBoolean(Boolean source, BooleanDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatLocale(Locale source, GenericDataTypeFormatter<Locale> formatter) {
        try {
            System.out.println("Format: " + localeAsString(source) + ". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
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
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatCharset(Charset source, GenericDataTypeFormatter<Charset> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    private static void testParseClass(String source, GenericDataTypeParser<Class> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    private static void testFormatClass(Class source, GenericDataTypeFormatter<Class> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseByteArray(String source, GenericDataTypeParser<byte[]> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + Arrays.toString(parser.parse(source)));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatByteArray(byte[] source, GenericDataTypeFormatter<byte[]> formatter) {
        try {
            System.out.println("Format: \"" + Arrays.toString(source) + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + Arrays.toString(source) + "\". Error: " + e.getMessage());
        }
    }

    private static <T> void testParse(String source, DataTypeParser<T> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T> void testFormat(T source, DataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
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

        System.out.println("---GenericDataTypeFormatter Class");
        testFormatClass(null, GenericDataTypeFormatter.newClassDataTypeFormatterWithSupplier(null));
        testFormatClass(null, GenericDataTypeFormatter.newClassDataTypeFormatter(null));
        testFormatClass(null, GenericDataTypeFormatter.newClassDataTypeFormatterWithSupplier(Object.class::getName));
        testFormatClass(null, GenericDataTypeFormatter.newClassDataTypeFormatter(Object.class.getName()));
        testFormatClass(Object.class, GenericDataTypeFormatter.newClassDataTypeFormatterWithSupplier(null));
        testFormatClass(Integer.class, GenericDataTypeFormatter.newClassDataTypeFormatterWithSupplier(null));
        testFormatClass(String.class, GenericDataTypeFormatter.newClassDataTypeFormatterWithSupplier(null));
        testFormatClass(ArrayList.class, GenericDataTypeFormatter.newClassDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Class");
        testParseClass(null, GenericDataTypeParser.newClassDataTypeParserWithSuppliers(null, null));
        testParseClass(null, GenericDataTypeParser.newClassDataTypeParser(null));
        testParseClass(null, GenericDataTypeParser.newClassDataTypeParserWithSuppliers(() -> Object.class, null));
        testParseClass(null, GenericDataTypeParser.newClassDataTypeParser(Object.class));
        testParseClass("", GenericDataTypeParser.newClassDataTypeParserWithSuppliers(null, null));
        testParseClass("", GenericDataTypeParser.newClassDataTypeParser(null));
        testParseClass("", GenericDataTypeParser.newClassDataTypeParserWithSuppliers(null, () -> Object.class));
        testParseClass("", GenericDataTypeParser.newClassDataTypeParser(Object.class));
        testParseClass("java.lang.Object", GenericDataTypeParser.newClassDataTypeParser(null));
        testParseClass("java.lang.Integer", GenericDataTypeParser.newClassDataTypeParser(null));
        testParseClass("java.lang.String", GenericDataTypeParser.newClassDataTypeParser(null));
        testParseClass("java.util.ArrayList", GenericDataTypeParser.newClassDataTypeParser(null));
        testParseClass("test", GenericDataTypeParser.newClassDataTypeParser(null));

        System.out.println("---GenericDataTypeFormatter byte[]");
        testFormatByteArray(null, GenericDataTypeFormatter.newByteArrayDataTypeFormatterWithSupplier(ByteArrayFunctions.toHex(), null));
        testFormatByteArray(null, GenericDataTypeFormatter.newByteArrayDataTypeFormatter(ByteArrayFunctions.toHex(), null));
        testFormatByteArray(null, GenericDataTypeFormatter.newByteArrayDataTypeFormatterWithSupplier(ByteArrayFunctions.toHex(), () -> "<NULL>"));
        testFormatByteArray(new byte[]{66, 67}, GenericDataTypeFormatter.newByteArrayDataTypeFormatter(ByteArrayFunctions.toHex(), null));
        testFormatByteArray(new byte[]{72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33}, GenericDataTypeFormatter.newByteArrayDataTypeFormatter(ByteArrayFunctions.toStringStandard(StandardCharsets.US_ASCII), null));
        testFormatByteArray(new byte[]{66, 67}, GenericDataTypeFormatter.newByteArrayDataTypeFormatter(ByteArrayFunctions.toBase64(Base64.getEncoder()), null));

        System.out.println("---GenericDataTypeParser byte[]");
        testParseByteArray(null, GenericDataTypeParser.newByteArrayDataTypeParserWithSuppliers(ByteArrayFunctions.fromHex(), null, null));
        testParseByteArray(null, GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromHex(), null));
        testParseByteArray(null, GenericDataTypeParser.newByteArrayDataTypeParserWithSuppliers(ByteArrayFunctions.fromHex(), () -> new byte[]{}, null));
        testParseByteArray(null, GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("", GenericDataTypeParser.newByteArrayDataTypeParserWithSuppliers(ByteArrayFunctions.fromHex(), null, null));
        testParseByteArray("", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromHex(), null));
        testParseByteArray("", GenericDataTypeParser.newByteArrayDataTypeParserWithSuppliers(ByteArrayFunctions.fromHex(), null, () -> new byte[]{}));
        testParseByteArray("", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("4243", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("4243Z", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("Hello world!", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromStringStandard(StandardCharsets.US_ASCII), new byte[]{}));
        testParseByteArray("QkM=", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromBase64(Base64.getDecoder()), new byte[]{}));
        testParseByteArray("--", GenericDataTypeParser.newByteArrayDataTypeParser(ByteArrayFunctions.fromBase64(Base64.getDecoder()), new byte[]{}));

        System.out.println("---GenericDataTypeFormatter Character");
        testFormat('A', GenericDataTypeFormatter.newCharacterDataTypeFormatterWithSupplier(null));
        testFormat('€', GenericDataTypeFormatter.newCharacterDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Character");
        testParse("A", GenericDataTypeParser.newCharacterDataTypeParser(null));
        testParse("€", GenericDataTypeParser.newCharacterDataTypeParser(null));
        testParse("test", GenericDataTypeParser.newCharacterDataTypeParser(null));

        System.out.println("---GenericDataTypeFormatter Currency");
        testFormat(Currency.getInstance("EUR"), GenericDataTypeFormatter.newCurrencyDataTypeFormatterWithSupplier(null));
        testFormat(Currency.getInstance(Locale.JAPAN), GenericDataTypeFormatter.newCurrencyDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Currency");
        testParse("EUR", GenericDataTypeParser.newCurrencyDataTypeParser(null));
        testParse("JPY", GenericDataTypeParser.newCurrencyDataTypeParser(null));
        testParse("test", GenericDataTypeParser.newCurrencyDataTypeParser(null));

        System.out.println("---GenericDataTypeFormatter UUID");
        testFormat(UUID.randomUUID(), GenericDataTypeFormatter.newUuidDataTypeFormatterWithSupplier(null));
        testFormat(UUID.fromString("0c9ce18d-7d41-4015-bfb4-22fca6689ab7"), GenericDataTypeFormatter.newUuidDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser UUID");
        testParse("0c9ce18d-7d41-4015-bfb4-22fca6689ab7", GenericDataTypeParser.newUuidDataTypeParser(null));
        testParse("test", GenericDataTypeParser.newUuidDataTypeParser(null));

        System.out.println("---GenericDataTypeFormatter URI");
        try {
            testFormat(new URI("https://abcd.efgh.ijkl/"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));

            testFormat(new URI("http://www.example.com:1080/docs/resource1.html#chapter1"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("mailto:java-net@www.example.com"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("news:comp.lang.java"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("urn:isbn:096139210x"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("http://example.com/languages/java/"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("sample/a/index.html#28"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("../../demo/b/index.html"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
            testFormat(new URI("file:///~/calendar"), GenericDataTypeFormatter.newUriDataTypeFormatterWithSupplier(true, null));
        } catch (URISyntaxException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("---GenericDataTypeParser URI");
        testParse("https://abcd.efgh.ijkl/", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f", GenericDataTypeParser.newUriDataTypeParser(true, null));

        testParse("http://www.example.com:1080/docs/resource1.html#chapter1", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("mailto:java-net@www.example.com", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("news:comp.lang.java", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("urn:isbn:096139210x", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("http://example.com/languages/java/", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("sample/a/index.html#28", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("../../demo/b/index.html", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("file:///~/calendar", GenericDataTypeParser.newUriDataTypeParser(true, null));

        testParse("   ", GenericDataTypeParser.newUriDataTypeParser(true, null));
        testParse("ä:ä//ä:ä?ä   ", GenericDataTypeParser.newUriDataTypeParser(true, null));

        System.out.println("---ConvertingDataTypeFormatter formatterConverterUrlToUri");
        try {
            DataTypeFormatter<URL> dataTypeFormatter = new ConvertingDataTypeFormatter<>(
                    URL_TO_URI,
                    GenericDataTypeFormatter.newUriDataTypeFormatter(true, null),
                    null,
                    null);

            testFormat(new URI("https://abcd.efgh.ijkl/").toURL(), dataTypeFormatter);
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f").toURL(), dataTypeFormatter);
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f").toURL(), dataTypeFormatter);

            testFormat(new URI("http://www.example.com:1080/docs/resource1.html#chapter1").toURL(), dataTypeFormatter);
            testFormat(new URI("mailto:java-net@www.example.com").toURL(), dataTypeFormatter);
            testFormat(new URI("http://example.com/languages/java/").toURL(), dataTypeFormatter);
            testFormat(new URI("file:///~/calendar").toURL(), dataTypeFormatter);
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("---ConvertingDataTypeParser parserConverterUriToUrl");
        DataTypeParser<URL> urlDataTypeParser = new ConvertingDataTypeParser<>(
                null,
                GenericDataTypeParser.newUriDataTypeParser(true, null),
                URI_TO_URL,
                null,
                null);
        testParse("https://abcd.efgh.ijkl/", urlDataTypeParser);
        testParse("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f", urlDataTypeParser);
        testParse("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f", urlDataTypeParser);

        testParse("http://www.example.com:1080/docs/resource1.html#chapter1", urlDataTypeParser);
        testParse("mailto:java-net@www.example.com", urlDataTypeParser);
        testParse("news:comp.lang.java", urlDataTypeParser);
        testParse("urn:isbn:096139210x", urlDataTypeParser);
        testParse("http://example.com/languages/java/", urlDataTypeParser);
        testParse("sample/a/index.html#28", urlDataTypeParser);
        testParse("../../demo/b/index.html", urlDataTypeParser);
        testParse("file:///~/calendar", urlDataTypeParser);

        testParse("   ", urlDataTypeParser);
        testParse("ä:ä//ä:ä?ä   ", urlDataTypeParser);

        System.out.println("---GenericDataTypeFormatter Path");
        testFormat(Path.of("."), GenericDataTypeFormatter.newPathDataTypeFormatterWithSupplier(null));
        testFormat(Path.of("C:\\folder\\file.txt"), GenericDataTypeFormatter.newPathDataTypeFormatterWithSupplier(null));
        testFormat(Path.of("../../folder/"), GenericDataTypeFormatter.newPathDataTypeFormatterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Path");
        testParse(".", GenericDataTypeParser.newPathDataTypeParser(null));
        testParse("C:\\folder\\file.txt", GenericDataTypeParser.newPathDataTypeParser(null));
        testParse("../../folder/", GenericDataTypeParser.newPathDataTypeParser(null));
        testParse(" ", GenericDataTypeParser.newPathDataTypeParser(null));

        try {
            System.out.println("---GenericDataTypeFormatter InetAddress");
            testFormat(InetAddress.getLocalHost(), GenericDataTypeFormatter.newInetAddressHostAddressDataTypeFormatter(null));
            testFormat(InetAddress.getLocalHost(), GenericDataTypeFormatter.newInetAddressHostNameDataTypeFormatter(null));
            testFormat(InetAddress.getLoopbackAddress(), GenericDataTypeFormatter.newInetAddressHostAddressDataTypeFormatter(null));
            testFormat(InetAddress.getLoopbackAddress(), GenericDataTypeFormatter.newInetAddressHostNameDataTypeFormatter(null));
            testFormat(InetAddress.getByName("127.0.0.1"), GenericDataTypeFormatter.newInetAddressHostAddressDataTypeFormatter(null));
            testFormat(InetAddress.getByName("127.0.0.1"), GenericDataTypeFormatter.newInetAddressHostNameDataTypeFormatter(null));
            testFormat(InetAddress.getByName("137.254.56.25"), GenericDataTypeFormatter.newInetAddressHostAddressDataTypeFormatter(null));
            testFormat(InetAddress.getByName("137.254.56.25"), GenericDataTypeFormatter.newInetAddressHostNameDataTypeFormatter(null));
            testFormat(InetAddress.getByName("java.net"), GenericDataTypeFormatter.newInetAddressHostAddressDataTypeFormatter(null));
            testFormat(InetAddress.getByName("java.net"), GenericDataTypeFormatter.newInetAddressHostNameDataTypeFormatter(null));

            System.out.println("---GenericDataTypeParser InetAddress");
            testParse("127.0.0.1", GenericDataTypeParser.newInetAddressDataTypeParser(null));
            testParse("localhost", GenericDataTypeParser.newInetAddressDataTypeParser(null));
            testParse("137.254.56.25", GenericDataTypeParser.newInetAddressDataTypeParser(null));
            testParse("java.net", GenericDataTypeParser.newInetAddressDataTypeParser(null));
            testParse(".", GenericDataTypeParser.newInetAddressDataTypeParser(null));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
