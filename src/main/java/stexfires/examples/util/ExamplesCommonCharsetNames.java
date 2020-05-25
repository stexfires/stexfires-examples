package stexfires.examples.util;

import stexfires.util.CommonCharsetNames;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesCommonCharsetNames {

    private ExamplesCommonCharsetNames() {
    }

    private static void showCommonCharsetNames() {
        System.out.println("-showCommonCharsetNames---");

        for (CommonCharsetNames type : CommonCharsetNames.values()) {
            System.out.println(type.name() + " (getCanonicalName()) ? " + type.getCanonicalName());
            System.out.println(type.name() + " (charset())          ? " + type.charset());
            System.out.println(type.name() + " (name())             ? " + type.name());
            System.out.println(type.name() + " (toString())         ? " + type.toString());
        }
    }

    public static void main(String[] args) {
        showCommonCharsetNames();
    }

}
