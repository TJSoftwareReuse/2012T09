package queryserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import CM.ConfigProperties;

public class QueryServerTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testReadConfig() {
		QueryServer qs = new QueryServer();
		int isError = 0;
		try{
			qs.readConfig();
		}
		catch(Exception e){
			//System.out.println("ReadConfig() failed\n");
			isError = 1;
		}
		
		assertEquals(0, isError);
	}

	@Test
	public void testInit() throws Exception {
		QueryServer qs = new QueryServer();
		
		//检测错误配置(数据格式错误)
		qs.setConfigPath("config1.properties");
		qs.init();
		FileInputStream is = new FileInputStream("fm.log");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line,oldLine = null;
		int lineNum1=0,lineNum2=0;
		while((line = br.readLine()) != null){
			oldLine = line;
			lineNum1++;
		}
		br.close();
		isr.close();
		is.close();
		String massage = oldLine.substring(20, oldLine.length());
		assertEquals("ERROR INVAILID INPUT FORMAT", massage);

		//测试配置错误(数据文件名错误)
		qs.setConfigPath("config2.properties");
		qs.init();
		is = new FileInputStream("fm.log");
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		lineNum1=0;
		while((line = br.readLine()) != null){
			oldLine = line;
			lineNum1++;
		}
		br.close();
		isr.close();
		is.close();
		massage = oldLine.substring(20, oldLine.length());
		assertEquals("ERROR FILE NOT FOUND", massage);
		
		//检测正确配置
		qs.setConfigPath("config.properties"); 
		qs.init();
		FileInputStream is1 = new FileInputStream("fm.log");
		InputStreamReader isr1 = new InputStreamReader(is1);
		BufferedReader br1 = new BufferedReader(isr1);
		while((line = br1.readLine()) != null){
			//oldLine = line;
			lineNum2++;
		}
		br1.close();
		//System.out.println(lineNum1+" "+lineNum2);
		assertEquals(lineNum1, lineNum2);
		
		

	}

	
	/*
	@Test
	public void testRun() {
		QueryServer qs = new QueryServer();
		qs.run();
	}*/
	

}
