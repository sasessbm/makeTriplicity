package makeTriplicity;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws Exception {
		ArrayList<String> medicineNameList = new ArrayList<String>();
		boolean isExsist = false;
		medicineNameList = GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
		for(String medicineNameInList : medicineNameList){
			System.out.println(medicineNameInList);
			if(medicineNameInList.equals("アバスチン")){isExsist = true;}
			//if(medicineNameInList.equals("リオレサール")){isExsist = true;}
		}
		System.out.println(isExsist);
	}

}
