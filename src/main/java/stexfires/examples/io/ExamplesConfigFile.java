package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.config.ConfigFile;
import stexfires.io.config.ConfigFileSpec;
import stexfires.io.config.ConfigModifier;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.OneFieldRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.producer.ProducerException;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.ISO_8859_1;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesConfigFile {

    private ExamplesConfigFile() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new KeyValueRecord("", 1L, "property1", "value1"),
                new KeyValueRecord("    ", 2L, "property2", "value2"),
                new KeyValueRecord(null, 3L, "property3", "value3"),
                new KeyValueRecord(LineSeparator.LF.string(), 4L, "property4", "value4"),
                new KeyValueRecord("\t", 5L, "property5", "value5"),
                new KeyValueRecord(LineSeparator.CR_LF.string(), 6L, "property6", "value6"),

                new EmptyRecord(),

                new KeyValueRecord("Section700", 700L, "property100", "value700"),
                new KeyValueRecord("Section700", 701L, "property200", "value701"),
                new KeyValueRecord("Section700", 702L, "property300", "value702"),
                new KeyValueRecord("Section700", 703L, "property400", "value703"),
                new KeyValueRecord("Section700", 704L, "property500", "value704"),
                new KeyValueRecord("Section700", 705L, "property600", "value705"),

                new KeyValueRecord(" Section600 ", 600L, "property600", "value600"),
                new KeyValueRecord("   Section600   ", 601L, "property601", "value601"),
                new KeyValueRecord(" Section 600 ", 600L, "property600", "value600"),
                new KeyValueRecord("   Section 600   ", 601L, "property601", "value601"),

                new KeyValueRecord("Section500", 500L, "property500", "value500"),
                new KeyValueRecord("section500", 501L, "property501", "value501"),
                new KeyValueRecord("Section500", 502L, "property502", "value502"),
                new KeyValueRecord("Section500", 503L, "property500", "value503"),

                new StandardRecord("Section200", 200L),
                new StandardRecord("Section200", 201L, "A", "B", "C"),
                new StandardRecord("Section200", 202L, "A", "D"),
                new StandardRecord("Section200", 203L, "A", "B", "D"),
                new StandardRecord("Section200", 204L, "a", "b", "c", "d"),
                new StandardRecord("Section200", 205L, "A"),

                new OneFieldRecord("Section300", 300L, "property300"),

                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 401L, "property400", "value401"),
                new TwoFieldsRecord("Section400", 401L, "property401", "value401"),

                new KeyValueRecord("Section100", 100L, "", null),
                new KeyValueRecord("Section100", 101L, "", ""),
                new KeyValueRecord("Section100", 102L, "", " "),
                new KeyValueRecord("Section100", 103L, "  ", null),
                new KeyValueRecord("Section100", 104L, "  ", ""),
                new KeyValueRecord("Section100", 105L, "  ", " ")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        ConfigFile file = new ConfigFileSpec(ISO_8859_1.charset(), CodingErrorAction.REPORT,
                ConfigFileSpec.DEFAULT_VALUE_DELIMITER,
                lineSeparator).file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream(), new ConfigModifier<>(0, 1, true, Locale.ENGLISH), file);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(file, new SystemOutLogger<>());
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
            test1(Path.of(args[0], "ConfigFile_1.ini"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
