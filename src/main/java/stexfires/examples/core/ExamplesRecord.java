package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.util.Strings;

public final class ExamplesRecord {

    private ExamplesRecord() {
    }

    private static void showRecord(Record record) {
        System.out.println("toString:    " + record.toString());
        System.out.println("hashCode:    " + record.hashCode());
        System.out.println("size:        " + record.size());
        System.out.println("isEmpty:     " + record.isEmpty());
        System.out.println("hasCategory: " + record.hasCategory());
        System.out.println("getCategory: " + record.getCategory());
        System.out.println("hasRecordId: " + record.hasRecordId());
        System.out.println("getRecordId: " + record.getRecordId());
        System.out.println();
    }

    private static void showEmptyRecord() {
        System.out.println("-showEmptyRecord---");

        showRecord(new EmptyRecord());
    }

    private static void showKeyValueRecord() {
        System.out.println("-showKeyValueRecord---");

        showRecord(new KeyValueRecord("key", "value"));
        showRecord(new KeyValueRecord("category", 0L, "key", "value"));
    }

    private static void showPairRecord() {
        System.out.println("-showPairRecord---");

        showRecord(new PairRecord("first", "second"));
        showRecord(new PairRecord("category", 0L, "first", "second"));
    }

    private static void showSingleRecord() {
        System.out.println("-showSingleRecord---");

        showRecord(new SingleRecord("value"));
        showRecord(new SingleRecord("category", 0L, "value"));
    }

    private static void showStandardRecord() {
        System.out.println("-showStandardRecord---");

        showRecord(new StandardRecord());

        showRecord(new StandardRecord(Strings.list("value1", "value2", "value3")));
        showRecord(new StandardRecord("category", Strings.list("value1", "value2", "value3")));
        showRecord(new StandardRecord("category", 0L, Strings.list("value1", "value2", "value3")));

        showRecord(new StandardRecord(Strings.stream("value1", "value2", "value3")));
        showRecord(new StandardRecord("category", Strings.stream("value1", "value2", "value3")));
        showRecord(new StandardRecord("category", 0L, Strings.stream("value1", "value2", "value3")));

        showRecord(new StandardRecord("value1", "value2", "value3"));
        showRecord(new StandardRecord("category", 0L, "value1", "value2", "value3"));
    }


    public static void main(String[] args) {
        showEmptyRecord();
        showKeyValueRecord();
        showPairRecord();
        showSingleRecord();
        showStandardRecord();
    }

}
