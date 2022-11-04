package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.path.DosPathRecord;
import stexfires.io.path.PathRecord;
import stexfires.io.path.PathRecords;
import stexfires.io.path.PathType;
import stexfires.io.path.PathTypeFilter;
import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.ConsumerException;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesPathRecord {

    private ExamplesPathRecord() {
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws IOException {
        System.out.println("-test1---");

        System.out.println("---newDosPathRecord---");
        DosPathRecord pathRecord = PathRecords.newDosPathRecord(path);
        System.out.println(pathRecord);
        System.out.println(pathRecord.pathType());
        System.out.println(pathRecord.fileName());
        System.out.println(pathRecord.fileNameAsOptional());
        System.out.println(pathRecord.path());
        System.out.println(pathRecord.parent());
        System.out.println(pathRecord.parentAsOptional());
        System.out.println(pathRecord.pathNameCount());
        System.out.println(pathRecord.fileSize());
        System.out.println(pathRecord.creationTime());
        System.out.println(pathRecord.lastModifiedTime());
        System.out.println(pathRecord.lastAccessTime());
        System.out.println(pathRecord.isArchive());
        System.out.println(pathRecord.isReadOnly());
        System.out.println(pathRecord.isHidden());
        System.out.println(pathRecord.isSystem());
        System.out.println(pathRecord.fileExtension());
        System.out.println(pathRecord.fileExtensionAsOptional());

        System.out.println("---listDosPathRecords printLines---");
        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecords(path)) {
            TextRecordStreams.printLines(pathStream);
        }

        System.out.println("---listDosPathRecords collect---");
        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecords(path)) {
            System.out.println(TextRecordStreams.collect(pathStream));
        }

        System.out.println("---listDosPathRecords joinMessages fileName---");
        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecords(path)) {
            System.out.println(TextRecordStreams.joinMessages(pathStream, PathRecord::fileName, ", "));
        }

        System.out.println("---walkPathRecords printLines filter---");
        try (Stream<PathRecord> pathStream = PathRecords.walkPathRecords(path)) {
            TextRecordStreams.printLines(pathStream.filter(new PathTypeFilter<>(PathType.DIRECTORY).negate()::isValid));
        }

        System.out.println("---parents---");
        DosPathRecord parentDosPathRecord = pathRecord;
        while (parentDosPathRecord != null) {
            System.out.println(parentDosPathRecord);
            parentDosPathRecord = parentDosPathRecord.parentAsOptional()
                                                     .map(PathRecords::newDosPathRecord)
                                                     .orElse(null);
        }
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        System.out.println("---listDosPathRecords writeFile---");
        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());

        var file = SimpleDelimitedFileSpec
                .write(
                        StandardCharsets.ISO_8859_1,
                        CodingErrorAction.REPORT,
                        null,
                        "\t",
                        fieldSpecs,
                        lineSeparator)
                .file(path.resolve("PathRecord_1.csv"));

        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecords(path)) {
            RecordFiles.writeFile(pathStream, file);
        }
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
            test1(Path.of(args[0]), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0]), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
