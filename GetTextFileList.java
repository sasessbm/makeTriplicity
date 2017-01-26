package makeTriplicity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GetTextFileList{

	public static ArrayList<String> fileRead(String filePath) {

		ArrayList<String> medicineNameList = new ArrayList<String>(); 
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				medicineNameList.add(line);
				//System.out.println(line);
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
		
		return medicineNameList;
	}

}