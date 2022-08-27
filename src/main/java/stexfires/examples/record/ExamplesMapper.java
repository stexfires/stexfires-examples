package stexfires.examples.record;

import stexfires.record.Field;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.impl.TwoValuesRecord;
import stexfires.record.mapper.AddValueMapper;
import stexfires.record.mapper.CategoryMapper;
import stexfires.record.mapper.ConditionalMapper;
import stexfires.record.mapper.ConstantMapper;
import stexfires.record.mapper.FunctionMapper;
import stexfires.record.mapper.IdentityMapper;
import stexfires.record.mapper.LookupMapper;
import stexfires.record.mapper.RecordIdMapper;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.SupplierMapper;
import stexfires.record.mapper.ToOneValueRecordMapper;
import stexfires.record.mapper.ValuesMapper;
import stexfires.record.mapper.fieldvalue.AddPrefixFieldValueMapper;
import stexfires.record.message.ConstantMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.message.SizeMessage;
import stexfires.record.message.ValueMessage;
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
                new OneValueRecord("category", 0L, "value1"),
                new OneValueRecord("value2"),
                new KeyValueRecord("category", 1L, "key", "value"),
                new ManyValuesRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyValuesRecord("category", 2L),
                new EmptyRecord()
        );
    }

    private static Stream<ValueRecord> generateStreamValueRecord() {
        return Stream.of(
                new OneValueRecord("category", 0L, "value1"),
                new OneValueRecord("value2"),
                new KeyValueRecord("category", 1L, "key", "value")
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

        printMapper("constructor", new AddValueMapper<>(record -> "Size: " + record.size()));
        printMapper("supplier", AddValueMapper.supplier(new LocalTimeStringSupplier()));
        printMapper("intSupplier", AddValueMapper.intSupplier(() -> 1));
        printMapper("longSupplier", AddValueMapper.longSupplier(new SequencePrimitiveLongSupplier(0L)));
        printMapper("recordMessage", AddValueMapper.recordMessage(new ShortMessage<>()));
        printMapper("constant", AddValueMapper.constant("constant"));
        printMapper("constantNull", AddValueMapper.constantNull());
        printMapper("category", AddValueMapper.category());
        printMapper("categoryOrElse", AddValueMapper.categoryOrElse("missing category"));
        printMapper("categoryFunction", AddValueMapper.categoryFunction(category -> "new " + category));
        printMapper("categoryOperator", AddValueMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE));
        printMapper("categoryOperator", AddValueMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        printMapper("categoryAsOptionalFunction", AddValueMapper.categoryAsOptionalFunction(optionalCategory -> optionalCategory.orElse("missing category")));
        printMapper("recordId", AddValueMapper.recordId());
        printMapper("valueAt", AddValueMapper.valueAt(2));
        printMapper("valueAtOrElse", AddValueMapper.valueAtOrElse(2, "missing value"));
        printMapper("fieldAtOrElse", AddValueMapper.fieldAtOrElse(2, new AddPrefixFieldValueMapper("new: "), "missing value"));
        printMapper("fileName", AddValueMapper.fileName(Paths.get("").toAbsolutePath()));
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
        printMapper("valueAt", CategoryMapper.valueAt(2));
        printMapper("valueAtOrElse", CategoryMapper.valueAtOrElse(2, "missing value"));
        printMapper("fieldAtOrElse", CategoryMapper.fieldAtOrElse(2, new AddPrefixFieldValueMapper("new: "), "missing value"));
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

        printMapper("constructor", new ConstantMapper<>(new TwoValuesRecord("A", "B")));
    }

    private static void showFunctionMapper() {
        System.out.println("-showFunctionMapper---");

        printMapper("constructor", new FunctionMapper<>(
                record -> record.categoryOrElse("new category"),
                record -> record.recordIdAsOptional().orElse(-1L),
                record -> Strings.list(record.valueAtOrElse(0, ""))
        ));
        printMapper("functionMappers", FunctionMapper.functionMappers(
                CategoryMapper.recordId(),
                RecordIdMapper.constant(100L),
                ValuesMapper.reverseValues())
        );
        printMapper("functionMappers identity", FunctionMapper.functionMappers(
                CategoryMapper.identity(),
                RecordIdMapper.identity(),
                ValuesMapper.identity())
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
                recordId -> recordId == null ? null : AddValueMapper.recordId(),
                new IdentityMapper<>()));

        Map<String, RecordMapper<? super TextRecord, TextRecord>> recordMapperMap = new HashMap<>();
        recordMapperMap.put("value1", AddValueMapper.constant("lookup value1"));
        recordMapperMap.put("value2", AddValueMapper.constant("lookup value2"));
        recordMapperMap.put("key", AddValueMapper.constant("lookup key"));
        printMapper("messageMap", LookupMapper.messageMap(new ValueMessage<>(0), recordMapperMap));
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
        printMapper("valueAt", RecordIdMapper.valueAt(2, value -> value == null ? 0L : 1L));
    }

    private static void showRecordMapper() {
        System.out.println("-showRecordMapper---");

        printMapper("concat 2", RecordMapper.concat(
                CategoryMapper.constantNull(),
                new ToOneValueRecordMapper<>(1)));
        printMapperValueRecord("concat 3", RecordMapper.concat(
                CategoryMapper.categoryOrElse("missing category"),
                RecordIdMapper.longSupplier(new SequencePrimitiveLongSupplier(1000L)),
                AddValueMapper.constant("new value")));
        printMapper("compose",
                CategoryMapper.constantNull().compose(AddValueMapper.constant("new value")));
        printMapper("andThen",
                new ToOneValueRecordMapper<>(0).andThen(AddValueMapper.constant("new value")));
    }

    private static void showSupplierMapper() {
        System.out.println("-showSupplierMapper---");

        printMapper("constructor", new SupplierMapper<>(() -> new OneValueRecord("value")));
    }

    private static void showValuesMapper() {
        System.out.println("-showValuesMapper---");

        printMapper("constructor", new ValuesMapper<>(record -> Strings.list("new value 0", "new value 1", "new value 2")));
        printMapper("identity", ValuesMapper.identity());
        printMapper("recordFieldFunction", ValuesMapper.recordFieldFunction((record, field) -> String.valueOf(10L * record.recordIdAsOptional().orElse(0L) + field.index())));
        printMapper("mapAllFields", ValuesMapper.mapAllFields(new AddPrefixFieldValueMapper("new: ")));
        printMapperValueRecord("mapOneField", ValuesMapper.mapOneField(ValueRecord::valueField, new AddPrefixFieldValueMapper("new: ")));
        printMapper("size 0", ValuesMapper.size(0, "<NULL>"));
        printMapper("size 1", ValuesMapper.size(1, "<NULL>"));
        printMapper("size 2", ValuesMapper.size(2, "<NULL>"));
        printMapper("reverseValues", ValuesMapper.reverseValues());
        printMapper("createMessages array", ValuesMapper.createMessages(
                new ConstantMessage<>("new"),
                new SizeMessage<>()
        ));
        printMapper("createMessages list", ValuesMapper.createMessages(
                Collections.singletonList(new SizeMessage<>())
        ));
        printMapper("applyFunctions array", ValuesMapper.applyFunctions(
                TextRecord::category, TextRecord::valueOfLastField
        ));
        printMapper("applyFunctions list", ValuesMapper.applyFunctions(
                Collections.singletonList(TextRecord::toString)
        ));
        printMapper("add", ValuesMapper.add(record -> "new value"));
        printMapper("remove 0", ValuesMapper.remove(0));
        printMapper("remove 2", ValuesMapper.remove(2));
        printMapper("remove valueIsNullOrEmpty", ValuesMapper.remove(Field::valueIsNullOrEmpty));
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
