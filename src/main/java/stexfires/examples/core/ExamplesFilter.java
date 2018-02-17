package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.filter.CategoryFilter;
import stexfires.core.filter.ClassFilter;
import stexfires.core.filter.ConstantFilter;
import stexfires.core.filter.IsValidIndexFilter;
import stexfires.core.filter.MessageFilter;
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
                new SingleRecord("category", 1L, "B"),
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

    private static void showCategoryFilter() {
        System.out.println("-showCategoryFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new CategoryFilter<>(Objects::isNull)));

        System.out.println("compare");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.compare(StringComparisonType.ENDS_WITH, "y")));

        System.out.println("check");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.check(StringCheckType.EMPTY)));

        System.out.println("equalTo");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.equalTo("category")));

        System.out.println("isNotNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.isNotNull()));

        System.out.println("isNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.isNull()));

        System.out.println("containedIn Collection");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.containedIn(Strings.list("category", "Category"))));

        System.out.println("containedIn Array");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                CategoryFilter.containedIn("category", "Category")));
    }

    private static void showClassFilter() {
        System.out.println("-showClassFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new ClassFilter<>(clazz -> !EmptyRecord.class.equals(clazz))));

        System.out.println("equalTo");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ClassFilter.equalTo(PairRecord.class)));

        System.out.println("containedIn Collection");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ClassFilter.containedIn(Collections.singletonList(PairRecord.class))));
    }

    private static void showConstantFilter() {
        System.out.println("-showConstantFilter---");

        System.out.println("constructor true");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new ConstantFilter<>(true)));

        System.out.println("constructor false");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new ConstantFilter<>(false)));
    }

    private static void showIsValidIndexFilter() {
        System.out.println("-showIsValidIndexFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new IsValidIndexFilter<>(1)));
    }

    private static void showMessageFilter() {
        System.out.println("-showMessageFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new MessageFilter<>(new CategoryMessage<>(""), Predicate.isEqual("c"))));

        System.out.println("compare");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.compare(new CategoryMessage<>(""), StringComparisonType.EQUALS_IGNORE_CASE, "category")));

        System.out.println("check");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.check(new CategoryMessage<>(""), StringCheckType.EMPTY)));

        System.out.println("equalTo");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.equalTo(new CategoryMessage<>(""), "c")));

        System.out.println("isNotNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.isNotNull(new CategoryMessage<>())));

        System.out.println("isNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.isNull(new CategoryMessage<>())));

        System.out.println("containedIn Collection");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.containedIn(new CategoryMessage<>(""), Strings.list("c"))));

        System.out.println("containedIn Array");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                MessageFilter.containedIn(new CategoryMessage<>(""), "c", "")));
    }

    private static void showRecordFilter() {
        System.out.println("-showRecordFilter---");

        RecordFilter<Record> recordFilter = ClassFilter.equalTo(StandardRecord.class).and(SizeFilter.equalTo(8));

        System.out.println("concatAnd");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordFilter.concatAnd(ClassFilter.equalTo(StandardRecord.class),
                        SizeFilter.equalTo(8))));

        System.out.println("concatOr");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordFilter.concatOr(ClassFilter.equalTo(StandardRecord.class),
                        ClassFilter.equalTo(KeyValueRecord.class))));

        System.out.println("and");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ClassFilter.equalTo(StandardRecord.class).and(SizeFilter.equalTo(8))));

        System.out.println("negate");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.equalTo(1).negate()));

        System.out.println("or");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ClassFilter.equalTo(StandardRecord.class).or(ClassFilter.equalTo(KeyValueRecord.class))));
    }

    private static void showRecordIdFilter() {
        System.out.println("-showRecordIdFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new RecordIdFilter<>(value -> value == 1L)));

        System.out.println("compare");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.compare(NumberComparisonType.GREATER_THAN, 2L)));

        System.out.println("check");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.check(NumberCheckType.EVEN)));

        System.out.println("equalTo");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.equalTo(1L)));

        System.out.println("isNotNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.isNotNull()));

        System.out.println("isNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.isNull()));

        System.out.println("containedIn Collection");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.containedIn(Collections.singletonList(4L))));

        System.out.println("containedIn Array");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.containedIn(2L, 4L)));

        System.out.println("between");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                RecordIdFilter.between(3L, 5L)));
    }

    private static void showSizeFilter() {
        System.out.println("-showSizeFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new SizeFilter<>(value -> value == 0)));

        System.out.println("compare");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.compare(NumberComparisonType.GREATER_THAN, 2)));

        System.out.println("check");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.check(NumberCheckType.ODD)));

        System.out.println("equalTo");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.equalTo(1)));

        System.out.println("isEmpty");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.isEmpty()));

        System.out.println("containedIn Collection");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.containedIn(Collections.singletonList(Integer.valueOf(2)))));

        System.out.println("containedIn Array");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.containedIn(Integer.valueOf(8), null, 2)));

        System.out.println("between");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SizeFilter.between(2, 9)));
    }

    private static void showSupplierFilter() {
        System.out.println("-showSupplierFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new SupplierFilter<>(() -> false)));

        System.out.println("booleanSupplier");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SupplierFilter.booleanSupplier(() -> true)));

        System.out.println("random");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SupplierFilter.random(50)));

        System.out.println("pattern");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                SupplierFilter.pattern(true, false, false)));
    }

    private static void showValueFilter() {
        System.out.println("-showValueFilter---");

        System.out.println("constructor");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                new ValueFilter<>(1, true, value -> false)));

        System.out.println("compare");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ValueFilter.compare(1, StringComparisonType.ENDS_WITH, "t")));

        System.out.println("check");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ValueFilter.check(0, StringCheckType.EMPTY)));

        System.out.println("equalTo");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ValueFilter.equalTo(0, "S")));

        System.out.println("isNotNull");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ValueFilter.isNotNull(2)));

        System.out.println("containedIn Collection");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ValueFilter.containedIn(0, Strings.list("A", "B"))));

        System.out.println("containedIn Array");
        RecordStreams.printLines(RecordStreams.filter(generateStream(),
                ValueFilter.containedIn(0, "C", "D")));
    }

    public static void main(String[] args) {
        showCategoryFilter();
        showClassFilter();
        showConstantFilter();
        showIsValidIndexFilter();
        showMessageFilter();
        showRecordFilter();
        showRecordIdFilter();
        showSizeFilter();
        showSupplierFilter();
        showValueFilter();
    }

}
