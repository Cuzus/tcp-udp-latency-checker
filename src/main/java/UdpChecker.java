import java.io.FileWriter;
import java.io.IOException;
import java.net.*;

/**
 * @author cuzus.org
 * @version 1.0
 */
public class UdpChecker
{
	private String _ip;
	private int _port;
	private int _timeout;
	
    public UdpChecker(String ip, int port, int timeout)
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
		return "[UDP] " + getIpPortStr() + " ";
	}
	
    public boolean execute(String command)
    {
        long startMs = System.currentTimeMillis();
		
        try
        {
            DatagramSocket datagramSocket = new DatagramSocket(_port);

            byte[] buffer = command.getBytes();

            InetAddress address = InetAddress.getByName(_ip);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, _port);

            datagramSocket.send(packet);

            datagramSocket.setSoTimeout(_timeout);

            byte[] receiveBuffer = new byte[1];

            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            datagramSocket.receive(receivePacket);
			
            long latency = System.currentTimeMillis() - startMs;

            String message = getPrefix() + "Latency is " + String.valueOf(latency) + " ms";
			
			System.out.println(message);
			Util.logToFile(message);
			
            datagramSocket.close();
			
			return true;
        }
        catch (SocketTimeoutException ste)
		{
            System.out.println(getPrefix() + "Receive packet timeout expired");
        }
        catch (UnknownHostException uhe)
		{
            System.out.println(getPrefix() + "Unknown host");
        }
        catch (BindException be)
        {
            System.out.println(getPrefix() + "Address already in use");
        }
        catch (IOException ioe)
        {
			System.out.println(getPrefix() + ioe.getMessage());
            ioe.printStackTrace();
        }
		
		return false;
    }
}