package stexfires.examples.util;

import stexfires.util.function.BooleanBinaryOperator;
import stexfires.util.function.BooleanUnaryOperator;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesBooleanFunctionInterfaces {

    private ExamplesBooleanFunctionInterfaces() {
    }

    private static void printBooleanUnaryOperator(String title, BooleanUnaryOperator operator) {
        System.out.println(title + " : false -> " + operator.applyAsBoolean(false));
        System.out.println(title + " : true  -> " + operator.applyAsBoolean(true));
    }

    private static void printBooleanBinaryOperator(String title, BooleanBinaryOperator operator) {
        System.out.println(title + " : false false -> " + operator.applyAsBoolean(false, false));
        System.out.println(title + " : false true  -> " + operator.applyAsBoolean(false, true));
        System.out.println(title + " : true  false -> " + operator.applyAsBoolean(true, false));
        System.out.println(title + " : true  true  -> " + operator.applyAsBoolean(true, true));
    }

    private static void showBooleanUnaryOperator() {
        System.out.println("-showBooleanUnaryOperator---");

        printBooleanUnaryOperator("IDENTITY", BooleanUnaryOperator.IDENTITY());
        printBooleanUnaryOperator("NOT", BooleanUnaryOperator.NOT());

        printBooleanUnaryOperator("AND IDENTITY, NOT", BooleanUnaryOperator.AND(BooleanUnaryOperator.IDENTITY(), BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("AND NOT, NOT", BooleanUnaryOperator.AND(BooleanUnaryOperator.NOT(), BooleanUnaryOperator.NOT()));

        printBooleanUnaryOperator("OR IDENTITY, NOT", BooleanUnaryOperator.OR(BooleanUnaryOperator.IDENTITY(), BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("OR NOT, NOT", BooleanUnaryOperator.OR(BooleanUnaryOperator.NOT(), BooleanUnaryOperator.NOT()));

        printBooleanUnaryOperator("compose IDENTITY, NOT", BooleanUnaryOperator.IDENTITY().compose(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("andThen IDENTITY, NOT", BooleanUnaryOperator.IDENTITY().andThen(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("and IDENTITY, NOT", BooleanUnaryOperator.IDENTITY().and(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("or IDENTITY, NOT", BooleanUnaryOperator.IDENTITY().or(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("negate IDENTITY", BooleanUnaryOperator.IDENTITY().negate());
        printBooleanUnaryOperator("negate NOT", BooleanUnaryOperator.NOT().negate());

        System.out.println("asPredicate NOT : false -> " + BooleanUnaryOperator.NOT().asPredicate().test(Boolean.FALSE));
        System.out.println("asPredicate NOT : true -> " + BooleanUnaryOperator.NOT().asPredicate().test(Boolean.TRUE));
        System.out.println("asUnaryOperator NOT : false -> " + BooleanUnaryOperator.NOT().asUnaryOperator().apply(Boolean.FALSE));
        System.out.println("asUnaryOperator NOT : true -> " + BooleanUnaryOperator.NOT().asUnaryOperator().apply(Boolean.TRUE));
    }

    private static void showBooleanBinaryOperator() {
        System.out.println("-showBooleanBinaryOperator---");

        printBooleanBinaryOperator("AND  ", BooleanBinaryOperator.AND());
        printBooleanBinaryOperator("NAND ", BooleanBinaryOperator.AND());

        printBooleanBinaryOperator("OR   ", BooleanBinaryOperator.OR());
        printBooleanBinaryOperator("NOR  ", BooleanBinaryOperator.NOR());

        printBooleanBinaryOperator("XOR  ", BooleanBinaryOperator.XOR());
        printBooleanBinaryOperator("XNOR ", BooleanBinaryOperator.XNOR());

        printBooleanBinaryOperator("andThen AND NOT", BooleanBinaryOperator.AND().andThen(BooleanUnaryOperator.NOT()));

        System.out.println("asBinaryOperator AND : true  false -> " + BooleanBinaryOperator.AND().asBinaryOperator().apply(Boolean.TRUE, Boolean.FALSE));
        System.out.println("asBooleanUnaryOperator AND : true  false -> " + BooleanBinaryOperator.AND().asBooleanUnaryOperator(false).applyAsBoolean(true));
    }

    public static void main(String... args) {
        showBooleanUnaryOperator();
        showBooleanBinaryOperator();
    }

}
