package tests;

import protocol.GBNHost;
import protocol.MyHost;
import protocol.SRHost;

import java.io.IOException;

/**
 * 分别运行两个main函数，一遍send一遍receive，就能打印到不同终端了。。。。。
 */
public class SenderMain {
    private static int host1Port = 808;    // host 1占用端口

    private static int host2Port = 809;

    public static void main(String[] args) throws IOException {
        MyHost sender = new GBNHost(host1Port, 1, 20, 3, "Sender");
        sender.setDestPort(host2Port);

//        MyHost sender = new SRHost(host1Port, 5, 20, 3, "Sender");
//        sender.setDestPort(host2Port);

        new Thread(() -> {
            try {
                sender.sendData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
