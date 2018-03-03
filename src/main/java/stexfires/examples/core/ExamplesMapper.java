package stexfires.examples.core;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.filter.CategoryFilter;
import stexfires.core.mapper.AddValueMapper;
import stexfires.core.mapper.CategoryMapper;
import stexfires.core.mapper.ConditionalMapper;
import stexfires.core.mapper.ConstantMapper;
import stexfires.core.mapper.FunctionMapper;
import stexfires.core.mapper.IdentityMapper;
import stexfires.core.mapper.LookupMapper;
import stexfires.core.mapper.RecordIdMapper;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.mapper.SupplierMapper;
import stexfires.core.mapper.ValuesMapper;
import stexfires.core.mapper.fieldvalue.AddPrefixFieldValueMapper;
import stexfires.core.mapper.to.ToSingleMapper;
import stexfires.core.message.ConstantMessage;
import stexfires.core.message.ShortMessage;
import stexfires.core.message.SizeMessage;
import stexfires.core.message.ValueMessage;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;
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

@SuppressWarnings({"MagicNumber", "ImplicitNumericConversion"})
public final class ExamplesMapper {

    private ExamplesMapper() {
    }

    private static Stream<Record> generateStream() {
        return Stream.of(
                new SingleRecord("category", 0L, "value1"),
                new SingleRecord("value2"),
                new KeyValueRecord("category", 1L, "key", "value"),
                new StandardRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord("category", 2L),
                new EmptyRecord()
        );
    }

    private static Stream<ValueRecord> generateStreamValueRecord() {
        return Stream.of(
                new SingleRecord("category", 0L, "value1"),
                new SingleRecord("value2"),
                new KeyValueRecord("category", 1L, "key", "value")
        );
    }

