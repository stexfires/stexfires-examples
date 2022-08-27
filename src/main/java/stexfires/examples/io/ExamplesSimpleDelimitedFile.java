package stexfires.examples.io;

import stexfires.core.TextRecord;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.producer.ProducerException;
import stexfires.core.impl.EmptyRecord;
import stexfires.core.impl.SingleRecord;
import stexfires.core.impl.StandardRecord;
import stexfires.io.RecordFiles;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFile;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.singlevalue.SingleValueFile;
import stexfires.io.singlevalue.SingleValueFileSpec;
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
                new StandardRecord(null, 0L, "a", "0", "AAAA", "ä"),
                new StandardRecord(null, 1L, "b", "1", "BB", "€"),
                new StandardRecord(null, 2L, "c", "2", "C", ""),
                new StandardRecord(null, 3L, "d", "3.33", "dDDDDd", null),
                new StandardRecord(null, 4L, "e", "004", "eEEEEEEEEEEEEEEEEEe", "    "),
                new StandardRecord(null, 5L, "f", null, "    ", "fff"),
                new StandardRecord(null, 6L, null, null, null, null),
                new StandardRecord(null, 7L, "", "", "", ""),
                new StandardRecord(null, 8L, "  ", "  ", "  ", "  "),
                new StandardRecord(null, 9L, "j", "9", "k", "l", "m", "n", "o"),
                new EmptyRecord(),
                new StandardRecord(null, 11L),
                new StandardRecord(null, 12L, "---")
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
        Stream<SingleRecord> singleRecordStream = Stream.of(
                new SingleRecord("Header"),
                new SingleRecord("------"),
                new SingleRecord("A1"),
                new SingleRecord(""),
                new SingleRecord(" "),
                new SingleRecord("B1,B2"),
                new SingleRecord(","),
                new SingleRecord(" , "),
                new SingleRecord("C1,C2,C3,"),
                new SingleRecord(",,,"),
                new SingleRecord(" , , ,"),
                new SingleRecord("Footer")
        );
        RecordFiles.writeFile(singleRecordStream, singleValueFileWrite);

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
