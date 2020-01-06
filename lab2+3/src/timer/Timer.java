package timer;

import protocol.MyHost;

/**
 * 计时器。
 * 参考自https://blog.csdn.net/qq_33669680/article/details/78332899
 */
public class Timer extends Thread {

    private TimeModel timeModel;

    private MyHost myHost;

    public Timer(MyHost myHost, TimeModel timeModel) {
        this.myHost = myHost;
        this.timeModel = timeModel;
    }

    @Override
    public void run() {
        while (true) {
            int time = timeModel.getTime();
            if (time > 0) {
                try {
                    Thread.sleep(time * 1000);

                    System.out.println("\n");
                    if (myHost != null) {
                        System.out.println(myHost.getHostName() + "等待ACK超时");
                        myHost.timeOut();
                    }
//                    if (gbnHost != null) {
//                        System.out.println("GBN接收方等待ACK超时");
//                        gbnHost.timeOut(gbnHost.getDestPort());
//                    } else if (srHost != null) {
//                        System.out.println("SR接收方等待ACK超时");
//                        srHost.timeOut(srHost.getDestPort());
//                    }

                    timeModel.setTime(0);

                } catch (Exception e) {
                }
            }
        }
    }
}