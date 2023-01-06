package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeFormatter;
import stexfires.data.DataTypeParseException;
import stexfires.data.DataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.util.Strings;
import stexfires.util.function.StringPredicates;
import stexfires.util.function.StringUnaryOperators;
import stexfires.util.function.Suppliers;

import java.util.Locale;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesStringDataType {

    private ExamplesStringDataType() {
    }

    private static void testParse(String source, DataTypeParser<String> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(String source, DataTypeFormatter<String> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {

        System.out.println("---StringDataTypeFormatter NULL and EMPTY handling");
        testFormat(null, new StringDataTypeFormatter(null, null, null));
        testFormat(null, new StringDataTypeFormatter(null, () -> "<NULL>", null));
        testFormat("", new StringDataTypeFormatter(null, null, null));
        testFormat("", new StringDataTypeFormatter(null, null, () -> "<EMPTY>"));

        System.out.println("---StringDataTypeParser NULL and EMPTY handling");
        testParse(null, new StringDataTypeParser(null, null, null, null));
        testParse(null, new StringDataTypeParser(null, null, () -> "<NULL>", null));
        testParse("", new StringDataTypeParser(null, null, null, null));
        testParse("", new StringDataTypeParser(null, null, null, () -> "<EMPTY>"));

        System.out.println("---StringDataTypeFormatter identity");
        testFormat(null, StringDataTypeFormatter.newIdentityFormatter());
        testFormat("", StringDataTypeFormatter.newIdentityFormatter());
        testFormat("Test", StringDataTypeFormatter.newIdentityFormatter());
        testFormat("ä Ä ß s SS", StringDataTypeFormatter.newIdentityFormatter());

        System.out.println("---StringDataTypeParser identity");
        testParse(null, StringDataTypeParser.newIdentityParser());
        testParse("", StringDataTypeParser.newIdentityParser());
        testParse("Test", StringDataTypeParser.newIdentityParser());
        testParse("ä Ä ß s SS", StringDataTypeParser.newIdentityParser());

        System.out.println("---StringDataTypeFormatter upperCase");
        testFormat(null, StringDataTypeFormatter.newFormatter(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("", StringDataTypeFormatter.newFormatter(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("    ", StringDataTypeFormatter.newFormatter(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("Test", StringDataTypeFormatter.newFormatter(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("ä Ä ß s SS", StringDataTypeFormatter.newFormatter(StringUnaryOperators.upperCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeParser upperCase");
        testParse(null, StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("    ", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("test", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("TEST", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("Ä Ä SS S SS", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeFormatter lowerCase");
        testFormat("Test", StringDataTypeFormatter.newFormatter(StringUnaryOperators.lowerCase(Locale.GERMAN)));
        testFormat("ä Ä ß s SS", StringDataTypeFormatter.newFormatter(StringUnaryOperators.lowerCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeParser lowerCase");
        testParse("test", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.lowerCase(Locale.GERMAN)));
        testParse("TEST", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.lowerCase(Locale.GERMAN)));
        testParse("ä ä ß s ss", StringDataTypeParser.newParserWithEqualityCheck(StringUnaryOperators.lowerCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeFormatter surround");
        testFormat(null, new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constant("''"), Suppliers.constant("''")));
        testFormat("", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constant("''"), Suppliers.constant("''")));
        testFormat("    ", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constant("''"), Suppliers.constant("''")));
        testFormat("''", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constant("''"), Suppliers.constant("''")));
        testFormat("Test", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constant("''"), Suppliers.constant("''")));

        System.out.println("---StringDataTypeParser surroundedBy strict");
        testParse(null, new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("Test", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("''", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("'    '", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("''''", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("'Test'", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));

        System.out.println("---StringDataTypeParser surroundedBy optional");
        testParse(null, new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
        testParse("", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
        testParse("Test", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
        testParse("''", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
        testParse("'    '", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
        testParse("''''", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
        testParse("'Test'", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constant(Strings.EMPTY)));
    }

}
