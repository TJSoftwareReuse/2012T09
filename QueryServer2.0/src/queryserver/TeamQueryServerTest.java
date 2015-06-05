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
		if(name1.contains("����˹")&&name1.contains("���컶")&&name1.contains("�����B"));
		else b=1;
		Vector<String> name2 = hs.get("2");
		if(name2.contains("��Цӯ")&&name2.contains("����")&&name2.contains("��")&&name2.contains("��ΰ")&&name2.contains("�س�"));
		else b=1;
		Vector<String> name3 = hs.get("3");
		if(name3.contains("��ʥ��")&&name3.contains("�˱���")&&name3.contains("���ȸ�")&&name3.contains("֣�¿�")&&name3.contains("����"));
		else b=1;
		Vector<String> name4 = hs.get("4");
		if(name4.contains("������")&&name4.contains("���ﳽ")&&name4.contains("���ĳ�")&&name4.contains("��ˬ"));
		else b=1;
		Vector<String> name5 = hs.get("5");
		if(name5.contains("���峿")&&name5.contains("���")&&name5.contains("�����")&&name5.contains("�����")&&name5.contains("����"));
		else b=1;
		Vector<String> name6 = hs.get("6");
		if(name6.contains("��˧")&&name6.contains("����")&&name6.contains("����")&&name6.contains("Τ�ᾳ")&&name6.contains("ʱ��"));
		else b=1;
		Vector<String> name7 = hs.get("7");
		if(name7.contains("����")&&name7.contains("����")&&name7.contains("����")&&name7.contains("���")&&name7.contains("�ں�"));
		else b=1;
		Vector<String> name8 = hs.get("8");
		if(name8.contains("������")&&name8.contains("������")&&name8.contains("����")&&name8.contains("���")&&name8.contains("��־��"));
		else b=1;
		Vector<String> name9= hs.get("9");
		if(name9.contains("���")&&name9.contains("����")&&name9.contains("����Դ")&&name9.contains("����Ծ"));
		else b=1;
		Vector<String> name = hs.get("10");
		if(name.contains("��ľ��")&&name.contains("��Զ")&&name.contains("�����")&&name.contains("Ҷ��Ȩ"));
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