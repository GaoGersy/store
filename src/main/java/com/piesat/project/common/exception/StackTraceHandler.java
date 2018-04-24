package com.piesat.project.common.exception;


import com.piesat.project.model.SysExceptionInfo;

public class StackTraceHandler {
    public static SysExceptionInfo getStackTrace(Throwable errors) {
        return getStackTrace(errors, -1);
    }

    public static SysExceptionInfo getStackTrace(Throwable errors, int traceLevel) {
        StringBuffer result = new StringBuffer();
        StackTraceElement[] trace = errors.getStackTrace();
        if (traceLevel == -1) {
            traceLevel = trace.length;
        }
        for (int i = 0; i < traceLevel; i++) {
            result.append("\r\n" + trace[i]);
        }
        Throwable ourCause = errors.getCause();
        if (ourCause != null) {
            result.append(getStackTraceAsCause(ourCause, trace));
        }
        return new SysExceptionInfo(errors.toString(), result.toString());
    }

    private static String getStackTraceAsCause(
            Throwable ourCause, StackTraceElement[] causedTrace) {
        StringBuffer result = new StringBuffer();
        StackTraceElement[] trace = ourCause.getStackTrace();
        int m = trace.length - 1, n = causedTrace.length - 1;
        while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
            m--;
            n--;
        }
        int framesInCommon = trace.length - 1 - m;
        result.append("\nCaused by: " + ourCause);
        for (int i = 0; i <= m; i++) {
            result.append("\n\tat " + trace[i]);
        }
        if (framesInCommon != 0) {
            result.append("\n\t... " + framesInCommon + " more");
        }

        Throwable tempCause = ourCause.getCause();
        if (tempCause != null) {
            result.append(getStackTraceAsCause(tempCause, trace));
        }
        return result.toString();
    }

}