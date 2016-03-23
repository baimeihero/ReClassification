package iscas.main.preparedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.Data;

import iscas.bean.Filelist;
import iscas.bean.Metrics;
import iscas.bean.Sheetdetail;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;

public class CalulateSimilarityBasedSheetName extends Thread {
	private static HashMap<String, Integer> IDmap = new HashMap<>();
	private static List<String> md5s = new ArrayList<String>();
	private static HashMap<String, List<String>> SpreadSheets = new HashMap<String, List<String>>();
   private static final String OutPutPath="E:\\ReClassData\\OriginalSheetName\\";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		calculateBasedOnTFITDF();
		System.exit(0);
	}

	public static void calculateBasedCommentSheet() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		List<Sheetdetail> SheetNames = DatabaseManager.getAllSheetnames();
		List<Filelist> fileList = DatabaseManager.getFilelists();
		for(Filelist file:fileList){
		    if(!IDmap.containsKey(file.getFileMd5())){
		    	IDmap.put(file.getFileMd5(), file.getId());
		    }
		}

		for (Sheetdetail SheetName : SheetNames) {
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
		HashMap<Float, String> scores = new HashMap<Float, String>();
		for (int i = 0; i < md5s.size() - 1; i++) {
			scores.clear();
			System.out.println(i);
			for (int j = i + 1; j < md5s.size(); j++) {
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
				float weight = (float) count / (1 + Math.min(words1.size(), words2.size()));
				weight = (float) Math.round(weight * 1000) / 1000;
				if (scores.containsKey(weight)) {
					scores.put(weight, scores.get(weight) + ";" + IDmap.get(md51) + "-" +  IDmap.get(md52));
				} else {
					scores.put(weight, IDmap.get(md51 )+ "-" + IDmap.get(md52));
				}

			}
			Iterator<Float> keys = scores.keySet().iterator();
			while (keys.hasNext()) {
				Float key = keys.next();
				Metrics m = DatabaseManager.getMetricByValue(key);
				if (m == null) {
					m = new Metrics();
					m.setMetricValue(key);
				}
				m.setPairBasedSheetName(scores.get(key));
				DatabaseManager.saveOrUpdateMetrics(m);
			}
		}

		
		System.out.println("========wancheng==========");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df2.format(new Date()));// new Date()为获取当前系统时间
	}
	public static void calculateBasedOnTFITDF(){
		List<Sheetdetail> SheetNames = DatabaseManager.getAllSheetnames();
		List<Filelist> fileList = DatabaseManager.getFilelists();
		for(Filelist file:fileList){
		    if(!IDmap.containsKey(file.getFileMd5())){
		    	IDmap.put(file.getFileMd5(), file.getId());
		    }
		}

		for (Sheetdetail SheetName : SheetNames) {
			String fileMD5 = SheetName.getFileMd5();
			String word = SheetName.getSheetName();		
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
		
		//==============================输出到文件
		Iterator<String> keys = SpreadSheets.keySet().iterator();
		while(keys.hasNext()){
			String key=keys.next();
			List<String> words=SpreadSheets.get(key);
			String contents="";
			for(String word:words){
				contents+=word+" ";
			}
			FileManager.createTxt(OutPutPath, IDmap.get(key)+".txt", contents);
		}
		
	}

}
