package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.filter.ClassFilter;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.impl.TwoValuesRecord;
import stexfires.record.mapper.fieldvalue.AddPrefixFieldValueMapper;
import stexfires.record.message.CategoryMessage;
import stexfires.record.message.ClassNameMessage;
import stexfires.record.message.CompareMessageBuilder;
import stexfires.record.message.ConditionalMessage;
import stexfires.record.message.ConstantMessage;
import stexfires.record.message.ExtendedValuesMessage;
import stexfires.record.message.FormatterMessage;
import stexfires.record.message.JoinedValuesMessage;
import stexfires.record.message.NullSafeMessage;
import stexfires.record.message.RecordIdMessage;
import stexfires.record.message.RecordMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.message.SizeMessage;
import stexfires.record.message.SupplierMessage;
import stexfires.record.message.ToStringMessage;
import stexfires.record.message.ValueMessage;
import stexfires.util.Strings;

import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMessage {

    private ExamplesMessage() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new OneValueRecord("category", 0L, "A"),
                new OneValueRecord("category", 1L, "B"),
                new OneValueRecord("", 2L, "C"),
                new OneValueRecord("Category", 3L, "D"),
                new OneValueRecord(null, 4L, "E"),
                new OneValueRecord("c", 5L, "F"),
                new TwoValuesRecord("c", 6L, "X", "Y"),
                new KeyValueRecord("key", "value"),
                new ManyValuesRecord("Category", 7L, "S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyValuesRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyValuesRecord(),
                new ManyValuesRecord("Category", 8L, null, null, null, null),
                new EmptyRecord()
        );
    }

    private static Stream<OneValueRecord> generateStreamOneValueRecord() {
        return Stream.of(
                new OneValueRecord("value1"),
                new OneValueRecord(null, 2L, "value2"),
                new OneValueRecord("category", 3L, "value3")
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueRecord("key1", "value1"),
                new KeyValueRecord(null, 2L, "key2", "value2"),
                new KeyValueRecord("category", 3L, "key3", "value3")
        );
    }

    private static void printMessage(String title, RecordMessage<TextRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(TextRecordStreams.mapToMessage(generateStream(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void printMessageOneValueRecord(String title, RecordMessage<? super OneValueRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(TextRecordStreams.mapToMessage(generateStreamOneValueRecord(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void printMessageKeyValueRecord(String title, RecordMessage<? super KeyValueRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(TextRecordStreams.mapToMessage(generateStreamKeyValueRecord(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void showCategoryMessage() {
        System.out.println("-showCategoryMessage---");

        printMessage("constructor",
                new CategoryMessage<>());
        printMessage("constructor nullCategoryValue",
                new CategoryMessage<>("<NULL>"));
        printMessageOneValueRecord("constructor OneValueRecord",
                new CategoryMessage<>());
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new CategoryMessage<>());
    }

    private static void showClassNameMessage() {
        System.out.println("-showClassNameMessage---");

        printMessage("constructor",
                new ClassNameMessage<>());
        printMessage("constructor hashCode",
                new ClassNameMessage<>(true));
        printMessageOneValueRecord("constructor OneValueRecord",
                new ClassNameMessage<>());
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ClassNameMessage<>());
    }

    private static void showCompareMessageBuilder() {
        System.out.println("-showCompareMessageBuilder---");

        printMessage("category / size",
                new CompareMessageBuilder().category().size().build());
        printMessageOneValueRecord("className / category(other)",
                new CompareMessageBuilder().className().category("<NULL>>").build());
        printMessage("values",
                new CompareMessageBuilder().values().build());
    }

    private static void showConditionalMessage() {
        System.out.println("-showConditionalMessage---");

        printMessage("constructor",
                new ConditionalMessage<>(ClassFilter.equalTo(OneValueRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        printMessageOneValueRecord("constructor OneValueRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(OneValueRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(OneValueRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
    }

    private static void showConstantMessage() {
        System.out.println("-showConstantMessage---");

        printMessage("constructor",
                new ConstantMessage<>("message"));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ConstantMessage<>("KeyValueRecord"));
    }

    private static void showExtendedValuesMessage() {
        System.out.println("-showExtendedValuesMessage---");

        printMessage("constructor",
                new ExtendedValuesMessage<>("(", ")"));
        printMessage("constructor first/last",
                new ExtendedValuesMessage<>("", "", "<", ">"));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ExtendedValuesMessage<>("(", ")"));
    }

    private static void showFormatterMessage() {
        System.out.println("-showFormatterMessage---");

        printMessage("constructor",
                new FormatterMessage<>("%nClassName: %1$-40s Category: %2$-10S RecordId: %3$5d Size: %4$2d Values: (%5$-3s | %6$S)",
                        Locale.getDefault(), 2, "<>"));
        printMessage("constructor withoutValues",
                FormatterMessage.withoutValues("%4$d",
                        Locale.getDefault()));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new FormatterMessage<>("%5$s=%6$s",
                        Locale.getDefault(), 2, ""));
    }

    private static void showJoinedValuesMessage() {
        System.out.println("-showJoinedValuesMessage---");

        printMessage("constructor",
                new JoinedValuesMessage<>());
        printMessage("constructor delimiter",
                new JoinedValuesMessage<>(""));
        printMessageKeyValueRecord("constructor delimiter showMessageKeyValueRecord",
                new JoinedValuesMessage<>("="));
    }

    private static void showNullSafeMessage() {
        System.out.println("-showNullSafeMessage---");

        System.out.println(new NullSafeMessage<>(new ToStringMessage<>()).createMessage(null));
        System.out.println(new NullSafeMessage<>(new ToStringMessage<>(), "<NULL>").createMessage(null));
    }

    private static void showRecordIdMessage() {
        System.out.println("-showRecordIdMessage---");

        printMessage("constructor",
                new RecordIdMessage<>());
        printMessage("constructor prefix missingRecordIdMessage",
                new RecordIdMessage<>("", "-"));
        printMessageKeyValueRecord("constructor prefix missingRecordIdMessage KeyValueRecord",
                new RecordIdMessage<>(RecordIdMessage.DEFAULT_PREFIX, "-"));
    }

    private static void showRecordMessage() {
        System.out.println("-showRecordMessage---");

        printMessage("prepend / append",
                new SizeMessage<>().prepend("Size:").append(" RecordId:").append(new RecordIdMessage<>()));
    }

    private static void showShortMessage() {
        System.out.println("-showShortMessage---");

        printMessage("constructor",
                new ShortMessage<>());
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ShortMessage<>());
    }

    private static void showSizeMessage() {
        System.out.println("-showSizeMessage---");

        printMessage("constructor",
                new SizeMessage<>());
    }

    private static void showSupplierMessage() {
        System.out.println("-showSupplierMessage---");

        printMessage("constructor",
                new SupplierMessage<>(() -> "message"));
        printMessage("localTime",
                SupplierMessage.localTime());
        printMessage("threadName",
                SupplierMessage.threadName());
        printMessage("sequence",
                SupplierMessage.sequence(0L));
    }

    private static void showToStringMessage() {
        System.out.println("-showToStringMessage---");

        printMessage("constructor",
                new ToStringMessage<>());
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ToStringMessage<>());
    }

    private static void showValueMessage() {
        System.out.println("-showValueMessage---");

        printMessage("constructor index",
                new ValueMessage<>(1));
        printMessage("constructor index nullFieldMessage",
                new ValueMessage<>(1, "<NULL>"));
        printMessage("constructor index nullFieldMessage fieldValueMapper",
                new ValueMessage<>(1, "<NULL>", new AddPrefixFieldValueMapper("new value 1: ")));
        printMessage("constructor function",
                new ValueMessage<>(TextRecord::lastField));
        printMessage("constructor function nullFieldMessage",
                new ValueMessage<>(TextRecord::lastField, "<NULL>"));
        printMessage("constructor function nullFieldMessage fieldValueMapper",
                new ValueMessage<>(TextRecord::lastField, "<NULL>", new AddPrefixFieldValueMapper("new value 1: ")));
        printMessageKeyValueRecord("key",
                ValueMessage.key());
        printMessageKeyValueRecord("keyField",
                ValueMessage.keyField(new AddPrefixFieldValueMapper("new key: ")));
        printMessageOneValueRecord("value",
                ValueMessage.value());
        printMessageOneValueRecord("valueField",
                ValueMessage.valueField(new AddPrefixFieldValueMapper("new value: ")));
    }

    public static void main(String... args) {
        showCategoryMessage();
        showClassNameMessage();
        showCompareMessageBuilder();
        showConditionalMessage();
        showConstantMessage();
        showExtendedValuesMessage();
        showFormatterMessage();
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
