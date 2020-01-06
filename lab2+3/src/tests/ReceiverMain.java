package tests;

import protocol.GBNHost;
import protocol.MyHost;

import java.io.IOException;

public class ReceiverMain {
    private static int host1Port = 808;    // host 1占用端口

    private static int host2Port = 809;    // host 2 占用

    public static void main(String[] args) throws IOException {
        MyHost receiver = new GBNHost(host2Port, 1, 20, 3, "Receiver");
        receiver.setDestPort(host1Port);

//        MyHost receiver = new SRHost(host2Port, 5, 20, 3, "Receiver");
//        receiver.setDestPort(host1Port);

        new Thread(() -> {
            try {
                receiver.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
