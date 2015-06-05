package queryserver;

import CM.ConfigProperties;
import PerMinute.PMPerMinute;

import com.manager.failure.FailureManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import src.com.team8.License.License;
/**
 *
 * @author chenqiyuan 
 */

public class QueryServer implements Runnable{

    /**
     * @param args the command line arguments
     */
    
    
    private static void load()
    {
        File file;
        
        QueryServer.dataMtoT = new HashMap<String, String>();
        QueryServer.dataTtoM = new HashMap<String,Vector<String>>();
        
        try {
        	file = new File(config.getValue("DATA_PATH"));
        	
        	if(!file.exists()){
        		FailureManager.logError("FILE NOT FOUND");
        		return;
        	}
        	dataLastTime = file.lastModified();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tmpString = null;
            while ((tmpString = reader.readLine()) != null){
                //System.out.print(tmpString + "\n");
                String[] tmpStr = tmpString.split(":");
                if (tmpStr.length != 2){
                    FailureManager.logError("INVAILID INPUT FORMAT");
                    return;
                }
                dataMtoT.put(tmpStr[0], tmpStr[1]);
                
                
                if(dataTtoM.containsKey(tmpStr[1]))
                {
                	Vector<String> members;
                	members = dataTtoM.get(tmpStr[1]);
                	members.add(tmpStr[0]);
                	dataTtoM.put(tmpStr[1], members);                	
                }
                else
                {
                	Vector<String> members = new Vector<String>();
                	members.add(tmpStr[0]);
                	dataTtoM.put(tmpStr[1],members);
                }
                
                //System.out.println(tmpStr[0] + " " + tmpStr[1]);
            }
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QueryServer.class.getName()).log(Level.SEVERE, null, ex);            
            FailureManager.logError("FILE NOT FOUND");
        } catch (IOException ex) {
            Logger.getLogger(QueryServer.class.getName()).log(Level.SEVERE, null, ex);
            FailureManager.logError("INPUT EXCEPTION");
        }  
    }
    
    public static void readConfig() throws IOException
    {
        config = new ConfigProperties(configPath);
        File  file = new File(configPath);
        configLastTime = file.lastModified();
    }
    
    
    public static void init() throws IOException{
        readConfig();
        currentConfig = config.getAllValue();
        try {
			FailureManager.resetOutputFile(config.getValue("FM_PATH"));
		} catch (Exception e) {
			//System.out.println(1);
			e.printStackTrace();
		}
        pm = new PerMinute.PMPerMinute(config.getValue("PM_PATH"));
        pm.setIntervalTime(Long.parseLong(config.getValue("PM_TIME")));
        license = new License(Integer.parseInt(config.getValue("LICENSE_NUM")));
        serverType = config.getValue("SERVER_TYPE");
        load();
    }
    
    public static void runServer(){
        FailureManager.logInfo("System start : query "+serverType);
        pm.start();
        while (true){
        	if(serverType.equals("MEMBER"))
        	{
	            System.out.print("Please input the name below (Enter 'quit' to quit)\n");
	            String name = new Scanner(System.in).next().toString();
	            if (name.equals("quit"))
	            {
	            	stop = true;
	                break;
	                
	            }
	            pm.addIndex("Request", 1);
	            if (license.inLicense()){
	                FailureManager.logInfo("Query provided with the name : " + name);
	                pm.addIndex("RequestProvided", 1);
	                String team = dataMtoT.get(name);
	                if (team != null){
	                    System.out.print("Student " + name + " is in Team " + team + "\n");
	                } else {
	                    System.out.print("Student " + name + " cannot be found\n");
	                }
	                
	            } else {
	                FailureManager.logInfo("Query rejected with the name : " + name);
	                pm.addIndex("RequestRejected", 1);
	                System.out.println("Query Reject : Out of license limit.");
	            }
        	}
        	else
        	if(serverType.equals("TEAM"))
        	{
        		System.out.print("Please input the team below (Enter 'quit' to quit)\n");
                String team = new Scanner(System.in).next().toString();
                if (team.equals("quit"))
                {
                	stop = true;
                    break;
                }
                pm.addIndex("Request", 1);
                if (license.inLicense()){
                    FailureManager.logInfo("Query provided with the team : " + team);
                    pm.addIndex("RequestProvided", 1);
                    Vector<String> names = dataTtoM.get(team);
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
        	else
        	{
        		FailureManager.logError("Invalid service parameter. ");
        		stop = true;
        		break;
        	}
        }
        pm.stop();
        System.out.print("Goodbye.\n");
        FailureManager.logInfo("System shut down");
    } 
    
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run(){
    	while(!stop)
    	{
    		File file = new File(configPath);
    		if(file.lastModified() != configLastTime)
    		{
    			try {
					readConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    			if( !config.getValue("DATA_PATH").equals(currentConfig.get("DATA_PATH")))
    			{
    				load();
    				FailureManager.logInfo("Data changes.");
    			}
    			if(!config.getValue("FM_PATH").equals(currentConfig.get("FM_PATH")))
    			{
    				FailureManager.resetOutputFile(config.getValue("FM_PATH"));
    				FailureManager.logInfo("Fm path changes to "+config.getValue("FM_PATH"));
    			}
    			
    			if(!config.getValue("PM_PATH").equals(currentConfig.get("PM_PATH")))
    			{
    				try {
						pm.setPath(config.getValue("PM_PATH"));
						FailureManager.logInfo("Pm path changes to "+config.getValue("PM_PATH"));
					} catch (IOException e) {
						e.printStackTrace();
					}
    			}
    			
    			if(!config.getValue("LICENSE_NUM").equals(currentConfig.get("LICENSE_NUM")))
    			{
    				license = new License(Integer.parseInt(config.getValue("LICENSE_NUM")));
    				FailureManager.logInfo("License number changes to "+config.getValue("LICENSE_NUM"));
    			}
    			
    			if(!config.getValue("PM_TIME").equals(currentConfig.get("PM_TIME")))
    			{
    				pm.setIntervalTime(Long.parseLong(config.getValue("PM_TIME")));
    				FailureManager.logInfo("Pm time changes to "+config.getValue("PM_TIME"));
    			}
    			
    			if(!config.getValue("SERVER_TYPE").equals(currentConfig.get("SERVER_TYPE")))
    			{
    				serverType = config.getValue("SERVER_TYPE");
    				FailureManager.logInfo("Service type changes to "+config.getValue("SERVER_TYPE"));
    			}
    			
    			
    			configLastTime = file.lastModified();
    			currentConfig = config.getAllValue();
    		}
    		
    		file = new File(config.getValue("DATA_PATH"));
    		if(file.lastModified() != dataLastTime)
    		{
    			load();
				FailureManager.logInfo("Data changes.");
    		}	
    	}
    	
    	System.out.println("stoped");
    	
    }
    
    
    public static void main(String[] args){
    	
    	QueryServer qs = new QueryServer();
    	thread = new Thread(qs);
        try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
        if (dataMtoT != null)
        {
        	thread.start();//开始监测配置是否发生变化
            runServer();
            
        }
        
    }
    
    public static void setconfigPath(String string) {
		configPath=string;
		// TODO Auto-generated method stub
		
	}
	public static void setstop(boolean string) {
		stop=string;
		// TODO Auto-generated method stub
		
	}
    
	private static String configPath = "config.properties";
    private static License license;
    private static PMPerMinute pm;
    private static ConfigProperties config = null;
    private static HashMap<String, String> dataMtoT;
    private static HashMap<String,Vector<String>> dataTtoM;
    private static String serverType;
    private static Map<String,String> currentConfig;
    private static Thread thread;
    private static boolean stop = false;
    private static long configLastTime;
    private static long dataLastTime;
	
}
