package CM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigProperties{
	
	private String _path = null;
	private Properties config = null;
	
	/**
	 *构造函数， 默认从classpath下的config.properties读取配置信息，配置文件类型 properties
	 *  @throws IOException
	 *   
	 */
	public ConfigProperties() throws IOException
	{
		_path = "config.properties";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		config = new Properties();
		config.load(in);
		in.close();
	}
	
	/**
	 * 构造函数，从给定路径中读取properties配置文件，配置文件类型properties
	 * @param path  配置文件所在路径及文件名，参见File
	 * @throws  IOException
	 */
	public ConfigProperties(String path) throws IOException
	{
		_path = path;
		InputStream in  = new FileInputStream(new File(path));
		
		config = new Properties();
		config.load(in);
		in.close();
		
	}
	
	/**
	 * 查询单个配置属性的值
	 * @param key 属性名
	 * @return 若查询属性存在，返回该属性的值；若查询属性不存在，返回null
	 */
	public String getValue(String key)
	{
		
		return config.getProperty(key);
	}
	
	
	/**
	 * 设置某个已存在属性的值
	 * @param key 属性名
	 * @param value 属性值
	 * @return  设置成功，返回ture，设置失败返回false
	 * @throws IOException 
	 */
	public boolean setValue(String key ,String value) throws IOException
	{
			if(null == config.setProperty(key, value))
				return false;
			FileOutputStream out = new FileOutputStream(_path);
			config.store(out, null);
			out.close();
			return true;
	}
	
	/**
	 * 获取所有配置属性名以及属性值
	 * @return  返回map，key为属性名，value为属性值
	 * 
	 */
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
	
}
