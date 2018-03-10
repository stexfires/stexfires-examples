package stexfires.examples.core;

import stexfires.core.Field;
import stexfires.core.mapper.fieldvalue.AddPostfixFieldValueMapper;
import stexfires.core.mapper.fieldvalue.AddPrefixFieldValueMapper;
import stexfires.core.mapper.fieldvalue.ConditionalFieldValueMapper;
import stexfires.core.mapper.fieldvalue.ConstantFieldValueMapper;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.mapper.fieldvalue.IdentityFieldValueMapper;
import stexfires.core.mapper.fieldvalue.IndexedFieldValueMapper;
import stexfires.core.mapper.fieldvalue.ReplaceNullFieldValueMapper;
import stexfires.core.mapper.fieldvalue.StringOperationFieldValueMapper;
import stexfires.core.mapper.fieldvalue.SupplierFieldValueMapper;
import stexfires.util.StringUnaryOperatorType;
import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.SequenceStringSupplier;
import stexfires.util.supplier.ThreadNameStringSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")
public final class ExamplesFieldValueMapper {

    private ExamplesFieldValueMapper() {
    }

    private static void showMapper(String title, FieldValueMapper fieldValueMapper) {
        System.out.println("--" + title);

        Stream<Field> fieldStream = Stream.of(
                new Field(0, false, "value1"),
                new Field(1, false, null),
                new Field(2, false, ""),
                new Field(3, true, "value2")
        );

        System.out.println(fieldStream.map(fieldValueMapper::mapToValue).collect(Collectors.toList()));
    }

    private static void showAddPostfixFieldValueMapper() {
        System.out.println("-showAddPostfixFieldValueMapper---");

        showMapper("constructor", new AddPostfixFieldValueMapper("-postfix"));
    }

    private static void showAddPrefixFieldValueMapper() {
        System.out.println("-showAddPrefixFieldValueMapper---");

        showMapper("constructor", new AddPrefixFieldValueMapper("prefix-"));
    }

    private static void showConditionalFieldValueMapper() {
        System.out.println("-showConditionalFieldValueMapper---");

        showMapper("constructor valueIsNull", new ConditionalFieldValueMapper(
                Field::valueIsNull,
                new ConstantFieldValueMapper("<field value is null>"),
                new IdentityFieldValueMapper()));
    }

    private static void showConstantFieldValueMapper() {
        System.out.println("-showConstantFieldValueMapper---");

        showMapper("constructor", new ConstantFieldValueMapper());
        showMapper("constructor constant", new ConstantFieldValueMapper("constant"));
    }

    private static void showFieldValueMapper() {
        System.out.println("-showFieldValueMapper---");

        showMapper("append", new AddPrefixFieldValueMapper("<").append(">"));
        showMapper("prepend", new ReplaceNullFieldValueMapper("NULL").prepend("<").append(">"));
    }

    private static void showIdentityFieldValueMapper() {
        System.out.println("-showIdentityFieldValueMapper---");

        showMapper("constructor", new IdentityFieldValueMapper());
    }

    private static void showIndexedFieldValueMapper() {
        System.out.println("-showIndexedFieldValueMapper---");

        showMapper("constructor", new IndexedFieldValueMapper(
                i -> i == 1 ? Optional.of(new ConstantFieldValueMapper("index 1")) : Optional.empty(),
                new ConstantFieldValueMapper("else")
        ));

        showMapper("byArray", IndexedFieldValueMapper.byArray(
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

        showMapper("byList", IndexedFieldValueMapper.byList(fieldValueMappersList));

        Map<Integer, FieldValueMapper> fieldValueMappersMap = new TreeMap<>();
        fieldValueMappersMap.put(0, new AddPrefixFieldValueMapper("Index-0: "));
        fieldValueMappersMap.put(1, new AddPrefixFieldValueMapper("Index-1: "));
        fieldValueMappersMap.put(2, new AddPrefixFieldValueMapper("Index-2: "));
        fieldValueMappersMap.put(3, new AddPrefixFieldValueMapper("Index-3: "));

        showMapper("byMap", IndexedFieldValueMapper.byMap(fieldValueMappersMap));
    }

    private static void showReplaceNullFieldValueMapper() {
        System.out.println("-showReplaceNullFieldValueMapper---");

        showMapper("constructor NOT_NULL", new ReplaceNullFieldValueMapper("NOT_NULL"));
    }

    private static void showStringOperationFieldValueMapper() {
        System.out.println("-showStringOperationFieldValueMapper---");

        showMapper("constructor TRIM_TO_EMPTY", new StringOperationFieldValueMapper(StringUnaryOperatorType.TRIM_TO_EMPTY));
        showMapper("constructor REVERSE", new StringOperationFieldValueMapper(StringUnaryOperatorType.REVERSE));
        showMapper("constructor UPPER_CASE", new StringOperationFieldValueMapper(StringUnaryOperatorType.UPPER_CASE));
        showMapper("constructor UPPER_CASE ENGLISH", new StringOperationFieldValueMapper(StringUnaryOperatorType.UPPER_CASE, Locale.ENGLISH));
        showMapper("constructor identity", new StringOperationFieldValueMapper(st -> st));
    }

    private static void showSupplierFieldValueMapper() {
        System.out.println("-showSupplierFieldValueMapper---");

        showMapper("constructor LocalTimeStringSupplier", new SupplierFieldValueMapper(new LocalTimeStringSupplier()));
        showMapper("constructor SequenceStringSupplier", new SupplierFieldValueMapper(new SequenceStringSupplier(1000L)));
        showMapper("constructor ThreadNameStringSupplier", new SupplierFieldValueMapper(new ThreadNameStringSupplier()));
    }

    public static void main(String[] args) {
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
