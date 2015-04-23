package PerMinute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	/**
	 * ���캯��
	 * @param path ���ܱ��汣���·��
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
	 * ���һ��ָ��������ָ��ֵ
	 * @param name ָ����
	 * @param value ָ��ֵ
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
	 * ��ȡָ��ָ���ֵ
	 * @param name ָ����
	 * @return ָ��ֵ����ָ�겻�ڴ���ʱΪnull
	 */
	public Double getIndex(String name)
	{
		return indexMap.get(name);
	}
	
	/**
	 * �趨�洢���ܱ��汣���·��
	 * @param path ָ��·��
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
	 * ��ȡ��ǰϵͳʱ��
	 * @return ��ǰϵͳʱ��
	 */
	private String cTime()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
		String ctime = formatter.format(new Date());
		return ctime;
	}
	
	/**
	 * ���ɱ���
	 * @throws IOException
	 */
	private void createReport() throws IOException
	{

		Map<String, Double> map = indexMap;
		indexMap = new HashMap<String, Double>();
		String url = _path+"/"+cTime();
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
	 * ��ʱִ�е�����
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
	 * ��ʼ���ɱ���
	 */
	public void  start()
	{
		scheduleExec.scheduleAtFixedRate(task,60000,60000,TimeUnit.MILLISECONDS);
	}
	
	/**
	 * ֹͣ���ɱ���
	 */
	public void stop()throws SecurityException
	{
		scheduleExec.shutdown();
	}

}
