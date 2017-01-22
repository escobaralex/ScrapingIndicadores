package appvaloresdataextractor;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class myLog {

    static StringBuilder msgLog = new StringBuilder();//\r\n

    public static void d(String msg) {
        msgLog.append(msg + "finC");
        System.out.println("\033[32m" + msg);
    }

    public static void e(String msg) {
        msgLog.append(msg + "finC");
        System.out.println("\033[31m" + msg);
    }

    public static void i(String msg) {
        msgLog.append(msg + "finC");
        System.out.println("\033[33m" + msg);
    }

    public static void s() {
        String[] logs = msgLog.toString().split("finC");
        FileWriter fichero = null;
        PrintWriter pw = null;
        Date fechaDate = new Date();
        String nameFile = fechaDate.toString().replace(" ", "_");
        nameFile = nameFile.replace(":","-");
        try {
            fichero = new FileWriter("c:/APP/Log_" + nameFile + ".txt");
            pw = new PrintWriter(fichero);

            for (int i = 0; i < logs.length; i++) {
                pw.println("Log: " + logs[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
