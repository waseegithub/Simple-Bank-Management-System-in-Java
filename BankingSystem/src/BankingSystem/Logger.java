package BankingSystem;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String TrasationLog = "transactionLog.txt";
    private static final String errorLog = "ErrorLog.txt";

    public static void logTransaction(String msg) throws IOException {
        try (FileWriter writer = new FileWriter(TrasationLog, true)) {
            writer.write(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logError(String msg) throws IOException {
        try (FileWriter writer = new FileWriter(errorLog, true)) {
            writer.write(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
