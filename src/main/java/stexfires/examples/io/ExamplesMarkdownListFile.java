package stexfires.examples.io;

import stexfires.core.TextRecordStreams;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.SingleRecord;
import stexfires.io.RecordFiles;
import stexfires.io.markdown.list.MarkdownListFile;
import stexfires.io.markdown.list.MarkdownListFileSpec;
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
                        "Header SingleRecord",
                        "Footer SingleRecord",
                        MarkdownListFileSpec.BulletPoint.NUMBER,
                        true
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<SingleRecord> stream = TextRecordStreams.of(
                new SingleRecord("a"),
                new SingleRecord("b"),
                new SingleRecord(null),
                new SingleRecord("d")
        );
        RecordFiles.writeFile(stream, file);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        MarkdownListFile file = MarkdownListFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        lineSeparator,
                        "Header KeyValueRecord",
                        "Footer KeyValueRecord",
                        MarkdownListFileSpec.BulletPoint.STAR,
                        false
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<KeyValueRecord> stream = TextRecordStreams.of(
                new KeyValueRecord("key1", "value1"),
                new KeyValueRecord("key2", "value2"),
                new KeyValueRecord("key3", null),
                new KeyValueRecord("key4", "value4")
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
