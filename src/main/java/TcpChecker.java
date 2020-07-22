import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * @author cuzus.org
 * @version 1.0
 */
public class TcpChecker
{
	private String _ip;
	private int _port;
	private int _timeout;
	
    public TcpChecker(String ip, int port, int timeout)
    {
		_ip = ip;
		_port = port;
		_timeout = timeout;
    }
	
	public String getIpPortStr()
	{
		return _ip + ":" + _port;
	}
	
	private String getPrefix()
	{
		return "[TCP] " + getIpPortStr() + " ";
	}
	
    public boolean execute()
    {
        long startMs = System.currentTimeMillis();

        try
        {
            Socket socket = new Socket(_ip, _port);
            socket.setSoTimeout(_timeout);
			
            if (socket.isConnected())
			{
                socket.close();
				
                long latency = System.currentTimeMillis() - startMs;
				
                String message = getPrefix() + "Latency is " + latency + " ms";
				
                System.out.println(message);
                Util.logToFile(message);
            }
			
			return true;
        }
		catch (ConnectException ce)
		{
            System.out.println(getPrefix() + "Socket connection failed");
        }
        catch (IOException ioe)
        {
			System.out.println(getPrefix() + ioe.getMessage());
            ioe.printStackTrace();
        }
		
		return false;
    }
}