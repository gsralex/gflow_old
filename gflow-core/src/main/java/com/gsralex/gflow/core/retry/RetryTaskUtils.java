package com.gsralex.gflow.core.retry;

/**
 * @author gsralex
 * @version 2019/2/7
 */
public class RetryTaskUtils {

    private static final int RETRY_TIMES = 3;

    public static boolean doProcess(RetryTask task) {
        return doProcess(task, RETRY_TIMES);
    }

    public static boolean doProcess(RetryTask task, int times) {
        boolean ok;
        int cnt = 0;
        while (true) {
            ok = task.doAction();
            cnt++;
            if (ok) {
                return ok;
            }
            if (cnt >= times) {
                return false;
            }
        }
    }
}
