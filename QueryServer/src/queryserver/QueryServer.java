/*************************************************************************
    > File Name: Queryserver.java
    > Author: Iris Chen
    > Mail: o0lazybear0o@gmail.com 
    > Created Time: Sat May 16 13:39:49 2015
 ************************************************************************/
package queryserver;

import CM.ConfigProperties;
import PerMinute.PMPerMinute;
import com.manager.failure.FailureManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.com.team8.License.License;

/**
 *
 * @author Iris
 */



public class QueryServer {

    /**
     * @param args the command line arguments
     */
    
    
    private static void load()
    {
        File file;
        file = new File(config.getValue("DATA_PATH"));
        QueryServer.data = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tmpString = null;
            while ((tmpString = reader.readLine()) != null){
                //System.out.print(tmpString + "\n");
                String[] tmpStr = tmpString.split(":");
                if (tmpStr.length != 2){
                    FailureManager.logError("INVAILID INPUT FORMAT");
                    return;
                }
                data.put(tmpStr[0], tmpStr[1]);               
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QueryServer.class.getName()).log(Level.SEVERE, null, ex);            
            FailureManager.logError("FILE NOT FOUD");
        } catch (IOException ex) {
            Logger.getLogger(QueryServer.class.getName()).log(Level.SEVERE, null, ex);
            FailureManager.logError("INPUT EXCEPTION");
        }  
    }
    
    public static void readConfig() throws IOException
    {
        config = new ConfigProperties("config.properties");
    }
    
    public static void init() throws IOException{
        readConfig();
        FailureManager.resetOutputFile(config.getValue("FM_PATH"));
        pm = new PerMinute.PMPerMinute(config.getValue("PM_PATH"));
        pm.start();
        license = new License(Integer.parseInt(config.getValue("LICENSE_NUM")));
        load();
    }
    
    public static void run(){
        FailureManager.logInfo("System start");
        while (true){
            System.out.print("Please input the name below (Enter '0' to quit)\n");
            String name = new Scanner(System.in).next().toString();
            if (name.equals("0"))
                break;
            pm.addIndex("Request", 1);
            if (license.inLicense()){
                FailureManager.logInfo("Query provided with the name : " + name);
                pm.addIndex("RequestProvided", 1);
                String team = data.get(name);
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
        pm.stop();
        System.out.print("Goodbye.\n");
        FailureManager.logInfo("System shut down");
    }
    
    public static void main(String[] args) throws IOException {
        init();
        if (data != null)
            run();
    }
    
    private static License license;
    private static PMPerMinute pm;
    private static ConfigProperties config = null;
    private static HashMap<String, String> data;
    private static PrintStream out = System.out;
}
