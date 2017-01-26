package makeTriplicity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextFileReader{
	
  public static void main(String[] args) throws Exception {
	  fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
  }
  
  public static void fileRead(String filePath) {
	    FileReader fr = null;
	    BufferedReader br = null;
	    try {
	        fr = new FileReader(filePath);
	        br = new BufferedReader(fr);
	 
	        String line;
	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
  
}