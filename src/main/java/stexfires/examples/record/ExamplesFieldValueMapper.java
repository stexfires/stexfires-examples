package stexfires.examples.record;

import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.mapper.field.AddPostfixFieldTextMapper;
import stexfires.record.mapper.field.AddPrefixFieldTextMapper;
import stexfires.record.mapper.field.ConditionalFieldTextMapper;
import stexfires.record.mapper.field.ConstantFieldTextMapper;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.mapper.field.IdentityFieldTextMapper;
import stexfires.record.mapper.field.IndexedFieldTextMapper;
import stexfires.record.mapper.field.ReplaceNullFieldTextMapper;
import stexfires.record.mapper.field.StringOperationFieldTextMapper;
import stexfires.record.mapper.field.SupplierFieldTextMapper;
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

    private static void printMapper(String title, FieldTextMapper fieldTextMapper) {
        System.out.println("--" + title);
        Strings.printLine(Fields.mapToTexts(generateStream(), fieldTextMapper), Strings.DEFAULT_DELIMITER);
    }

    private static void showAddPostfixFieldValueMapper() {
        System.out.println("-showAddPostfixFieldValueMapper---");

        printMapper("constructor", new AddPostfixFieldTextMapper("-postfix"));
    }

    private static void showAddPrefixFieldValueMapper() {
        System.out.println("-showAddPrefixFieldValueMapper---");

        printMapper("constructor", new AddPrefixFieldTextMapper("prefix-"));
    }

    private static void showConditionalFieldValueMapper() {
        System.out.println("-showConditionalFieldValueMapper---");

        printMapper("constructor valueIsNull", new ConditionalFieldTextMapper(
                Field::isNull,
                new ConstantFieldTextMapper("<field value is null>"),
                new IdentityFieldTextMapper()));
    }

    private static void showConstantFieldValueMapper() {
        System.out.println("-showConstantFieldValueMapper---");

        printMapper("constructor", new ConstantFieldTextMapper());
        printMapper("constructor constant", new ConstantFieldTextMapper("constant"));
    }

    private static void showFieldValueMapper() {
        System.out.println("-showFieldValueMapper---");

        printMapper("append", new AddPrefixFieldTextMapper("<").append(">"));
        printMapper("prepend", new ReplaceNullFieldTextMapper("NULL").prepend("<").append(">"));
    }

    private static void showIdentityFieldValueMapper() {
        System.out.println("-showIdentityFieldValueMapper---");

        printMapper("constructor", new IdentityFieldTextMapper());
    }

    private static void showIndexedFieldValueMapper() {
        System.out.println("-showIndexedFieldValueMapper---");

        printMapper("constructor", new IndexedFieldTextMapper(
                i -> i == 1 ? Optional.of(new ConstantFieldTextMapper("index 1")) : Optional.empty(),
                new ConstantFieldTextMapper("else")
        ));

        printMapper("byArray", IndexedFieldTextMapper.byArray(
                new AddPrefixFieldTextMapper("Index-0: "),
                new AddPrefixFieldTextMapper("Index-1: "),
                new AddPrefixFieldTextMapper("Index-2: "),
                new AddPrefixFieldTextMapper("Index-3: ")
        ));

        List<FieldTextMapper> fieldTextMappersList = new ArrayList<>();
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-0: "));
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-1: "));
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-2: "));
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-3: "));

        printMapper("byList", IndexedFieldTextMapper.byList(fieldTextMappersList));

        Map<Integer, FieldTextMapper> fieldValueMappersMap = new TreeMap<>();
        fieldValueMappersMap.put(0, new AddPrefixFieldTextMapper("Index-0: "));
        fieldValueMappersMap.put(1, new AddPrefixFieldTextMapper("Index-1: "));
        fieldValueMappersMap.put(2, new AddPrefixFieldTextMapper("Index-2: "));
        fieldValueMappersMap.put(3, new AddPrefixFieldTextMapper("Index-3: "));

        printMapper("byMap", IndexedFieldTextMapper.byMap(fieldValueMappersMap));
    }

    private static void showReplaceNullFieldValueMapper() {
        System.out.println("-showReplaceNullFieldValueMapper---");

        printMapper("constructor NOT_NULL", new ReplaceNullFieldTextMapper("NOT_NULL"));
    }

    private static void showStringOperationFieldValueMapper() {
        System.out.println("-showStringOperationFieldValueMapper---");

        printMapper("constructor TRIM_TO_EMPTY", new StringOperationFieldTextMapper(StringUnaryOperatorType.TRIM_TO_EMPTY));
        printMapper("constructor REVERSE", new StringOperationFieldTextMapper(StringUnaryOperatorType.REVERSE));
        printMapper("constructor UPPER_CASE", new StringOperationFieldTextMapper(StringUnaryOperatorType.UPPER_CASE));
        printMapper("constructor UPPER_CASE ENGLISH", new StringOperationFieldTextMapper(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        printMapper("constructor identity", new StringOperationFieldTextMapper(st -> st));
    }

    private static void showSupplierFieldValueMapper() {
        System.out.println("-showSupplierFieldValueMapper---");

        printMapper("constructor LocalTimeStringSupplier", new SupplierFieldTextMapper(new LocalTimeStringSupplier()));
        printMapper("constructor SequenceStringSupplier", new SupplierFieldTextMapper(new SequenceStringSupplier(1000L)));
        printMapper("constructor ThreadNameStringSupplier", new SupplierFieldTextMapper(new ThreadNameStringSupplier()));
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
