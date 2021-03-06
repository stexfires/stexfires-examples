package stexfires.examples.io;

import stexfires.core.TextRecord;
import stexfires.core.TextRecordStreams;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.record.StandardRecord;
import stexfires.io.RecordFiles;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFile;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMarkdownTableFile {

    private ExamplesMarkdownTableFile() {
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test1---");

        List<MarkdownTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new MarkdownTableFieldSpec("A", 5));
        fieldSpecs.add(new MarkdownTableFieldSpec("B|B", 6));
        fieldSpecs.add(new MarkdownTableFieldSpec("C", 10, Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec("S5", 5, Alignment.START));
        fieldSpecs.add(new MarkdownTableFieldSpec("C5", 5, Alignment.CENTER));
        fieldSpecs.add(new MarkdownTableFieldSpec("E5", 5, Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec("S6", 6, Alignment.START));
        fieldSpecs.add(new MarkdownTableFieldSpec("C6", 6, Alignment.CENTER));
        fieldSpecs.add(new MarkdownTableFieldSpec("E6", 6, Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec(null, 7));
        fieldSpecs.add(new MarkdownTableFieldSpec("", 7));
        fieldSpecs.add(new MarkdownTableFieldSpec(" ", 7));
        MarkdownTableFile file = MarkdownTableFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        fieldSpecs,
                        lineSeparator,
                        MarkdownTableFileSpec.DEFAULT_ALIGNMENT,
                        "Header",
                        "Footer"
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<TextRecord> stream = TextRecordStreams.of(
                new StandardRecord("a", "1", "aa"),
                new StandardRecord("b", "22", "bbbbbb"),
                new StandardRecord("c", "333", "cccccccccc"),
                new StandardRecord("d", "4444", "ddddddddddddddddddddd"),
                new StandardRecord(" ", " ", " "),
                new StandardRecord("", "", ""),
                new StandardRecord(null, 1L, null, null, null),
                new StandardRecord(" "),
                new StandardRecord(""),
                new StandardRecord(null, 1L, null, null),
                new StandardRecord(),
                new StandardRecord("b", "|", "a|b|c")
        );
        RecordFiles.writeFile(stream, file);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        List<MarkdownTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new MarkdownTableFieldSpec("A", 5));
        fieldSpecs.add(new MarkdownTableFieldSpec("B|B", 6));
        fieldSpecs.add(new MarkdownTableFieldSpec("C", 10, Alignment.CENTER));
        MarkdownTableFile file = MarkdownTableFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        fieldSpecs,
                        lineSeparator)
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<TextRecord> stream = TextRecordStreams.of(
                new StandardRecord("a", "1", "aa"),
                new StandardRecord("b", "22", "bbbbbb"),
                new StandardRecord("c", "333", "cccccccccc"),
                new StandardRecord("d", "4444", "ddddddddddddddddddddd"),
                new StandardRecord(" ", " ", " "),
                new StandardRecord("", "", ""),
                new StandardRecord(null, 1L, null, null, null),
                new StandardRecord(" "),
                new StandardRecord(""),
                new StandardRecord(null, 1L, null, null),
                new StandardRecord(),
                new StandardRecord("b", "|", "a|b|c")
        );
        RecordFiles.writeFile(stream, file);

        List<MarkdownTableFieldSpec> fieldSpecs2 = new ArrayList<>();
        fieldSpecs2.add(new MarkdownTableFieldSpec("Column", 10));
        MarkdownTableFile file2 = MarkdownTableFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        fieldSpecs2,
                        lineSeparator,
                        Alignment.START,
                        lineSeparator.string(2) + "Header second table",
                        "Footer second table")
                .file(path);

        // Write
        System.out.println("write APPEND: " + path);
        Stream<TextRecord> stream2 = TextRecordStreams.of(
                new StandardRecord("a"),
                new StandardRecord("b")
        );
        RecordFiles.writeFile(stream2, file2, StandardOpenOption.APPEND);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            test1(Path.of(args[0], "MarkdownTableFile_1.md"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "MarkdownTableFile_2.md"), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
