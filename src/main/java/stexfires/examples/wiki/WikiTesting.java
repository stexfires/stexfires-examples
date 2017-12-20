package stexfires.examples.wiki;

import stexfires.core.Record;
import stexfires.core.mapper.NewValuesMapper;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.mapper.to.ToSingleMapper;
import stexfires.core.record.ValueRecord;
import stexfires.io.RecordFiles;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.delimited.simple.SimpleDelimitedProducer;
import stexfires.io.markdown.list.MarkdownListConsumer;
import stexfires.io.markdown.list.MarkdownListFileSpec;
import stexfires.io.markdown.table.MarkdownTableConsumer;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public final class WikiTesting {

    private static SimpleDelimitedFileSpec createProducerFileSpec() {
        List<SimpleDelimitedFieldSpec> fieldSpecsProducer = new ArrayList<>();
        fieldSpecsProducer.add(new SimpleDelimitedFieldSpec());
        fieldSpecsProducer.add(new SimpleDelimitedFieldSpec());
        return SimpleDelimitedFileSpec.read(StandardCharsets.ISO_8859_1,
                "\t",
                fieldSpecsProducer,
                0, 0, true, true);
    }

    private static MarkdownTableFileSpec createTableConsumerFileSpec(String title) {
        List<MarkdownTableFieldSpec> fieldSpecsConsumer = new ArrayList<>();
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Title", 100));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Link", 200));
        return MarkdownTableFileSpec.write(StandardCharsets.ISO_8859_1,
                fieldSpecsConsumer,
                LineSeparator.CR_LF, Alignment.START, title, "");
    }

    private static MarkdownListFileSpec createListConsumerFileSpec(String title) {
        return MarkdownListFileSpec.write(StandardCharsets.ISO_8859_1, LineSeparator.CR_LF,
                title, "", MarkdownListFileSpec.DEFAULT_BULLET_POINT, true);
    }

    private static void convertToMarkdownTable(String title, SimpleDelimitedProducer producer,
                                               OutputStream outputStream) throws IOException {
        MarkdownTableFileSpec consumerFileSpec = createTableConsumerFileSpec(title);
        MarkdownTableConsumer consumer = consumerFileSpec.consumer(outputStream);

        RecordMapper<Record, Record> mapper = NewValuesMapper.applyFunctions(r -> "[" + r.getValueAt(0) + "]" + "(" + r.getValueAt(1) + ")",
                r -> "[" + r.getValueAt(1).replace("http://", "").replace("https://", "") + "]" + "(" + r.getValueAt(1) + ")");

        RecordFiles.convert(producer, mapper, consumer);
    }

    private static void convertToMarkdownList(String title, SimpleDelimitedProducer producer,
                                              OutputStream outputStream) throws IOException {
        MarkdownListFileSpec consumerFileSpec = createListConsumerFileSpec(title);
        MarkdownListConsumer consumer = consumerFileSpec.consumer(outputStream);

        RecordMapper<Record, ValueRecord> mapper = NewValuesMapper.applyFunctions(r -> "[" + r.getValueAt(0) + "]" + "(" + r.getValueAt(1) + ")")
                .andThen(new ToSingleMapper<>(0));

        RecordFiles.convert(producer, mapper, consumer);
    }

    public static void main(String[] args) {
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() && !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        List<String> resources = new ArrayList<>();
        resources.add("stexfires/examples/wiki/Wiki_Testing_JUnit5.txt");
        resources.add("stexfires/examples/wiki/Wiki_Testing_Other.txt");

        List<String> headers = new ArrayList<>();
        headers.add("## JUnit 5 ##");
        headers.add("## Other testing or specification frameworks ##");

        SimpleDelimitedFileSpec producerFileSpec = createProducerFileSpec();

        // Markdown Table
        String outputFileNameTable = "Wiki_Testing_Table.md";
        File outputFileTable = new File(outputDirectory, outputFileNameTable);
        System.out.println("Generate Markdown Table: " + outputFileTable);

        try (OutputStream outputStream = new FileOutputStream(outputFileTable)) {
            for (int i = 0; i < resources.size(); i++) {
                try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resources.get(i))) {
                    SimpleDelimitedProducer producer = producerFileSpec.producer(inputStream);
                    convertToMarkdownTable(headers.get(i), producer, outputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Markdown List
        String outputFileNameList = "Wiki_Testing_List.md";
        File outputFileList = new File(outputDirectory, outputFileNameList);
        System.out.println("Generate Markdown List: " + outputFileList);

        try (OutputStream outputStream = new FileOutputStream(outputFileList)) {
            for (int i = 0; i < resources.size(); i++) {
                try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resources.get(i))) {
                    SimpleDelimitedProducer producer = producerFileSpec.producer(inputStream);
                    convertToMarkdownList(headers.get(i), producer, outputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
