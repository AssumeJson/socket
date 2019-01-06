
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 服务端
 *
 * @author by Assume
 * @date 2019/1/4 11:52
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //初始化 socket服务端对象
        ServerSocket serverSocket = new ServerSocket(9001);

        System.out.println("服务端已经启动，IP:" + serverSocket.getInetAddress() + "，port:" + serverSocket.getLocalPort());
        for (; ; ) {
            // 获取 客户端
            Socket accept = serverSocket.accept();
            new ServerHandler(accept).start();
        }
    }


    private static void todo(ServerSocket server) throws IOException {
        boolean flag = true;
        // 监听端口
        Socket socket = server.accept();
        System.out.println("客户端连接成功，IP：" + socket.getInetAddress());
        // 获取 输入流
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        // 获取 socket 输出流
        OutputStream outputStream = socket.getOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        while (flag) {
            String str = bufferedReader.readLine();
            System.out.println("接收信息：" + str);
            if ("bye".equalsIgnoreCase(str)) {
                ps.println("bye");
                flag = false;
            } else {
                ps.println("hello" + str);
            }
        }

        bufferedReader.close();
        ps.close();
        socket.close();
        server.close();
    }

}

class ServerHandler extends Thread {
    private Socket socket;

    private boolean flag = true;

    ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 打印 客户端信息
        System.out.println("客户端连接成功，IP" + socket.getInetAddress() + "，P:" + socket.getPort());
        try {
            // 获取 打印流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 获取 输出流
            PrintStream ps = new PrintStream(socket.getOutputStream());

            while (flag) {
                String str = bufferedReader.readLine();
                if ("bye".equalsIgnoreCase(str)) {
                    // 会送消息 bye
                    ps.println("bye");
                    System.out.println("拜拜:IP：" + socket.getInetAddress());
                    flag = false;

                } else {
                    ps.println(str.trim().length());
                    System.out.println("接收信息：" + str.trim());
                }
            }
            bufferedReader.close();
            ps.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("服务器异常" + e.getMessage());
        }

    }
}
