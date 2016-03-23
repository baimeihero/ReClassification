package iscas.main.preparedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import iscas.util.Constants;
import iscas.util.FileManager;
import javassist.compiler.ast.Symbol;

public class WordFrequenceCalculate {
	private final static String path = Constants.OutPutPath;
	private static String fileName = "StringbasedRefinedLabelInfo.txt";
	private  static String WordFrequence = "WF";


	public static void main(String[] args) {

		if (args == null || args.length == 0) {
			System.out.println("Please input data file name!!");
			return;
		}
		fileName=args[0];
		if (!fileName.endsWith(".txt")) {
			System.out.println("Please input word frequence name that start with \"WF_\"!!");
			return;
		}
		WordFrequence+="_"+fileName;

		statisticsWordsFrequence();

	}

	public static void statisticsWordsFrequence() {
		List<String> records = readText(path + fileName);
		HashMap<String, HashMap<String, Integer>> map = new HashMap<>();

		for (String record : records) {
			if (record.trim().length() == 0 || record.indexOf(";") == -1)
				continue;
			System.out.println(record);
			String pairs[] = record.split(";");
			String fileName = pairs[0];
			String md5 = pairs[1];
			record = pairs[2];

			List<String> temps = Arrays.asList(record.split("\\W+"));
			for (String word : temps) {
				word = word.trim();
				if (word.length() == 0)
					continue;
				if (map.containsKey(md5)) {
					HashMap<String, Integer> words = map.get(md5);
					if (words.containsKey(word)) {
						words.put(word, words.get(word) + 1);
					} else {
						words.put(word, 1);
					}
				} else {
					HashMap<String, Integer> words = new HashMap<>();
					words.put(word, 1);
					map.put(md5, words);
				}
			}
		}

		Iterator<String> mkeys = map.keySet().iterator();
		int i = 1;
		String content = "";
		while (mkeys.hasNext()) {

			String md5 = mkeys.next();
			HashMap<String, Integer> words = map.get(md5);
			Iterator<String> keys = words.keySet().iterator();
			content += md5;
			while (keys.hasNext()) {
				String key = keys.next();
				content += ";" + key + ":" + words.get(key);
			}
			content += "\r\n";
			if (i % 1000 == 0) {
				appendTxt(path, WordFrequence, content);
				content = "";
			}
		}
		if (content != "")
			appendTxt(path, WordFrequence, content);
	}

	

	private static void createTxt(String path, String fileName, String content) {
	
		try {

			File bpath = new File(path);

			if (!bpath.exists())
				bpath.mkdirs();

			File writename = new File(path + fileName);
			if (!writename.exists())
				writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void appendTxt(String path, String fileName, String content) {

		try {

			File bpath = new File(path);

			if (!bpath.exists())
				bpath.mkdirs();

			File writename = new File(path + fileName);
			if (!writename.exists())
				writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static List<String> readText(String filePathAndName) {
		List<String> list = new ArrayList<String>();

		FileInputStream fs;
		try {
			File file = new File(filePathAndName);
			if (file.exists()) {
				fs = new FileInputStream(filePathAndName);
				InputStreamReader isr;
				isr = new InputStreamReader(fs);
				BufferedReader br = new BufferedReader(isr);
				String data = "";
				int i = 0;
				while ((data = br.readLine()) != null) {

					list.add(i++, data);
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

		return list;

	}

}
