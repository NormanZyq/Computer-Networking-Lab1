package protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class MyHost {
    /**
     * 窗口长度。
     */
    final int WINDOW_SIZE;

    /**
     * 分组个数。
     */
    final int DATA_NUMBER;

    /**
     * 超时时间，秒。
     */
    final int TIMEOUT;

    /**
     * 这个主机的名称。
     */
    String hostName;

    /*下面的是发送数据相关的变量*/

    /**
     * 下一个发送的分组。
     */
    int nextSeq = 1;

    /**
     * 当前窗口起始位置。
     */
    int base = 1;

    /**
     * 分组发送的目标地址。
     */
    InetAddress destAddress;

    /**
     * 发送分组的目标端口，初始化为80
     */
    int destPort = 80;

    /*接收数据相关*/

    /**
     * 期望收到的分组序列号。
     */
    int expectedSeq = 1;

    /*Sockets*/

    /**
     * 发送数据使用的socket。
     */
    DatagramSocket sendSocket;

    /**
     * 接收分组使用的socket。
     */
    DatagramSocket receiveSocket;

    MyHost(int RECEIVE_PORT, int WINDOW_SIZE,
           int DATA_NUMBER, int TIMEOUT, String name) throws IOException {
        this.WINDOW_SIZE = WINDOW_SIZE;
        this.DATA_NUMBER = DATA_NUMBER;
        this.TIMEOUT = TIMEOUT;
        this.hostName = name;

        sendSocket = new DatagramSocket();
        receiveSocket = new DatagramSocket(RECEIVE_PORT);
        destAddress = InetAddress.getLocalHost();
    }

    /**
     * 获得目标地址。
     *
     * @return 返回目标地址
     */
    public InetAddress getDestAddress() {
        return destAddress;
    }

    /**
     * 设置发送分组的目标地址。
     *
     * @param destAddress 目标地址
     */
    public void setDestAddress(InetAddress destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * 获得目标端口。
     *
     * @return 目标端口，int
     */
    public int getDestPort() {
        return destPort;
    }

    /**
     * 设置目标端口。
     *
     * @param destPort 目标端口
     */
    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }

    /**
     * 开始发送数据，在实现的时候定义好发送数据的内容。
     * @throws IOException  IO相关异常发生时抛出
     */
    public abstract void sendData() throws IOException;

    /**
     * 超时事件。
     * @throws IOException  IO相关异常发生时抛出
     */
    public abstract void timeOut() throws IOException;

    /**
     * 作为一个接收端开始接收数据。
     * @throws IOException  IO相关异常发生时抛出
     */
    public abstract void receive() throws IOException;

    /**
     * 向发送方回应ACK。
     *
     * @param seq    ACK序列号
     * @param toAddr 目的地址
     * @param toPort 目的端口
     * @throws IOException socket相关错误时抛出
     */
    void sendACK(int seq, InetAddress toAddr, int toPort) throws IOException {
        String response = hostName + " responses ACK: " + seq;
        byte[] responseData = response.getBytes();
        // 获得来源IP地址和端口，确定发给谁
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, toAddr, toPort);
        receiveSocket.send(responsePacket);
    }

    /**
     * 获得主机名称。
     * @return  主机名称
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 停止这个主机，无论是否在接收或者发送。
     */
    public abstract void stopHost();
}