    private static void showMapper(String title, RecordMapper<Record, Record> recordMapper) {
        System.out.println("--" + title);
        RecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>());
    }

    private static void showMapperValueRecord(String title, RecordMapper<ValueRecord, Record> recordMapper) {
        System.out.println("--" + title);
        RecordStreams.mapAndConsume(generateStreamValueRecord(), recordMapper, new SystemOutConsumer<>());
    }

    private static void showAddValueMapper() {
        System.out.println("-showAddValueMapper---");

        showMapper("constructor", new AddValueMapper<>(record -> "Size: " + record.size()));
        showMapper("supplier", AddValueMapper.supplier(new LocalTimeStringSupplier()));
        showMapper("intSupplier", AddValueMapper.intSupplier(() -> 1));
        showMapper("longSupplier", AddValueMapper.longSupplier(new SequencePrimitiveLongSupplier(0L)));
        showMapper("recordMessage", AddValueMapper.recordMessage(new ShortMessage<>()));
        showMapper("constant", AddValueMapper.constant("constant"));
        showMapper("constantNull", AddValueMapper.constantNull());
        showMapper("category", AddValueMapper.category());
        showMapper("categoryOrElse", AddValueMapper.categoryOrElse("missing category"));
        showMapper("categoryFunction", AddValueMapper.categoryFunction(category -> "new " + category));
        showMapper("categoryOperator", AddValueMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE));
        showMapper("categoryOperator", AddValueMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        showMapper("categoryAsOptionalFunction", AddValueMapper.categoryAsOptionalFunction(optionalCategory -> optionalCategory.orElse("missing category")));
        showMapper("recordId", AddValueMapper.recordId());
        showMapper("valueAt", AddValueMapper.valueAt(2));
        showMapper("valueAtOrElse", AddValueMapper.valueAtOrElse(2, "missing value"));
        showMapper("fieldAtOrElse", AddValueMapper.fieldAtOrElse(2, new AddPrefixFieldValueMapper("new: "), "missing value"));
        showMapper("fileName", AddValueMapper.fileName(Paths.get("").toAbsolutePath()));
    }

    private static void showCategoryMapper() {
        System.out.println("-showCategoryMapper---");

        showMapper("constructor", new CategoryMapper<>(record -> "Size: " + record.size()));
        showMapper("identity", CategoryMapper.identity());
        showMapper("supplier", CategoryMapper.supplier(new LocalTimeStringSupplier()));
        showMapper("intSupplier", CategoryMapper.intSupplier(() -> 1));
        showMapper("longSupplier", CategoryMapper.longSupplier(new SequencePrimitiveLongSupplier(0L)));
        showMapper("recordMessage", CategoryMapper.recordMessage(new ShortMessage<>()));
        showMapper("constant", CategoryMapper.constant("constant"));
        showMapper("constantNull", CategoryMapper.constantNull());
        showMapper("category", CategoryMapper.category());
        showMapper("categoryOrElse", CategoryMapper.categoryOrElse("missing category"));
        showMapper("categoryFunction", CategoryMapper.categoryFunction(category -> "new " + category));
        showMapper("categoryOperator", CategoryMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE));
        showMapper("categoryOperator", CategoryMapper.categoryOperator(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        showMapper("categoryAsOptionalFunction", CategoryMapper.categoryAsOptionalFunction(optionalCategory -> optionalCategory.orElse("missing category")));
        showMapper("recordId", CategoryMapper.recordId());
        showMapper("valueAt", CategoryMapper.valueAt(2));
        showMapper("valueAtOrElse", CategoryMapper.valueAtOrElse(2, "missing value"));
        showMapper("fieldAtOrElse", CategoryMapper.fieldAtOrElse(2, new AddPrefixFieldValueMapper("new: "), "missing value"));
        showMapper("fileName", CategoryMapper.fileName(Paths.get("").toAbsolutePath()));
    }

    private static void showConditionalMapper() {
        System.out.println("-showConditionalMapper---");

        showMapper("constructor", new ConditionalMapper<>(
                CategoryFilter.isNotNull(),
                CategoryMapper.category(),
                CategoryMapper.constant("new category")
        ));
    }

    private static void showConstantMapper() {
        System.out.println("-showConstantMapper---");

        showMapper("constructor", new ConstantMapper<>(new PairRecord("A", "B")));
    }

    private static void showFunctionMapper() {
        System.out.println("-showFunctionMapper---");

        showMapper("constructor", new FunctionMapper<>(
                record -> record.getCategoryOrElse("new category"),
                record -> record.getRecordIdAsOptional().orElse(-1L),
                record -> Strings.list(record.getValueAtOrElse(0, ""))
        ));
        showMapper("functionMappers", FunctionMapper.functionMappers(
                CategoryMapper.recordId(),
                RecordIdMapper.constant(100L),
                ValuesMapper.reverseValues())
        );
        showMapper("functionMappers identity", FunctionMapper.functionMappers(
                CategoryMapper.identity(),
                RecordIdMapper.identity(),
                ValuesMapper.identity())
        );
    }

    private static void showIdentityMapper() {
        System.out.println("-showIdentityMapper---");

        showMapper("constructor", new IdentityMapper<>());
    }

    @SuppressWarnings("VariableNotUsedInsideIf")
    private static void showLookupMapper() {
        System.out.println("-showLookupMapper---");

        showMapper("constructor", new LookupMapper<>(Record::getRecordId,
                recordId -> recordId == null ? null : AddValueMapper.recordId(),
                new IdentityMapper<>()));

        Map<String, RecordMapper<? super Record, Record>> recordMapperMap = new HashMap<>();
        recordMapperMap.put("value1", AddValueMapper.constant("lookup value1"));
        recordMapperMap.put("value2", AddValueMapper.constant("lookup value2"));
        recordMapperMap.put("key", AddValueMapper.constant("lookup key"));
        showMapper("messageMap", LookupMapper.messageMap(new ValueMessage<>(0), recordMapperMap));
    }

    @SuppressWarnings({"VariableNotUsedInsideIf", "OptionalIsPresent"})
    private static void showRecordIdMapper() {
        System.out.println("-showRecordIdMapper---");

        showMapper("constructor", new RecordIdMapper<>(record -> record.getRecordIdAsOptional().orElse(-1L) + 100L));
        showMapper("identity", RecordIdMapper.identity());
        showMapper("supplier", RecordIdMapper.supplier(new SequenceLongSupplier(1000L)));
        showMapper("intSupplier", RecordIdMapper.intSupplier(() -> 1));
        showMapper("longSupplier", RecordIdMapper.longSupplier(new SequencePrimitiveLongSupplier(1000L)));
        showMapper("constant", RecordIdMapper.constant(100L));
        showMapper("constantNull", RecordIdMapper.constantNull());
        showMapper("categoryFunction", RecordIdMapper.categoryFunction(cat -> cat == null ? 0L : 1L));
        showMapper("categoryAsOptionalFunction", RecordIdMapper.categoryAsOptionalFunction(cat -> cat.isPresent() ? 0L : 1L));
        showMapper("recordId", RecordIdMapper.recordId());
        showMapper("valueAt", RecordIdMapper.valueAt(2, value -> value == null ? 0L : 1L));
    }

    private static void showRecordMapper() {
        System.out.println("-showRecordMapper---");

        showMapper("concat 2", RecordMapper.concat(
                CategoryMapper.constantNull(),
                new ToSingleMapper<>(1)));
        showMapperValueRecord("concat 3", RecordMapper.concat(
                CategoryMapper.categoryOrElse("missing category"),
                RecordIdMapper.longSupplier(new SequencePrimitiveLongSupplier(1000L)),
                AddValueMapper.constant("new value")));
        showMapper("compose",
                CategoryMapper.constantNull().compose(AddValueMapper.constant("new value")));
        showMapper("andThen",
                new ToSingleMapper<>(0).andThen(AddValueMapper.constant("new value")));
    }

    private static void showSupplierMapper() {
        System.out.println("-showSupplierMapper---");

        showMapper("constructor", new SupplierMapper<>(() -> new SingleRecord("value")));
    }

    private static void showValuesMapper() {
        System.out.println("-showValuesMapper---");

        showMapper("constructor", new ValuesMapper<>(record -> Strings.list("new value 0", "new value 1", "new value 2")));
        showMapper("identity", ValuesMapper.identity());
        showMapper("recordFieldFunction", ValuesMapper.recordFieldFunction((record, field) -> String.valueOf(10L * record.getRecordIdAsOptional().orElse(0L) + field.getIndex())));
        showMapper("mapAllFields", ValuesMapper.mapAllFields(new AddPrefixFieldValueMapper("new: ")));
        showMapperValueRecord("mapOneField", ValuesMapper.mapOneField(ValueRecord::getValueField, new AddPrefixFieldValueMapper("new: ")));
        showMapper("size 0", ValuesMapper.size(0, "<NULL>"));
        showMapper("size 1", ValuesMapper.size(1, "<NULL>"));
        showMapper("size 2", ValuesMapper.size(2, "<NULL>"));
        showMapper("reverseValues", ValuesMapper.reverseValues());
        showMapper("createMessages array", ValuesMapper.createMessages(
                new ConstantMessage<>("new"),
                new SizeMessage<>()
        ));
        showMapper("createMessages list", ValuesMapper.createMessages(
                Collections.singletonList(new SizeMessage<>())
        ));
        showMapper("applyFunctions array", ValuesMapper.applyFunctions(
                Record::getCategory, Record::getValueOfLastField
        ));
        showMapper("applyFunctions list", ValuesMapper.applyFunctions(
                Collections.singletonList(Record::toString)
        ));
        showMapper("add", ValuesMapper.add(record -> "new value"));
        showMapper("remove 0", ValuesMapper.remove(0));
        showMapper("remove 2", ValuesMapper.remove(2));
        showMapper("remove valueIsNullOrEmpty", ValuesMapper.remove(Field::valueIsNullOrEmpty));
    }

    public static void main(String[] args) {
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
