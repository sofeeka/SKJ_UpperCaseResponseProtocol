import java.io.*;
import java.net.*;


public class ServerThread extends Thread
{
    private final Socket socket;

    public ServerThread(Socket socket)
    {
        super();
        this.socket = socket;
    }

    public void run()
    {
        String thread_ID = Long.toString(currentThread().threadId()); // DEBUG
        Logger.errLog("THREAD " + thread_ID + " starting"); // DEBUG

        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            line = in.readLine();
            Logger.outLog("THREAD " + thread_ID + " received line: " + line);

            out.println(line.toUpperCase());

        } catch (IOException e1) { /*do nothing*/ }

        try
        {
            Logger.outLog("Server closing the socket");
            socket.close();
        } catch (IOException e) { /* do nothing */}

        Logger.outLog("THREAD" + thread_ID + " exiting"); // DEBUG
    }
}