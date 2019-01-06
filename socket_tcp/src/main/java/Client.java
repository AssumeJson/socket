import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket 客户端
 *
 * @author by Assume
 * @date 2019/1/4 11:52
 */
public class Client {

    private final static Integer PORT = 9001;

    private final static Integer TIME_OUT = 3000;

    public static void main(String[] args) throws IOException {

        // 初始化 socket
        Socket socket = new Socket();

        // 设置端口，IP,超时时间
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), PORT), TIME_OUT);

        System.out.println("客户端连接成功");
        System.out.println("客户端信息：" + socket.getLocalAddress() + "端口号：" + socket.getLocalPort());
        System.out.println("服务端信息：" + socket.getInetAddress() + "端口号：" + socket.getPort());

        // 阻塞

        try {
            todo(socket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void todo(Socket client) throws IOException {

        Boolean flag = true;

        // 获取键盘输入流
        InputStream in = System.in;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        // 得到Socket输出流，并转换为打印流
        OutputStream os = client.getOutputStream();
        PrintStream ps = new PrintStream(os);

        // 得到socket 输入流
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        while (flag) {
            String str = br.readLine();
            // 发送到服务器
            ps.println(str);

            // 从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        }

        br.close();
        ps.close();
        socketBufferedReader.close();
        client.close();
    }
}
