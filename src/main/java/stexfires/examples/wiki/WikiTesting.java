package stexfires.examples.wiki;

import stexfires.core.TextRecord;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.mapper.ToSingleMapper;
import stexfires.core.mapper.ValuesMapper;
import stexfires.core.producer.ProducerException;
import stexfires.core.record.ValueRecord;
import stexfires.io.RecordIOStreams;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class WikiTesting {

    private static final String RESOURCE_PATH = "stexfires/examples/wiki/";
    private static final int TITLE_MIN_WIDTH = 100;
    private static final int LINK_MIN_WIDTH = 200;
    private static final String TITLE_NAME = "Title";
    private static final String LINK_NAME = "Link";

    private WikiTesting() {
    }

    private static SimpleDelimitedFileSpec createProducerFileSpec() {
        List<SimpleDelimitedFieldSpec> fieldSpecsProducer = new ArrayList<>();
        fieldSpecsProducer.add(new SimpleDelimitedFieldSpec());
        fieldSpecsProducer.add(new SimpleDelimitedFieldSpec());
        return SimpleDelimitedFileSpec.read(StandardCharsets.UTF_8,
                "\t",
                fieldSpecsProducer,
                0, 0, true, true);
    }

    private static MarkdownTableFileSpec createTableConsumerFileSpec(String title) {
        List<MarkdownTableFieldSpec> fieldSpecsConsumer = new ArrayList<>();
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec(TITLE_NAME, TITLE_MIN_WIDTH));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec(LINK_NAME, LINK_MIN_WIDTH));
        return MarkdownTableFileSpec.write(StandardCharsets.UTF_8,
                fieldSpecsConsumer,
                LineSeparator.CR_LF, Alignment.START, title, "");
    }

    private static MarkdownListFileSpec createListConsumerFileSpec(String title) {
        return MarkdownListFileSpec.write(StandardCharsets.UTF_8, LineSeparator.CR_LF,
                title, "", MarkdownListFileSpec.DEFAULT_BULLET_POINT, true);
    }

    private static void convertToMarkdownTable(String title, SimpleDelimitedProducer producer,
                                               OutputStream outputStream)
            throws ProducerException, ConsumerException, IOException {
        MarkdownTableFileSpec consumerFileSpec = createTableConsumerFileSpec(title);
        MarkdownTableConsumer consumer = consumerFileSpec.consumer(outputStream);
        RecordMapper<TextRecord, TextRecord> mapper = ValuesMapper.applyFunctions(
                r -> "[" + r.valueAt(0) + "]" + "(" + r.valueAt(1) + ")",
                r -> "[" + r.valueAt(1).replace("http://", "").replace("https://", "")
                        + "]" + "(" + r.valueAt(1) + ")");

        RecordIOStreams.convert(producer, mapper, consumer);
    }

    private static void convertToMarkdownList(String title, SimpleDelimitedProducer producer,
                                              OutputStream outputStream)
            throws ProducerException, ConsumerException, IOException {
        MarkdownListFileSpec consumerFileSpec = createListConsumerFileSpec(title);
        MarkdownListConsumer consumer = consumerFileSpec.consumer(outputStream);
        RecordMapper<TextRecord, ValueRecord> mapper = ValuesMapper.applyFunctions(
                                                                           r -> "[" + r.valueAt(0) + "]" + "(" + r.valueAt(1) + ")")
                                                                   .andThen(new ToSingleMapper<>(0));

        RecordIOStreams.convert(producer, mapper, consumer);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        List<String> resources = new ArrayList<>();
        resources.add(RESOURCE_PATH + "Wiki_Testing_JUnit5.txt");
        resources.add(RESOURCE_PATH + "Wiki_Testing_Other.txt");

        List<String> headers = new ArrayList<>();
        headers.add("## JUnit 5 ##");
        headers.add("## Other testing or specification frameworks ##");

        SimpleDelimitedFileSpec producerFileSpec = createProducerFileSpec();

        // Markdown Table
        String outputFileNameTable = "Wiki_Testing_Table.md";
        File outputFileTable = new File(outputDirectory, outputFileNameTable);
        System.out.println("Generate MarkdownTable file: " + outputFileTable);

        try (OutputStream outputStream = new FileOutputStream(outputFileTable)) {
            for (int i = 0; i < resources.size(); i++) {
                try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resources.get(i))) {
                    SimpleDelimitedProducer producer = producerFileSpec.producer(inputStream);
                    convertToMarkdownTable(headers.get(i), producer, outputStream);
                }
            }
        } catch (ProducerException | ConsumerException | IOException e) {
            System.out.println("Cannot generate MarkdownTable file! " + e.getMessage());
        }

        // Markdown List
        String outputFileNameList = "Wiki_Testing_List.md";
        File outputFileList = new File(outputDirectory, outputFileNameList);
        System.out.println("Generate MarkdownList file: " + outputFileList);

        try (OutputStream outputStream = new FileOutputStream(outputFileList)) {
            for (int i = 0; i < resources.size(); i++) {
                try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resources.get(i))) {
                    SimpleDelimitedProducer producer = producerFileSpec.producer(inputStream);
                    convertToMarkdownList(headers.get(i), producer, outputStream);
                }
            }
        } catch (ProducerException | ConsumerException | IOException e) {
            System.out.println("Cannot generate MarkdownList file! " + e.getMessage());
        }
    }

}
