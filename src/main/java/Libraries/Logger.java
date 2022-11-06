package Libraries;

import java.io.*;


public class Logger {

    public void saveBin(File file, Basket basket) throws IOException {

        try (FileOutputStream out = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
                oos.writeObject(basket);
            }
        }


    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {

        try (FileInputStream in = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(in)) {
            Basket basket = (Basket) ois.readObject();
            return basket;
        }
    }
}
