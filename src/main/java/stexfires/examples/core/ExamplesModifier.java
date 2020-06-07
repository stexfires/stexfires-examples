package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.comparator.NULLS;
import stexfires.core.comparator.RecordComparators;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.filter.CategoryFilter;
import stexfires.core.filter.RecordIdFilter;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.mapper.ToPairMapper;
import stexfires.core.message.CategoryMessage;
import stexfires.core.message.CompareMessageBuilder;
import stexfires.core.message.JoinedValuesMessage;
import stexfires.core.message.RecordIdMessage;
import stexfires.core.message.ShortMessage;
import stexfires.core.message.ValueMessage;
import stexfires.core.modifier.DistinctModifier;
import stexfires.core.modifier.FilterModifier;
import stexfires.core.modifier.GroupModifier;
import stexfires.core.modifier.IdentityModifier;
import stexfires.core.modifier.LogFilterModifier;
import stexfires.core.modifier.LogModifier;
import stexfires.core.modifier.MapModifier;
import stexfires.core.modifier.PivotModifier;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.modifier.SkipLimitModifier;
import stexfires.core.modifier.SortModifier;
import stexfires.core.modifier.UnaryGroupModifier;
import stexfires.core.modifier.UnpivotModifier;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static stexfires.core.modifier.GroupModifier.*;
import static stexfires.util.NumberComparisonType.LESS_THAN;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesModifier {

    private ExamplesModifier() {
    }

    private static Stream<SingleRecord> generateStreamSingleRecord() {
        return Stream.of(
                new SingleRecord("C0", 0L, "value0"),
                new SingleRecord("C0", 1L, "value0"),
                new SingleRecord("C1", 2L, "value0"),
                new SingleRecord("C1", 3L, "value1"),
                new SingleRecord("C2", 4L, "value2"),
                new SingleRecord("C0", 5L, "value1"),
                new SingleRecord("C0", 6L, "value2")
        );
    }

    private static Stream<SingleRecord> generateStreamSingleRecordGroup() {
        return Stream.of(
                new SingleRecord("A", 0L, "a1"),
                new SingleRecord("A", 1L, "a2"),
                new SingleRecord("B", 2L, "b1"),
                new SingleRecord("B", 3L, "b2"),
                new SingleRecord("C", 4L, "c1"),
                new SingleRecord("B", 5L, "b3"),
                new SingleRecord("D", 6L, "d1"),
                new SingleRecord("A", 7L, "a3"),
                new SingleRecord("A", 8L, "a4")
        );
    }

    private static Stream<StandardRecord> generateStreamStandardRecord() {
        return Stream.of(
                new StandardRecord("C0", 0L, "B", "3", null, null),
                new StandardRecord("C0", 1L, "Z", "7", "a", null),
                new StandardRecord("C0", 2L, "A", "2", "a", null),
                new StandardRecord("C0", 3L, "C", "2", "b", null),
                new StandardRecord("C1", 4L, "X", "0", "c", null)
        );
    }

    private static void showModifierSingleRecord(String title, RecordStreamModifier<SingleRecord, ? extends Record> recordModifier) {
        System.out.println("--" + title);
        RecordStreams.modifyAndConsume(generateStreamSingleRecord(), recordModifier, new SystemOutConsumer<>());
    }

    private static void showModifierSingleRecordGroup(String title, RecordStreamModifier<SingleRecord, ? extends Record> recordModifier) {
        System.out.println("--" + title);
        RecordStreams.modifyAndConsume(generateStreamSingleRecordGroup(), recordModifier, new SystemOutConsumer<>());
    }

    private static void showModifierStandardRecord(String title, RecordStreamModifier<StandardRecord, ? extends Record> recordModifier) {
        System.out.println("--" + title);
        RecordStreams.modifyAndConsume(generateStreamStandardRecord(), recordModifier, new SystemOutConsumer<>());
    }

    private static void showPivotStandardRecord(String title, PivotModifier<StandardRecord> recordModifier, StandardRecord... records) {
        System.out.println("--" + title);
        Stream<StandardRecord> recordStream = Stream.of(records);
        SystemOutConsumer<Record> consumer = new SystemOutConsumer<>(new CategoryMessage<>().prepend("(").append(") ").append(new JoinedValuesMessage<>()));
        RecordStreams.modifyAndConsume(recordStream, recordModifier, consumer);
    }

    private static void showPivotKeyValueRecord(String title, PivotModifier<KeyValueRecord> recordModifier, KeyValueRecord... records) {
        System.out.println("--" + title);
        Stream<KeyValueRecord> recordStream = Stream.of(records);
        SystemOutConsumer<Record> consumer = new SystemOutConsumer<>(new CategoryMessage<>().prepend("(").append(") ").append(new JoinedValuesMessage<>()));
        RecordStreams.modifyAndConsume(recordStream, recordModifier, consumer);
    }

    private static void showUnaryGroup(String title, UnaryGroupModifier<SingleRecord> recordModifier) {
        System.out.println("--" + title);
        Stream<SingleRecord> recordStream = Stream.of(
                new SingleRecord("A", 1L, "a1"),
                new SingleRecord("A", 2L, "a2"),
                new SingleRecord("B", 3L, "b1"),
                new SingleRecord("B", 4L, "b2"),
                new SingleRecord("A", 5L, "a3"),
                new SingleRecord("B", 6L, "b3"),
                new SingleRecord("A", 7L, "a4"),
                new SingleRecord("B", 8L, "b4"),
                new SingleRecord("A", 9L, "a5"),
                new SingleRecord("A", 0L, "a0"));
        RecordStreams.modifyAndConsume(recordStream, recordModifier, new SystemOutConsumer<>());
    }

    private static void showUnpivot(String title, UnpivotModifier<StandardRecord, ? extends Record> recordModifier, StandardRecord... records) {
        System.out.println("--" + title);
        Stream<StandardRecord> recordStream = Stream.of(records);
        RecordStreams.modifyAndConsume(recordStream, recordModifier, new SystemOutConsumer<>());
    }

    private static void showDistinctModifier() {
        System.out.println("-showDistinctModifier---");

        showModifierSingleRecord("constructor category",
                new DistinctModifier<>(new CategoryMessage<>()));
        showModifierSingleRecord("constructor recordId",
                new DistinctModifier<>(new RecordIdMessage<>()));
        showModifierSingleRecord("constructor value",
                new DistinctModifier<>(new ValueMessage<>(SingleRecord.VALUE_INDEX)));
        showModifierSingleRecord("constructor CompareMessageBuilder",
                new DistinctModifier<>(new CompareMessageBuilder().category().values()));
    }

    private static void showFilterModifier() {
        System.out.println("-showFilterModifier---");

        showModifierSingleRecord("constructor category",
                new FilterModifier<>(CategoryFilter.equalTo("C1")));
        showModifierStandardRecord("constructor recordId",
                new FilterModifier<>(RecordIdFilter.equalTo(1L)));
    }

    private static void showGroupModifier() {
        System.out.println("-showGroupModifier---");

        showModifierSingleRecordGroup("constructor category; size > 1; aggregateToValue size",
                new GroupModifier<>(
                        groupByCategory(),
                        havingSizeGreaterThan(1),
                        aggregateToValue(
                                categoryOfFirstElement(),
                                list -> String.valueOf(list.size())
                        )));
        showModifierSingleRecordGroup("constructor CategoryMessage; size < 4; aggregateToValues",
                new GroupModifier<>(
                        groupByMessage(new CategoryMessage<>()),
                        havingSize(LESS_THAN.intPredicate(4)),
                        aggregateToValues(
                                messageOfFirstElement(new CategoryMessage<>()),
                                list -> list.stream().map(ValueRecord::getValueOfValueField).collect(Collectors.toList())
                        )));
        showModifierSingleRecordGroup("constructor category; aggregateToValuesWithMessage",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToValuesWithMessage(
                                new CategoryMessage<>(),
                                ValueMessage.value()
                        )));

        showModifierStandardRecord("constructor category; aggregateToValues maxValuesNullsFirst",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToValues(
                                categoryOfFirstElement(),
                                maxValuesNullsFirst("<missing value>")
                        )));

        showModifierStandardRecord("constructor category; aggregateToValues minValuesNullsLast",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToValues(
                                categoryOfFirstElement(),
                                minValuesNullsLast(null)
                        )));

        showModifierSingleRecord("constructor valueField; aggregateToValue category",
                new GroupModifier<SingleRecord, Record>(
                        groupByValueField(),
                        aggregateToValue(
                                messageOfFirstElement(ValueMessage.value()),
                                list -> list.stream().map(Record::getCategory).collect(Collectors.joining(","))
                        )));
    }

    private static void showIdentityModifier() {
        System.out.println("-showIdentityModifier---");

        showModifierSingleRecord("constructor",
                new IdentityModifier<>());
    }

    private static void showLogFilterModifier() {
        System.out.println("-showLogFilterModifier---");

        showModifierSingleRecord("constructor CategoryFilter",
                new LogFilterModifier<>(CategoryFilter.equalTo("C1"),
                        new SystemOutLogger<>(new CategoryMessage<>().prepend("Valid: ")),
                        new SystemOutLogger<>(new ShortMessage<>().prepend("Not valid: "))
                ));
    }

    private static void showLogModifier() {
        System.out.println("-showLogModifier---");

        showModifierSingleRecord("constructor",
                new LogModifier<>(new SystemOutLogger<>(new ShortMessage<>().prepend("Log: "))));
    }

    private static void showMapModifier() {
        System.out.println("-showMapModifier---");

        showModifierSingleRecord("constructor ToPairMapper",
                new MapModifier<>(new ToPairMapper<>(SingleRecord.VALUE_INDEX, SingleRecord.VALUE_INDEX)));
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

        showPivotStandardRecord("Pivot 1.1 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(1, 2, nullValue,
                        0, valueClasses),
                new StandardRecord(null, 1L, "jp", "key1"),
                new StandardRecord(null, 2L, "en", "key1", "value1en"),
                new StandardRecord(null, 3L, "en", "key2", "value2en"),
                new StandardRecord(null, 4L, "en", "key3"),
                new StandardRecord(null, 5L, "en", "key4", "value4en"),
                new StandardRecord(null, 6L, "de", "key1", "value1de"),
                new StandardRecord(null, 7L, "de", "key2", "value2de"),
                new StandardRecord(null, 8L, "de", "key3", "value3de"),
                new StandardRecord(null, 9L, "fr", "key2", "value2fr")
        );

        showPivotKeyValueRecord("Pivot 1.2 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(KeyValueRecord::getValueOfKeyField, KeyValueRecord::getValueOfValueField, nullValue,
                        KeyValueRecord::getCategory, valueClasses
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

        showPivotStandardRecord("Pivot 1.3 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(Record::getCategory, r -> r.getValueAt(1), nullValue,
                        r -> r.getValueAt(0), valueClasses),
                new StandardRecord("key1", 1L, "jp"),
                new StandardRecord("key1", 2L, "en", "value1en"),
                new StandardRecord("key2", 3L, "en", "value2en"),
                new StandardRecord("key3", 4L, "en"),
                new StandardRecord("key4", 5L, "en", "value4en"),
                new StandardRecord("key1", 6L, "de", "value1de"),
                new StandardRecord("key2", 7L, "de", "value2de"),
                new StandardRecord("key3", 8L, "de", "value3de"),
                new StandardRecord("key2", 9L, "fr", "value2fr")
        );

        showPivotStandardRecord("Pivot 2 pivotWithIndexes",
                PivotModifier.pivotWithIndexes(0, 3, nullValueShort,
                        1, 2),
                new StandardRecord(null, 1L, "key1", "A1", "B1", "no"),
                new StandardRecord(null, 2L, "key1", "A2", "B2", "no"),
                new StandardRecord(null, 3L, "key1", "A3", "B3", "no"),
                new StandardRecord(null, 3L, "key1", "A4", "B4", "no"),
                new StandardRecord(null, 4L, "key2", "C1", "D1"),
                new StandardRecord(null, 5L, "key2", "C2", "D2"),
                new StandardRecord(null, 6L, "key2", "C3", "D3"),
                new StandardRecord(null, 5L, "key3", "E1", "F1"),
                new StandardRecord(null, 5L, "key4", "G1"),
                new StandardRecord(null, 5L, "key4", "H1"),
                new StandardRecord(null, 6L, "key5", null, "I2", "I3")
        );

        showPivotKeyValueRecord("Pivot 3 constructor classification",
                new PivotModifier<>(
                        KeyValueRecord::getValueOfKeyField, KeyValueRecord::getValueOfKeyField, r -> Stream.empty(),
                        KeyValueRecord::getValueOfValueField, nullValue, KeyValueRecord::getCategory, valueClasses
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

        showPivotStandardRecord("Pivot 4 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(0, 2, nullValue,
                        1, valueClassifications),
                new StandardRecord("key2", "Type1", "Value2.1", "not relevant"),
                new StandardRecord("key1", "Type1", "Value1.1", "not relevant"),
                new StandardRecord("key2", "Type3", "Value2.3", "not relevant"),
                new StandardRecord("key1", "Type2", "Value1.2", "not relevant"),
                new StandardRecord("key3", "Type1", "Value3.1", "not relevant"),
                new StandardRecord("key1", "Type3", "Value1.3", "not relevant"),
                new StandardRecord("key2", "Type2", "Value2.2", "not relevant"),
                new StandardRecord("key3", "Type3", "Value3.3", "not relevant"),
                new StandardRecord("key4"),
                new StandardRecord("key5", "Type5", "Value5.5", "not relevant")
        );
    }

    private static void showRecordStreamModifier() {
        System.out.println("-showRecordStreamModifier---");

        showModifierSingleRecord("concat 2",
                RecordStreamModifier.concat(
                        new FilterModifier<>(CategoryFilter.equalTo("C1")),
                        new DistinctModifier<>(new CategoryMessage<>())));
        showModifierSingleRecord("concat 3",
                RecordStreamModifier.concat(
                        new FilterModifier<>(CategoryFilter.equalTo("C1")),
                        new IdentityModifier<>(),
                        new SkipLimitModifier<>(1L, 1L)));
        showModifierSingleRecord("compose",
                new DistinctModifier<SingleRecord>(new CategoryMessage<>())
                        .compose(new FilterModifier<>(CategoryFilter.equalTo("C1"))));
        showModifierSingleRecord("andThen",
                new FilterModifier<SingleRecord>(CategoryFilter.equalTo("C1"))
                        .andThen(new DistinctModifier<>(new CategoryMessage<>())));
    }

    private static void showSkipLimitModifier() {
        System.out.println("-showSkipLimitModifier---");

        showModifierSingleRecord("constructor",
                new SkipLimitModifier<>(2L, 3L));
        showModifierSingleRecord("skip",
                SkipLimitModifier.skip(4L));
        showModifierSingleRecord("limit",
                SkipLimitModifier.limit(2L));
    }

    private static void showSortModifier() {
        System.out.println("-showSortModifier---");

        showModifierSingleRecord("constructor category",
                new SortModifier<>(RecordComparators.category(Comparator.naturalOrder(), NULLS.FIRST)));
        showModifierSingleRecord("constructor valueField",
                new SortModifier<>(RecordComparators.valueOfValueField(Comparator.naturalOrder(), NULLS.FIRST)));
    }

    private static void showUnaryGroupModifier() {
        System.out.println("-showUnaryGroupModifier---");

        showUnaryGroup("first",
                UnaryGroupModifier.first(Record::getCategory));

        showUnaryGroup("last",
                UnaryGroupModifier.last(Record::getCategory));

        showUnaryGroup("min recordId",
                UnaryGroupModifier.min(Record::getCategory, RecordComparators.recordId(NULLS.FIRST)));

        showUnaryGroup("max recordId",
                UnaryGroupModifier.max(Record::getCategory, RecordComparators.recordId(NULLS.FIRST)));

        showUnaryGroup("reduce maxBy recordId",
                UnaryGroupModifier.reduce(Record::getCategory,
                        BinaryOperator.maxBy(RecordComparators.recordId(NULLS.FIRST))));

        showUnaryGroup("collect maxBy recordId",
                UnaryGroupModifier.collect(Record::getCategory,
                        Collectors.maxBy(RecordComparators.recordId(NULLS.FIRST)),
                        null));

        showUnaryGroup("collect reducing maxBy recordId",
                UnaryGroupModifier.collect(Record::getCategory,
                        Collectors.reducing(BinaryOperator.maxBy(RecordComparators.recordId(NULLS.FIRST))),
                        null));
    }

    private static void showUnpivotModifier() {
        System.out.println("-showUnpivotModifier---");

        showUnpivot("Unpivot 1.1 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new StandardRecord(r.getValueOfFirstField(), "S 1", r.getValueAt(1)),
                        new StandardRecord(r.getValueOfFirstField(), "S 3", r.getValueAt(3))
                )),
                new StandardRecord("k1", "a1", "a2", "a3"),
                new StandardRecord("k2", "b1", "b2", "b3"),
                new StandardRecord("k3", "c1", "c2"),
                new StandardRecord("k4", "d1", "d2", "d3")
        );

        showUnpivot("Unpivot 1.2 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new StandardRecord(r.getCategory(), "S 1", r.getValueAt(0)),
                        new StandardRecord(r.getCategory(), "S 3", r.getValueAt(2))
                )),
                new StandardRecord("k1", 1L, "a1", "a2", "a3"),
                new StandardRecord("k2", 2L, "b1", "b2", "b3"),
                new StandardRecord("k3", 3L, "c1", "c2"),
                new StandardRecord("k4", 4L, "d1", "d2", "d3")
        );

        showUnpivot("Unpivot 1.3 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new StandardRecord(r.getCategory(), r.getRecordId(), "S 1", r.getValueAt(0)),
                        new StandardRecord(r.getCategory(), r.getRecordId(), "S 3", r.getValueAt(2))
                )),
                new StandardRecord("k1", 1L, "a1", "a2", "a3"),
                new StandardRecord("k2", 2L, "b1", "b2", "b3"),
                new StandardRecord("k3", 3L, "c1", "c2"),
                new StandardRecord("k4", 4L, "d1", "d2", "d3")
        );

        showUnpivot("Unpivot 2.1 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, String::valueOf, true, 1, 2, 3),
                new StandardRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new StandardRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new StandardRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new StandardRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new StandardRecord("k5", "e1"),
                new StandardRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new StandardRecord("cat7", 7L, "k7", "g1"),
                new StandardRecord("cat8", 8L, "k8")
        );

        showUnpivot("Unpivot 2.2 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, i -> "Index: " + i, false, 2, 3),
                new StandardRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new StandardRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new StandardRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new StandardRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new StandardRecord("k5", "e1"),
                new StandardRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new StandardRecord("cat7", 7L, "k7", "g1"),
                new StandardRecord("cat8", 8L, "k8")
        );

        Map<Integer, String> map23 = new TreeMap<>();
        map23.put(1, "S 1");
        map23.put(2, "S 2");
        map23.put(3, "S 3");
        showUnpivot("Unpivot 2.3 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, true, map23),
                new StandardRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new StandardRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new StandardRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new StandardRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new StandardRecord("k5", "e1"),
                new StandardRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new StandardRecord("cat7", 7L, "k7", "g1"),
                new StandardRecord("cat8", 8L, "k8")
        );

        List<Integer> keyIndexes24 = new ArrayList<>();
        keyIndexes24.add(1);
        keyIndexes24.add(0);
        Map<Integer, String> map24 = new TreeMap<>();
        map24.put(2, "S 1");
        map24.put(4, "S 3");
        showUnpivot("Unpivot 2.4 oneRecordPerValue two keys",
                UnpivotModifier.oneRecordPerValue(keyIndexes24, map24::get, false, map24.keySet()),
                new StandardRecord("cat1", 1L, "k1", "k1b", "a1", "a2", "a3"),
                new StandardRecord("cat2", 2L, "k2", "k2b", "b1", "b2", "b3"),
                new StandardRecord("cat3", 3L, "k3", "k3b", "c1", "c2", "c3"),
                new StandardRecord("cat4", 4L, "k4", "k4b", "d1", "d2", "d3"),
                new StandardRecord("k5", "k5b", "e1"),
                new StandardRecord("cat6", 6L, "k6", "k6b", "f1", null, "f2"),
                new StandardRecord("cat7", 7L, "k7", "k7b", "g1"),
                new StandardRecord("cat8", 8L, "k8", "k8b")
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
        showUnpivot("Unpivot 3 oneRecordPerValues two keys, many values",
                UnpivotModifier.oneRecordPerValues(keyValues3,
                        String::valueOf,
                        valueIndexes31, valueIndexes32, valueIndexes33),
                new StandardRecord("cat1", 1L, "k1", "a1 1", "a2 1", "a3 1", "b1 1", "b2 1", "k1b"),
                new StandardRecord("cat2", 2L, "k2", "a1 2", "a2 2", "a3 2", "b1 2", "b2 2", "k2b"),
                new StandardRecord("cat3", 3L, "k3", "a1 3", null, "a3 3", "b1 3", "b2 3", "k3b")
        );
    }

    public static void main(String[] args) {
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
