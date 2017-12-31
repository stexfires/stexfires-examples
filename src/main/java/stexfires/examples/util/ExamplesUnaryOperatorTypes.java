package stexfires.examples.util;

import stexfires.util.NumberUnaryOperatorType;
import stexfires.util.StringUnaryOperatorType;

import java.util.ArrayList;
import java.util.List;

public final class ExamplesUnaryOperatorTypes {

    private static void showNumberUnaryOperatorType() {
        System.out.println("-showNumberUnaryOperatorType---");

        for (NumberUnaryOperatorType type : NumberUnaryOperatorType.values()) {
            for (int i = -2; i < 5; i++) {
                System.out.println(type.name() + " (" + i + ") ? " + type.operate(i));
            }
        }
    }

    private static void showStringUnaryOperatorType() {
        System.out.println("-showStringUnaryOperatorType---");

        List<String> values = new ArrayList<>();
        values.add("a");
        values.add("aa");
        values.add("d");
        values.add("A");
        values.add("A1");
        values.add(" a ");
        values.add("Hello world");
        values.add("1");
        values.add("€");
        values.add("#");
        values.add("");
        values.add("\t");
        values.add("\r\n");
        values.add(" ");
        values.add("  ");
        values.add(null);

        for (StringUnaryOperatorType type : StringUnaryOperatorType.values()) {
            for (String value : values) {
                System.out.println(type.name() + " ('" + value + "') ? '" + type.operate(value) + "'");
            }
        }
    }

    public static void main(String[] args) {
        showNumberUnaryOperatorType();
        showStringUnaryOperatorType();
    }

}
