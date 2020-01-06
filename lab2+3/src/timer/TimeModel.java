package timer;

/**
 * 保证线程安全性。
 * 来自https://blog.csdn.net/qq_33669680/article/details/78332899
 */
public class TimeModel {

    public volatile int time;

    public synchronized int getTime() {
        return time;
    }

    public synchronized void setTime(int time) {
        this.time = time;
    }
}
