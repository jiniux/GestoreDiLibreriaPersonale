package it.jiniux.gdlp.utility;

public final class ExceptionChainVisitor {
    private ExceptionChainVisitor() {}

    @SuppressWarnings("unchecked")
    public static <T extends Exception> T findExceptionInChain(Throwable throwable, Class<T> exceptionClass) {
        if (throwable == null || exceptionClass == null) {
            return null;
        }
        if (exceptionClass.isInstance(throwable)) {
            return (T) throwable;
        }
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return findExceptionInChain(cause, throwable, exceptionClass);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static  <T extends Exception> T findExceptionInChain(Throwable throwable, Throwable previous, Class<T> exceptionClass) {
        if (throwable == null || exceptionClass == null) {
            return null;
        }
        if (exceptionClass.isInstance(throwable)) {
            return (T) throwable;
        }
        if (previous != null && previous.equals(throwable)) {
            return null; // avoid infinite recursion
        }
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return findExceptionInChain(cause, throwable, exceptionClass);
        }
        return null;
    }
}
