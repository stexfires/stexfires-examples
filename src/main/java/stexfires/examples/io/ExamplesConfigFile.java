package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.config.ConfigFileSpec;
import stexfires.io.config.ConfigModifier;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
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
                new KeyValueFieldsRecord("", 1L, "property1", "value1"),
                new KeyValueFieldsRecord("    ", 2L, "property2", "value2"),
                new KeyValueFieldsRecord(null, 3L, "property3", "value3"),
                new KeyValueFieldsRecord(LineSeparator.LF.string(), 4L, "property4", "value4"),
                new KeyValueFieldsRecord("\t", 5L, "property5", "value5"),
                new KeyValueFieldsRecord(LineSeparator.CR_LF.string(), 6L, "property6", "value6"),

                TextRecords.empty(),

                new KeyValueFieldsRecord("Section700", 700L, "property100", "value700"),
                new KeyValueFieldsRecord("Section700", 701L, "property200", "value701"),
                new KeyValueFieldsRecord("Section700", 702L, "property300", "value702"),
                new KeyValueFieldsRecord("Section700", 703L, "property400", "value703"),
                new KeyValueFieldsRecord("Section700", 704L, "property500", "value704"),
                new KeyValueFieldsRecord("Section700", 705L, "property600", "value705"),

                new KeyValueFieldsRecord(" Section600 ", 600L, "property600", "value600"),
                new KeyValueFieldsRecord("   Section600   ", 601L, "property601", "value601"),
                new KeyValueFieldsRecord(" Section 600 ", 600L, "property600", "value600"),
                new KeyValueFieldsRecord("   Section 600   ", 601L, "property601", "value601"),

                new KeyValueFieldsRecord("Section500", 500L, "property500", "value500"),
                new KeyValueFieldsRecord("section500", 501L, "property501", "value501"),
                new KeyValueFieldsRecord("Section500", 502L, "property502", "value502"),
                new KeyValueFieldsRecord("Section500", 503L, "property500", "value503"),

                new ManyFieldsRecord("Section200", 200L),
                new ManyFieldsRecord("Section200", 201L, "A", "B", "C"),
                new ManyFieldsRecord("Section200", 202L, "A", "D"),
                new ManyFieldsRecord("Section200", 203L, "A", "B", "D"),
                new ManyFieldsRecord("Section200", 204L, "a", "b", "c", "d"),
                new ManyFieldsRecord("Section200", 205L, "A"),

                new ValueFieldRecord("Section300", 300L, "property300"),

                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 401L, "property400", "value401"),
                new TwoFieldsRecord("Section400", 401L, "property401", "value401"),

                new KeyValueFieldsRecord("Section100", 100L, "", null),
                new KeyValueFieldsRecord("Section100", 101L, "", ""),
                new KeyValueFieldsRecord("Section100", 102L, "", " "),
                new KeyValueFieldsRecord("Section100", 103L, "  ", null),
                new KeyValueFieldsRecord("Section100", 104L, "  ", ""),
                new KeyValueFieldsRecord("Section100", 105L, "  ", " ")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var file = new ConfigFileSpec(ISO_8859_1.charset(), CodingErrorAction.REPORT, null, null,
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
