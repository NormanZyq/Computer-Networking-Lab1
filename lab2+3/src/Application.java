import protocol.MyHost;
import protocol.SRHost;

import java.io.IOException;

public class Application {

    private static final int HOST_1_PORT = 808;    // host 1占用端口

    private static final int HOST_2_PORT = 809;

    // main
    public static void main(String[] args) throws IOException {

        /*
            以SR协议为例运行发送和接收
         */
        MyHost sender = new SRHost(HOST_1_PORT, 6, 50, 3, "Sender");
        sender.setDestPort(HOST_2_PORT);
        MyHost receiver = new SRHost(HOST_2_PORT, 6, 50, 3, "Receiver");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    receiver.receive();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sender.sendData();
                    // 发送完了，但是接收方还在接收，关闭它
                    receiver.stopHost();
                    sender.stopHost();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
