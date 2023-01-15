package stexfires.examples.data;

import stexfires.data.DataTypeFormatException;
import stexfires.data.DataTypeFormatter;
import stexfires.data.DataTypeParseException;
import stexfires.data.DataTypeParser;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;
import stexfires.data.TimeDataTypeFormatter;
import stexfires.data.TimeDataTypeParser;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesTimeDataType {

    private ExamplesTimeDataType() {
    }

    private static <T extends TemporalAccessor> void testParse(String source, DataTypeParser<T> parser, DateTimeFormatter formatter) {
        try {
            T parseResult = parser.parse(source);
            String formattedResult = null;
            if (parseResult != null) {
                try {
                    formattedResult = "\"" + formatter.format(parseResult) + "\" (" + parseResult.getClass().getSimpleName() + ")";
                } catch (DateTimeException e) {
                    formattedResult = e.getMessage();
                }
            }
            System.out.println("Parse: \"" + source + "\". Result: " + formattedResult + " toString: " + parseResult);
        } catch (DataTypeParseException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T extends TemporalAccessor> void testFormat(T source, DataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeFormatException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        DateTimeFormatter instantFormatter = DateTimeFormatter.ISO_INSTANT;
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy G", Locale.US);
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM", Locale.US);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.US);
        DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofLocalizedPattern("MMMMd").withLocale(Locale.US);
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.US);
        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);
        DateTimeFormatter japaneseDateFormatter = DateTimeFormatter.ofLocalizedPattern("GyMMd").withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
        DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        DateTimeFormatter zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

        System.out.println("---TimeDataTypeFormatter");
        testFormat(null, new TimeDataTypeFormatter<Instant>(instantFormatter, null));
        testFormat(null, new TimeDataTypeFormatter<Instant>(instantFormatter, () -> "NULL"));
        testFormat(Instant.now(), new TimeDataTypeFormatter<>(instantFormatter, null));
        testFormat(Instant.now(), new TimeDataTypeFormatter<>(DateTimeFormatter.ISO_DATE, null));
        testFormat(Year.of(2022), new TimeDataTypeFormatter<>(yearFormatter, null));
        testFormat(YearMonth.of(2022, 11), new TimeDataTypeFormatter<>(yearMonthFormatter, null));
        testFormat(Month.NOVEMBER, new TimeDataTypeFormatter<>(monthFormatter, null));
        testFormat(MonthDay.of(11, 25), new TimeDataTypeFormatter<>(monthDayFormatter, null));
        testFormat(DayOfWeek.FRIDAY, new TimeDataTypeFormatter<>(dayOfWeekFormatter, null));
        testFormat(LocalDate.of(2022, 11, 25), new TimeDataTypeFormatter<>(localDateFormatter, null));
        testFormat(JapaneseDate.from(LocalDate.of(2022, 11, 25)), new TimeDataTypeFormatter<>(japaneseDateFormatter, null));
        testFormat(LocalTime.of(22, 51, 35), new TimeDataTypeFormatter<>(localTimeFormatter, null));
        testFormat(ZonedDateTime.now(), new TimeDataTypeFormatter<>(zonedDateTimeFormatter, null));

        System.out.println("---TimeDataTypeParser");
        testParse(null, new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse(null, new TimeDataTypeParser<>(instantFormatter, Instant::from, Instant::now, null), instantFormatter);
        testParse("", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse("", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, Instant::now), instantFormatter);
        testParse("2022-11-25T22:51:35.766633800Z", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse("2022-11-25", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse("2022-11-25+01:00", new TimeDataTypeParser<>(DateTimeFormatter.ISO_DATE, Instant::from, null, null), instantFormatter);
        testParse("2022 AD", new TimeDataTypeParser<>(yearFormatter, Year::from, null, null), yearFormatter);
        testParse("2022-11", new TimeDataTypeParser<>(yearMonthFormatter, YearMonth::from, null, null), yearMonthFormatter);
        testParse("Nov", new TimeDataTypeParser<>(monthFormatter, Month::from, null, null), monthFormatter);
        testParse("November 25", new TimeDataTypeParser<>(monthDayFormatter, MonthDay::from, null, null), monthDayFormatter);
        testParse("Fri", new TimeDataTypeParser<>(dayOfWeekFormatter, DayOfWeek::from, null, null), dayOfWeekFormatter);
        testParse("25.11.2022", new TimeDataTypeParser<>(localDateFormatter, LocalDate::from, null, null), localDateFormatter);
        testParse("令和4/11/25", new TimeDataTypeParser<>(japaneseDateFormatter, JapaneseDate::from, null, null), japaneseDateFormatter);
        testParse("22:51:35", new TimeDataTypeParser<>(localTimeFormatter, LocalTime::from, null, null), localTimeFormatter);
        testParse("2022-11-25T22:51:35.1634235+01:00[Europe/Berlin]", new TimeDataTypeParser<>(zonedDateTimeFormatter, ZonedDateTime::from, null, null), zonedDateTimeFormatter);

        System.out.println("---GenericDataTypeFormatter newInstantEpochSecondsDataTypeFormatter newInstantEpochMilliDataTypeFormatter");
        testFormat(Instant.now(), GenericDataTypeFormatter.newInstantEpochSecondDataTypeFormatter(null));
        testFormat(Instant.now(), GenericDataTypeFormatter.newInstantEpochMilliDataTypeFormatter(null));

        System.out.println("---GenericDataTypeFormatter newInstantEpochSecondDataTypeParser newInstantEpochMilliDataTypeParser");
        testParse("1673760570", GenericDataTypeParser.newInstantEpochSecondDataTypeParser(null), instantFormatter);
        testParse("1673761073289", GenericDataTypeParser.newInstantEpochMilliDataTypeParser(null), instantFormatter);
    }

}
