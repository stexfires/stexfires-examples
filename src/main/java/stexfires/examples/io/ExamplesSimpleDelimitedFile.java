package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFile;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.singlevalue.SingleValueFile;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.producer.ProducerException;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesSimpleDelimitedFile {

    private ExamplesSimpleDelimitedFile() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ManyValuesRecord(null, 0L, "a", "0", "AAAA", "ä"),
                new ManyValuesRecord(null, 1L, "b", "1", "BB", "€"),
                new ManyValuesRecord(null, 2L, "c", "2", "C", ""),
                new ManyValuesRecord(null, 3L, "d", "3.33", "dDDDDd", null),
                new ManyValuesRecord(null, 4L, "e", "004", "eEEEEEEEEEEEEEEEEEe", "    "),
                new ManyValuesRecord(null, 5L, "f", null, "    ", "fff"),
                new ManyValuesRecord(null, 6L, null, null, null, null),
                new ManyValuesRecord(null, 7L, "", "", "", ""),
                new ManyValuesRecord(null, 8L, "  ", "  ", "  ", "  "),
                new ManyValuesRecord(null, 9L, "j", "9", "k", "l", "m", "n", "o"),
                new EmptyRecord(),
                new ManyValuesRecord(null, 11L),
                new ManyValuesRecord(null, 12L, "---")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        SimpleDelimitedFile simpleDelimitedFile = new SimpleDelimitedFileSpec(StandardCharsets.UTF_8, CodingErrorAction.REPORT,
                ",",
                fieldSpecs,
                0, 0,
                false, false,
                lineSeparator).file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream(), simpleDelimitedFile);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(simpleDelimitedFile, new SystemOutLogger<>());
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        SingleValueFile singleValueFileWrite = SingleValueFileSpec
                .write(
                        StandardCharsets.UTF_8,
                        CodingErrorAction.REPORT,
                        lineSeparator,
                        false)
                .file(path);

        // Write
        System.out.println("write (prepare read with SingleValueFile): " + path);
        Stream<OneValueRecord> oneValueRecordStream = Stream.of(
                new OneValueRecord("Header"),
                new OneValueRecord("------"),
                new OneValueRecord("A1"),
                new OneValueRecord(""),
                new OneValueRecord(" "),
                new OneValueRecord("B1,B2"),
                new OneValueRecord(","),
                new OneValueRecord(" , "),
                new OneValueRecord("C1,C2,C3,"),
                new OneValueRecord(",,,"),
                new OneValueRecord(" , , ,"),
                new OneValueRecord("Footer")
        );
        RecordFiles.writeFile(oneValueRecordStream, singleValueFileWrite);

        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        SimpleDelimitedFile simpleDelimitedFile = SimpleDelimitedFileSpec
                .read(StandardCharsets.UTF_8,
                        ",",
                        fieldSpecs,
                        2, 1, true, true)
                .file(path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(simpleDelimitedFile, new SystemOutLogger<>());
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
            test1(Path.of(args[0], "SimpleDelimitedFile_1.csv"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "SimpleDelimitedFile_2.csv"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
