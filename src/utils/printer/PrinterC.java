package utils.printer;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class PrinterC implements Printer {

    //Singleton
    private static PrinterC instance = null;
    private PrintWriter msg;

    private PrinterC() {
        msg = new PrintWriter(new OutputStreamWriter(System.out));
    }

    public static PrinterC getInstance() {
        if (instance == null) {
            instance = new PrinterC();
        }
        return instance;
    }

    @Override
    public void print(String input) {
        msg.print(input);
        msg.flush();
    }
}