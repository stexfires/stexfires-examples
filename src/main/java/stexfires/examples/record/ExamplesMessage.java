package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.filter.ClassFilter;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.OneFieldRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.mapper.field.AddPrefixFieldTextMapper;
import stexfires.record.message.CategoryMessage;
import stexfires.record.message.ClassNameMessage;
import stexfires.record.message.CompareMessageBuilder;
import stexfires.record.message.ConditionalMessage;
import stexfires.record.message.ConstantMessage;
import stexfires.record.message.ExtendedTextsMessage;
import stexfires.record.message.FormatterMessage;
import stexfires.record.message.JoinedTextsMessage;
import stexfires.record.message.NullSafeMessage;
import stexfires.record.message.RecordIdMessage;
import stexfires.record.message.RecordMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.message.SizeMessage;
import stexfires.record.message.SupplierMessage;
import stexfires.record.message.TextMessage;
import stexfires.record.message.ToStringMessage;
import stexfires.util.Strings;

import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMessage {

    private ExamplesMessage() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new OneFieldRecord("category", 0L, "A"),
                new OneFieldRecord("category", 1L, "B"),
                new OneFieldRecord("", 2L, "C"),
                new OneFieldRecord("Category", 3L, "D"),
                new OneFieldRecord(null, 4L, "E"),
                new OneFieldRecord("c", 5L, "F"),
                new TwoFieldsRecord("c", 6L, "X", "Y"),
                new KeyValueRecord("key", "value"),
                new StandardRecord("Category", 7L, "S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord(),
                new StandardRecord("Category", 8L, null, null, null, null),
                new EmptyRecord()
        );
    }

    private static Stream<OneFieldRecord> generateStreamOneValueRecord() {
        return Stream.of(
                new OneFieldRecord("value1"),
                new OneFieldRecord(null, 2L, "value2"),
                new OneFieldRecord("category", 3L, "value3")
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

    private static void printMessageOneValueRecord(String title, RecordMessage<? super OneFieldRecord> recordMessage) {
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
        printMessageOneValueRecord("constructor OneFieldRecord",
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
        printMessageOneValueRecord("constructor OneFieldRecord",
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
                new CompareMessageBuilder().texts().build());
    }

    private static void showConditionalMessage() {
        System.out.println("-showConditionalMessage---");

        printMessage("constructor",
                new ConditionalMessage<>(ClassFilter.equalTo(OneFieldRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        printMessageOneValueRecord("constructor OneFieldRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(OneFieldRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(OneFieldRecord.class),
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
                new ExtendedTextsMessage<>("(", ")"));
        printMessage("constructor first/last",
                new ExtendedTextsMessage<>("", "", "<", ">"));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new ExtendedTextsMessage<>("(", ")"));
    }

    private static void showFormatterMessage() {
        System.out.println("-showFormatterMessage---");

        printMessage("constructor",
                new FormatterMessage<>("%nClassName: %1$-40s Category: %2$-10S RecordId: %3$5d Size: %4$2d Values: (%5$-3s | %6$S)",
                        Locale.getDefault(), 2, "<>"));
        printMessage("constructor withoutValues",
                FormatterMessage.withoutTexts("%4$d",
                        Locale.getDefault()));
        printMessageKeyValueRecord("constructor KeyValueRecord",
                new FormatterMessage<>("%5$s=%6$s",
                        Locale.getDefault(), 2, ""));
    }

    private static void showJoinedValuesMessage() {
        System.out.println("-showJoinedValuesMessage---");

        printMessage("constructor",
                new JoinedTextsMessage<>());
        printMessage("constructor delimiter",
                new JoinedTextsMessage<>(""));
        printMessageKeyValueRecord("constructor delimiter showMessageKeyValueRecord",
                new JoinedTextsMessage<>("="));
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
                new TextMessage<>(1));
        printMessage("constructor index nullFieldMessage",
                new TextMessage<>(1, "<NULL>"));
        printMessage("constructor index nullFieldMessage fieldValueMapper",
                new TextMessage<>(1, "<NULL>", new AddPrefixFieldTextMapper("new value 1: ")));
        printMessage("constructor function",
                new TextMessage<>(TextRecord::lastField));
        printMessage("constructor function nullFieldMessage",
                new TextMessage<>(TextRecord::lastField, "<NULL>"));
        printMessage("constructor function nullFieldMessage fieldValueMapper",
                new TextMessage<>(TextRecord::lastField, "<NULL>", new AddPrefixFieldTextMapper("new value 1: ")));
        printMessageKeyValueRecord("key",
                TextMessage.key());
        printMessageKeyValueRecord("keyField",
                TextMessage.keyField(new AddPrefixFieldTextMapper("new key: ")));
        printMessageOneValueRecord("value",
                TextMessage.value());
        printMessageOneValueRecord("valueField",
                TextMessage.valueField(new AddPrefixFieldTextMapper("new value: ")));
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
