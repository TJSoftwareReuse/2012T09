package queryserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.junit.Test;



public class QueryServerTest {

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
	public void testRunServer() throws IOException {
		Thread thread1;

		QueryServer qs = new QueryServer();
		thread1 = new Thread(qs);
		int isError = 0;
		qs.init();
		
        	thread1.start();
            try
            {qs.runServer();}
            catch(Exception e){
			//System.out.println("ReadConfig() failed\n");
			isError = 1;
		}
		
		assertEquals(0, isError);
	}

	@Test
	public void testRun() throws IOException {
		final QueryServer qs = new QueryServer();
		Thread thread1= new Thread(qs);
		//3sºó±äconfig
		new Thread(new Runnable() { 
			public void run() { 
			 
				
			try {
				Thread.sleep(10000);
				 qs.setconfigPath("config1.properties");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			} 
			}).start(); 
		int isError = 0;
		qs.init();
		qs.setstop(false);
		try {thread1.start();}
		catch(Exception e){isError = 1;}
		qs.runServer();
		
		
		assertEquals(0, isError);
	}

	

}
