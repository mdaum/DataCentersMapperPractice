import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONPractice { //practicing mapper parsing here so I don't have to test that on cluster
	static File f;
	public static void main(String[]args) throws IOException{
		f = new File("blobsTop500PC.txt");
		HeroTimes();
	}
	public static void HeroTimes() throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(f));
		String line = r.readLine();
	}
}
