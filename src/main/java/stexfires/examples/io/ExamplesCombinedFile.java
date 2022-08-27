package stexfires.examples.io;

import stexfires.core.consumer.ConsumerException;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.producer.ProducerException;
import stexfires.core.impl.KeyValueRecord;
import stexfires.core.ValueRecord;
import stexfires.io.RecordFiles;
import stexfires.io.combined.CombinedReadableRecordFile;
import stexfires.io.combined.CombinedWritableRecordFile;
import stexfires.io.config.ConfigFile;
import stexfires.io.config.ConfigFileSpec;
import stexfires.io.config.ConfigModifier;
import stexfires.io.singlevalue.SingleValueFile;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.UTF_8;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesCombinedFile {

    private ExamplesCombinedFile() {
    }

    private static Stream<KeyValueRecord> generateStream() {
        return Stream.of(
                new KeyValueRecord("SectionA", 1L, "key1", "value1a"),
                new KeyValueRecord("SectionB", 2L, "key2", "value2"),
                new KeyValueRecord("SectionA", 3L, "key3", "value3"),
                new KeyValueRecord(null, 4L, "", null),
                new KeyValueRecord("SectionA", 5L, "key1", "value1b"),
                new KeyValueRecord(null, 6L, "", ""),
                new KeyValueRecord("SectionC", 7L, "key7", "value7")
        );
    }

    private static void test1(Path pathConfig, Path pathSingle, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        ConfigFile configFile = new ConfigFileSpec(UTF_8.charset(), CodingErrorAction.REPORT,
                ConfigFileSpec.DEFAULT_VALUE_DELIMITER,
                lineSeparator).file(pathConfig);

        SingleValueFile singleValueFile = new SingleValueFileSpec(
                UTF_8.charset(), CodingErrorAction.REPORT,
                true, 0, 0,
                lineSeparator, true).file(pathSingle);

        // Write
        System.out.println("write: " + pathConfig + " " + pathSingle);
        CombinedWritableRecordFile<KeyValueRecord> combinedFileWrite = new CombinedWritableRecordFile<>(configFile, singleValueFile);
        RecordFiles.writeFile(generateStream(), new ConfigModifier<>(0, 1, true, Locale.ENGLISH), combinedFileWrite);

        // Read / log
        System.out.println("read/log: " + pathConfig + " " + pathSingle);
        CombinedReadableRecordFile<ValueRecord> combinedFileRead = new CombinedReadableRecordFile<>(configFile, singleValueFile);
        RecordFiles.logFile(combinedFileRead, new SystemOutLogger<>());
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
            test1(Path.of(args[0], "Combined_ConfigFile_1.ini"),
                    Path.of(args[0], "Combined_SingleValueFile_1.txt"),
                    LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
