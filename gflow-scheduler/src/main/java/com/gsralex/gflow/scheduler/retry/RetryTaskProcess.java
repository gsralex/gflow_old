package com.gsralex.gflow.scheduler.retry;

/**
 * @author gsralex
 * @version 2019/2/7
 */
public class RetryTaskProcess {

    public boolean doProcess(RetryTask task) {
        return doProcess(task, 3);
    }

    public boolean doProcess(RetryTask task, int times) {
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
