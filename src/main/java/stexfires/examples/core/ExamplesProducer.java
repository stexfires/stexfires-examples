package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.Records;
import stexfires.core.producer.ConstantProducer;
import stexfires.core.producer.DividingProducer;
import stexfires.core.producer.KeyValueProducer;
import stexfires.core.producer.RecordProducer;
import stexfires.core.producer.SingleProducer;
import stexfires.core.record.KeyValueRecord;
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

    private static void showProducer(RecordProducer<? extends Record> recordProducer) {
        RecordStreams.printLines(RecordStreams.produce(recordProducer));
    }

    private static void showConstantProducer() {
        System.out.println("-showConstantProducer---");

        long streamSize = 2L;

        showProducer(new ConstantProducer<>(streamSize, new KeyValueRecord("key1", "value1")));
        showProducer(ConstantProducer.emptyRecords(streamSize));
        showProducer(ConstantProducer.singleRecords(streamSize, "value1"));
        showProducer(ConstantProducer.pairRecords(streamSize, "value1", "value2"));
        showProducer(ConstantProducer.keyValueRecords(streamSize, "key1", "value1"));
        showProducer(ConstantProducer.standardRecords(streamSize, Strings.list("value1", "value2")));
        showProducer(ConstantProducer.standardRecords(streamSize, "value1", "value2", "value3"));
    }

    private static void showDividingProducer() {
        System.out.println("-showDividingProducer---");

        int recordSize = 2;
        String category = "category";

        showProducer(new DividingProducer(recordSize, "A", "B", "C", "D", "E"));
        showProducer(new DividingProducer(category, recordSize, "A", "B", "C", "D", "E", "F"));
        showProducer(new DividingProducer(category, new SequenceLongSupplier(100L), recordSize, "A", "B", "C"));
    }

    private static void showKeyValueProducer() {
        System.out.println("-showKeyValueProducer---");

        Map<String, Integer> keyValueMap = new HashMap<>();
        keyValueMap.put("A", 1);
        keyValueMap.put("B", 2);
        keyValueMap.put("C", null);

        String category = "category";

        showProducer(new KeyValueProducer(keyValueMap));
        showProducer(new KeyValueProducer(category, keyValueMap));
        showProducer(new KeyValueProducer(category, new SequenceLongSupplier(100L), keyValueMap));
        showProducer(new KeyValueProducer(category, Records.recordIdSequence(), keyValueMap,
                Strings::asString, i -> i == null ? "<null>" : "#" + i.hashCode()));
    }

    private static void showSingleProducer() {
        System.out.println("-showSingleProducer---");

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(null);

        String category = "category";

        showProducer(new SingleProducer(values));
        showProducer(new SingleProducer(category, values));
        showProducer(new SingleProducer(category, new SequenceLongSupplier(100L), values));
        showProducer(new SingleProducer(category, Records.recordIdSequence(), values,
                i -> i == null ? "<null>" : "#" + i.hashCode()));
    }

    public static void main(String[] args) {
        showConstantProducer();
        showDividingProducer();
        showKeyValueProducer();
        showSingleProducer();
    }

}
