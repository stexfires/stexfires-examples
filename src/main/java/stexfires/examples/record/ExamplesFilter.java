package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.filter.ClassFilter;
import stexfires.record.filter.ConstantFilter;
import stexfires.record.filter.IsValidIndexFilter;
import stexfires.record.filter.MessageFilter;
import stexfires.record.filter.NotNullFilter;
import stexfires.record.filter.RecordFilter;
import stexfires.record.filter.RecordIdFilter;
import stexfires.record.filter.SizeFilter;
import stexfires.record.filter.SupplierFilter;
import stexfires.record.filter.TextFilter;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.message.CategoryMessage;
import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;
import stexfires.util.Strings;
import stexfires.util.function.StringPredicates;

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
                new ValueFieldRecord("category", 0L, "A"),
                new ValueFieldRecord("category", 1L, ""),
                new ValueFieldRecord("", 2L, "C"),
                new ValueFieldRecord("Category", 3L, "D"),
                new ValueFieldRecord(null, 4L, "E"),
                new ValueFieldRecord("c", 5L, "F"),
                new TwoFieldsRecord("c", 6L, "X", "Y"),
                new KeyValueFieldsRecord("key", "value"),
                new ManyFieldsRecord("Category", 7L, "S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord(),
                TextRecords.empty()
        );
    }

    private static void printFilter(String title, RecordFilter<TextRecord> recordFilter) {
        System.out.println("--" + title);
        TextRecordStreams.printLines(TextRecordStreams.filter(generateStream(), recordFilter));
    }

    private static void showCategoryFilter() {
        System.out.println("-showCategoryFilter---");

        printFilter("constructor Objects::isNull",
                new CategoryFilter<>(Objects::isNull));
        printFilter("constructor StringPredicates.isEmpty()",
                new CategoryFilter<>(StringPredicates.isEmpty()));
        printFilter("constructor StringPredicates.endsWith()",
                new CategoryFilter<>(StringPredicates.endsWith("y")));
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
                ClassFilter.equalTo(TwoFieldsRecord.class));
        printFilter("containedIn Collection",
                ClassFilter.containedIn(Collections.singletonList(TwoFieldsRecord.class)));
        printFilter("containedIn List",
                ClassFilter.containedIn(List.of(TwoFieldsRecord.class, EmptyRecord.class)));
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

        printFilter("constructor Predicate.isEqual",
                new MessageFilter<>(message, Predicate.isEqual("c")));
        printFilter("constructor StringPredicates.isEmpty()",
                new MessageFilter<>(message, StringPredicates.isEmpty()));
        printFilter("constructor StringPredicates.equalsIgnoreCase()",
                new MessageFilter<>(message, StringPredicates.equalsIgnoreCase("category")));
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
                RecordFilter.concatAnd(ClassFilter.equalTo(ManyFieldsRecord.class), SizeFilter.equalTo(8)));
        printFilter("concatOr",
                RecordFilter.concatOr(ClassFilter.equalTo(ManyFieldsRecord.class), ClassFilter.equalTo(KeyValueFieldsRecord.class)));
        printFilter("and",
                ClassFilter.equalTo(ManyFieldsRecord.class).and(SizeFilter.equalTo(8)));
        printFilter("negate",
                SizeFilter.equalTo(1).negate());
        printFilter("or",
                ClassFilter.equalTo(ManyFieldsRecord.class).or(ClassFilter.equalTo(KeyValueFieldsRecord.class)));
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
        printFilter("primitiveBooleanSupplier",
                SupplierFilter.primitiveBooleanSupplier(() -> true));
        printFilter("random",
                SupplierFilter.random(50));
        printFilter("pattern",
                SupplierFilter.pattern(true, false, false));
    }

    private static void showValueFilter() {
        System.out.println("-showValueFilter---");

        printFilter("constructor index",
                new TextFilter<>(0, "A"::equals));
        printFilter("constructor index null",
                new TextFilter<>(1, true, value -> false));
        printFilter("constructor function",
                new TextFilter<>(TextRecord::lastField, "A"::equals));
        printFilter("constructor function null",
                new TextFilter<>(record -> record.fieldAt(1), false, "t"::equals));
        printFilter("constructor index StringPredicates.isEmpty()",
                new TextFilter<>(0, StringPredicates.isEmpty()));
        printFilter("constructor function StringPredicates.isEmpty()",
                new TextFilter<>(TextRecord::lastField, StringPredicates.isEmpty()));
        printFilter("constructor index StringPredicates.endsWith()",
                new TextFilter<>(1, StringPredicates.endsWith("t")));
        printFilter("constructor function StringPredicates.equals()",
                new TextFilter<>(TextRecord::lastField, StringPredicates.equals("d")));
        printFilter("equalTo index",
                TextFilter.equalTo(0, "S"));
        printFilter("equalTo function",
                TextFilter.equalTo(TextRecord::lastField, "d"));
        printFilter("isNotNull index",
                TextFilter.isNotNull(2));
        printFilter("isNotNull function",
                TextFilter.isNotNull(TextRecord::lastField));
        printFilter("containedIn index Collection",
                TextFilter.containedIn(0, Strings.list("A", "B")));
        printFilter("containedIn function Collection",
                TextFilter.containedIn(TextRecord::lastField, Strings.list("A", "d")));
        printFilter("containedIn index Array",
                TextFilter.containedIn(0, "C", "D"));
        printFilter("containedIn function Array",
                TextFilter.containedIn(TextRecord::lastField, "A", "d"));
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
