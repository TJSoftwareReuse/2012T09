package PerMinute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PMPerMinute {
	
	
	private String  _path = null;
	private Map<String , Double> indexMap = new HashMap<String, Double>();
	private ScheduledExecutorService scheduleExec;
	private Tasks task = new Tasks();
	private String startTime = null;
	private long intervalTime = 60000;
	
	/**
	 * 构造函数
	 * @param path 性能报告保存的路径
	 */
	public PMPerMinute(String path)
	{
		_path = path;
		try {
			this.setPath(_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.scheduleExec = Executors.newScheduledThreadPool(10);
	}
	
	/**
	 * 添加一个指标名及其指标值
	 * @param name 指标名
	 * @param value 指标值
	 */
	public void addIndex(String name, double value)
	{
		if(indexMap.containsKey(name))
		{
			double v = indexMap.get(name);
			indexMap.remove(name);
			indexMap.put(name, value+v);
		}
		else
		{
			indexMap.put(name, value);
		}
		
	}
	
	/**
	 * 获取指定指标的值
	 * @param name 指标名
	 * @return 指标值，当指标不在存在时为null
	 */
	public Double getIndex(String name)
	{
		return indexMap.get(name);
	}
	
	/**
	 * 设定存储性能报告保存的路径
	 * @param path 指定路径
	 * @throws IOException
	 */
	public void setPath(String path) throws IOException
	{
		File file = new File(path);
		if(!file.exists() || !file.isDirectory())
		{
			file.mkdirs();
		}
		_path = path; 
	}
	
	/**
	 * 获取当前系统时间
	 * @return 当前系统时间
	 */
	private String cTime()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
		String ctime = formatter.format(new Date());
		return ctime;
	}
	
	
	/**
	 * 生成报告
	 * @throws IOException
	 */
	private void createReport() throws IOException
	{
		
		
		Map<String, Double> map = indexMap;
		String current = cTime();
		indexMap = new HashMap<String, Double>();
		String url = _path+"/"+startTime+" T "+current;
		startTime = current;
		File file = new File(url);
		file.createNewFile();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		if (map.isEmpty())
		{
			out.print("null");
			out.close();
			return ;
		}
		for(String key : map.keySet())
		{
			out.print(key+" : ");
			out.println(map.get(key));
		}
		out.close();
	}
	
	/**
	 * 
	 * 定时执行的任务
	 *
	 */
	private class Tasks extends TimerTask
	{

		@Override
		public void run() {
			try {
				createReport();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 开始生成报告
	 */
	public void  start()
	{
		startTime = cTime();
		scheduleExec.scheduleAtFixedRate(task,intervalTime,intervalTime,TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 停止生成报告
	 */
	public void stop()
	{
		scheduleExec.shutdown();
		if(!indexMap.isEmpty())
		{
			try {
				createReport();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		startTime = null;
	}
	
	
	/**
	 * 设置间隔时间段
	 * @param intervalTime 单位ms
	 */
	public void setIntervalTime(long intervalTime) {
		
		if(startTime == null)
		{
			this.intervalTime = intervalTime;
			return;
		}
		
		
		scheduleExec.shutdown();
		String currentTime = cTime();
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
		long current = 0;
		long startT = 0;
		long delay = 0;
		
		try {
			current = formatter.parse(currentTime).getTime();
			startT = formatter.parse(startTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		delay = startT+this.intervalTime-current;
		this.intervalTime = intervalTime;
		scheduleExec = Executors.newScheduledThreadPool(10);
		scheduleExec.scheduleAtFixedRate(task,delay,intervalTime,TimeUnit.MILLISECONDS);
	}
	

}
