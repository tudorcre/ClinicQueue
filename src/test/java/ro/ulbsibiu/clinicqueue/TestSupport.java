package ro.ulbsibiu.clinicqueue;

import java.util.Objects;

/**
 * Mini-framework de testare, suficient pentru proiectul demonstrativ.
 */
public final class TestSupport {
    private TestSupport() {
    }

    public static void assertEquals(final Object expected, final Object actual, final String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + " Expected=" + expected + ", actual=" + actual);
        }
    }

    public static void assertTrue(final boolean condition, final String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertThrows(
            final Class<? extends Throwable> expectedException,
            final Runnable executable,
            final String message) {
        try {
            executable.run();
        } catch (Throwable actualException) {
            if (expectedException.isInstance(actualException)) {
                return;
            }
            throw new AssertionError(message + " Exception type=" + actualException.getClass().getName());
        }
        throw new AssertionError(message + " Nu a fost aruncată nicio excepție.");
    }
}
