package stexfires.examples.core;

import stexfires.core.Fields;
import stexfires.core.TextRecord;
import stexfires.core.impl.EmptyRecord;
import stexfires.core.impl.KeyValueRecord;
import stexfires.core.impl.PairRecord;
import stexfires.core.impl.SingleRecord;
import stexfires.core.impl.StandardRecord;
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
        System.out.println("category:                  " + record.category());
        System.out.println("hasCategory:               " + record.hasCategory());
        System.out.println("categoryOrElse:            " + record.categoryOrElse("else"));
        System.out.println("categoryAsOptional:        " + record.categoryAsOptional());
        System.out.println("categoryAsStream:          " + record.categoryAsStream().findFirst());
        System.out.println("recordId:                  " + record.recordId());
        System.out.println("hasRecordId:               " + record.hasRecordId());
        System.out.println("recordIdAsOptionalLong:    " + record.recordIdAsOptionalLong());
        System.out.println("recordIdAsOptional:        " + record.recordIdAsOptional());
        System.out.println("recordIdAsStream:          " + record.recordIdAsStream().findFirst());
        System.out.println("size:                      " + record.size());
        System.out.println("isEmpty:                   " + record.isEmpty());
        System.out.println("isValidIndex:              " + record.isValidIndex(0));
        System.out.println("fieldAt:                   " + record.fieldAt(0));
        System.out.println("firstField:                " + record.firstField());
        System.out.println("lastField:                 " + record.lastField());
        System.out.println("valueAt:                   " + record.valueAt(0));
        System.out.println("valueAtOrElse:             " + record.valueAtOrElse(0, "else"));
        System.out.println("valueOfFirstField:         " + record.valueOfFirstField());
        System.out.println("valueOfLastField:          " + record.valueOfLastField());
        System.out.println("hashCode:                  " + record.hashCode());
        System.out.println("toString:                  " + record);
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
        System.out.println("keyField:                  " + record.keyField());
        System.out.println("valueOfKeyField:           " + record.valueOfKeyField());
        System.out.println("valueField:                " + record.valueField());
        System.out.println("valueOfValueField:         " + record.valueOfValueField());
        System.out.println("newKeyRecord:              " + record.newKeyRecord("newKey"));
        System.out.println("newValueRecord:            " + record.newValueRecord("new value"));
        printRecord(record);

        KeyValueRecord record1 = new KeyValueRecord("category", 0L, "key", "value");
        System.out.println("keyField:                  " + record1.keyField());
        System.out.println("valueOfKeyField:           " + record1.valueOfKeyField());
        System.out.println("valueField:                " + record1.valueField());
        System.out.println("valueOfValueField:         " + record1.valueOfValueField());
        System.out.println("newKeyRecord:              " + record1.newKeyRecord("newKey"));
        System.out.println("newValueRecord:            " + record1.newValueRecord("new value"));
        printRecord(record1);
    }

    private static void showPairRecord() {
        System.out.println("-showPairRecord---");

        PairRecord record = new PairRecord("first", "second");
        System.out.println("newRecordSwapped:          " + record.newRecordSwapped());
        System.out.println("secondField:               " + record.secondField());
        System.out.println("valueOfSecondField:        " + record.valueOfSecondField());
        printRecord(record);

        PairRecord record1 = new PairRecord("category", 0L, "first", "second");
        System.out.println("newRecordSwapped:          " + record1.newRecordSwapped());
        System.out.println("secondField:               " + record1.secondField());
        System.out.println("valueOfSecondField:        " + record1.valueOfSecondField());
        printRecord(record1);
    }

    private static void showSingleRecord() {
        System.out.println("-showSingleRecord---");

        SingleRecord record = new SingleRecord("value");
        System.out.println("valueField:                " + record.valueField());
        System.out.println("valueOfValueField:         " + record.valueOfValueField());
        printRecord(record);

        SingleRecord record1 = new SingleRecord("category", 0L, "value");
        System.out.println("valueField:                " + record1.valueField());
        System.out.println("valueOfValueField:         " + record1.valueOfValueField());
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

    public static void main(String... args) {
        showEmptyRecord();
        showKeyValueRecord();
        showPairRecord();
        showSingleRecord();
        showStandardRecord();
    }

}
