import Libraries.Basket;
import Libraries.JsonConverter;
import Libraries.TSVParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    protected static String tsvFile = "categories.tsv";

    public static void main(String[] args) {

        Basket basket = new Basket();

        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String inRequest = in.readLine();

                    JsonConverter inJson = new JsonConverter();
                    inJson.jsonToElement(inRequest);
                    String title = inJson.getTitle(); //определение названия покупки из запроса на сервер
//                    String date = inJson.getDate();
                    long sum = inJson.getSum();

                    TSVParser tsvRead = new TSVParser(tsvFile);
                    String category = tsvRead.getCategory(title); //определение категории продукта после парсинга tsv

                    basket.addItem(category, sum);
//                    long returnSum = basket.returnSum(category);
//                    System.out.println(category + " " + returnSum);
                    basket.sumCompare(); //определение максимальной траты

                    JsonConverter outJson = new JsonConverter();
                    out.println(outJson.elementToJson(basket.getMaxSumCategory(), basket.getMaxSumLong())); //выгрузка клиенту JSON с информацией о максимальной трате
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}
