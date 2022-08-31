package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.filter.RecordIdFilter;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.mapper.ToTwoValuesRecordMapper;
import stexfires.record.message.CategoryMessage;
import stexfires.record.message.CompareMessageBuilder;
import stexfires.record.message.JoinedValuesMessage;
import stexfires.record.message.RecordIdMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.message.ValueMessage;
import stexfires.record.modifier.DistinctModifier;
import stexfires.record.modifier.FilterModifier;
import stexfires.record.modifier.GroupModifier;
import stexfires.record.modifier.IdentityModifier;
import stexfires.record.modifier.LogFilterModifier;
import stexfires.record.modifier.LogModifier;
import stexfires.record.modifier.MapModifier;
import stexfires.record.modifier.PivotModifier;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.modifier.SkipLimitModifier;
import stexfires.record.modifier.SortModifier;
import stexfires.record.modifier.UnaryGroupModifier;
import stexfires.record.modifier.UnpivotModifier;
import stexfires.util.SortNulls;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static stexfires.record.modifier.GroupModifier.*;
import static stexfires.util.NumberComparisonType.LESS_THAN;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesModifier {

    private ExamplesModifier() {
    }

    private static Stream<OneValueRecord> generateStreamOneValueRecord() {
        return Stream.of(
                new OneValueRecord("C0", 0L, "value0"),
                new OneValueRecord("C0", 1L, "value0"),
                new OneValueRecord("C1", 2L, "value0"),
                new OneValueRecord("C1", 3L, "value1"),
                new OneValueRecord("C2", 4L, "value2"),
                new OneValueRecord("C0", 5L, "value1"),
                new OneValueRecord("C0", 6L, "value2")
        );
    }

    private static Stream<OneValueRecord> generateStreamOneValueRecordGroup() {
        return Stream.of(
                new OneValueRecord("A", 0L, "a1"),
                new OneValueRecord("A", 1L, "a2"),
                new OneValueRecord("B", 2L, "b1"),
                new OneValueRecord("B", 3L, "b2"),
                new OneValueRecord("C", 4L, "c1"),
                new OneValueRecord("B", 5L, "b3"),
                new OneValueRecord("D", 6L, "d1"),
                new OneValueRecord("A", 7L, "a3"),
                new OneValueRecord("A", 8L, "a4")
        );
    }

    private static Stream<ManyValuesRecord> generateStreamManyValuesRecord() {
        return Stream.of(
                new ManyValuesRecord("C0", 0L, "B", "3", null, null),
                new ManyValuesRecord("C0", 1L, "Z", "7", "a", null),
                new ManyValuesRecord("C0", 2L, "A", "2", "a", null),
                new ManyValuesRecord("C0", 3L, "C", "2", "b", null),
                new ManyValuesRecord("C1", 4L, "X", "0", "c", null)
        );
    }

    private static void printModifierOneValueRecord(String title, RecordStreamModifier<OneValueRecord, ? extends TextRecord> recordModifier) {
        System.out.println("--" + title);
        TextRecordStreams.modifyAndConsume(generateStreamOneValueRecord(), recordModifier, new SystemOutConsumer<>());
    }

    private static void printModifierOneValueRecordGroup(String title, RecordStreamModifier<OneValueRecord, ? extends TextRecord> recordModifier) {
        System.out.println("--" + title);
        TextRecordStreams.modifyAndConsume(generateStreamOneValueRecordGroup(), recordModifier, new SystemOutConsumer<>());
    }

    private static void printModifierManyValuesRecord(String title, RecordStreamModifier<ManyValuesRecord, ? extends TextRecord> recordModifier) {
        System.out.println("--" + title);
        TextRecordStreams.modifyAndConsume(generateStreamManyValuesRecord(), recordModifier, new SystemOutConsumer<>());
    }

    private static void printPivotManyValuesRecord(String title, PivotModifier<ManyValuesRecord> recordModifier, ManyValuesRecord... records) {
        System.out.println("--" + title);
        Stream<ManyValuesRecord> recordStream = Stream.of(records);
        SystemOutConsumer<TextRecord> consumer = new SystemOutConsumer<>(new CategoryMessage<>().prepend("(").append(") ").append(new JoinedValuesMessage<>()));
        TextRecordStreams.modifyAndConsume(recordStream, recordModifier, consumer);
    }

    private static void printPivotKeyValueRecord(String title, PivotModifier<KeyValueRecord> recordModifier, KeyValueRecord... records) {
        System.out.println("--" + title);
        Stream<KeyValueRecord> recordStream = Stream.of(records);
        SystemOutConsumer<TextRecord> consumer = new SystemOutConsumer<>(new CategoryMessage<>().prepend("(").append(") ").append(new JoinedValuesMessage<>()));
        TextRecordStreams.modifyAndConsume(recordStream, recordModifier, consumer);
    }

    private static void printUnaryGroup(String title, UnaryGroupModifier<OneValueRecord> recordModifier) {
        System.out.println("--" + title);
        Stream<OneValueRecord> recordStream = Stream.of(
                new OneValueRecord("A", 1L, "a1"),
                new OneValueRecord("A", 2L, "a2"),
                new OneValueRecord("B", 3L, "b1"),
                new OneValueRecord("B", 4L, "b2"),
                new OneValueRecord("A", 5L, "a3"),
                new OneValueRecord("B", 6L, "b3"),
                new OneValueRecord("A", 7L, "a4"),
                new OneValueRecord("B", 8L, "b4"),
                new OneValueRecord("A", 9L, "a5"),
                new OneValueRecord("A", 0L, "a0"));
        TextRecordStreams.modifyAndConsume(recordStream, recordModifier, new SystemOutConsumer<>());
    }

    private static void printUnpivot(String title, UnpivotModifier<ManyValuesRecord, ? extends TextRecord> recordModifier, ManyValuesRecord... records) {
        System.out.println("--" + title);
        Stream<ManyValuesRecord> recordStream = Stream.of(records);
        TextRecordStreams.modifyAndConsume(recordStream, recordModifier, new SystemOutConsumer<>());
    }

    private static void showDistinctModifier() {
        System.out.println("-showDistinctModifier---");

        printModifierOneValueRecord("constructor category",
                new DistinctModifier<>(new CategoryMessage<>()));
        printModifierOneValueRecord("constructor recordId",
                new DistinctModifier<>(new RecordIdMessage<>()));
        printModifierOneValueRecord("constructor value",
                new DistinctModifier<>(new ValueMessage<>(OneValueRecord.VALUE_INDEX)));
        printModifierOneValueRecord("constructor CompareMessageBuilder",
                new DistinctModifier<>(new CompareMessageBuilder().category().values()));
    }

    private static void showFilterModifier() {
        System.out.println("-showFilterModifier---");

        printModifierOneValueRecord("constructor category",
                new FilterModifier<>(CategoryFilter.equalTo("C1")));
        printModifierManyValuesRecord("constructor recordId",
                new FilterModifier<>(RecordIdFilter.equalTo(1L)));
    }

    private static void showGroupModifier() {
        System.out.println("-showGroupModifier---");

        printModifierOneValueRecordGroup("constructor category; size > 1; aggregateToValue size",
                new GroupModifier<>(
                        groupByCategory(),
                        havingSizeGreaterThan(1),
                        aggregateToValue(
                                categoryOfFirstElement(),
                                list -> String.valueOf(list.size())
                        )));
        printModifierOneValueRecordGroup("constructor CategoryMessage; size < 4; aggregateToValues",
                new GroupModifier<>(
                        groupByMessage(new CategoryMessage<>()),
                        havingSize(LESS_THAN.intPredicate(4)),
                        aggregateToValues(
                                messageOfFirstElement(new CategoryMessage<>()),
                                list -> list.stream().map(ValueRecord::value).collect(Collectors.toList())
                        )));
        printModifierOneValueRecordGroup("constructor category; aggregateToValuesWithMessage",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToValuesWithMessage(
                                new CategoryMessage<>(),
                                ValueMessage.value()
                        )));

        printModifierManyValuesRecord("constructor category; aggregateToValues maxValuesNullsFirst",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToValues(
                                categoryOfFirstElement(),
                                maxValuesNullsFirst("<missing value>")
                        )));

        printModifierManyValuesRecord("constructor category; aggregateToValues minValuesNullsLast",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToValues(
                                categoryOfFirstElement(),
                                minValuesNullsLast(null)
                        )));

        printModifierOneValueRecord("constructor valueField; aggregateToValue category",
                new GroupModifier<OneValueRecord, TextRecord>(
                        groupByValue(),
                        aggregateToValue(
                                messageOfFirstElement(ValueMessage.value()),
                                list -> list.stream().map(TextRecord::category).collect(Collectors.joining(","))
                        )));
    }

    private static void showIdentityModifier() {
        System.out.println("-showIdentityModifier---");

        printModifierOneValueRecord("constructor",
                new IdentityModifier<>());
    }

    private static void showLogFilterModifier() {
        System.out.println("-showLogFilterModifier---");

        printModifierOneValueRecord("constructor CategoryFilter",
                new LogFilterModifier<>(CategoryFilter.equalTo("C1"),
                        new SystemOutLogger<>(new CategoryMessage<>().prepend("Valid: ")),
                        new SystemOutLogger<>(new ShortMessage<>().prepend("Not valid: "))
                ));
    }

    private static void showLogModifier() {
        System.out.println("-showLogModifier---");

        printModifierOneValueRecord("constructor",
                new LogModifier<>(new SystemOutLogger<>(new ShortMessage<>().prepend("Log: "))));
    }

    private static void showMapModifier() {
        System.out.println("-showMapModifier---");

        printModifierOneValueRecord("constructor ToTwoValuesRecordMapper",
                new MapModifier<>(new ToTwoValuesRecordMapper<>(OneValueRecord.VALUE_INDEX, OneValueRecord.VALUE_INDEX)));
    }

    private static void showPivotModifier() {
        System.out.println("-showPivotModifier---");

        List<String> valueClasses = new ArrayList<>();
        valueClasses.add("en");
        valueClasses.add("de");
        valueClasses.add("fr");
        valueClasses.add("jp");

        String nullValue = "--------";
        String nullValueShort = "--";

        printPivotManyValuesRecord("Pivot 1.1 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(1, 2, nullValue,
                        0, valueClasses),
                new ManyValuesRecord(null, 1L, "jp", "key1"),
                new ManyValuesRecord(null, 2L, "en", "key1", "value1en"),
                new ManyValuesRecord(null, 3L, "en", "key2", "value2en"),
                new ManyValuesRecord(null, 4L, "en", "key3"),
                new ManyValuesRecord(null, 5L, "en", "key4", "value4en"),
                new ManyValuesRecord(null, 6L, "de", "key1", "value1de"),
                new ManyValuesRecord(null, 7L, "de", "key2", "value2de"),
                new ManyValuesRecord(null, 8L, "de", "key3", "value3de"),
                new ManyValuesRecord(null, 9L, "fr", "key2", "value2fr")
        );

        printPivotKeyValueRecord("Pivot 1.2 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(KeyValueRecord::key, KeyValueRecord::value, nullValue,
                        KeyValueRecord::category, valueClasses
                ),
                new KeyValueRecord("jp", 1L, "key1", null),
                new KeyValueRecord("en", 2L, "key1", "value1en"),
                new KeyValueRecord("en", 3L, "key2", "value2en"),
                new KeyValueRecord("en", 4L, "key3", null),
                new KeyValueRecord("en", 5L, "key4", "value4en"),
                new KeyValueRecord("de", 6L, "key1", "value1de"),
                new KeyValueRecord("de", 7L, "key2", "value2de"),
                new KeyValueRecord("de", 8L, "key3", "value3de"),
                new KeyValueRecord("fr", 9L, "key2", "value2fr")
        );

        printPivotManyValuesRecord("Pivot 1.3 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(TextRecord::category, r -> r.valueAt(1), nullValue,
                        r -> r.valueAt(0), valueClasses),
                new ManyValuesRecord("key1", 1L, "jp"),
                new ManyValuesRecord("key1", 2L, "en", "value1en"),
                new ManyValuesRecord("key2", 3L, "en", "value2en"),
                new ManyValuesRecord("key3", 4L, "en"),
                new ManyValuesRecord("key4", 5L, "en", "value4en"),
                new ManyValuesRecord("key1", 6L, "de", "value1de"),
                new ManyValuesRecord("key2", 7L, "de", "value2de"),
                new ManyValuesRecord("key3", 8L, "de", "value3de"),
                new ManyValuesRecord("key2", 9L, "fr", "value2fr")
        );

        printPivotManyValuesRecord("Pivot 2 pivotWithIndexes",
                PivotModifier.pivotWithIndexes(0, 3, nullValueShort,
                        1, 2),
                new ManyValuesRecord(null, 1L, "key1", "A1", "B1", "no"),
                new ManyValuesRecord(null, 2L, "key1", "A2", "B2", "no"),
                new ManyValuesRecord(null, 3L, "key1", "A3", "B3", "no"),
                new ManyValuesRecord(null, 3L, "key1", "A4", "B4", "no"),
                new ManyValuesRecord(null, 4L, "key2", "C1", "D1"),
                new ManyValuesRecord(null, 5L, "key2", "C2", "D2"),
                new ManyValuesRecord(null, 6L, "key2", "C3", "D3"),
                new ManyValuesRecord(null, 5L, "key3", "E1", "F1"),
                new ManyValuesRecord(null, 5L, "key4", "G1"),
                new ManyValuesRecord(null, 5L, "key4", "H1"),
                new ManyValuesRecord(null, 6L, "key5", null, "I2", "I3")
        );

        printPivotKeyValueRecord("Pivot 3 constructor classification",
                new PivotModifier<>(
                        KeyValueRecord::key, KeyValueRecord::key, r -> Stream.empty(),
                        KeyValueRecord::value, nullValue, KeyValueRecord::category, valueClasses
                ),
                new KeyValueRecord("jp", 1L, "key1", null),
                new KeyValueRecord("en", 2L, "key1", "value1en"),
                new KeyValueRecord("en", 3L, "key2", "value2en"),
                new KeyValueRecord("en", 4L, "key3", null),
                new KeyValueRecord("en", 5L, "key4", "value4en"),
                new KeyValueRecord("de", 6L, "key1", "value1de"),
                new KeyValueRecord("de", 7L, "key2", "value2de"),
                new KeyValueRecord("de", 8L, "key3", "value3de"),
                new KeyValueRecord("fr", 9L, "key2", "value2fr")
        );

        List<String> valueClassifications = new ArrayList<>();
        valueClassifications.add("Type1");
        valueClassifications.add("Type2");
        valueClassifications.add("Type3");
        valueClassifications.add("Type4");

        printPivotManyValuesRecord("Pivot 4 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(0, 2, nullValue,
                        1, valueClassifications),
                new ManyValuesRecord("key2", "Type1", "Value2.1", "not relevant"),
                new ManyValuesRecord("key1", "Type1", "Value1.1", "not relevant"),
                new ManyValuesRecord("key2", "Type3", "Value2.3", "not relevant"),
                new ManyValuesRecord("key1", "Type2", "Value1.2", "not relevant"),
                new ManyValuesRecord("key3", "Type1", "Value3.1", "not relevant"),
                new ManyValuesRecord("key1", "Type3", "Value1.3", "not relevant"),
                new ManyValuesRecord("key2", "Type2", "Value2.2", "not relevant"),
                new ManyValuesRecord("key3", "Type3", "Value3.3", "not relevant"),
                new ManyValuesRecord("key4"),
                new ManyValuesRecord("key5", "Type5", "Value5.5", "not relevant")
        );
    }

    private static void showRecordStreamModifier() {
        System.out.println("-showRecordStreamModifier---");

        printModifierOneValueRecord("concat 2",
                RecordStreamModifier.concat(
                        new FilterModifier<>(CategoryFilter.equalTo("C1")),
                        new DistinctModifier<>(new CategoryMessage<>())));
        printModifierOneValueRecord("concat 3",
                RecordStreamModifier.concat(
                        new FilterModifier<>(CategoryFilter.equalTo("C1")),
                        new IdentityModifier<>(),
                        new SkipLimitModifier<>(1L, 1L)));
        printModifierOneValueRecord("compose",
                new DistinctModifier<OneValueRecord>(new CategoryMessage<>())
                        .compose(new FilterModifier<>(CategoryFilter.equalTo("C1"))));
        printModifierOneValueRecord("andThen",
                new FilterModifier<OneValueRecord>(CategoryFilter.equalTo("C1"))
                        .andThen(new DistinctModifier<>(new CategoryMessage<>())));
    }

    private static void showSkipLimitModifier() {
        System.out.println("-showSkipLimitModifier---");

        printModifierOneValueRecord("constructor",
                new SkipLimitModifier<>(2L, 3L));
        printModifierOneValueRecord("skip",
                SkipLimitModifier.skip(4L));
        printModifierOneValueRecord("limit",
                SkipLimitModifier.limit(2L));
    }

    private static void showSortModifier() {
        System.out.println("-showSortModifier---");

        printModifierOneValueRecord("constructor category",
                new SortModifier<>(RecordComparators.category(Comparator.naturalOrder(), SortNulls.FIRST)));
        printModifierOneValueRecord("constructor valueField",
                new SortModifier<>(RecordComparators.value(Comparator.naturalOrder(), SortNulls.FIRST)));
    }

    private static void showUnaryGroupModifier() {
        System.out.println("-showUnaryGroupModifier---");

        printUnaryGroup("first",
                UnaryGroupModifier.first(TextRecord::category));

        printUnaryGroup("last",
                UnaryGroupModifier.last(TextRecord::category));

        printUnaryGroup("min recordId",
                UnaryGroupModifier.min(TextRecord::category, RecordComparators.recordId(SortNulls.FIRST)));

        printUnaryGroup("max recordId",
                UnaryGroupModifier.max(TextRecord::category, RecordComparators.recordId(SortNulls.FIRST)));

        printUnaryGroup("reduce maxBy recordId",
                UnaryGroupModifier.reduce(TextRecord::category,
                        BinaryOperator.maxBy(RecordComparators.recordId(SortNulls.FIRST))));

        printUnaryGroup("collect maxBy recordId",
                UnaryGroupModifier.collect(TextRecord::category,
                        Collectors.maxBy(RecordComparators.recordId(SortNulls.FIRST)),
                        null));

        printUnaryGroup("collect reducing maxBy recordId",
                UnaryGroupModifier.collect(TextRecord::category,
                        Collectors.reducing(BinaryOperator.maxBy(RecordComparators.recordId(SortNulls.FIRST))),
                        null));
    }

    private static void showUnpivotModifier() {
        System.out.println("-showUnpivotModifier---");

        printUnpivot("Unpivot 1.1 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new ManyValuesRecord(r.valueOfFirstField(), "S 1", r.valueAt(1)),
                        new ManyValuesRecord(r.valueOfFirstField(), "S 3", r.valueAt(3))
                )),
                new ManyValuesRecord("k1", "a1", "a2", "a3"),
                new ManyValuesRecord("k2", "b1", "b2", "b3"),
                new ManyValuesRecord("k3", "c1", "c2"),
                new ManyValuesRecord("k4", "d1", "d2", "d3")
        );

        printUnpivot("Unpivot 1.2 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new ManyValuesRecord(r.category(), "S 1", r.valueAt(0)),
                        new ManyValuesRecord(r.category(), "S 3", r.valueAt(2))
                )),
                new ManyValuesRecord("k1", 1L, "a1", "a2", "a3"),
                new ManyValuesRecord("k2", 2L, "b1", "b2", "b3"),
                new ManyValuesRecord("k3", 3L, "c1", "c2"),
                new ManyValuesRecord("k4", 4L, "d1", "d2", "d3")
        );

        printUnpivot("Unpivot 1.3 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new ManyValuesRecord(r.category(), r.recordId(), "S 1", r.valueAt(0)),
                        new ManyValuesRecord(r.category(), r.recordId(), "S 3", r.valueAt(2))
                )),
                new ManyValuesRecord("k1", 1L, "a1", "a2", "a3"),
                new ManyValuesRecord("k2", 2L, "b1", "b2", "b3"),
                new ManyValuesRecord("k3", 3L, "c1", "c2"),
                new ManyValuesRecord("k4", 4L, "d1", "d2", "d3")
        );

        printUnpivot("Unpivot 2.1 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, String::valueOf, true, 1, 2, 3),
                new ManyValuesRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new ManyValuesRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new ManyValuesRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new ManyValuesRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new ManyValuesRecord("k5", "e1"),
                new ManyValuesRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new ManyValuesRecord("cat7", 7L, "k7", "g1"),
                new ManyValuesRecord("cat8", 8L, "k8")
        );

        printUnpivot("Unpivot 2.2 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, i -> "Index: " + i, false, 2, 3),
                new ManyValuesRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new ManyValuesRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new ManyValuesRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new ManyValuesRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new ManyValuesRecord("k5", "e1"),
                new ManyValuesRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new ManyValuesRecord("cat7", 7L, "k7", "g1"),
                new ManyValuesRecord("cat8", 8L, "k8")
        );

        Map<Integer, String> map23 = new TreeMap<>();
        map23.put(1, "S 1");
        map23.put(2, "S 2");
        map23.put(3, "S 3");
        printUnpivot("Unpivot 2.3 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, true, map23),
                new ManyValuesRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new ManyValuesRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new ManyValuesRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new ManyValuesRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new ManyValuesRecord("k5", "e1"),
                new ManyValuesRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new ManyValuesRecord("cat7", 7L, "k7", "g1"),
                new ManyValuesRecord("cat8", 8L, "k8")
        );

        List<Integer> keyIndexes24 = new ArrayList<>();
        keyIndexes24.add(1);
        keyIndexes24.add(0);
        Map<Integer, String> map24 = new TreeMap<>();
        map24.put(2, "S 1");
        map24.put(4, "S 3");
        printUnpivot("Unpivot 2.4 oneRecordPerValue two keys",
                UnpivotModifier.oneRecordPerValue(keyIndexes24, map24::get, false, map24.keySet()),
                new ManyValuesRecord("cat1", 1L, "k1", "k1b", "a1", "a2", "a3"),
                new ManyValuesRecord("cat2", 2L, "k2", "k2b", "b1", "b2", "b3"),
                new ManyValuesRecord("cat3", 3L, "k3", "k3b", "c1", "c2", "c3"),
                new ManyValuesRecord("cat4", 4L, "k4", "k4b", "d1", "d2", "d3"),
                new ManyValuesRecord("k5", "k5b", "e1"),
                new ManyValuesRecord("cat6", 6L, "k6", "k6b", "f1", null, "f2"),
                new ManyValuesRecord("cat7", 7L, "k7", "k7b", "g1"),
                new ManyValuesRecord("cat8", 8L, "k8", "k8b")
        );

        List<Integer> keyValues3 = new ArrayList<>();
        keyValues3.add(6);
        keyValues3.add(0);
        List<Integer> valueIndexes31 = new ArrayList<>();
        valueIndexes31.add(1);
        valueIndexes31.add(4);
        List<Integer> valueIndexes32 = new ArrayList<>();
        valueIndexes32.add(2);
        valueIndexes32.add(5);
        List<Integer> valueIndexes33 = new ArrayList<>();
        valueIndexes33.add(3);
        valueIndexes33.add(null);
        printUnpivot("Unpivot 3 oneRecordPerValues two keys, many values",
                UnpivotModifier.oneRecordPerValues(keyValues3,
                        String::valueOf,
                        valueIndexes31, valueIndexes32, valueIndexes33),
                new ManyValuesRecord("cat1", 1L, "k1", "a1 1", "a2 1", "a3 1", "b1 1", "b2 1", "k1b"),
                new ManyValuesRecord("cat2", 2L, "k2", "a1 2", "a2 2", "a3 2", "b1 2", "b2 2", "k2b"),
                new ManyValuesRecord("cat3", 3L, "k3", "a1 3", null, "a3 3", "b1 3", "b2 3", "k3b")
        );
    }

    public static void main(String... args) {
        showDistinctModifier();
        showFilterModifier();
        showGroupModifier();
        showIdentityModifier();
        showLogFilterModifier();
        showLogModifier();
        showMapModifier();
        showPivotModifier();
        showRecordStreamModifier();
        showSkipLimitModifier();
        showSortModifier();
        showUnaryGroupModifier();
        showUnpivotModifier();
    }

}
