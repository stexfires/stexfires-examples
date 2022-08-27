package stexfires.examples.io;

import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.mapper.AddValueMapper;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.io.RecordFiles;
import stexfires.io.html.table.HtmlTableFieldSpec;
import stexfires.io.html.table.HtmlTableFile;
import stexfires.io.html.table.HtmlTableFileSpec;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesHtmlTableFile {

    private ExamplesHtmlTableFile() {
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test1---");

        List<HtmlTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new HtmlTableFieldSpec("property & property"));
        fieldSpecs.add(new HtmlTableFieldSpec());
        fieldSpecs.add(new HtmlTableFieldSpec("<section>"));
        HtmlTableFile file = HtmlTableFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        fieldSpecs,
                        lineSeparator,
                        "<!DOCTYPE html>" + lineSeparator
                                + "<html>" + lineSeparator
                                + "<head>" + lineSeparator
                                + "  <meta charset=\"UTF-8\"/>" + lineSeparator
                                + "  <style>table, th, td { border: 1px solid black; }</style>" + lineSeparator
                                + "</head>" + lineSeparator
                                + "<body>" + lineSeparator
                                + "  <h1>Header</h1>",
                        "</body>" + lineSeparator + "</html>",
                        "  "
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<KeyValueRecord> stream = TextRecordStreams.of(
                new KeyValueRecord("", null, "<br>", "</td>"),
                new KeyValueRecord("", null, "\"", "\"aa\""),
                new KeyValueRecord("", null, "&& &", "&auml;"),
                new KeyValueRecord("", 2L, "property2", "value2"),
                new KeyValueRecord("Section1", 6L, "property6", "value6"),
                new KeyValueRecord("    ", 11L, "property11", "value11"),
                new KeyValueRecord("Section2", 8L, "property8", "value8"),
                new KeyValueRecord("section1", 4L, "property4", "value4"),
                new KeyValueRecord(null, 1L, "property1", "value1"),
                new KeyValueRecord("Missing value", 20L, "", null),
                new KeyValueRecord("Missing value", 21L, "", ""),
                new KeyValueRecord("Missing value", 22L, " ", " "),
                new KeyValueRecord("Section1", 5L, "property5", "value5"),
                new KeyValueRecord("", 3L, "property3", "value3"),
                new KeyValueRecord("\t", 12L, "property12", "value12"),
                new KeyValueRecord(" Section3 ", 14L, "property14", "value14"),
                new KeyValueRecord("   Section3   ", 15L, "property15", "value15"),
                new KeyValueRecord("Section1", 6L, "property6", "value6b"),
                new KeyValueRecord("Section2", 7L, "property7", "value7"),
                new KeyValueRecord("Section2", 10L, "property6", "value6/section2"),
                new KeyValueRecord(lineSeparator.string(), 13L, "property13", "value13")
        );
        RecordFiles.writeFile(stream, AddValueMapper.category(), file);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        List<HtmlTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new HtmlTableFieldSpec("A"));
        fieldSpecs.add(new HtmlTableFieldSpec("B"));
        fieldSpecs.add(new HtmlTableFieldSpec("C"));
        HtmlTableFile file = HtmlTableFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        fieldSpecs,
                        lineSeparator,
                        "<!DOCTYPE html>" + lineSeparator
                                + "<html>" + lineSeparator
                                + "<head>" + lineSeparator
                                + "  <meta charset=\"UTF-8\"/>" + lineSeparator
                                + "  <style>table, th, td { border: 1px solid black; }</style>" + lineSeparator
                                + "</head>" + lineSeparator
                                + "<body>" + lineSeparator
                                + "  <h1>Header</h1>",
                        "</body>" + lineSeparator + "</html>",
                        "  "
                )
                .file(path);

        // Write
        System.out.println("write: " + path);
        Stream<StandardRecord> stream = TextRecordStreams.of(
                new StandardRecord(Stream.of("A0", "B0", "C0")),
                new StandardRecord(Stream.of("A1", "B1")),
                new StandardRecord(Stream.of("A2", null, "C2")),
                new StandardRecord(Stream.of(null, "B3")),
                new StandardRecord(Stream.of("A4", "B4", "C4", "D4", "E4")),
                new StandardRecord()
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
            test1(Path.of(args[0], "HtmlTableFile_1.html"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "HtmlTableFile_2.html"), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
