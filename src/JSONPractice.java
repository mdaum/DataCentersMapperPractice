import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONPractice { //practicing mapper parsing here so I don't have to test that on cluster
	static File f;
	
	public static void main(String[]args) throws IOException, ParseException{
		f = new File("blobsTop500PC.txt");
		HeroAcc();
		HeroTimes();
	}
	public static void HeroTimes() throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(f));
		int counter =0;
		while(true){
		String line = r.readLine();
		if(line==null)break;
		line=line.replaceAll("u'", "\"");//lots of sanitizing
		line=line.replaceAll("'", "\"");
		line=line.replaceAll("None", "null");
		line=line.replaceAll("True", "true");
		line=line.replaceAll(": 0,", ": 0.0,");
		line=line.replaceAll(": 0}", ": 0.0}");
		line=line.toLowerCase();
		//System.out.println(line);
		JSONParser j = new JSONParser();		
		JSONObject top=null;
		try {
			top = (JSONObject)j.parse(line);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<JSONObject> regions = new ArrayList<JSONObject>();
		regions.add ((JSONObject)top.get("any"));//grab each region
		regions.add((JSONObject)top.get("e"));
		regions.add((JSONObject)top.get("kr"));
		regions.add((JSONObject)top.get("us"));
		for (JSONObject re : regions) {
			if(re==null)continue;
			JSONObject times = (JSONObject)((JSONObject)((JSONObject)re.get("heroes")).get("playtime")).get("competitive");
			if(times==null)continue;
				for (Object d : times.entrySet()) {
					Map.Entry<String, Object> goo = (Map.Entry<String, Object>)d;
					//System.out.println(goo.getKey()+": "+(double)goo.getValue());
				}
				//System.out.println("new region\n\n");
			}
		counter++;
		}
		System.out.println(counter);
	}
	public static void HeroAcc() throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(f));
		int counter =0;
		while(true){
		String line = r.readLine();
		if(line==null)break;
		line=line.replaceAll("u'", "\"");//lots of sanitizing
		line=line.replaceAll("'", "\"");
		line=line.replaceAll("None", "null");
		line=line.replaceAll("True", "true");
		line=line.replaceAll(": 0,", ": 0.0,");
		line=line.replaceAll(": 0}", ": 0.0}");
		line=line.replaceAll(": \"--\"", "0.0");
		line=line.toLowerCase();
		System.out.println(line);
		JSONParser j = new JSONParser();		
		JSONObject top=null;
		try {
			top = (JSONObject)j.parse(line);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<JSONObject> regions = new ArrayList<JSONObject>();
		regions.add ((JSONObject)top.get("any"));//grab each region
		regions.add((JSONObject)top.get("e"));
		regions.add((JSONObject)top.get("kr"));
		regions.add((JSONObject)top.get("us"));
		for (JSONObject re : regions) {
			if(re==null)continue;
			JSONObject stats = (JSONObject)((JSONObject)((JSONObject)re.get("heroes")).get("stats")).get("competitive");
			if(stats==null)continue;
				for (Object d : stats.entrySet()) {
					Map.Entry<String, Object> goo = (Map.Entry<String, Object>)d;
					System.out.print(goo.getKey()+", ");
					try{
					double time = (double)((JSONObject)((JSONObject)goo.getValue()).get("general_stats")).get("time_played");
					double acc = (double)((JSONObject)((JSONObject)goo.getValue()).get("general_stats")).get("weapon_accuracy");
					if(time>1)time=time*time;
					System.out.println(acc);
					}
					catch(NullPointerException e){
						continue;
					}
				}
			System.out.println("new region\n\n");
			}
		counter++;
		return;
		}
		System.out.println(counter);
	}
	}
