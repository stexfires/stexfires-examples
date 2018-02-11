package stexfires.examples.util;

import stexfires.util.NumberCheckType;
import stexfires.util.StringCheckType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class ExamplesCheckTypes {

    private ExamplesCheckTypes() {
    }

    private static void showNumberCheckType() {
        System.out.println("-showNumberCheckType---");

        for (NumberCheckType type : NumberCheckType.values()) {
            for (int i = -2; i < 5; i++) {
                System.out.println(type.name() + " (" + i + ") ? " + type.check(i));
            }

            System.out.println(type.name() + " (Integer.MIN_VALUE) ? " + type.check(Integer.MIN_VALUE));
            System.out.println(type.name() + " (Integer.MAX_VALUE) ? " + type.check(Integer.MAX_VALUE));

            System.out.println(type.name() + " (Long.MIN_VALUE)    ? " + type.check(Long.MIN_VALUE));
            System.out.println(type.name() + " (Long.MAX_VALUE)    ? " + type.check(Long.MAX_VALUE));

            System.out.println(type.name() + " (BigInteger.ZERO)   ? " + type.check(BigInteger.ZERO));
            System.out.println(type.name() + " (BigInteger.ONE)    ? " + type.check(BigInteger.ONE));
            System.out.println(type.name() + " (BigInteger.TEN)    ? " + type.check(BigInteger.TEN));
        }
    }

    private static void showStringCheckType() {
        System.out.println("-showStringCheckType---");

        List<String> values = new ArrayList<>();
        values.add("a");
        values.add("aa");
        values.add("d");
        values.add("A");
        values.add("A1");
        values.add(" a ");
        values.add(" A ");
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

        for (StringCheckType type : StringCheckType.values()) {
            for (String value : values) {
                System.out.println(type.name() + " ('" + value + "') ? '" + type.check(value) + "'");
            }
        }
    }

    public static void main(String[] args) {
        showNumberCheckType();
        showStringCheckType();
    }

}
