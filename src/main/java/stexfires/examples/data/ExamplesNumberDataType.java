package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeParseException;
import stexfires.data.NumberDataTypeFormatter;
import stexfires.data.NumberDataTypeParser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberDataType {

    private ExamplesNumberDataType() {
    }

    private static <T extends Number> void testFormat(T source, NumberDataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T extends Number> void testParse(String source, NumberDataTypeParser<T> parser, NumberFormat formatter) {
        try {
            T parseResult = parser.parse(source);
            String formattedResult = null;
            if (parseResult != null) {
                try {
                    formattedResult = "\"" + formatter.format(parseResult) + "\" (" + parseResult.getClass().getSimpleName() + ")";
                } catch (IllegalArgumentException e) {
                    formattedResult = e.getMessage();
                }
            }
            System.out.println("Parse: \"" + source + "\". Result: " + formattedResult + " toString: " + parseResult);
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        NumberFormat numberFormat0 = NumberFormat.getIntegerInstance(Locale.GERMANY);
        if (!(numberFormat0 instanceof DecimalFormat integerFormat)) {
            throw new RuntimeException("NumberFormat is not a DecimalFormat");
        }

        NumberFormat numberFormat1 = NumberFormat.getNumberInstance(Locale.GERMANY);
        if (!(numberFormat1 instanceof DecimalFormat decimalFormat)) {
            throw new RuntimeException("NumberFormat is not a DecimalFormat");
        }
        decimalFormat.setMaximumFractionDigits(5);

        NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.GERMANY);
        if (!(numberFormat2 instanceof DecimalFormat bigDecimalFormat)) {
            throw new RuntimeException("NumberFormat is not a DecimalFormat");
        }
        bigDecimalFormat.setMaximumFractionDigits(10);
        bigDecimalFormat.setParseBigDecimal(true);

        System.out.println("---NumberDataTypeFormatter");
        testFormat(null, new NumberDataTypeFormatter<Integer>(integerFormat, null));
        testFormat(null, new NumberDataTypeFormatter<Integer>(integerFormat, () -> "NULL"));
        testFormat(1, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(-1, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Integer.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Integer.MIN_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Integer.MIN_VALUE, new NumberDataTypeFormatter<>(NumberFormat.getPercentInstance(Locale.GERMANY), () -> "NULL"));

        testFormat(Long.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(1234.567d, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.NaN, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.MAX_VALUE, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.MIN_VALUE, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.POSITIVE_INFINITY, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));
        testFormat(Double.NEGATIVE_INFINITY, new NumberDataTypeFormatter<>(decimalFormat, () -> "NULL"));

        testFormat(Short.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Byte.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(Float.MAX_VALUE, new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));

        testFormat(new BigInteger("99999999999999999999999999999999999999"), new NumberDataTypeFormatter<>(integerFormat, () -> "NULL"));
        testFormat(new BigDecimal("99999999999999999999999999999999999999.99999"), new NumberDataTypeFormatter<>(bigDecimalFormat, () -> "NULL"));

        System.out.println("---NumberDataTypeParser");
        testParse(null, new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse(null, new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), () -> -1, null), integerFormat);
        testParse("", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, () -> -1), integerFormat);
        testParse("123", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("12.345", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("a123", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("12a3", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("1,23", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("1,23", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), decimalFormat);
        testParse("1.23", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse("-123.456", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse(String.valueOf(Integer.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse(String.valueOf(10L + Integer.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);
        testParse(String.valueOf(Long.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertIntegerFunction(), null, null), integerFormat);

        testParse(String.valueOf(Long.MAX_VALUE), new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertLongFunction(), null, null), integerFormat);
        testParse("9.223.372.036.854.775.807", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertLongFunction(), null, null), integerFormat);
        testParse("9.223.372.036.854.775.808", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertLongFunction(), null, null), integerFormat);
        testParse("1.234,567", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);
        testParse("1", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);
        testParse("NaN", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);
        testParse("1.7976931348623157E308", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);
        testParse("179.769.313.486.231.570.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000.000", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);
        testParse("∞", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);
        testParse("-∞", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertDoubleFunction(), null, null), decimalFormat);

        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser.convertBigIntegerFunction(), null, null), bigDecimalFormat);
        testParse("9,99", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertBigIntegerFunction(), null, null), decimalFormat);
        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999,99999", new NumberDataTypeParser<>(decimalFormat, NumberDataTypeParser.convertBigIntegerFunction(), null, null), decimalFormat);
        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999,99999", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser.convertBigIntegerFunction(), null, null), bigDecimalFormat);

        testParse("99.999", new NumberDataTypeParser<>(integerFormat, NumberDataTypeParser.convertBigDecimalFunction(), null, null), bigDecimalFormat);
        testParse("99.999.999.999.999.999.999.999.999.999.999.999.999,99999", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser.convertBigDecimalFunction(), null, null), bigDecimalFormat);
        testParse("∞", new NumberDataTypeParser<>(bigDecimalFormat, NumberDataTypeParser.convertBigDecimalFunction(), null, null), bigDecimalFormat);
    }

}
