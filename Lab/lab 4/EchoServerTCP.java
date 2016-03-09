import java.net.*;
import java.io.*;
import java.util.Scanner;

public class EchoServerTCP {

    public static void main(String args[]) {
        Socket clientSocket = null;
        try {
            int serverPort = 30000; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
//            clientSocket = listenSocket.accept();
            Scanner in;
            PrintWriter out;
            while (true) {
        clientSocket = listenSocket.accept();
            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                String data = in.nextLine();
        String[] splited = data.split("\\s+");
        if (data.startsWith("GET")) {
            String filename = "."+splited[1];
            File f = new File(filename);
            if(f.exists()) { 
                        System.out.println("Echoing: " + data);
                out.println("HTTP/1.1 200 OK\n\n");
                print_file(out, filename);
            } else {
                System.out.println("nothign at all");
                out.println("HTTP/1.1 404 NOT FOUND\n\n");
                out.println("<html>PAGE NOT FOUND\n</html>");
            }
                    out.flush();
            out.close();
        } else {
                    out.flush();
            out.close();
            continue;
        }
                //out.println(splited[1]);
            }
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }

    public static void print_file(PrintWriter out, String filename) {
    String everything = null;
        try {
    BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
            }
            everything = sb.toString();
            br.close();
        } catch (Exception e) {
    } finally {
        }
    out.println(everything);
    }
}
