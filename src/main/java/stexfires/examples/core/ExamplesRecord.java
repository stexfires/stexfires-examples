package stexfires.examples.core;

import stexfires.core.Fields;
import stexfires.core.TextRecord;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.util.Strings;

import java.util.Arrays;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesRecord {

    private ExamplesRecord() {
    }

    private static void printRecord(TextRecord record) {
        System.out.println("arrayOfFields:             " + Arrays.toString(record.arrayOfFields()));
        System.out.println("listOfFields:              " + record.listOfFields());
        System.out.println("streamOfFields:            " + Fields.collectFields(record.streamOfFields()));
        System.out.println("getCategory:               " + record.getCategory());
        System.out.println("hasCategory:               " + record.hasCategory());
        System.out.println("getCategoryOrElse:         " + record.getCategoryOrElse("else"));
        System.out.println("getCategoryAsOptional:     " + record.getCategoryAsOptional());
        System.out.println("streamOfCategory:          " + record.streamOfCategory().findFirst());
        System.out.println("getRecordId:               " + record.getRecordId());
        System.out.println("hasRecordId:               " + record.hasRecordId());
        System.out.println("getRecordIdAsOptionalLong: " + record.getRecordIdAsOptionalLong());
        System.out.println("getRecordIdAsOptional:     " + record.getRecordIdAsOptional());
        System.out.println("streamOfRecordId:          " + record.streamOfRecordId().findFirst());
        System.out.println("size:                      " + record.size());
        System.out.println("isEmpty:                   " + record.isEmpty());
        System.out.println("isValidIndex:              " + record.isValidIndex(0));
        System.out.println("getFieldAt:                " + record.getFieldAt(0));
        System.out.println("getFirstField:             " + record.getFirstField());
        System.out.println("getLastField:              " + record.getLastField());
        System.out.println("getValueAt:                " + record.getValueAt(0));
        System.out.println("getValueAtOrElse:          " + record.getValueAtOrElse(0, "else"));
        System.out.println("getValueOfFirstField:      " + record.getValueOfFirstField());
        System.out.println("getValueOfLastField:       " + record.getValueOfLastField());
        System.out.println("hashCode:                  " + record.hashCode());
        System.out.println("toString:                  " + record.toString());
        System.out.println();
    }

    private static void showEmptyRecord() {
        System.out.println("-showEmptyRecord---");

        EmptyRecord record = new EmptyRecord();
        printRecord(record);
    }

    private static void showKeyValueRecord() {
        System.out.println("-showKeyValueRecord---");

        KeyValueRecord record = new KeyValueRecord("key", "value");
        System.out.println("getKeyField:               " + record.getKeyField());
        System.out.println("getValueOfKeyField:        " + record.getValueOfKeyField());
        System.out.println("getValueField:             " + record.getValueField());
        System.out.println("getValueOfValueField:      " + record.getValueOfValueField());
        System.out.println("newValueRecord:            " + record.newValueRecord("new value"));
        printRecord(record);

        KeyValueRecord record1 = new KeyValueRecord("category", 0L, "key", "value");
        System.out.println("getKeyField:               " + record1.getKeyField());
        System.out.println("getValueOfKeyField:        " + record1.getValueOfKeyField());
        System.out.println("getValueField:             " + record1.getValueField());
        System.out.println("getValueOfValueField:      " + record1.getValueOfValueField());
        System.out.println("newValueRecord:            " + record1.newValueRecord("new value"));
        printRecord(record1);
    }

    private static void showPairRecord() {
        System.out.println("-showPairRecord---");

        PairRecord record = new PairRecord("first", "second");
        System.out.println("newRecordSwapped:          " + record.newRecordSwapped());
        System.out.println("getSecondField:            " + record.getSecondField());
        System.out.println("getValueOfSecondField:     " + record.getValueOfSecondField());
        printRecord(record);

        PairRecord record1 = new PairRecord("category", 0L, "first", "second");
        System.out.println("newRecordSwapped:          " + record1.newRecordSwapped());
        System.out.println("getSecondField:            " + record1.getSecondField());
        System.out.println("getValueOfSecondField:     " + record1.getValueOfSecondField());
        printRecord(record1);
    }

    private static void showSingleRecord() {
        System.out.println("-showSingleRecord---");

        SingleRecord record = new SingleRecord("value");
        System.out.println("getValueField:             " + record.getValueField());
        System.out.println("getValueOfValueField:      " + record.getValueOfValueField());
        printRecord(record);

        SingleRecord record1 = new SingleRecord("category", 0L, "value");
        System.out.println("getValueField:             " + record1.getValueField());
        System.out.println("getValueOfValueField:      " + record1.getValueOfValueField());
        printRecord(record1);
    }

    private static void showStandardRecord() {
        System.out.println("-showStandardRecord---");

        printRecord(new StandardRecord());

        printRecord(new StandardRecord(Strings.list("value1", "value2", "value3")));
        printRecord(new StandardRecord("category", Strings.list("value1", "value2", "value3")));
        printRecord(new StandardRecord("category", 0L, Strings.list("value1", "value2", "value3")));

        printRecord(new StandardRecord(Strings.stream("value1", "value2", "value3")));
        printRecord(new StandardRecord("category", Strings.stream("value1", "value2", "value3")));
        printRecord(new StandardRecord("category", 0L, Strings.stream("value1", "value2", "value3")));

        printRecord(new StandardRecord("value1", "value2", "value3"));
        printRecord(new StandardRecord("category", 0L, "value1", "value2", "value3"));
    }

    public static void main(String[] args) {
        showEmptyRecord();
        showKeyValueRecord();
        showPairRecord();
        showSingleRecord();
        showStandardRecord();
    }

}
