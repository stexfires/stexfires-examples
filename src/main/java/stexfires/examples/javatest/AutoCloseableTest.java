package stexfires.examples.javatest;

@SuppressWarnings("UnnecessaryLocalVariable")
public final class AutoCloseableTest {

    private AutoCloseableTest() {
    }

    private static void showTestCase1() {
        System.out.println("-showTestCase1---");
        try {
            try (TestClose t = new TestClose(false)) {
                t.test(false);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }
    }

    private static void showTestCase2() {
        System.out.println("-showTestCase2---");
        try {
            try (TestClose t = new TestClose(true)) {
                t.test(false);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }
    }

    private static void showTestCase3() {
        System.out.println("-showTestCase3---");
        try {
            try (TestClose t = new TestClose(false)) {
                t.test(true);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }
    }

    private static void showTestCase4() {
        System.out.println("-showTestCase4---");
        TestClose tBefore = new TestClose(false);
        try {
            TestClose t = tBefore;
            t.test(true);
        } catch (Exception e) {
            System.out.println("catch " + e);
        }
    }

    private static void showTestCase5() {
        System.out.println("-showTestCase5---");
        TestClose tBefore = new TestClose(false);
        try {
            try (TestClose t = tBefore) {
                t.test(true);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }
    }

    public static void main(String[] args) {
        showTestCase1();
        showTestCase2();
        showTestCase3();
        showTestCase4();
        showTestCase5();
    }

    private static class TestClose implements AutoCloseable {

        TestClose(boolean ex) {
            System.out.println("constructor " + ex);
            if (ex) throw new RuntimeException("constructor");
        }

        @SuppressWarnings("MethodMayBeStatic")
        void test(boolean ex) {
            System.out.println("test " + ex);
            if (ex) throw new RuntimeException("test");
        }

        @Override
        public void close() {
            System.out.println("close");
        }
    }

}
