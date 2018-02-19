package stexfires.examples.core;

import stexfires.core.RecordStreams;
import stexfires.core.Records;
import stexfires.core.producer.ConstantProducer;
import stexfires.core.producer.DividingProducer;
import stexfires.core.producer.KeyValueProducer;
import stexfires.core.producer.SingleProducer;
import stexfires.core.record.KeyValueRecord;
import stexfires.util.Strings;
import stexfires.util.supplier.SequenceLongSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("MagicNumber")
public final class ExamplesProducer {

    private ExamplesProducer() {
    }

    private static void showConstantProducer() {
        System.out.println("-showConstantProducer---");

        long streamSize = 2L;

        RecordStreams.printLines(RecordStreams.produce(new ConstantProducer<>(streamSize, new KeyValueRecord("key1", "value1"))));
        RecordStreams.printLines(RecordStreams.produce(ConstantProducer.emptyRecords(streamSize)));
        RecordStreams.printLines(RecordStreams.produce(ConstantProducer.singleRecords(streamSize, "value1")));
        RecordStreams.printLines(RecordStreams.produce(ConstantProducer.pairRecords(streamSize, "value1", "value2")));
        RecordStreams.printLines(RecordStreams.produce(ConstantProducer.keyValueRecords(streamSize, "key1", "value1")));
        RecordStreams.printLines(RecordStreams.produce(ConstantProducer.standardRecords(streamSize, Strings.list("value1", "value2"))));
    }

    private static void showDividingProducer() {
        System.out.println("-showDividingProducer---");

        String category = "category";

        RecordStreams.printLines(RecordStreams.produce(new DividingProducer(2, "A", "B", "C", "D", "E")));
        RecordStreams.printLines(RecordStreams.produce(new DividingProducer(category, 2, "A", "B", "C", "D", "E")));
        RecordStreams.printLines(RecordStreams.produce(new DividingProducer(category, new SequenceLongSupplier(100L), 2, "A", "B", "C", "D", "E")));
    }

    private static void showKeyValueProducer() {
        System.out.println("-showKeyValueProducer---");

        Map<String, Integer> keyValueMap = new HashMap<>();
        keyValueMap.put("A", 1);
        keyValueMap.put("B", 2);
        keyValueMap.put("C", null);

        String category = "category";

        RecordStreams.printLines(RecordStreams.produce(new KeyValueProducer(keyValueMap)));
        RecordStreams.printLines(RecordStreams.produce(new KeyValueProducer(category, keyValueMap)));
        RecordStreams.printLines(RecordStreams.produce(new KeyValueProducer(category, new SequenceLongSupplier(100L), keyValueMap)));
        RecordStreams.printLines(RecordStreams.produce(new KeyValueProducer(category, Records.recordIdSequence(), keyValueMap,
                Strings::asString, i -> i == null ? "<null>" : "#" + i.hashCode())));
    }

    private static void showSingleProducer() {
        System.out.println("-showSingleProducer---");

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(null);

        String category = "category";

        RecordStreams.printLines(RecordStreams.produce(new SingleProducer(values)));
        RecordStreams.printLines(RecordStreams.produce(new SingleProducer(category, values)));
        RecordStreams.printLines(RecordStreams.produce(new SingleProducer(category, new SequenceLongSupplier(100L), values)));
        RecordStreams.printLines(RecordStreams.produce(new SingleProducer(category, Records.recordIdSequence(), values,
                i -> i == null ? "<null>" : "#" + i.hashCode())));
    }

    public static void main(String[] args) {
        showConstantProducer();
        showDividingProducer();
        showKeyValueProducer();
        showSingleProducer();
    }

}
