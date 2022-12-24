package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.StringIdentityDataTypeFormatter;
import stexfires.data.StringIdentityDataTypeParser;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesStringDataType {

    private ExamplesStringDataType() {
    }

    private static void testParse(String source, StringIdentityDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(String source, StringIdentityDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        System.out.println("---StringIdentityDataTypeFormatter");
        testFormat(null, new StringIdentityDataTypeFormatter(null, null));
        testFormat(null, new StringIdentityDataTypeFormatter(() -> "<NULL>", null));
        testFormat("", new StringIdentityDataTypeFormatter(null, null));
        testFormat("", new StringIdentityDataTypeFormatter(null, () -> "<EMPTY>"));
        testFormat("Test", new StringIdentityDataTypeFormatter(null, null));

        System.out.println("---StringIdentityDataTypeParser");
        testParse(null, new StringIdentityDataTypeParser(null, null));
        testParse(null, new StringIdentityDataTypeParser(() -> "<NULL>", null));
        testParse("", new StringIdentityDataTypeParser(null, null));
        testParse("", new StringIdentityDataTypeParser(null, () -> "<EMPTY>"));
        testParse("Test", new StringIdentityDataTypeParser(null, null));
    }

}
