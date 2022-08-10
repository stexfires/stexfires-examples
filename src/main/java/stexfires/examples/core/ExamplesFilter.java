package stexfires.examples.core;

import stexfires.core.TextRecord;
import stexfires.core.TextRecordStreams;
import stexfires.core.filter.CategoryFilter;
import stexfires.core.filter.ClassFilter;
import stexfires.core.filter.ConstantFilter;
import stexfires.core.filter.IsValidIndexFilter;
import stexfires.core.filter.MessageFilter;
import stexfires.core.filter.NotNullFilter;
import stexfires.core.filter.RecordFilter;
import stexfires.core.filter.RecordIdFilter;
import stexfires.core.filter.SizeFilter;
import stexfires.core.filter.SupplierFilter;
import stexfires.core.filter.ValueFilter;
import stexfires.core.message.CategoryMessage;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;
import stexfires.util.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesFilter {

    private ExamplesFilter() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new SingleRecord("category", 0L, "A"),
                new SingleRecord("category", 1L, ""),
                new SingleRecord("", 2L, "C"),
                new SingleRecord("Category", 3L, "D"),
                new SingleRecord(null, 4L, "E"),
                new SingleRecord("c", 5L, "F"),
                new PairRecord("c", 6L, "X", "Y"),
                new KeyValueRecord("key", "value"),
                new StandardRecord("Category", 7L, "S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord(),
                new EmptyRecord()
        );
    }

    private static void printFilter(String title, RecordFilter<TextRecord> recordFilter) {
        System.out.println("--" + title);
        TextRecordStreams.printLines(TextRecordStreams.filter(generateStream(), recordFilter));
    }

    private static void showCategoryFilter() {
        System.out.println("-showCategoryFilter---");

        printFilter("constructor",
                new CategoryFilter<>(Objects::isNull));
        printFilter("compare",
                CategoryFilter.compare(StringComparisonType.ENDS_WITH, "y"));
        printFilter("check",
                CategoryFilter.check(StringCheckType.EMPTY));
        printFilter("equalTo",
                CategoryFilter.equalTo("category"));
        printFilter("isNotNull",
                CategoryFilter.isNotNull());
        printFilter("isNull",
                CategoryFilter.isNull());
        printFilter("containedIn Collection",
                CategoryFilter.containedIn(Strings.list("category", "Category")));
        printFilter("containedIn Array",
                CategoryFilter.containedIn("category", "Category"));
    }

    private static void showClassFilter() {
        System.out.println("-showClassFilter---");

        printFilter("constructor",
                new ClassFilter<>(clazz -> EmptyRecord.class != clazz));
        printFilter("equalTo",
                ClassFilter.equalTo(PairRecord.class));
        printFilter("containedIn Collection",
                ClassFilter.containedIn(Collections.singletonList(PairRecord.class)));
        printFilter("containedIn List",
                ClassFilter.containedIn(List.of(PairRecord.class, EmptyRecord.class)));
    }

    private static void showConstantFilter() {
        System.out.println("-showConstantFilter---");

        printFilter("constructor true",
                new ConstantFilter<>(true));
        printFilter("constructor false",
                new ConstantFilter<>(false));
    }

    private static void showIsValidIndexFilter() {
        System.out.println("-showIsValidIndexFilter---");

        printFilter("constructor",
                new IsValidIndexFilter<>(1));
    }

    private static void showMessageFilter() {
        System.out.println("-showMessageFilter---");

        CategoryMessage<TextRecord> message = new CategoryMessage<>("");
        CategoryMessage<TextRecord> messageNull = new CategoryMessage<>();

        printFilter("constructor",
                new MessageFilter<>(message, Predicate.isEqual("c")));
        printFilter("compare",
                MessageFilter.compare(message, StringComparisonType.EQUALS_IGNORE_CASE, "category"));
        printFilter("check",
                MessageFilter.check(message, StringCheckType.EMPTY));
        printFilter("equalTo",
                MessageFilter.equalTo(message, "c"));
        printFilter("isNotNull",
                MessageFilter.isNotNull(messageNull));
        printFilter("isNull",
                MessageFilter.isNull(messageNull));
        printFilter("containedIn Collection",
                MessageFilter.containedIn(message, Strings.list("c")));
        printFilter("containedIn Array",
                MessageFilter.containedIn(message, "c", ""));
    }

    private static void showNotNullFilter() {
        System.out.println("-showNotNullFilter---");

        printFilter("constructor",
                new NotNullFilter<>());
    }

    private static void showRecordFilter() {
        System.out.println("-showRecordFilter---");

        printFilter("concatAnd",
                RecordFilter.concatAnd(ClassFilter.equalTo(StandardRecord.class), SizeFilter.equalTo(8)));
        printFilter("concatOr",
                RecordFilter.concatOr(ClassFilter.equalTo(StandardRecord.class), ClassFilter.equalTo(KeyValueRecord.class)));
        printFilter("and",
                ClassFilter.equalTo(StandardRecord.class).and(SizeFilter.equalTo(8)));
        printFilter("negate",
                SizeFilter.equalTo(1).negate());
        printFilter("or",
                ClassFilter.equalTo(StandardRecord.class).or(ClassFilter.equalTo(KeyValueRecord.class)));
    }

    private static void showRecordIdFilter() {
        System.out.println("-showRecordIdFilter---");

        printFilter("constructor",
                new RecordIdFilter<>(value -> value == 1L));
        printFilter("compare",
                RecordIdFilter.compare(NumberComparisonType.GREATER_THAN, 2L));
        printFilter("check",
                RecordIdFilter.check(NumberCheckType.EVEN));
        printFilter("equalTo",
                RecordIdFilter.equalTo(1L));
        printFilter("isNotNull",
                RecordIdFilter.isNotNull());
        printFilter("isNull",
                RecordIdFilter.isNull());
        printFilter("containedIn Collection",
                RecordIdFilter.containedIn(Collections.singletonList(4L)));
        printFilter("containedIn Array",
                RecordIdFilter.containedIn(2L, 4L));
        printFilter("between",
                RecordIdFilter.between(3L, 5L));
    }

    private static void showSizeFilter() {
        System.out.println("-showSizeFilter---");

        printFilter("constructor",
                new SizeFilter<>(value -> value == 0));
        printFilter("compare",
                SizeFilter.compare(NumberComparisonType.GREATER_THAN, 2));
        printFilter("check",
                SizeFilter.check(NumberCheckType.ODD));
        printFilter("equalTo",
                SizeFilter.equalTo(1));
        printFilter("isEmpty",
                SizeFilter.isEmpty());
        printFilter("containedIn Collection",
                SizeFilter.containedIn(Collections.singletonList(2)));
        printFilter("containedIn Array",
                SizeFilter.containedIn(8, null, 2));
        printFilter("between",
                SizeFilter.between(2, 9));
    }

    private static void showSupplierFilter() {
        System.out.println("-showSupplierFilter---");

        printFilter("constructor",
                new SupplierFilter<>(() -> false));
        printFilter("booleanSupplier",
                SupplierFilter.booleanSupplier(() -> true));
        printFilter("random",
                SupplierFilter.random(50));
        printFilter("pattern",
                SupplierFilter.pattern(true, false, false));
    }

    private static void showValueFilter() {
        System.out.println("-showValueFilter---");

        printFilter("constructor index",
                new ValueFilter<>(0, "A"::equals));
        printFilter("constructor index null",
                new ValueFilter<>(1, true, value -> false));
        printFilter("constructor function",
                new ValueFilter<>(TextRecord::lastField, "A"::equals));
        printFilter("constructor function null",
                new ValueFilter<>(record -> record.fieldAt(1), false, "t"::equals));
        printFilter("compare index",
                ValueFilter.compare(1, StringComparisonType.ENDS_WITH, "t"));
        printFilter("compare function",
                ValueFilter.compare(TextRecord::lastField, StringComparisonType.EQUALS, "d"));
        printFilter("check index",
                ValueFilter.check(0, StringCheckType.EMPTY));
        printFilter("check function",
                ValueFilter.check(TextRecord::lastField, StringCheckType.EMPTY));
        printFilter("equalTo index",
                ValueFilter.equalTo(0, "S"));
        printFilter("equalTo function",
                ValueFilter.equalTo(TextRecord::lastField, "d"));
        printFilter("isNotNull index",
                ValueFilter.isNotNull(2));
        printFilter("isNotNull function",
                ValueFilter.isNotNull(TextRecord::lastField));
        printFilter("containedIn index Collection",
                ValueFilter.containedIn(0, Strings.list("A", "B")));
        printFilter("containedIn function Collection",
                ValueFilter.containedIn(TextRecord::lastField, Strings.list("A", "d")));
        printFilter("containedIn index Array",
                ValueFilter.containedIn(0, "C", "D"));
        printFilter("containedIn function Array",
                ValueFilter.containedIn(TextRecord::lastField, "A", "d"));
    }

    public static void main(String... args) {
        showCategoryFilter();
        showClassFilter();
        showConstantFilter();
        showIsValidIndexFilter();
        showMessageFilter();
        showNotNullFilter();
        showRecordFilter();
        showRecordIdFilter();
        showSizeFilter();
        showSupplierFilter();
        showValueFilter();
    }

}
