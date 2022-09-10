package stexfires.examples.record;

import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.AddTextMapper;
import stexfires.record.mapper.CategoryMapper;
import stexfires.record.mapper.ConditionalMapper;
import stexfires.record.mapper.ConstantMapper;
import stexfires.record.mapper.FunctionMapper;
import stexfires.record.mapper.IdentityMapper;
import stexfires.record.mapper.LookupMapper;
import stexfires.record.mapper.RecordIdMapper;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.SupplierMapper;
import stexfires.record.mapper.TextsMapper;
import stexfires.record.mapper.field.AddPrefixFieldTextMapper;
import stexfires.record.mapper.impl.ToValueFieldRecordMapper;
import stexfires.record.message.ConstantMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.message.SizeMessage;
import stexfires.record.message.TextMessage;
import stexfires.util.StringUnaryOperatorType;
import stexfires.util.Strings;
import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.SequenceLongSupplier;
import stexfires.util.supplier.SequencePrimitiveLongSupplier;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMapper {

    private ExamplesMapper() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ValueFieldRecord("category", 0L, "value1"),
                new ValueFieldRecord("value2"),
                new KeyValueFieldsRecord("category", 1L, "key", "value"),
                new ManyFieldsRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord("category", 2L),
                TextRecords.empty()
        );
    }

    private static Stream<ValueRecord> generateStreamValueRecord() {
        return Stream.of(
                new ValueFieldRecord("category", 0L, "value1"),
                new ValueFieldRecord("value2"),
                new KeyValueFieldsRecord("category", 1L, "key", "value")
        );
    }

    private static void printMapper(String title, RecordMapper<TextRecord, ? extends TextRecord> recordMapper) {
        System.out.println("--" + title);
        TextRecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>());
    }

    private static void printMapperValueRecord(String title, RecordMapper<? super ValueRecord, ? extends TextRecord> recordMapper) {
        System.out.println("--" + title);
        TextRecordStreams.mapAndConsume(generateStreamValueRecord(), recordMapper, new SystemOutConsumer<>());
    }

    private static void showAddValueMapper() {
        System.out.println("-showAddValueMapper---");

        printMapper("constructor", new AddTextMapper<>(record -> "Size: " + record.size()));
        printMapper("supplier", AddTextMapper.supplier(new LocalTimeStringSupplier()));
        printMapper("intSupplier", AddTextMapper.intSupplier(() -> 1));
        printMapper("longSupplier", AddTextMapper.longSupplier(new SequencePrimitiveLongSupplier(0L)));
        printMapper("recordMessage", AddTextMapper.recordMessage(new ShortMessage<>()));
        printMapper("constant", AddTextMapper.constant("constant"));
        printMapper("constantNull", AddTextMapper.constantNull());
        printMapper("category", AddTextMapper.category());
        printMapper("categoryOrElse", AddTextMapper.categoryOrElse("missing category"));
        printMapper("categoryFunction", AddTextMapper.categoryFunction(category -> "new " + category));
        printMapper("categoryOperator", AddTextMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE));
        printMapper("categoryOperator", AddTextMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        printMapper("categoryAsOptionalFunction", AddTextMapper.categoryAsOptionalFunction(optionalCategory -> optionalCategory.orElse("missing category")));
        printMapper("recordId", AddTextMapper.recordId());
        printMapper("textAt", AddTextMapper.textAt(2));
        printMapper("textAtOrElse", AddTextMapper.textAtOrElse(2, "missing value"));
        printMapper("fieldAtOrElse", AddTextMapper.fieldAtOrElse(2, new AddPrefixFieldTextMapper("new: "), "missing value"));
        printMapper("fileName", AddTextMapper.fileName(Paths.get("").toAbsolutePath()));
    }

    private static void showCategoryMapper() {
        System.out.println("-showCategoryMapper---");

        printMapper("constructor", new CategoryMapper<>(record -> "Size: " + record.size()));
        printMapper("identity", CategoryMapper.identity());
        printMapper("supplier", CategoryMapper.supplier(new LocalTimeStringSupplier()));
        printMapper("intSupplier", CategoryMapper.intSupplier(() -> 1));
        printMapper("longSupplier", CategoryMapper.longSupplier(new SequencePrimitiveLongSupplier(0L)));
        printMapper("recordMessage", CategoryMapper.recordMessage(new ShortMessage<>()));
        printMapper("constant", CategoryMapper.constant("constant"));
        printMapper("constantNull", CategoryMapper.constantNull());
        printMapper("category", CategoryMapper.category());
        printMapper("categoryOrElse", CategoryMapper.categoryOrElse("missing category"));
        printMapper("categoryFunction", CategoryMapper.categoryFunction(category -> "new " + category));
        printMapper("categoryOperator", CategoryMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE));
        printMapper("categoryOperator", CategoryMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        printMapper("categoryAsOptionalFunction", CategoryMapper.categoryAsOptionalFunction(optionalCategory -> optionalCategory.orElse("missing category")));
        printMapper("recordId", CategoryMapper.recordId());
        printMapper("textAt", CategoryMapper.textAt(2));
        printMapper("textAtOrElse", CategoryMapper.textAtOrElse(2, "missing value"));
        printMapper("fieldAtOrElse", CategoryMapper.fieldAtOrElse(2, new AddPrefixFieldTextMapper("new: "), "missing value"));
        printMapper("fileName", CategoryMapper.fileName(Paths.get("").toAbsolutePath()));
    }

    private static void showConditionalMapper() {
        System.out.println("-showConditionalMapper---");

        printMapper("constructor", new ConditionalMapper<>(
                CategoryFilter.isNotNull(),
                CategoryMapper.category(),
                CategoryMapper.constant("new category")
        ));
    }

    private static void showConstantMapper() {
        System.out.println("-showConstantMapper---");

        printMapper("constructor", new ConstantMapper<>(new TwoFieldsRecord("A", "B")));
    }

    private static void showFunctionMapper() {
        System.out.println("-showFunctionMapper---");

        printMapper("constructor", new FunctionMapper<>(
                record -> record.categoryAsOptional().orElse("new category"),
                record -> record.recordIdAsOptional().orElse(-1L),
                record -> Strings.list(record.textAtOrElse(0, ""))
        ));
        printMapper("functionMappers", FunctionMapper.functionMappers(
                CategoryMapper.recordId(),
                RecordIdMapper.constant(100L),
                TextsMapper.reverseTexts())
        );
        printMapper("functionMappers identity", FunctionMapper.functionMappers(
                CategoryMapper.identity(),
                RecordIdMapper.identity(),
                TextsMapper.identity())
        );
    }

    private static void showIdentityMapper() {
        System.out.println("-showIdentityMapper---");

        printMapper("constructor", new IdentityMapper<>());
    }

    @SuppressWarnings({"VariableNotUsedInsideIf", "ReturnOfNull"})
    private static void showLookupMapper() {
        System.out.println("-showLookupMapper---");

        printMapper("constructor", new LookupMapper<>(TextRecord::recordId,
                recordId -> recordId == null ? null : AddTextMapper.recordId(),
                new IdentityMapper<>()));

        Map<String, RecordMapper<? super TextRecord, TextRecord>> recordMapperMap = new HashMap<>();
        recordMapperMap.put("value1", AddTextMapper.constant("lookup value1"));
        recordMapperMap.put("value2", AddTextMapper.constant("lookup value2"));
        recordMapperMap.put("key", AddTextMapper.constant("lookup key"));
        printMapper("messageMap", LookupMapper.messageMap(new TextMessage<>(0), recordMapperMap));
    }

    @SuppressWarnings("VariableNotUsedInsideIf")
    private static void showRecordIdMapper() {
        System.out.println("-showRecordIdMapper---");

        printMapper("constructor", new RecordIdMapper<>(record -> record.recordIdAsOptional().orElse(-1L) + 100L));
        printMapper("identity", RecordIdMapper.identity());
        printMapper("supplier", RecordIdMapper.supplier(new SequenceLongSupplier(1000L)));
        printMapper("intSupplier", RecordIdMapper.intSupplier(() -> 1));
        printMapper("longSupplier", RecordIdMapper.longSupplier(new SequencePrimitiveLongSupplier(1000L)));
        printMapper("constant", RecordIdMapper.constant(100L));
        printMapper("constantNull", RecordIdMapper.constantNull());
        printMapper("categoryFunction", RecordIdMapper.categoryFunction(cat -> cat == null ? 0L : 1L));
        printMapper("categoryAsOptionalFunction", RecordIdMapper.categoryAsOptionalFunction(cat -> cat.isPresent() ? 0L : 1L));
        printMapper("recordId", RecordIdMapper.recordId());
        printMapper("textAt", RecordIdMapper.textAt(2, value -> value == null ? 0L : 1L));
    }

    private static void showRecordMapper() {
        System.out.println("-showRecordMapper---");

        printMapper("concat 2", RecordMapper.concat(
                CategoryMapper.constantNull(),
                new ToValueFieldRecordMapper<>(1)));
        printMapperValueRecord("concat 3", RecordMapper.concat(
                CategoryMapper.categoryOrElse("missing category"),
                RecordIdMapper.longSupplier(new SequencePrimitiveLongSupplier(1000L)),
                AddTextMapper.constant("new value")));
        printMapper("compose",
                CategoryMapper.constantNull().compose(AddTextMapper.constant("new value")));
        printMapper("andThen",
                new ToValueFieldRecordMapper<>(0).andThen(AddTextMapper.constant("new value")));
    }

    private static void showSupplierMapper() {
        System.out.println("-showSupplierMapper---");

        printMapper("constructor", new SupplierMapper<>(() -> new ValueFieldRecord("value")));
    }

    private static void showValuesMapper() {
        System.out.println("-showValuesMapper---");

        printMapper("constructor", new TextsMapper<>(record -> Strings.list("new value 0", "new value 1", "new value 2")));
        printMapper("identity", TextsMapper.identity());
        printMapper("recordFieldFunction", TextsMapper.recordFieldFunction((record, field) -> String.valueOf(10L * record.recordIdAsOptional().orElse(0L) + field.index())));
        printMapper("mapAllFields", TextsMapper.mapAllFields(new AddPrefixFieldTextMapper("new: ")));
        printMapperValueRecord("mapOneField", TextsMapper.mapOneField(ValueRecord::valueField, new AddPrefixFieldTextMapper("new: ")));
        printMapper("size 0", TextsMapper.size(0, "<NULL>"));
        printMapper("size 1", TextsMapper.size(1, "<NULL>"));
        printMapper("size 2", TextsMapper.size(2, "<NULL>"));
        printMapper("reverseTexts", TextsMapper.reverseTexts());
        printMapper("createMessages array", TextsMapper.createMessages(
                new ConstantMessage<>("new"),
                new SizeMessage<>()
        ));
        printMapper("createMessages list", TextsMapper.createMessages(
                Collections.singletonList(new SizeMessage<>())
        ));
        printMapper("applyFunctions array", TextsMapper.applyFunctions(
                TextRecord::category, TextRecord::lastText
        ));
        printMapper("applyFunctions list", TextsMapper.applyFunctions(
                Collections.singletonList(TextRecord::toString)
        ));
        printMapper("add", TextsMapper.add(record -> "new value"));
        printMapper("remove 0", TextsMapper.remove(0));
        printMapper("remove 2", TextsMapper.remove(2));
        printMapper("remove isNullOrEmpty", TextsMapper.remove(TextField::isNullOrEmpty));
    }

    public static void main(String... args) {
        showAddValueMapper();
        showCategoryMapper();
        showConditionalMapper();
        showConstantMapper();
        showFunctionMapper();
        showIdentityMapper();
        showLookupMapper();
        showRecordIdMapper();
        showRecordMapper();
        showSupplierMapper();
        showValuesMapper();
    }

}
