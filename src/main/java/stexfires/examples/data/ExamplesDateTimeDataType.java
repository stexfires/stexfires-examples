package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.DateInstantDataTypeFormatter;
import stexfires.data.DateInstantDataTypeParser;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesDateTimeDataType {

    private ExamplesDateTimeDataType() {
    }

    private static void testParse(String source, DateInstantDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(Instant source, DateInstantDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        System.out.println("---DateInstantDataTypeFormatter");
        testFormat(null, new DateInstantDataTypeFormatter(DateTimeFormatter.ISO_INSTANT, null));
        testFormat(null, new DateInstantDataTypeFormatter(DateTimeFormatter.ISO_INSTANT, () -> "NULL"));
        testFormat(Instant.now(), new DateInstantDataTypeFormatter(DateTimeFormatter.ISO_INSTANT, null));
        testFormat(Instant.now(), new DateInstantDataTypeFormatter(DateTimeFormatter.ISO_DATE, null));

        System.out.println("---DateInstantDataTypeParser");
        testParse(null, new DateInstantDataTypeParser(DateTimeFormatter.ISO_INSTANT, null, null));
        testParse(null, new DateInstantDataTypeParser(DateTimeFormatter.ISO_INSTANT, Instant::now, null));
        testParse("", new DateInstantDataTypeParser(DateTimeFormatter.ISO_INSTANT, null, null));
        testParse("", new DateInstantDataTypeParser(DateTimeFormatter.ISO_INSTANT, null, Instant::now));
        testParse("2022-12-24T13:27:43.766633800Z", new DateInstantDataTypeParser(DateTimeFormatter.ISO_INSTANT, null, null));
        testParse("2022-12-24", new DateInstantDataTypeParser(DateTimeFormatter.ISO_INSTANT, null, null));
        testParse("2022-12-24+01:00", new DateInstantDataTypeParser(DateTimeFormatter.ISO_DATE, null, null));
    }

}
