package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.producer.ConstantProducer;
import stexfires.record.producer.DividingProducer;
import stexfires.record.producer.KeyValueRecordProducer;
import stexfires.record.producer.OneValueRecordProducer;
import stexfires.record.producer.RecordProducer;
import stexfires.util.Strings;
import stexfires.util.supplier.SequenceLongSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesProducer {

    private ExamplesProducer() {
    }

    private static void printProducer(RecordProducer<? extends TextRecord> recordProducer) {
        TextRecordStreams.printLines(TextRecordStreams.produce(recordProducer));
    }

    private static void showConstantProducer() {
        System.out.println("-showConstantProducer---");

        long streamSize = 2L;

        printProducer(new ConstantProducer<>(streamSize, new KeyValueRecord("key1", "value1")));
        printProducer(ConstantProducer.emptyRecords(streamSize));
        printProducer(ConstantProducer.oneValueRecords(streamSize, "value1"));
        printProducer(ConstantProducer.twoValuesRecords(streamSize, "value1", "value2"));
        printProducer(ConstantProducer.keyValueRecords(streamSize, "key1", "value1"));
        printProducer(ConstantProducer.keyValueCommentRecords(streamSize, "key1", "value1", "comment1"));
        printProducer(ConstantProducer.manyValuesRecords(streamSize, Strings.list("value1", "value2")));
        printProducer(ConstantProducer.manyValuesRecords(streamSize, "value1", "value2", "value3"));
    }

    private static void showDividingProducer() {
        System.out.println("-showDividingProducer---");

        int recordSize = 2;
        String category = "category";

        printProducer(new DividingProducer(recordSize, "A", "B", "C", "D", "E"));
        printProducer(new DividingProducer(category, recordSize, "A", "B", "C", "D", "E", "F"));
        printProducer(new DividingProducer(category, new SequenceLongSupplier(100L), recordSize, "A", "B", "C"));
    }

    private static void showKeyValueProducer() {
        System.out.println("-showKeyValueProducer---");

        Map<String, Integer> keyValueMap = new HashMap<>();
        keyValueMap.put("A", 1);
        keyValueMap.put("B", 2);
        keyValueMap.put("C", null);

        String category = "category";

        printProducer(new KeyValueRecordProducer(keyValueMap));
        printProducer(new KeyValueRecordProducer(category, keyValueMap));
        printProducer(new KeyValueRecordProducer(category, new SequenceLongSupplier(100L), keyValueMap));
        printProducer(new KeyValueRecordProducer(category, TextRecords.recordIdSequence(), keyValueMap,
                Strings::asString, i -> i == null ? "<null>" : "#" + i.hashCode()));
    }

    private static void showSingleProducer() {
        System.out.println("-showSingleProducer---");

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(null);

        String category = "category";

        printProducer(new OneValueRecordProducer(values));
        printProducer(new OneValueRecordProducer(category, values));
        printProducer(new OneValueRecordProducer(category, new SequenceLongSupplier(100L), values));
        printProducer(new OneValueRecordProducer(category, TextRecords.recordIdSequence(), values,
                i -> i == null ? "<null>" : "#" + i.hashCode()));
    }

    public static void main(String... args) {
        showConstantProducer();
        showDividingProducer();
        showKeyValueProducer();
        showSingleProducer();
    }

}
