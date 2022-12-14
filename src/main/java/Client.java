import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {


    public static void main(String[] args) {
        clientStart();
    }


    protected static String HOST = "127.0.0.1";
    protected static int PORT = 8989;

    protected static void clientStart() {
        try (Socket clientSocket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            JSONObject outJson = new JSONObject();
            outJson.put("title", "булка");
            outJson.put("date", "2022.02.08");
            outJson.put("sum", 200);
            out.println(outJson);
            System.out.println(in.readLine());
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
