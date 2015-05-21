package queryserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import src.com.team8.License.License;

import PerMinute.PMPerMinute;

import com.manager.failure.FailureManager;

public class TeamQueryServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	private static HashMap<String,String> qData;
	public static void main(String[] args) throws IOException {
		
		QueryServer.init();
		qData = QueryServer.getData();
		if(qData != null)
		{
			run();
		}

	}

	/**
	 * 转换数据格式
	 * @return 转换后数据 HashMap<String,Vector<String>>类型
	 */
	public static HashMap<String,Vector<String>> getData()
	{
		HashMap<String,Vector<String>> data = new HashMap<String,Vector<String>>();
		String team;
		Vector<String> names;
		
		for (String name : qData.keySet()) {
			team = qData.get(name);
			if(data.containsKey(team))
			{
				names = data.get(team);
				names.add(name);
				data.put(team, names);
			}
			else
			{
				names = new Vector<String>();
				names.add(name);
				data.put(team, names);
			}
			
		}
		
		return data;
	}
	
	
	public static void run()
	{
		HashMap<String,Vector<String>> data = null;
		PMPerMinute pm = null;
		License license = null;
		data = getData();
		pm = QueryServer.getPm();
		license = QueryServer.getLicense();
		FailureManager.logInfo("System start");
		while (true){
            System.out.print("Please input the team below (Enter 'quit' to quit)\n");
            String team = new Scanner(System.in).next().toString();
            if (team.equals("quit"))
                break;
            pm.addIndex("Request", 1);
            if (license.inLicense()){
                FailureManager.logInfo("Query provided with the team : " + team);
                pm.addIndex("RequestProvided", 1);
                Vector<String> names = data.get(team);
                if (names != null){
                    for (String name : names) {                  	
						System.out.println(name);
					}
                	
                } else {
                    System.out.print("Team " + team + " cannot be found\n");
                }
                
            } else {
                FailureManager.logInfo("Query rejected with the team : " + team);
                pm.addIndex("RequestRejected", 1);
                System.out.println("Query Reject : Out of license limit.");
            }
        }
        pm.stop();
        System.out.print("Goodbye.\n");
        FailureManager.logInfo("System shut down");
		
	}
	
}
