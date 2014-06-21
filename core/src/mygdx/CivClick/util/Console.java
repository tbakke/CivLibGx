package mygdx.CivClick.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author Tyler
 */
public class Console implements Serializable {

    private static boolean islogging = false;
    private static final List<Object> printlist = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    private static javax.swing.JTextArea consoleout;
    private static final File logfile = new File("output.log");

    /**
     * Custom System.out.println wrapper. Defaults to type u//ser if no output
     * type is supplied.
     *
     * @param message object to be passed along.
     */
    public static void println(Object message) {
        println(message, Type.u);
    }

    /**
     * Custom System.out.println wrapper.
     *
     * @param message object to be passed along.
     * @param type flag used to determine if the message is sent to the u//ser
     * or s//ystem.
     */
    public static void println(Object message, Type type) {
        switch (type) {
            case u:
                if (islogging) {
                    WriteLog(Timestamp() + message);
                }
                if (printlist.size() >= 28) { // This does scrolly stuff.
                    printlist.remove(0);
                }
                printlist.add(Timestamp() + message);
                consoleout.setText("");
                for (Object s : printlist) {
                    System.out.println(s);
                }
                break;
            case s:
                if (islogging) {
                    WriteLog(Timestamp() + message);
                }
                break;
        }
    }

    private static void WriteLog(Object message) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)))) {
            out.println(message);
        } catch (IOException ex) {
            println(ex.getMessage());
        }
    }

    /**
     * Starts logging system and console output to a file.
     */
    public static void StartLog() {
        islogging = !islogging;
        String s = "----------" + "LOG START" + Timestamp() + "----------";
        WriteLog(s);
    }

    /**
     * Sets a JTextArea to be printed to as well as setting System Err and Out
     * to this JTA.
     *
     * @param jta the JTextArea.
     */
    public static void setOut(JTextArea jta) {
        consoleout = jta;
        PrintStream ps = new PrintStream(new ConsoleOutputStream(consoleout));
        System.setErr(ps);
        System.setOut(ps);
    }

    private static String Timestamp() {
        return "[" + sdf.format(new Date()) + "] ";
    }

    public enum Type {

        u, // user
        s // system
    }
}
