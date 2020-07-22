import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author cuzus.org
 * @version 1.0
 */
public class Pinger
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scanner = new Scanner(new File("hosts.txt"));
			
            while (scanner.hasNext())
			{
                String fullAddress = scanner.nextLine();
				
                if (fullAddress.trim().isEmpty())
                {
                    System.out.println("Skipped empty line");
                    continue;
                }
				
				if (!Util.isValidIpAddress(fullAddress) && !Util.isValidDomain(fullAddress))
				{
					System.out.println("Skipped incorrect expression");
                    continue;
				}
				
                String[] ipAndPorts = fullAddress.split(":");
				String ip = ipAndPorts[0];
				int port = Integer.parseInt(ipAndPorts[1]);
				final int timeout = 5000;
				
                TcpChecker tcpChecker = new TcpChecker(ip, port, timeout);
                tcpChecker.execute();
				
				String[] requests = 
				{
					"\\status\\", 
					"\\xFF\\xFF\\xFF\\xFFTSource Engine Query\\x00"
				};
				
                UdpChecker udpChecker = new UdpChecker(ip, port, timeout);
                udpChecker.execute(requests[0]);
                udpChecker.execute(requests[1]);
            }
        }
		catch (FileNotFoundException nfe)
		{
            System.out.println(nfe.getMessage());
			nfe.printStackTrace();
        }
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
}