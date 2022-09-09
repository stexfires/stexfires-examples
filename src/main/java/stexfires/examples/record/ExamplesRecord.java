package stexfires.examples.record;

import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.OneFieldRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.record.impl.TwoFieldsRecord;
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
        System.out.println("textAt:                   " + record.textAt(0));
        System.out.println("textAtOrElse:             " + record.textAtOrElse(0, "else"));
        System.out.println("valueOfFirstField:         " + record.firstText());
        System.out.println("valueOfLastField:          " + record.lastText());
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
        System.out.println("key:                       " + record.key());
        System.out.println("valueField:                " + record.valueField());
        System.out.println("value:                     " + record.value());
        System.out.println("withKey:                   " + record.withKey("newKey"));
        System.out.println("withValue:                 " + record.withValue("new value"));
        printRecord(record);

        KeyValueRecord record1 = new KeyValueRecord("category", 0L, "key", "value");
        System.out.println("keyField:                  " + record1.keyField());
        System.out.println("key:                       " + record1.key());
        System.out.println("valueField:                " + record1.valueField());
        System.out.println("value:                     " + record1.value());
        System.out.println("withKey:                   " + record1.withKey("newKey"));
        System.out.println("withValue:                 " + record1.withValue("new value"));
        printRecord(record1);
    }

    private static void showTwoValuesRecord() {
        System.out.println("-showTwoValuesRecord---");

        TwoFieldsRecord record = new TwoFieldsRecord("first", "second");
        System.out.println("withSwappedTexts:          " + record.withSwappedTexts());
        System.out.println("secondField:               " + record.secondField());
        System.out.println("valueOfSecondField:        " + record.secondText());
        printRecord(record);

        TwoFieldsRecord record1 = new TwoFieldsRecord("category", 0L, "first", "second");
        System.out.println("withSwappedTexts:          " + record1.withSwappedTexts());
        System.out.println("secondField:               " + record1.secondField());
        System.out.println("valueOfSecondField:        " + record1.secondText());
        printRecord(record1);
    }

    private static void showOneValueRecord() {
        System.out.println("-showOneValueRecord---");

        OneFieldRecord record = new OneFieldRecord("value");
        System.out.println("valueField:                " + record.valueField());
        System.out.println("value:                     " + record.value());
        printRecord(record);

        OneFieldRecord record1 = new OneFieldRecord("category", 0L, "value");
        System.out.println("valueField:                " + record1.valueField());
        System.out.println("value:                     " + record1.value());
        printRecord(record1);
    }

    private static void showManyValuesRecord() {
        System.out.println("-showManyValuesRecord---");

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
        showTwoValuesRecord();
        showOneValueRecord();
        showManyValuesRecord();
    }

}
