package stexfires.examples.record;

import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.mapper.fieldvalue.AddPostfixFieldValueMapper;
import stexfires.record.mapper.fieldvalue.AddPrefixFieldValueMapper;
import stexfires.record.mapper.fieldvalue.ConditionalFieldValueMapper;
import stexfires.record.mapper.fieldvalue.ConstantFieldValueMapper;
import stexfires.record.mapper.fieldvalue.FieldValueMapper;
import stexfires.record.mapper.fieldvalue.IdentityFieldValueMapper;
import stexfires.record.mapper.fieldvalue.IndexedFieldValueMapper;
import stexfires.record.mapper.fieldvalue.ReplaceNullFieldValueMapper;
import stexfires.record.mapper.fieldvalue.StringOperationFieldValueMapper;
import stexfires.record.mapper.fieldvalue.SupplierFieldValueMapper;
import stexfires.util.StringUnaryOperatorType;
import stexfires.util.Strings;
import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.SequenceStringSupplier;
import stexfires.util.supplier.ThreadNameStringSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesFieldValueMapper {

    private ExamplesFieldValueMapper() {
    }

    private static Stream<Field> generateStream() {
        return Stream.of(
                new Field(0, 3, "value1"),
                new Field(1, 3, null),
                new Field(2, 3, ""),
                new Field(3, 3, "value2")
        );
    }

    private static void printMapper(String title, FieldValueMapper fieldValueMapper) {
        System.out.println("--" + title);
        Strings.printLine(Fields.mapToValue(generateStream(), fieldValueMapper), Strings.DEFAULT_DELIMITER);
    }

    private static void showAddPostfixFieldValueMapper() {
        System.out.println("-showAddPostfixFieldValueMapper---");

        printMapper("constructor", new AddPostfixFieldValueMapper("-postfix"));
    }

    private static void showAddPrefixFieldValueMapper() {
        System.out.println("-showAddPrefixFieldValueMapper---");

        printMapper("constructor", new AddPrefixFieldValueMapper("prefix-"));
    }

    private static void showConditionalFieldValueMapper() {
        System.out.println("-showConditionalFieldValueMapper---");

        printMapper("constructor valueIsNull", new ConditionalFieldValueMapper(
                Field::valueIsNull,
                new ConstantFieldValueMapper("<field value is null>"),
                new IdentityFieldValueMapper()));
    }

    private static void showConstantFieldValueMapper() {
        System.out.println("-showConstantFieldValueMapper---");

        printMapper("constructor", new ConstantFieldValueMapper());
        printMapper("constructor constant", new ConstantFieldValueMapper("constant"));
    }

    private static void showFieldValueMapper() {
        System.out.println("-showFieldValueMapper---");

        printMapper("append", new AddPrefixFieldValueMapper("<").append(">"));
        printMapper("prepend", new ReplaceNullFieldValueMapper("NULL").prepend("<").append(">"));
    }

    private static void showIdentityFieldValueMapper() {
        System.out.println("-showIdentityFieldValueMapper---");

        printMapper("constructor", new IdentityFieldValueMapper());
    }

    private static void showIndexedFieldValueMapper() {
        System.out.println("-showIndexedFieldValueMapper---");

        printMapper("constructor", new IndexedFieldValueMapper(
                i -> i == 1 ? Optional.of(new ConstantFieldValueMapper("index 1")) : Optional.empty(),
                new ConstantFieldValueMapper("else")
        ));

        printMapper("byArray", IndexedFieldValueMapper.byArray(
                new AddPrefixFieldValueMapper("Index-0: "),
                new AddPrefixFieldValueMapper("Index-1: "),
                new AddPrefixFieldValueMapper("Index-2: "),
                new AddPrefixFieldValueMapper("Index-3: ")
        ));

        List<FieldValueMapper> fieldValueMappersList = new ArrayList<>();
        fieldValueMappersList.add(new AddPrefixFieldValueMapper("Index-0: "));
        fieldValueMappersList.add(new AddPrefixFieldValueMapper("Index-1: "));
        fieldValueMappersList.add(new AddPrefixFieldValueMapper("Index-2: "));
        fieldValueMappersList.add(new AddPrefixFieldValueMapper("Index-3: "));

        printMapper("byList", IndexedFieldValueMapper.byList(fieldValueMappersList));

        Map<Integer, FieldValueMapper> fieldValueMappersMap = new TreeMap<>();
        fieldValueMappersMap.put(0, new AddPrefixFieldValueMapper("Index-0: "));
        fieldValueMappersMap.put(1, new AddPrefixFieldValueMapper("Index-1: "));
        fieldValueMappersMap.put(2, new AddPrefixFieldValueMapper("Index-2: "));
        fieldValueMappersMap.put(3, new AddPrefixFieldValueMapper("Index-3: "));

        printMapper("byMap", IndexedFieldValueMapper.byMap(fieldValueMappersMap));
    }

    private static void showReplaceNullFieldValueMapper() {
        System.out.println("-showReplaceNullFieldValueMapper---");

        printMapper("constructor NOT_NULL", new ReplaceNullFieldValueMapper("NOT_NULL"));
    }

    private static void showStringOperationFieldValueMapper() {
        System.out.println("-showStringOperationFieldValueMapper---");

        printMapper("constructor TRIM_TO_EMPTY", new StringOperationFieldValueMapper(StringUnaryOperatorType.TRIM_TO_EMPTY));
        printMapper("constructor REVERSE", new StringOperationFieldValueMapper(StringUnaryOperatorType.REVERSE));
        printMapper("constructor UPPER_CASE", new StringOperationFieldValueMapper(StringUnaryOperatorType.UPPER_CASE));
        printMapper("constructor UPPER_CASE ENGLISH", new StringOperationFieldValueMapper(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        printMapper("constructor identity", new StringOperationFieldValueMapper(st -> st));
    }

    private static void showSupplierFieldValueMapper() {
        System.out.println("-showSupplierFieldValueMapper---");

        printMapper("constructor LocalTimeStringSupplier", new SupplierFieldValueMapper(new LocalTimeStringSupplier()));
        printMapper("constructor SequenceStringSupplier", new SupplierFieldValueMapper(new SequenceStringSupplier(1000L)));
        printMapper("constructor ThreadNameStringSupplier", new SupplierFieldValueMapper(new ThreadNameStringSupplier()));
    }

    public static void main(String... args) {
        showAddPostfixFieldValueMapper();
        showAddPrefixFieldValueMapper();
        showConditionalFieldValueMapper();
        showConstantFieldValueMapper();
        showFieldValueMapper();
        showIdentityFieldValueMapper();
        showIndexedFieldValueMapper();
        showReplaceNullFieldValueMapper();
        showStringOperationFieldValueMapper();
        showSupplierFieldValueMapper();
    }

}
