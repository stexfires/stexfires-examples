package stexfires.examples.util;

import stexfires.util.CommonCharsetNames;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesCommonCharsetNames {

    private ExamplesCommonCharsetNames() {
    }

    private static void showCommonCharsetNames() {
        System.out.println("-showCommonCharsetNames---");

        for (var charset : CommonCharsetNames.values()) {
            System.out.println(charset.name() + " (canonicalName())    ? " + charset.canonicalName());
            System.out.println(charset.name() + " (charset())          ? " + charset.charset());
        }
    }

    public static void main(String... args) {
        showCommonCharsetNames();
    }

}
