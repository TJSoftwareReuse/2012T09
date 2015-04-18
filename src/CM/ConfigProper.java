package CM;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigProper {
	
	private Properties config = null;
	
	
	public ConfigProper() throws IOException
	{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.property");
		config = new Properties();
		config.load(in);
		in.close();
	}
	
	public ConfigProper(String configPath) throws IOException
	{
		InputStream in  = this.getClass().getClassLoader().getResourceAsStream(configPath);
		
		config = new Properties();
		config.load(in);
		in.close();
		
	}
	
	public String getValue(String key)
	{
		
		return config.getProperty(key);
	}
	
//	public boolean setValue(String key ,String value)
//	{
//		
//	}
	
	public Map<String,String> getAllValue()
	{
		Map<String,String> map = new HashMap<String,String>();
		Enumeration en = config.propertyNames();
		while(en.hasMoreElements())
		{
			String key = (String)en.nextElement();
			String value = config.getProperty(key);
			map.put(key,value);
		}
		
		return map;
		
	}
	
	
	public static void main(String[] args)
	{
		ConfigProper con = null;
		try {
			con = new ConfigProper();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print(con.getValue("sex"));
		
		
	}
	
	
}
