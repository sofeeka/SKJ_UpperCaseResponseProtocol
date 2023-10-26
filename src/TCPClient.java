import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;

public class TCPClient
{

    public static void main(String[] args)
    {
        Thread thread = startServer();
        startReadingClient();
        stopServer(thread);
    }

    public static Thread startServer()
    {
        Thread serverThread = new Thread(() ->
        {
            TCPServer server = new TCPServer();
            server.listenSocket();
        });
        serverThread.start();
        return serverThread;
    }

    private static void startReadingClient()
    {
        System.out.println("Client, enter your message");
        Scanner scanner = new Scanner(System.in);

        String line;
        while((line = scanner.nextLine()) != null)
        {
            if (line.isEmpty())
                break;
            System.out.println(runClient(line));
        }
    }

    public static String runClient(String line)
    {
        Socket socket = null;

        PrintWriter out = null;
        BufferedReader in = null;

        String address = "127.0.0.1";
        int port = 321;

        String response = null;

// Establish 2-way connection
        Logger.outLog("Establishing 2-way connection " + address + ":" + port); // DEBUG
        try
        {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (UnknownHostException e)
        {
            Logger.errLog("Unknown host");
            System.exit(-1);
        }
        catch  (IOException e)
        {
            Logger.errLog("No I/O");
            System.exit(-1);
        }
// Connection was established
        Logger.outLog("Connection was established"); // DEBUG

// Communicate in accordance to protocol
        try
        {
// Send request
            Logger.outLog("Will send request"); // DEBUG
            out.println(line);
// Request was sent
            Logger.outLog("Request was sent"); // DEBUG

// Receive response
            Logger.outLog("Will receive response"); // DEBUG
            response = in.readLine();
        }
        catch (IOException e)
        {
            Logger.errLog("Error during communication");
            System.exit(-1);
        }
// Response was received
        Logger.outLog("Response was received"); // DEBUG

// Close connection
        Logger.outLog("Will close connection with server"); // DEBUG
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            Logger.errLog("Cannot close the socket");
            System.exit(-1);
        }
// Connection was closed
        Logger.outLog("Connection was closed"); // DEBUG

        Logger.outLog("Bye, bye...");
        return response;
    }

    private static void stopServer(Thread thread)
    {
        try
        {
            thread.interrupt();
            System.exit(0);
        }catch (Exception e)
        {
            Logger.errLog(e.toString());
        }
    }
}