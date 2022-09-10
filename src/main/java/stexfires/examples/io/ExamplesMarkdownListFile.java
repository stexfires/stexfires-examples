package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.markdown.list.MarkdownListFile;
import stexfires.io.markdown.list.MarkdownListFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMarkdownListFile {

    private ExamplesMarkdownListFile() {
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test1---");

        MarkdownListFile file = MarkdownListFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        lineSeparator,
                        "Header ValueFieldRecord",
                        "Footer ValueFieldRecord",
                        MarkdownListFileSpec.BulletPoint.NUMBER,
                        true
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<ValueRecord> stream = TextRecordStreams.of(
                new ValueFieldRecord("a"),
                new ValueFieldRecord("b"),
                new ValueFieldRecord(null),
                new ValueFieldRecord("d")
        );
        RecordFiles.writeFile(stream, file);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        MarkdownListFile file = MarkdownListFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        lineSeparator,
                        "Header KeyValueFieldsRecord",
                        "Footer KeyValueFieldsRecord",
                        MarkdownListFileSpec.BulletPoint.STAR,
                        false
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<KeyValueRecord> stream = TextRecordStreams.of(
                new KeyValueFieldsRecord("key1", "value1"),
                new KeyValueFieldsRecord("key2", "value2"),
                new KeyValueFieldsRecord("key3", null),
                new KeyValueFieldsRecord("key4", "value4")
        );
        RecordFiles.writeFile(stream, file);
    }

    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            test1(Path.of(args[0], "MarkdownListFile_1.md"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "MarkdownListFile_2.md"), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
