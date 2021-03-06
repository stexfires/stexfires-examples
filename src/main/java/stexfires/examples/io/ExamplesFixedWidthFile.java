package stexfires.examples.io;

import stexfires.core.TextRecord;
import stexfires.core.TextRecordStreams;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.producer.ProducerException;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.io.RecordFiles;
import stexfires.io.fixedwidth.FixedWidthFieldSpec;
import stexfires.io.fixedwidth.FixedWidthFile;
import stexfires.io.fixedwidth.FixedWidthFileSpec;
import stexfires.util.Alignment;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesFixedWidthFile {

    private ExamplesFixedWidthFile() {
    }

    private static Stream<TextRecord> generateStream1() {
        return Stream.of(
                new StandardRecord(null, 0L, "a", "1", "AAAA", "ä", "zu viele Werte"),
                new StandardRecord(null, 1L, "b", "2", "BB", "€µ"),
                new StandardRecord(null, 2L, "c", "3", "C", ""),
                new StandardRecord(null, 3L, "d", "4", "dDDDDd", null),
                new StandardRecord(null, 4L, "e", "05", "eEEEEEEEEEEEEEEEEEe"),
                new StandardRecord(null, 5L),
                new StandardRecord(null, 6L, null, null, null, null),
                new StandardRecord(null, 7L, "", "", "", "")
        );
    }

    private static Stream<SingleRecord> generateStream2() {
        return Stream.of(
                new SingleRecord("abcdefghijklmnopqrstuvwxyz"),
                new SingleRecord(""),
                new SingleRecord("_"),
                new SingleRecord("_...------_____##___")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        List<FixedWidthFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new FixedWidthFieldSpec(0, 1));
        fieldSpecs.add(new FixedWidthFieldSpec(1, 3, Alignment.END, '.'));
        fieldSpecs.add(new FixedWidthFieldSpec(4, 6, Alignment.CENTER, '-'));
        fieldSpecs.add(new FixedWidthFieldSpec(15, 2, null, '#'));
        FixedWidthFile fixedWidthFile = new FixedWidthFileSpec(StandardCharsets.UTF_8, CodingErrorAction.REPORT,
                20, true,
                Alignment.START, '_',
                fieldSpecs,
                0, 0, true,
                false, lineSeparator).file(path);

        List<FixedWidthFieldSpec> fieldSpecsAppend1 = new ArrayList<>();
        fieldSpecsAppend1.add(new FixedWidthFieldSpec(0, 26));
        FixedWidthFile fixedWidthFileAppend1 = new FixedWidthFileSpec(StandardCharsets.UTF_8, CodingErrorAction.REPORT,
                30, true,
                Alignment.START, ' ',
                fieldSpecsAppend1,
                0, 0, true,
                false, lineSeparator).file(path);

        List<FixedWidthFieldSpec> fieldSpecsAppend2 = new ArrayList<>();
        fieldSpecsAppend2.add(new FixedWidthFieldSpec(0, 1));
        FixedWidthFile fixedWidthFileAppend2 = new FixedWidthFileSpec(StandardCharsets.UTF_8, CodingErrorAction.REPORT,
                1, true,
                Alignment.START, '_',
                fieldSpecsAppend2,
                0, 0, true,
                false, lineSeparator).file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream1(), fixedWidthFile);

        // Write
        System.out.println("write APPEND: " + path);
        RecordFiles.writeFile(generateStream2(), fixedWidthFileAppend1, StandardOpenOption.APPEND);

        // Write
        System.out.println("write APPEND: " + path);
        RecordFiles.writeFile(generateStream2(), fixedWidthFileAppend2, StandardOpenOption.APPEND);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(fixedWidthFile, new SystemOutLogger<>());
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        List<FixedWidthFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new FixedWidthFieldSpec(0, 1));
        fieldSpecs.add(new FixedWidthFieldSpec(1, 3, Alignment.END, '.'));
        fieldSpecs.add(new FixedWidthFieldSpec(4, 6, Alignment.CENTER, '-'));
        fieldSpecs.add(new FixedWidthFieldSpec(15, 2, null, '#'));
        FixedWidthFile fixedWidthFile = new FixedWidthFileSpec(StandardCharsets.UTF_8, CodingErrorAction.REPORT,
                20, false,
                Alignment.START, ' ',
                fieldSpecs,
                0, 0, false,
                false, lineSeparator).file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(TextRecordStreams.concat(generateStream1(), generateStream2()), fixedWidthFile);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(fixedWidthFile, new SystemOutLogger<>());
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
            test1(Path.of(args[0], "FixedWidthFile_1.txt"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "FixedWidthFile_2.txt"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | IOException | ConsumerException e) {
            e.printStackTrace();
        }
    }

}
