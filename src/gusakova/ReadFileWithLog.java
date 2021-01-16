package gusakova;


import java.io.*;
import java.util.logging.*;

public class ReadFileWithLog {

   public static  Logger log = Logger.getLogger(ReadFileWithLog.class
            .getName());
   public static FileInputStream  fStream;

    static {
        try {
            fStream = new FileInputStream("C:\\Users\\ZenBook 13\\Downloads\\products.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String strLine;
    public static int count = 0;
    public static String lastProduct = "";
    public static double lastCount = 0;
    public static double lastPrice = 0;
    public static double totalPrice = 0;
    public static StringBuilder stringBuilder = new StringBuilder();

    public static BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        log.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        log.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("MyLog.log");
            fh.setLevel(Level.ALL);
            log.addHandler(fh);
        } catch (IOException e) {
            log.log(Level.ALL, "Error", e);


        }


        logSuccess();
        logError(3000);
    }
    public static void logSuccess () {
        try {

            while ((strLine = br.readLine()) != null) {
                switch (count) {
                    case 0:
                        lastProduct = strLine;
                        break;
                    case 1:
                        lastPrice = Double.parseDouble(strLine);
                        break;
                    case 2:
                        lastCount = Double.parseDouble(strLine);
                }
                count++;
                if (count == 3) {
                    double calculatedPrice = lastPrice * lastCount;
                    totalPrice += calculatedPrice;
                    String productLine = lastProduct + " " + lastPrice + " x " + lastCount + " =" + calculatedPrice;
                    stringBuilder.append("\n");
                    stringBuilder.append(productLine);
                    count = 0;
                }

            }
            stringBuilder.append("\n===================");
            stringBuilder.append("\nИтого:              " + totalPrice);
            log.log(Level.ALL, "Result:" + stringBuilder.toString());

        } catch (IOException e) {

log.log(Level.ALL, e.getMessage(), e);
        }
    }

    public static void logError (double payment) {

        if (payment!=totalPrice) {
                String er = "Нет сдачи";
                log.log(Level.WARNING, "Error: " + er);
            }



    }
}



