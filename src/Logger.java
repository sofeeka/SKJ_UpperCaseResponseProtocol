public class Logger {
    private final static boolean outLogging = false;
    private final static boolean errLogging = false;

    public static void outLog(String s)
    {
        if(outLogging)
            System.out.println(s);
    }

    public static void errLog(String s)
    {
        if(errLogging)
            System.err.println(s);
    }
}
