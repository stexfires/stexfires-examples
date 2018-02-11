package stexfires.examples.util;

import stexfires.util.NumberComparisonType;
import stexfires.util.StringComparisonType;

import java.util.ArrayList;
import java.util.List;

public final class ExamplesComparisonTypes {

    private ExamplesComparisonTypes() {
    }

    private static void showNumberComparisonType() {
        System.out.println("-showNumberComparisonType---");

        for (NumberComparisonType type : NumberComparisonType.values()) {
            for (int i = -2; i < 5; i++) {
                for (int j = -2; j < 3; j++) {
                    Boolean result = null;
                    try {
                        result = type.compare(i, j);
                    } catch (Exception e) {
                        System.out.print("Exception: " + e.getMessage() + " : ");
                    }
                    System.out.println(type.name() + " (" + i + ", " + j + ") ? " + result);
                }

                System.out.println(type.name() + " (" + i + ",  Integer.MIN_VALUE) ? " + type.compare(i, Integer.MIN_VALUE));
                System.out.println(type.name() + " (" + i + ",  Integer.MAX_VALUE) ? " + type.compare(i, Integer.MAX_VALUE));
            }
        }
    }

    private static void showStringComparisonType() {
        System.out.println("-showStringComparisonType---");

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

        for (StringComparisonType type : StringComparisonType.values()) {
            for (String value1 : values) {
                for (String value2 : values) {
                    if (value2 == null) {
                        // Compare value must not be 'null'
                        continue;
                    }
                    Boolean result = null;
                    try {
                        result = type.compare(value1, value2);
                    } catch (Exception e) {
                        System.out.print("Exception: " + e.getMessage() + " : ");
                    }
                    System.out.println(type.name() + " ('" + value1 + "', '" + value2 + "') ? '" + result + "'");
                }
            }
        }
    }

    public static void main(String[] args) {
        showNumberComparisonType();
        showStringComparisonType();
    }

}
