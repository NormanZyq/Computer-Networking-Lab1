package protocol;

import org.junit.Test;

import java.io.IOException;

/**
 * 乱写的test，根本就不是test。
 */
public class HostTest {

    private int host1Port = 808;    // host 1占用端口

    private int host2Port = 809;    // host 2 占用

    /**
     * 使用host 1的端口作为接收时监听的端口。
     */
    private MyHost gbnHost1 = new GBNHost(host1Port, 1, 20, 3, "Host 2");

    /**
     * 使用host 2的端口作为接收时监听的端口。
     */
    private MyHost gbnHost2 = new GBNHost(host2Port, 1, 20, 3, "Host 1");


    private MyHost srHost2 = new SRHost(host2Port, 5, 20, 3, "Receiver");


    private MyHost srHost1 = new SRHost(host1Port, 5, 20, 3, "Sender");
//        sender.setDestPort(host2Port);

    public HostTest() throws IOException {
    }

    /**
     * host 2作为接收方，host 1 作为发送方。
     */
    @Test
    public void testGBN() {
        gbnHost1.setDestPort(host2Port);

        new Thread(() -> {
            try {
                gbnHost2.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                gbnHost1.sendData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * host 2作为接收方，host 1 作为发送方。
     */
    @Test
    public void testSR() {
        srHost2.setDestPort(host1Port);
        new Thread(() -> {
            try {
                srHost2.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                srHost1.sendData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * GBN 双向传输。
     */
    @Test
    public void testGBNDual() {
        new Thread(() -> {
            try {
                gbnHost2.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                gbnHost2.sendData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                gbnHost1.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                gbnHost1.sendData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
