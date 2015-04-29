import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;


public class PMPerMinuteTest {

	PMPerMinute ppm = new PMPerMinute("JUnitTest");
	ScheduledExecutorService service=Executors.newScheduledThreadPool(2);
	int isStart = 0;
	double index =0;
	private Map<String , Double> indexMap = new HashMap<String, Double>();
	Task1 task1 = new Task1();
	Task2 task2 = new Task2();
	
	@Before
	public void setUp() throws Exception {
	}


	@SuppressWarnings("deprecation")
	@Test
	public void testAddIndex() {
		double value;
		if(ppm.getIndex("name")==null)
			value=0;
		else {
			value = ppm.getIndex("name");
		}
		
		ppm.addIndex("name", 2d);
		String result = Double.toString(ppm.getIndex("name"));
		String expect = Double.toString(value+2);
		assertEquals(expect, result);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetIndex() {
		double value;
		if(ppm.getIndex("name")==null)
			value=0;
		else {
			value = ppm.getIndex("name");
		}
		
		ppm.addIndex("name", 2d);
		String result = Double.toString(ppm.getIndex("name"));
		String expect = Double.toString(value+2);
		assertEquals(expect, result);
		
	}

	@Test
	public void testSetPath() throws IOException {
		String path = "dirTest";
		int dirIsExit=0;
		ppm.setPath(path);
		File file = new File(path);
		if(!file.exists() || !file.isDirectory())
		{
			dirIsExit=0;
		}
		else {
			dirIsExit=1;
		}
		assertEquals(1,dirIsExit);
		
		
	}
	
	private class Task1 extends TimerTask
	{

		@Override
		public void run() {
			if(isStart==0)
			{
				ppm.start();
				isStart=1;
			}
		}
		
	}
	
	private class Task2 extends TimerTask
	{

		@Override
		public void run() {
			String name = "name"+index;
			ppm.addIndex(name, index);
			indexMap.put(name, index);
			index++;
		}
	}

	/*@SuppressWarnings({ "resource", "unused" })
	@Test
	public void testStart() throws NumberFormatException, IOException {
		//scheduleExec.scheduleAtFixedRate(task,60000,60000,TimeUnit.MILLISECONDS);
		service.scheduleAtFixedRate(task1,1,1,TimeUnit.SECONDS);
		service.scheduleAtFixedRate(task1, 2, 2, TimeUnit.SECONDS);
		
		 @SuppressWarnings("unchecked")
		ScheduledFuture future2=service.schedule(new Callable()
        {
			public String call()
            {
				task1.cancel();
                return "taskcancelled!";
            }
        },61,TimeUnit.SECONDS);
		
		ppm.start();
		 
		//service.shutdownNow();
		
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
		String ctime = formatter.format(new Date());
		FileReader fr;
		while((fr=new FileReader("JUnitTest"+"//"+ctime))==null){
	        break;
		}
		BufferedReader br=new BufferedReader(fr);
        String line="";
        String[] arrs=null;
        int result=1;
        while ((line=br.readLine())!=null) {
            arrs=line.split(",");
            if(ppm.getIndex(arrs[0])!= Double.parseDouble(arrs[1]))
            {
            	result=0;
            	break;
            }
        }
        br.close();
        fr.close();
        assertEquals(1, result);
        
	}*/

}
