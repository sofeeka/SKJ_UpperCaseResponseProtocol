import java.io.*;
import java.net.*;


public class TCPServer
{

    public void listenSocket()
    {

        ServerSocket server = null;
        Socket client = null;

        try
        {
            server = new ServerSocket(321);
        }
        catch (IOException e)
        {
            Logger.errLog("Could not listen");
            System.exit(-1);
        }
        Logger.outLog("Server listens on port: " + server.getLocalPort());

        while(true)
        {
            try
            {
                client = server.accept();
            }
            catch (IOException e)
            {
                Logger.errLog("Accept failed");
                System.exit(-1);
            }

            new ServerThread(client).start();
        }

    }
}