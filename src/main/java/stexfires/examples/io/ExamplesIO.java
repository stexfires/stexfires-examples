package stexfires.examples.io;

import stexfires.data.CollectionDataTypeFormatter;
import stexfires.data.CollectionDataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.io.consumer.StringWritableRecordConsumer;
import stexfires.io.container.RecordContainer;
import stexfires.io.container.RecordContainerLarge;
import stexfires.io.container.UnpackResult;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.TextFilter;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.CategoryMapper;
import stexfires.record.message.RecordMessage;
import stexfires.record.modifier.SortModifier;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.SortNulls;
import stexfires.util.function.StringUnaryOperators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static stexfires.examples.record.RecordSystemOutUtil.RECORD_CONSUMER;
import static stexfires.examples.record.RecordSystemOutUtil.printlnOptionalRecord;
import static stexfires.examples.record.RecordSystemOutUtil.printlnRecord;
import static stexfires.examples.record.RecordSystemOutUtil.printlnRecordList;
import static stexfires.io.RecordIOStreams.*;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "HardcodedLineSeparator"})
public final class ExamplesIO {

    private ExamplesIO() {
    }

    private static void showRead() throws UncheckedProducerException {
        System.out.println("-showRead---");

        var fileSpec = SingleValueFileSpec.producerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                1, 0, SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                true
        );

        String sourceString = "###Value####\na\nb\n\nd\n";
        CategoryMapper<ValueRecord> categoryMapper = CategoryMapper.constant("new_category");
        SortModifier<ValueRecord> sortModifier = new SortModifier<>(RecordComparators.recordId(SortNulls.FIRST).reversed());

        System.out.println("---producer(sourceString) read andCollect");
        printlnRecordList(
                read(fileSpec.producer(sourceString), andCollect(Collectors.toList())));

        System.out.println("---producer(sourceString) read andCollectMapped");
        printlnRecordList(
                read(fileSpec.producer(sourceString), andCollectMapped(Collectors.toList(), categoryMapper)));

        System.out.println("---producer(sourceString) read andCollectModified");
        printlnRecordList(
                read(fileSpec.producer(sourceString), andCollectModified(Collectors.toList(), sortModifier)));

        System.out.println("---producer(sourceString) read andConsume");
        read(fileSpec.producer(sourceString), andConsume(RECORD_CONSUMER));

        System.out.println("---producer(sourceString) read andConsumeMapped");
        read(fileSpec.producer(sourceString), andConsumeMapped(RECORD_CONSUMER, categoryMapper));

        System.out.println("---producer(sourceString) read andConsumeModified");
        read(fileSpec.producer(sourceString), andConsumeModified(RECORD_CONSUMER, sortModifier));

        System.out.println("---producer(sourceString) read andFindFirst");
        printlnOptionalRecord(
                read(fileSpec.producer(sourceString), andFindFirst()));

        System.out.println("---producer(sourceString) read andFindFirstMapped");
        printlnOptionalRecord(
                read(fileSpec.producer(sourceString), andFindFirstMapped(categoryMapper)));

        System.out.println("---producer(sourceString) read andFindFirstModified");
        printlnOptionalRecord(
                read(fileSpec.producer(sourceString), andFindFirstModified(sortModifier)));

        System.out.println("---producer(sourceString) readAndConsume");
        readAndConsume(fileSpec.producer(sourceString), RECORD_CONSUMER);

        System.out.println("---readFromString andCollect");
        printlnRecordList(
                readFromString(fileSpec, sourceString, andCollect(Collectors.toList())));

