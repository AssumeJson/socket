
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP 搜索者，用于搜索服务支持方
 *
 * @author by Assume
 * @date 2019/1/6 20:51
 */
public class UDPProvider {
    public static void main(String[] args) throws Exception {
        System.out.println("UDPProvider started");

        // 作为接收者，指定一个端口用于数据接收
        DatagramSocket ds = new DatagramSocket(20000);

        // 创建接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

        // 接收
        ds.receive(receivePack);

        // 打印接收到的信息与发送者的信息
        // 发送者的IP地址
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLen = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLen);
        System.out.println("UDPProvider receive form ip:" + ip +
                "\tport:" + port + "\tdata:" + data);

        // 构建一份回执信息
        String responseMessage = "Receive message with length:" + dataLen;
        byte[] responseMessageBytes = responseMessage.getBytes();
        // 直径根据发送者构建一份回送消息
        DatagramPacket responsePacket = new DatagramPacket(responseMessageBytes,
                responseMessage.length(), receivePack.getAddress(), receivePack.getPort());
        // 发送信息
        ds.send(responsePacket);
        // 完成
        System.out.println("UDPProvide Finished");
        ds.close();

    }
}
