package stexfires.examples.io;

import stexfires.io.RecordFileSpec;
import stexfires.io.RecordFiles;
import stexfires.io.combined.CombinedReadableRecordFile;
import stexfires.io.combined.CombinedWritableRecordFile;
import stexfires.io.config.ConfigFileSpec;
import stexfires.io.config.ConfigModifier;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.logger.RecordLogger;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.message.JoinedTextsMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesCombinedFile {

    private ExamplesCombinedFile() {
    }

    private static RecordLogger<TextRecord> formattedSystemOutLogger() {
        return new SystemOutLogger<>(new ShortMessage<>().append(" [", new JoinedTextsMessage<>(", ")).append("]"));
    }

    private static Stream<KeyValueRecord> generateStream() {
        return Stream.of(
                new KeyValueFieldsRecord("SectionA", 1L, "key1", "value1a"),
                new KeyValueFieldsRecord("SectionB", 2L, "key2", "value2"),
                new KeyValueFieldsRecord("SectionA", 3L, "key3", "value3"),
                new KeyValueFieldsRecord(null, 4L, "", null),
                new KeyValueFieldsRecord("SectionA", 5L, "key1", "value1b"),
                new KeyValueFieldsRecord(null, 6L, "", ""),
                new KeyValueFieldsRecord("SectionC", 7L, "key7", "value7")
        );
    }

    private static void test1(Path pathConfig, Path pathSingle, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var configFile =
                new ConfigFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        ConfigFileSpec.DEFAULT_VALUE_DELIMITER)
                        .file(pathConfig);

        var singleValueFile =
                new SingleValueFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        RecordFileSpec.DEFAULT_TEXT_BEFORE,
                        RecordFileSpec.DEFAULT_TEXT_AFTER,
                        true,
                        0,
                        0,
                        true)
                        .file(pathSingle);

        // Write
        System.out.println("write: " + pathConfig + " " + pathSingle);
        CombinedWritableRecordFile<KeyValueRecord> combinedFileWrite = new CombinedWritableRecordFile<>(configFile, singleValueFile);
        RecordFiles.writeFile(generateStream(), new ConfigModifier<>(Locale.ENGLISH, 0, 1, true), combinedFileWrite);

        // Read / log
        System.out.println("read/log: " + pathConfig + " " + pathSingle);
        CombinedReadableRecordFile<ValueRecord> combinedFileRead = new CombinedReadableRecordFile<>(configFile, singleValueFile);
        RecordFiles.logFile(combinedFileRead, formattedSystemOutLogger());
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
