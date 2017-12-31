package stexfires.examples.javatest;


public final class AutoCloseableTest {

    private static class TestClose implements AutoCloseable {

        TestClose(boolean ex) {
            System.out.println("constructor " + ex);
            if (ex) throw new RuntimeException("constructor");
        }

        void test(boolean ex) {
            System.out.println("test " + ex);
            if (ex) throw new RuntimeException("test");
        }

        @Override
        public void close() {
            System.out.println("close");
        }
    }

    public static void main(String[] args) {
        System.out.println("---Test case 1");
        try {
            try (TestClose t = new TestClose(false)) {
                t.test(false);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }

        System.out.println("---Test case 2");
        try {
            try (TestClose t = new TestClose(true)) {
                t.test(false);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }

        System.out.println("---Test case 3");
        try {
            try (TestClose t = new TestClose(false)) {
                t.test(true);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }

        System.out.println("---Test case 4");
        TestClose t4 = new TestClose(false);
        try {
            t4.test(true);
        } catch (Exception e) {
            System.out.println("catch " + e);
        }

        System.out.println("---Test case 5");
        TestClose t5 = new TestClose(false);
        try {
            try (TestClose t = t5) {
                t.test(true);
            }
        } catch (Exception e) {
            System.out.println("catch " + e);
        }
    }

}
