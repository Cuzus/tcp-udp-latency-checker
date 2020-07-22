import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author cuzus.org
 * @version 1.0
 */
public class Util
{
	private static final String IP_PATTERN =
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])" +
        ":" + 
        "(([0-9]{1,4})|([1-5][0-9]{4})" +
        "|(6[0-4][0-9]{3})|(65[0-4][0-9]{2})" +
        "|(655[0-2][0-9])|(6553[0-5]))$";
	
	private static final String DOMAIN_PATTERN = 
        "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}" +
        "[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}" +
        ":" + 
        "(([0-9]{1,4})|([1-5][0-9]{4})" +
        "|(6[0-4][0-9]{3})|(65[0-4][0-9]{2})" +
        "|(655[0-2][0-9])|(6553[0-5]))$";
	
	public static boolean isValidIpAddress(String addr)
	{
        Pattern pattern = Pattern.compile(IP_PATTERN);
        Matcher matcher = pattern.matcher(addr);
		
		return matcher.matches();
    }
	
	public static boolean isValidDomain(String addr)
	{
		Pattern pattern = Pattern.compile(DOMAIN_PATTERN);
        Matcher matcher = pattern.matcher(addr);
		
		return matcher.matches();
	}
	
	public static void logToFile(String message)
	{
		try
		{
			FileWriter fw = new FileWriter(new File("log.txt"), true);
			fw.write(message + "\n");
			fw.close();
		}
		catch (IOException ioe)
		{
			
		}
	}
}