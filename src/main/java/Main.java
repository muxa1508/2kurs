import Libraries.Basket;
import Libraries.JsonConverter;
import Libraries.Logger;
import Libraries.TSVParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    protected static String tsvFile = "categories.tsv";
    protected static String loggerFile = "data.bin";

    public static void main(String[] args) {


        File logFile = new File(loggerFile);
        Logger logger = new Logger();
        Basket basket;
        if (!logFile.exists()) {    //проверка на наличие файла data.bin
            basket = new Basket();
        } else {
            try {
                basket = Logger.loadFromBinFile(logFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


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

                    logger.saveBin(logFile, basket);
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
