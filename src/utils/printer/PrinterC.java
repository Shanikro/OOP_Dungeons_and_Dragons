package utils.printer;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class PrinterC implements Printer {

    private PrintWriter msg;

    //Singleton
    private static class PrinterHolder {
        private static final PrinterC instance = new PrinterC();
    }

    private PrinterC() {
        msg = new PrintWriter(new OutputStreamWriter(System.out));
    }

    public static PrinterC getInstance() {
        return PrinterHolder.instance;
    }

    @Override
    public void print(String input) {
        msg.print(input);
        msg.flush();
    }
}
