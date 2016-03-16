package iscas.main.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import iscas.bean.Metrics;
import iscas.bean.Sheetname;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;

/**
 * @author baimei
 *
 */
public class CalculateBasedSheetName {
	public static HashMap<String, List<String>> SpreadSheets = new HashMap<String, List<String>>();
	public static List<String> md5s = new ArrayList<String>();
	private final static String OutputPath = "D:\\GitHub\\ReClassification\\src\\iscas\\data\\";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		importIntoDatabase();
		System.exit(0);
	}

	/**
	 * @author Baimei
	 * @since 2016年3月9日
	 * @category Similarity=|Comment sheets|/(1+min(|file1 sheets|+|file2
	 *           sheets|))
	 * @throws 无
	 *             void
	 */
	public static void calculate() {

		List<Sheetname> SheetNames = DatabaseManager.getAllSheetnames();
		int process = 0;
		for (Sheetname SheetName : SheetNames) {
			System.out.println(process++);
			String fileMD5 = SheetName.getFileMd5();
			String word = SheetName.getSheetName();
			if (!md5s.contains(fileMD5))
				md5s.add(fileMD5);
			if (SpreadSheets.keySet().contains(fileMD5)) {
				List<String> words = SpreadSheets.get(fileMD5);
				if (!words.contains(word)) {
					words.add(word);
				}
			} else {
				List<String> words = new ArrayList<String>();
				words.add(word);
				SpreadSheets.put(fileMD5, words);
			}
		}
		// ===============================================
		System.out.println("==================初始化完成==============================");

		for (int i = 0; i < md5s.size() - 1; i++) {
			String content = "";
			for (int j = i + 1; j < md5s.size(); j++) {
				System.out.println(j + "/" + i);
				String md51 = md5s.get(i);
				String md52 = md5s.get(j);
				List<String> words1 = SpreadSheets.get(md51);
				List<String> words2 = SpreadSheets.get(md52);
				int count = 0;
				for (String word : words1) {
					if (words2.contains(word))
						count++;
				}
				if (count == 0)
					continue;
				double weight =(float)count / (1 + Math.min(words1.size(), words2.size()));
				content += md51 + "-" + md52 + "=" + weight + "\r\n";
			}
			if (content != "")
				FileManager.appendTxt(OutputPath, "SheetNameBasedWeight.txt", content);
		}

	}
	
	public static void importIntoDatabase(){
		List<String> list = new ArrayList<String>();

		FileInputStream fs;
		try {
			File file = new File(OutputPath+"SheetNameBasedWeight.txt");
			if (file.exists()) {
				fs = new FileInputStream(OutputPath+"SheetNameBasedWeight.txt");
				InputStreamReader isr;
				isr = new InputStreamReader(fs);
				BufferedReader br = new BufferedReader(isr);
				String data = "";
				int i = 0;
				while ((data = br.readLine()) != null) {
			       if(data.indexOf("=")==-1)
			    	   continue;
			       System.out.println(i++);
			       String temp[]=data.split("=");
		          Metrics m=new Metrics();
		          m.setFilePair(temp[0]);
		          m.setMtricBasedSheetName(Float.valueOf(temp[1]));
			       DatabaseManager.saveOrUpdateMetrics(m);
				}
				fs.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
