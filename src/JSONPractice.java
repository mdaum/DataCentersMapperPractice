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
		HeroTimes();
	}
	public static void HeroTimes() throws IOException, ParseException{
		BufferedReader r = new BufferedReader(new FileReader(f));
		String line = r.readLine();
		line=line.replaceAll("u'", "\"");//lots of sanitizing
		line=line.replaceAll("'", "\"");
		line=line.replaceAll("None", "null");
		line=line.replaceAll("True", "true");
		line=line.replaceAll(": 0,", ": 0.0,");
		line=line.replaceAll(": 0}", ": 0.0}");
		line=line.toLowerCase();
		System.out.println(line);
		JSONParser j = new JSONParser();		
		JSONObject top =(JSONObject)j.parse(line);
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
					System.out.println(goo.getKey()+": "+(double)goo.getValue());
				}
				System.out.println("new region\n\n");
			}
		}
	}
