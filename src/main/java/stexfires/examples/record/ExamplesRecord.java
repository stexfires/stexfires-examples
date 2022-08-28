package stexfires.examples.record;

import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.impl.TwoValuesRecord;
import stexfires.util.Strings;

import java.util.Arrays;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesRecord {

    private ExamplesRecord() {
    }

    private static void printRecord(TextRecord record) {
        System.out.println("arrayOfFields:             " + Arrays.toString(record.arrayOfFields()));
        System.out.println("listOfFields:              " + record.listOfFields());
        System.out.println("listOfFieldsReversed:      " + record.listOfFieldsReversed());
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

    private static void showTwoValuesRecord() {
        System.out.println("-showTwoValuesRecord---");

        TwoValuesRecord record = new TwoValuesRecord("first", "second");
        System.out.println("newRecordSwapped:          " + record.newRecordSwapped());
        System.out.println("secondField:               " + record.secondField());
        System.out.println("valueOfSecondField:        " + record.valueOfSecondField());
        printRecord(record);

        TwoValuesRecord record1 = new TwoValuesRecord("category", 0L, "first", "second");
        System.out.println("newRecordSwapped:          " + record1.newRecordSwapped());
        System.out.println("secondField:               " + record1.secondField());
        System.out.println("valueOfSecondField:        " + record1.valueOfSecondField());
        printRecord(record1);
    }

    private static void showOneValueRecord() {
        System.out.println("-showOneValueRecord---");

        OneValueRecord record = new OneValueRecord("value");
        System.out.println("valueField:                " + record.valueField());
        System.out.println("valueOfValueField:         " + record.valueOfValueField());
        printRecord(record);

        OneValueRecord record1 = new OneValueRecord("category", 0L, "value");
        System.out.println("valueField:                " + record1.valueField());
        System.out.println("valueOfValueField:         " + record1.valueOfValueField());
        printRecord(record1);
    }

    private static void showManyValuesRecord() {
        System.out.println("-showManyValuesRecord---");

        printRecord(new ManyValuesRecord());

        printRecord(new ManyValuesRecord(Strings.list("value1", "value2", "value3")));
        printRecord(new ManyValuesRecord("category", Strings.list("value1", "value2", "value3")));
        printRecord(new ManyValuesRecord("category", 0L, Strings.list("value1", "value2", "value3")));

        printRecord(new ManyValuesRecord(Strings.stream("value1", "value2", "value3")));
        printRecord(new ManyValuesRecord("category", Strings.stream("value1", "value2", "value3")));
        printRecord(new ManyValuesRecord("category", 0L, Strings.stream("value1", "value2", "value3")));

        printRecord(new ManyValuesRecord("value1", "value2", "value3"));
        printRecord(new ManyValuesRecord("category", 0L, "value1", "value2", "value3"));
    }

    public static void main(String... args) {
        showEmptyRecord();
        showKeyValueRecord();
        showTwoValuesRecord();
        showOneValueRecord();
        showManyValuesRecord();
    }

}