        System.out.println("---newRecordDataTypeParser");
        printlnRecord(
                newRecordDataTypeParser(";", "[", "]", null, null)
                        .parse("[category_ValueFieldRecord;1;value_ValueFieldRecord]"));
        printlnRecord(
                newRecordDataTypeParser(", ", null, null, null, null)
                        .parse(", , value0, value1, value2"));
        printlnRecord(
                newRecordDataTypeParser(fileSpec, null, null).parse(sourceString));
    }

    private static void showWrite() throws IOException, UncheckedConsumerException {
        System.out.println("-showWrite---");

        var fileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX, LineSeparator.LF,
                "###Value###",
                "######",
                false);

        var fileSpecCompact = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX, LineSeparator.LF,
                SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE,
                SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                true);

        ValueFieldRecord valueFieldRecord1 = new ValueFieldRecord("category_ValueFieldRecord", 1L, "value_ValueFieldRecord");
        ValueFieldRecord valueFieldRecord2 = new ValueFieldRecord("category_ValueFieldRecord", 2L, (String) null);
        ValueFieldRecord valueFieldRecord3 = new ValueFieldRecord(null, null, (String) null);
        KeyValueFieldsRecord keyValueFieldsRecord10 = new KeyValueFieldsRecord("category_KeyValueFieldsRecord", 10L, "key_KeyValueFieldsRecord", "value_KeyValueFieldsRecord");
        KeyValueFieldsRecord keyValueFieldsRecord11 = new KeyValueFieldsRecord("category_KeyValueFieldsRecord", 11L, "key_KeyValueFieldsRecord", null);

        List<ValueRecord> listValueRecords = List.of(valueFieldRecord1, valueFieldRecord2, valueFieldRecord3, keyValueFieldsRecord10, keyValueFieldsRecord11);

        System.out.println("---writeStreamIntoString");
        System.out.println(writeStreamIntoString(fileSpec,
                true, listValueRecords.stream()));
        System.out.println(writeStreamIntoString(fileSpec,
                false, TextRecordStreams.of(keyValueFieldsRecord10)));
        System.out.println(writeStreamIntoString(fileSpec,
                true, TextRecordStreams.of(valueFieldRecord1, keyValueFieldsRecord10)));

        System.out.println("---writeRecordIntoString");
        System.out.println(writeRecordIntoString(fileSpec,
                true, valueFieldRecord1));
        System.out.println(writeRecordIntoString(fileSpec,
                false, valueFieldRecord3));
        System.out.println(writeRecordIntoString(fileSpec,
                true, keyValueFieldsRecord10));

        System.out.println("---newRecordDataTypeFormatter");
        System.out.println(newRecordDataTypeFormatter(";", "[", "]", null)
                .format(valueFieldRecord1));
        System.out.println(newRecordDataTypeFormatter(fileSpec,
                true, null).format(valueFieldRecord1));

        System.out.println("---writeRecordIntoStringMessage");
        RecordMessage<ValueRecord> mapper = writeRecordIntoStringMessage(fileSpecCompact, true)
                .andThen(StringUnaryOperators.surround("<", ">"));
        System.out.println(
                TextRecordStreams.collectMessages(listValueRecords.stream(), mapper)
        );

        System.out.println("---writeStream StringWritableRecordConsumer");
        try (var stringWritableRecordConsumer = new StringWritableRecordConsumer<>(fileSpec)) {
            System.out.println(
                    writeStream(stringWritableRecordConsumer, listValueRecords.stream())
                            .consumedString(false));
        }
        System.out.println("---writeRecord StringWritableRecordConsumer");
        try (var stringWritableRecordConsumer = new StringWritableRecordConsumer<>(fileSpec)) {
            System.out.println(
                    writeRecord(stringWritableRecordConsumer, keyValueFieldsRecord10)
                            .consumedString(true));
        }

        System.out.println("---writeStreamIntoRecord");
        printlnRecord(writeStreamIntoRecord(
                fileSpecCompact,
                true,
                TextRecordStreams.filter(listValueRecords.stream(),
                        TextFilter.isNotNull(ValueRecord::valueField))));
    }

    private static void showStringList() {
        System.out.println("-showStringList---");

        System.out.println(toStringList(TextRecords.empty()));
        System.out.println(toStringList(new ValueFieldRecord("cat", 1L, "value")));
        System.out.println(toStringList(new ValueFieldRecord("value")));
        System.out.println(toStringList(new ValueFieldRecord(null)));

        List<String> stringList0 = new ArrayList<>();
        stringList0.add("cat");
        stringList0.add("1");
        stringList0.add("value");
        printlnRecord(fromStringList(stringList0));
        List<String> stringList1 = new ArrayList<>();
        stringList1.add(null);
        stringList1.add(null);
        stringList1.add("value");
        printlnRecord(fromStringList(stringList1));
        List<String> stringList2 = new ArrayList<>();
        stringList2.add(null);
        stringList2.add(null);
        printlnRecord(fromStringList(stringList2));
        List<String> stringList3 = new ArrayList<>();
        stringList3.add(null);
        stringList3.add("");
        stringList3.add("value0");
        stringList3.add(null);
        stringList3.add("value2");
        printlnRecord(fromStringList(stringList3));
    }

    private static ManyFieldsRecord generateRecord() {
        return new ManyFieldsRecord("sampleCategory", 42L, "value0", "value1", "value2");
    }

    @SuppressWarnings("ReturnOfNull")
    private static void showFormattedStringList() {
        System.out.println("-showFormattedStringList---");

        TextRecord record = generateRecord();

        // to String List
        List<String> stringList = toStringList(generateRecord());
        System.out.println(stringList);

        // from String List
        printlnRecord(fromStringList(stringList));

        // format String List
        String formattedStringList = CollectionDataTypeFormatter.withDelimiter(";", "[", "]", StringDataTypeFormatter.identity(), () -> null)
                                                                .format(stringList);
        System.out.println(formattedStringList);

        // Parse String List
        List<String> parsedStringList = CollectionDataTypeParser.withDelimiterAsList(";", "[", "]", StringDataTypeParser.identity(), () -> null, () -> null)
                                                                .parse(formattedStringList);
        printlnRecord(fromStringList(parsedStringList));
    }

    private static void showRecordContainer() throws IOException {
        System.out.println("-showRecordContainer---");

        TextRecord record = generateRecord();

        RecordContainer recordContainerLarge = new RecordContainerLarge();
        TextRecord packedLarge = recordContainerLarge.pack(record);
        UnpackResult unpackedLarge = recordContainerLarge.unpack(packedLarge);

        printlnRecord(packedLarge);
        printlnOptionalRecord(unpackedLarge.record());
    }

    public static void main(String... args) {
        try {
            showRead();
            showWrite();
            showStringList();
            showFormattedStringList();
            showRecordContainer();
        } catch (IOException | UncheckedProducerException | UncheckedConsumerException e) {
            e.printStackTrace();
        }
    }

}
