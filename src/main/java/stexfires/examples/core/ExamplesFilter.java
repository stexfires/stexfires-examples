package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UnnecessaryBoxing"})
public final class ExamplesFilter {

    private ExamplesFilter() {
    }

    private static Stream<Record> generateStream() {
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

    private static void showFilter(String title, RecordFilter<Record> recordFilter) {
        System.out.println("--" + title);
        RecordStreams.printLines(RecordStreams.filter(generateStream(), recordFilter));
    }

    private static void showCategoryFilter() {
        System.out.println("-showCategoryFilter---");

        showFilter("constructor",
                new CategoryFilter<>(Objects::isNull));
        showFilter("compare",
                CategoryFilter.compare(StringComparisonType.ENDS_WITH, "y"));
        showFilter("check",
                CategoryFilter.check(StringCheckType.EMPTY));
        showFilter("equalTo",
                CategoryFilter.equalTo("category"));
        showFilter("isNotNull",
                CategoryFilter.isNotNull());
        showFilter("isNull",
                CategoryFilter.isNull());
        showFilter("containedIn Collection",
                CategoryFilter.containedIn(Strings.list("category", "Category")));
        showFilter("containedIn Array",
                CategoryFilter.containedIn("category", "Category"));
    }

    private static void showClassFilter() {
        System.out.println("-showClassFilter---");

        showFilter("constructor",
                new ClassFilter<>(clazz -> !EmptyRecord.class.equals(clazz)));
        showFilter("equalTo",
                ClassFilter.equalTo(PairRecord.class));
        showFilter("containedIn Collection",
                ClassFilter.containedIn(Collections.singletonList(PairRecord.class)));
    }

    private static void showConstantFilter() {
        System.out.println("-showConstantFilter---");

        showFilter("constructor true",
                new ConstantFilter<>(true));
        showFilter("constructor false",
                new ConstantFilter<>(false));
    }

    private static void showIsValidIndexFilter() {
        System.out.println("-showIsValidIndexFilter---");

        showFilter("constructor",
                new IsValidIndexFilter<>(1));
    }

    private static void showMessageFilter() {
        System.out.println("-showMessageFilter---");

        CategoryMessage<Record> message = new CategoryMessage<>("");
        CategoryMessage<Record> messageNull = new CategoryMessage<>();

        showFilter("constructor",
                new MessageFilter<>(message, Predicate.isEqual("c")));
        showFilter("compare",
                MessageFilter.compare(message, StringComparisonType.EQUALS_IGNORE_CASE, "category"));
        showFilter("check",
                MessageFilter.check(message, StringCheckType.EMPTY));
        showFilter("equalTo",
                MessageFilter.equalTo(message, "c"));
        showFilter("isNotNull",
                MessageFilter.isNotNull(messageNull));
        showFilter("isNull",
                MessageFilter.isNull(messageNull));
        showFilter("containedIn Collection",
                MessageFilter.containedIn(message, Strings.list("c")));
        showFilter("containedIn Array",
                MessageFilter.containedIn(message, "c", ""));
    }

    private static void showNotNullFilter() {
        System.out.println("-showNotNullFilter---");

        showFilter("constructor",
                new NotNullFilter<>());
    }

    private static void showRecordFilter() {
        System.out.println("-showRecordFilter---");

        showFilter("concatAnd",
                RecordFilter.concatAnd(ClassFilter.equalTo(StandardRecord.class), SizeFilter.equalTo(8)));
        showFilter("concatOr",
                RecordFilter.concatOr(ClassFilter.equalTo(StandardRecord.class), ClassFilter.equalTo(KeyValueRecord.class)));
        showFilter("and",
                ClassFilter.equalTo(StandardRecord.class).and(SizeFilter.equalTo(8)));
        showFilter("negate",
                SizeFilter.equalTo(1).negate());
        showFilter("or",
                ClassFilter.equalTo(StandardRecord.class).or(ClassFilter.equalTo(KeyValueRecord.class)));
    }

    private static void showRecordIdFilter() {
        System.out.println("-showRecordIdFilter---");

        showFilter("constructor",
                new RecordIdFilter<>(value -> value == 1L));
        showFilter("compare",
                RecordIdFilter.compare(NumberComparisonType.GREATER_THAN, 2L));
        showFilter("check",
                RecordIdFilter.check(NumberCheckType.EVEN));
        showFilter("equalTo",
                RecordIdFilter.equalTo(1L));
        showFilter("isNotNull",
                RecordIdFilter.isNotNull());
        showFilter("isNull",
                RecordIdFilter.isNull());
        showFilter("containedIn Collection",
                RecordIdFilter.containedIn(Collections.singletonList(4L)));
        showFilter("containedIn Array",
                RecordIdFilter.containedIn(2L, 4L));
        showFilter("between",
                RecordIdFilter.between(3L, 5L));
    }

    private static void showSizeFilter() {
        System.out.println("-showSizeFilter---");

        showFilter("constructor",
                new SizeFilter<>(value -> value == 0));
        showFilter("compare",
                SizeFilter.compare(NumberComparisonType.GREATER_THAN, 2));
        showFilter("check",
                SizeFilter.check(NumberCheckType.ODD));
        showFilter("equalTo",
                SizeFilter.equalTo(1));
        showFilter("isEmpty",
                SizeFilter.isEmpty());
        showFilter("containedIn Collection",
                SizeFilter.containedIn(Collections.singletonList(Integer.valueOf(2))));
        showFilter("containedIn Array",
                SizeFilter.containedIn(Integer.valueOf(8), null, 2));
        showFilter("between",
                SizeFilter.between(2, 9));
    }

    private static void showSupplierFilter() {
        System.out.println("-showSupplierFilter---");

        showFilter("constructor",
                new SupplierFilter<>(() -> false));
        showFilter("booleanSupplier",
                SupplierFilter.booleanSupplier(() -> true));
        showFilter("random",
                SupplierFilter.random(50));
        showFilter("pattern",
                SupplierFilter.pattern(true, false, false));
    }

    private static void showValueFilter() {
        System.out.println("-showValueFilter---");

        showFilter("constructor index",
                new ValueFilter<>(0, "A"::equals));
        showFilter("constructor index null",
                new ValueFilter<>(1, true, value -> false));
        showFilter("constructor function",
                new ValueFilter<>(Record::getLastField, "A"::equals));
        showFilter("constructor function null",
                new ValueFilter<>(record -> record.getFieldAt(1), false, "t"::equals));
        showFilter("compare index",
                ValueFilter.compare(1, StringComparisonType.ENDS_WITH, "t"));
        showFilter("compare function",
                ValueFilter.compare(Record::getLastField, StringComparisonType.EQUALS, "d"));
        showFilter("check index",
                ValueFilter.check(0, StringCheckType.EMPTY));
        showFilter("check function",
                ValueFilter.check(Record::getLastField, StringCheckType.EMPTY));
        showFilter("equalTo index",
                ValueFilter.equalTo(0, "S"));
        showFilter("equalTo function",
                ValueFilter.equalTo(Record::getLastField, "d"));
        showFilter("isNotNull index",
                ValueFilter.isNotNull(2));
        showFilter("isNotNull function",
                ValueFilter.isNotNull(Record::getLastField));
        showFilter("containedIn index Collection",
                ValueFilter.containedIn(0, Strings.list("A", "B")));
        showFilter("containedIn function Collection",
                ValueFilter.containedIn(Record::getLastField, Strings.list("A", "d")));
        showFilter("containedIn index Array",
                ValueFilter.containedIn(0, "C", "D"));
        showFilter("containedIn function Array",
                ValueFilter.containedIn(Record::getLastField, "A", "d"));
    }

    public static void main(String[] args) {
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
