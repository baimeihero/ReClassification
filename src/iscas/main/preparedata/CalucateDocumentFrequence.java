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

public class CalucateDocumentFrequence {
	private final static String path = Constants.OutPutPath;
	private static String WordFrequence;
	private static String DocumnetFrequence = "DF";

	public static void main(String[] args) {

		if (args == null || args.length == 0) {
			System.out.println("Please input data file name!!");
			return;
		}
		WordFrequence = args[0];
		if (!WordFrequence.startsWith("WF_")) {
			System.out.println("Please input word frequence name that start with \"WF_\"!!");
			return;
		}
		DocumnetFrequence += "_" + WordFrequence.substring(WordFrequence.indexOf("_") + 1);
		statisticsDocumnetFrequence();
	}

	public static void statisticsDocumnetFrequence() {
		List<String> records = readText(path+WordFrequence);
		HashMap<String, Integer> map = new HashMap<>();
		int count = 1;
		for (String record : records) {
			if (count % 100 == 0) {
				System.out.println(count + "/" + records.size());
			}
			if (record.trim().length() == 0 || record.indexOf(";") == -1)
				continue;
			List<String> temps = Arrays.asList(record.split(";"));
			for (int i = 1; i < temps.size(); i++) {
				String pair = temps.get(i);
				if (pair.indexOf(":") == -1)
					continue;
				String pairs[] = pair.split(":");
				String word = pairs[0];
				if (word.trim().length() == 0)
					continue;
				if (map.containsKey(word)) {
					map.put(word, map.get(word) + 1);
				} else {
					map.put(word, 1);
				}
			}

		}
		String contents = "";
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			contents += ";" + key + ":" + map.get(key);
		}
		contents = contents.substring(1);
		createTxt(path, DocumnetFrequence, contents);
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
			}else{
				System.out.println("FIle not found");
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
