package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.filter.ClassFilter;
import stexfires.core.mapper.fieldvalue.AddPrefixFieldValueMapper;
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
import stexfires.util.Strings;

import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
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
        Strings.printLine(RecordStreams.mapToMessage(generateStream(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void showMessageSingleRecord(String title, RecordMessage<? super SingleRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(RecordStreams.mapToMessage(generateStreamSingleRecord(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void showMessageKeyValueRecord(String title, RecordMessage<? super KeyValueRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(RecordStreams.mapToMessage(generateStreamKeyValueRecord(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void showCategoryMessage() {
        System.out.println("-showCategoryMessage---");

        showMessage("constructor",
                new CategoryMessage<>());
        showMessage("constructor nullCategoryValue",
                new CategoryMessage<>("<NULL>"));
        showMessageSingleRecord("constructor SingleRecord",
                new CategoryMessage<>());
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new CategoryMessage<>());
    }

    private static void showClassNameMessage() {
        System.out.println("-showClassNameMessage---");

        showMessage("constructor",
                new ClassNameMessage<>());
        showMessage("constructor hashCode",
                new ClassNameMessage<>(true));
        showMessageSingleRecord("constructor SingleRecord",
                new ClassNameMessage<>());
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new ClassNameMessage<>());
    }

    private static void showCompareMessageBuilder() {
        System.out.println("-showCompareMessageBuilder---");

        showMessage("category / size",
                new CompareMessageBuilder().category().size().build());
        showMessageSingleRecord("className / category(other)",
                new CompareMessageBuilder().className().category("<NULL>>").build());
        showMessage("values",
                new CompareMessageBuilder().values().build());
    }

    private static void showConditionalMessage() {
        System.out.println("-showConditionalMessage---");

        showMessage("constructor",
                new ConditionalMessage<>(ClassFilter.equalTo(SingleRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        showMessageSingleRecord("constructor SingleRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(SingleRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(SingleRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
    }

    private static void showConstantMessage() {
        System.out.println("-showConstantMessage---");

        showMessage("constructor",
                new ConstantMessage<>("message"));
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new ConstantMessage<>("KeyValueRecord"));
    }

    private static void showExtendedValuesMessage() {
        System.out.println("-showExtendedValuesMessage---");

        showMessage("constructor",
                new ExtendedValuesMessage<>("(", ")"));
        showMessage("constructor first/last",
                new ExtendedValuesMessage<>("", "", "<", ">"));
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new ExtendedValuesMessage<>("(", ")"));
    }

    private static void showJoinedValuesMessage() {
        System.out.println("-showJoinedValuesMessage---");

        showMessage("constructor",
                new JoinedValuesMessage<>());
        showMessage("constructor delimiter",
                new JoinedValuesMessage<>(""));
        showMessageKeyValueRecord("constructor delimiter showMessageKeyValueRecord",
                new JoinedValuesMessage<>("="));
    }

    private static void showNullSafeMessage() {
        System.out.println("-showNullSafeMessage---");

        System.out.println(new NullSafeMessage<>(new ToStringMessage<>()).createMessage(null));
        System.out.println(new NullSafeMessage<>(new ToStringMessage<>(), "<NULL>").createMessage(null));
    }

    private static void showRecordIdMessage() {
        System.out.println("-showRecordIdMessage---");

        showMessage("constructor",
                new RecordIdMessage<>());
        showMessage("constructor prefix missingRecordIdMessage",
                new RecordIdMessage<>("", "-"));
        showMessageKeyValueRecord("constructor prefix missingRecordIdMessage KeyValueRecord",
                new RecordIdMessage<>(RecordIdMessage.DEFAULT_PREFIX, "-"));
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
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new ShortMessage<>());
    }

    private static void showSizeMessage() {
        System.out.println("-showSizeMessage---");

        showMessage("constructor",
                new SizeMessage<>());
    }

    private static void showSupplierMessage() {
        System.out.println("-showSupplierMessage---");

        showMessage("constructor",
                new SupplierMessage<>(() -> "message"));
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
        showMessageKeyValueRecord("constructor KeyValueRecord",
                new ToStringMessage<>());
    }

    private static void showValueMessage() {
        System.out.println("-showValueMessage---");

        showMessage("constructor index",
                new ValueMessage<>(1));
        showMessage("constructor index nullFieldMessage",
                new ValueMessage<>(1, "<NULL>"));
        showMessage("constructor index nullFieldMessage fieldValueMapper",
                new ValueMessage<>(1, "<NULL>", new AddPrefixFieldValueMapper("new value 1: ")));
        showMessage("constructor function",
                new ValueMessage<>(Record::getLastField));
        showMessage("constructor function nullFieldMessage",
                new ValueMessage<>(Record::getLastField, "<NULL>"));
        showMessage("constructor function nullFieldMessage fieldValueMapper",
                new ValueMessage<>(Record::getLastField, "<NULL>", new AddPrefixFieldValueMapper("new value 1: ")));
        showMessageKeyValueRecord("key",
                ValueMessage.key());
        showMessageKeyValueRecord("keyField",
                ValueMessage.keyField(new AddPrefixFieldValueMapper("new key: ")));
        showMessageSingleRecord("value",
                ValueMessage.value());
        showMessageSingleRecord("valueField",
                ValueMessage.valueField(new AddPrefixFieldValueMapper("new value: ")));
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
