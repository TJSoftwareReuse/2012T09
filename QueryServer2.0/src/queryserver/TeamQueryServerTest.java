package queryserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class TeamQueryServerTest {

	@Before
	public void setUp() throws Exception {
	}

	

	@Test
	public void testGetData() throws IOException {
 
		
		TeamQueryServer tq= new TeamQueryServer();
		QueryServer.init();
		tq.qData = QueryServer.getData();
		HashMap<String,Vector<String>> data=tq.getData();
		assertEquals(0, compare(data));
		
     
	}

	//
	public int compare(HashMap<String,Vector<String>> hs)
	{
		int b= 0;
		Vector<String> name1 = hs.get("1");
		if(name1.contains("ÀîÑÇË¹")&&name1.contains("»ÆĞì»¶")&&name1.contains("ĞíÃúœB"));
		else b=1;
		Vector<String> name2 = hs.get("2");
		if(name2.contains("ÍõĞ¦Ó¯")&&name2.contains("ËïÁÕ")&&name2.contains("Ğí½¢")&&name2.contains("ÀîÎ°")&&name2.contains("¹Ø³¿"));
		else b=1;
		Vector<String> name3 = hs.get("3");
		if(name3.contains("ºúÊ¥ÍĞ")&&name3.contains("µË±ùÜç")&&name3.contains("ÕÅŞÈ¸ñ")&&name3.contains("Ö£ÓÂ¿¡")&&name3.contains("´úÃÉ"));
		else b=1;
		Vector<String> name4 = hs.get("4");
		if(name4.contains("Áº¾ºÎÄ")&&name4.contains("ÅíÇï³½")&&name4.contains("ºúÎÄ³¬")&&name4.contains("ÑîË¬"));
		else b=1;
		Vector<String> name5 = hs.get("5");
		if(name5.contains("¹ØÇå³¿")&&name5.contains("Ñî´ºÓê")&&name5.contains("ÖÜÔóºê")&&name5.contains("ÑîÓîì§")&&name5.contains("ÕÅÁ¼"));
		else b=1;
		Vector<String> name6 = hs.get("6");
		if(name6.contains("Ó÷Ë§")&&name6.contains("ÁõÈï")&&name6.contains("ÕÅĞñ³¿")&&name6.contains("Î¤Îá¾³")&&name6.contains("Ê±Óê"));
		else b=1;
		Vector<String> name7 = hs.get("7");
		if(name7.contains("ÒüÇÉ")&&name7.contains("·½³Ì")&&name7.contains("ÕÔÅô")&&name7.contains("»Æê¿")&&name7.contains("ÓÚº½"));
		else b=1;
		Vector<String> name8 = hs.get("8");
		if(name8.contains("¶¡ÓîóÏ")&&name8.contains("ÇñÉĞÕÑ")&&name8.contains("¸ßÒÙ")&&name8.contains("Ñî·á")&&name8.contains("ºØÖ¾Åô"));
		else b=1;
		Vector<String> name9= hs.get("9");
		if(name9.contains("³Âè´")&&name9.contains("ñÒÕñ·½")&&name9.contains("³ÂÆôÔ´")&&name9.contains("ÓÚ×ÔÔ¾"));
		else b=1;
		Vector<String> name = hs.get("10");
		if(name.contains("½ªÄ¾»Û")&&name.contains("ÍõÔ¶")&&name.contains("ÁõÓí¼Î")&&name.contains("Ò¶½£È¨"));
		else b=1;
		
		
		return b;
		
	}
	
	
	
	@Test
	public void testRun() {
		
			TeamQueryServer tqs = new TeamQueryServer();
			try{
				tqs.run();;
			}
			catch(Exception e){
				System.out.println("Run Function failed\n");
			}
			
			System.out.println("Run Function done\n");
		
	}

}