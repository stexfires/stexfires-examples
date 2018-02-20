package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.filter.ClassFilter;
import stexfires.core.message.CategoryMessage;
import stexfires.core.message.ClassNameMessage;
import stexfires.core.message.CompareMessageBuilder;
import stexfires.core.message.ConditionalMessage;
import stexfires.core.message.ConstantMessage;
import stexfires.core.message.ExtendedValuesMessage;
import stexfires.core.message.JoinedValuesMessage;
import stexfires.core.message.NullSafeMessage;
import stexfires.core.message.RecordIdMessage;
import stexfires.core.message.RecordMessage;
import stexfires.core.message.ShortMessage;
import stexfires.core.message.SizeMessage;
import stexfires.core.message.SupplierMessage;
import stexfires.core.message.ToStringMessage;
import stexfires.core.message.ValueMessage;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;

import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")
public final class ExamplesMessage {

    private ExamplesMessage() {
    }

    private static Stream<Record> generateStream() {
        return Stream.of(
                new SingleRecord("category", 0L, "A"),
                new SingleRecord("category", 1L, "B"),
                new SingleRecord("", 2L, "C"),
                new SingleRecord("Category", 3L, "D"),
                new SingleRecord(null, 4L, "E"),
                new SingleRecord("c", 5L, "F"),
                new PairRecord("c", 6L, "X", "Y"),
                new KeyValueRecord("key", "value"),
                new StandardRecord("Category", 7L, "S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord(),
                new EmptyRecord()
        );
    }

    private static Stream<SingleRecord> generateStreamSingleRecord() {
        return Stream.of(
                new SingleRecord("value1"),
                new SingleRecord(null, 2L, "value2"),
                new SingleRecord("category", 3L, "value3")
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueRecord("key1", "value1"),
                new KeyValueRecord(null, 2L, "key2", "value2"),
                new KeyValueRecord("category", 3L, "key3", "value3")
        );
    }

    private static void showMessage(String title, RecordMessage<Record> recordMessage) {
        System.out.println("--" + title);
        System.out.println(RecordStreams.collectMessages(generateStream(), recordMessage));
    }

    private static void showMessageSingleRecord(String title, RecordMessage<SingleRecord> recordMessage) {
        System.out.println("--" + title);
        System.out.println(RecordStreams.collectMessages(generateStreamSingleRecord(), recordMessage));
    }

    private static void showMessageKeyValueRecord(String title, RecordMessage<KeyValueRecord> recordMessage) {
        System.out.println("--" + title);
        System.out.println(RecordStreams.collectMessages(generateStreamKeyValueRecord(), recordMessage));
    }

    private static void showCategoryMessage() {
        System.out.println("-showCategoryMessage---");

        showMessage("constructor",
                new CategoryMessage<>());
        showMessage("constructor nullCategoryValue",
                new CategoryMessage<>("<NULL>"));
    }

    private static void showClassNameMessage() {
        System.out.println("-showClassNameMessage---");

        showMessage("constructor",
                new ClassNameMessage<>());
        showMessage("constructor hashCode",
                new ClassNameMessage<>(true));
    }

    private static void showCompareMessageBuilder() {
        System.out.println("-showCompareMessageBuilder---");

        showMessage("category / size",
                new CompareMessageBuilder().category().size().build());
        showMessage("values",
                new CompareMessageBuilder().values().build());
    }

    private static void showConditionalMessage() {
        System.out.println("-showConditionalMessage---");

        showMessage("constructor",
                new ConditionalMessage<>(ClassFilter.equalTo(SingleRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
    }

    private static void showConstantMessage() {
        System.out.println("-showConstantMessage---");

        showMessage("constructor",
                new ConstantMessage<>("message"));
    }

    private static void showExtendedValuesMessage() {
        System.out.println("-showExtendedValuesMessage---");

        showMessage("constructor",
                new ExtendedValuesMessage<>("(", ")"));
        showMessage("constructor first/last",
                new ExtendedValuesMessage<>("", "", "<", ">"));
    }

    private static void showJoinedValuesMessage() {
        System.out.println("-showJoinedValuesMessage---");

        showMessage("constructor delimiter",
                new JoinedValuesMessage<>(""));
    }

    private static void showNullSafeMessage() {
        System.out.println("-showNullSafeMessage---");

        System.out.println(new NullSafeMessage<>(new ToStringMessage<>()).createMessage(null));
    }

    private static void showRecordIdMessage() {
        System.out.println("-showRecordIdMessage---");

        showMessage("constructor",
                new RecordIdMessage<>());
        showMessage("constructor prefix missingRecordIdMessage",
                new RecordIdMessage<>("", "-"));
    }

    private static void showRecordMessage() {
        System.out.println("-showRecordMessage---");

        showMessage("prepend / append",
                new SizeMessage<>().prepend("Size:").append(" RecordId:").append(new RecordIdMessage<>()));
    }

    private static void showShortMessage() {
        System.out.println("-showShortMessage---");

        showMessage("constructor",
                new ShortMessage<>());
    }

    private static void showSizeMessage() {
        System.out.println("-showSizeMessage---");

        showMessage("constructor",
                new SizeMessage<>());
    }

    private static void showSupplierMessage() {
        System.out.println("-showSupplierMessage---");

        showMessage("localTime",
                SupplierMessage.localTime());
        showMessage("threadName",
                SupplierMessage.threadName());
        showMessage("sequence",
                SupplierMessage.sequence(0L));
    }

    private static void showToStringMessage() {
        System.out.println("-showToStringMessage---");

        showMessage("constructor",
                new ToStringMessage<>());
    }

    private static void showValueMessage() {
        System.out.println("-showValueMessage---");

        showMessage("constructor",
                new ValueMessage<>(1));
        showMessage("constructor nullFieldMessage",
                new ValueMessage<>(1, "<NULL>"));
        showMessageSingleRecord("value",
                ValueMessage.value());
        showMessageKeyValueRecord("key",
                ValueMessage.key());
    }

    public static void main(String[] args) {
        showCategoryMessage();
        showClassNameMessage();
        showCompareMessageBuilder();
        showConditionalMessage();
        showConstantMessage();
        showExtendedValuesMessage();
        showJoinedValuesMessage();
        showNullSafeMessage();
        showRecordIdMessage();
        showRecordMessage();
        showShortMessage();
        showSizeMessage();
        showSupplierMessage();
        showToStringMessage();
        showValueMessage();
    }

}
