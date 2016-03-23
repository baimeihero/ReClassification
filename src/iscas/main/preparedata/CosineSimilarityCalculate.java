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

public class CosineSimilarityCalculate {

	private final static String path = Constants.OutPutPath;
	private static String CosineSimilarity = "CSSimilarity_";
	private static String WordFrequence = "WF_";
	private static String DocumnetFrequence = "DF_";
	public static HashMap<String, HashMap<String, Integer>> Files = null;
	public static HashMap<String, Integer> documments = null;
	public static List<String> AllWords = new ArrayList<String>();
	public static List<String> md5s = new ArrayList<String>();
	public static HashMap<String, HashMap<Integer, Double>> vectors = new HashMap<>();
	public static HashMap<String, List<Integer>> wordIndex = new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args == null || args.length == 0) {
			System.out.println("Please input data file name!!");
			return;
		}
		String filename = args[0];
		if (filename.trim().length() == 0) {
			System.out.println("Please input word frequence name t!");
			return;
		}
		WordFrequence += filename;
		DocumnetFrequence += filename;
		CosineSimilarity += filename;
		calculateVector();
	}

	public static void calculateVector() {
		if (Files == null) {
			Files = getWordFrequence();
			documments = getDocumnetFrequence();
			Iterator<String> keys = Files.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next().trim();
				if (key.trim().length() == 0)
					continue;
				md5s.add(AllWords.size(), key);
			}
			Iterator<String> keys1 = documments.keySet().iterator();
			while (keys1.hasNext()) {
				String key = keys1.next().trim();
				if (key.trim().length() == 0)
					continue;
				AllWords.add(AllWords.size(), key);
			}
			System.out.println("================Init Finished==================");
		}
		Iterator<String> md5s = Files.keySet().iterator();
		int count = 1;
		while (md5s.hasNext()) {
			if (count % 100 == 0)
				System.out.println("calculateVector:" + count);
			String md5 = md5s.next();
			HashMap<String, Integer> file = Files.get(md5);
			HashMap<Integer, Double> vector = calcalatorTFIDF(md5, file);
			vectors.put(md5, vector);
			count++;
		}
		System.out.println("===============Calculate Vector Done================");
		calculateScore();	

	}

	public static HashMap<Integer, Double> calcalatorTFIDF(String md5, HashMap<String, Integer> file) {
		HashMap<Integer, Double> vector = new HashMap<>();

		Iterator<String> keys = file.keySet().iterator();
		int total = 0;
		List<Integer> indexes = new ArrayList<>();
		while (keys.hasNext()) {
			String word = keys.next();
			int frequence = file.get(word);
			total += frequence;
			int index = AllWords.indexOf(word);
			double tf = (double) frequence;
			double idf = (double) Math.log((double) Files.size() / documments.get(word));
			indexes.add(indexes.size(), index);
			vector.put(index, (double) tf / total * idf);
		}
		wordIndex.put(md5, indexes);
		return vector;
	}

	public static void calculateScore() {
		for (int i = 0; i < md5s.size() - 1; i++) {
			String contents = md5s.get(i);
			System.out.println("CosineSimilarity:" + i);
			for (int j = i + 1; j < md5s.size(); j++) {
				double score = cosineSimilarity(md5s.get(i), md5s.get(j));
				score = (double) Math.round(score * 1000) / 1000;
				if (score == 0)
					continue;
				contents += ";" + md5s.get(j) + ":" + score;
			}
			if (contents.equals(md5s.get(i)))
				continue;
			contents += "\r\n";
			appendTxt(path, CosineSimilarity, contents);
		}
		System.out.println("===============CosineSimilarityCalculate Done================");
	}

	public static double cosineSimilarity(String md51, String md52) {
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
		double magnitude2 = 0.0;
		double cosineSimilarity = 0.0;

		List<Integer> indexes1 = wordIndex.get(md51);
		List<Integer> indexes2 = wordIndex.get(md52);
		HashMap<Integer, Double> vector1 = vectors.get(md51);
		HashMap<Integer, Double> vector2 = vectors.get(md52);
		if (indexes1.isEmpty() || indexes2.isEmpty()) {
			return 0;
		}
		List<Integer> comIndexes = new ArrayList<>();
		for (int index1 : indexes1) {
			if (indexes2.contains(index1))
				comIndexes.add(index1);
		}
		if (comIndexes.isEmpty())
			return 0;

		for (int index : indexes1) {
			magnitude1 += Math.pow(vector1.get(index), 2);
		}
		for (int index : indexes2) {
			magnitude2 += Math.pow(vector2.get(index), 2);
		}
		for (int index : comIndexes) {
			dotProduct += vector1.get(index) * vector2.get(index);
		}
		magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
		magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

		if (magnitude1 != 0.0 | magnitude2 != 0.0) {
			cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		} else {
			return 0.0;
		}
		return cosineSimilarity;
	}

	public static HashMap<String, HashMap<String, Integer>> getWordFrequence() {
		List<String> records = readText(path + WordFrequence);
		HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
		int count = 1;
		for (String record : records) {
			if (record.trim().length() == 0 || record.indexOf(";") == -1)
				continue;

			List<String> temps = Arrays.asList(record.split(";"));
			String md5 = temps.get(0);
			HashMap<String, Integer> words = new HashMap<>();
			for (int i = 1; i < temps.size(); i++) {
				String pair = temps.get(i);
				if (pair.indexOf(":") == -1)
					continue;
				String pairs[] = pair.split(":");
				String word = pairs[0];
				if (word.trim().length() == 0)
					continue;
				int frequence = Integer.parseInt(pairs[1]);
				if (!words.containsKey(word)) {
					words.put(word, frequence);
				}
			}
			map.put(md5, words);
		}
		return map;
	}

	public static HashMap<String, Integer> getDocumnetFrequence() {
		List<String> records = readText(path + DocumnetFrequence);
		HashMap<String, Integer> map = new HashMap<>();
		int count = 1;
		for (String record : records) {
			if (record.trim().length() == 0 || record.indexOf(";") == -1)
				continue;
			List<String> temps = Arrays.asList(record.split(";"));
			for (String pair : temps) {
				if (pair.indexOf(":") == -1)
					continue;
				String pairs[] = pair.split(":");
				String word = pairs[0];
				if (word.trim().length() == 0)
					continue;
				int frequence = Integer.parseInt(pairs[1]);
				if (!map.containsKey(word)) {
					map.put(word, frequence);
				}
			}
		}
		return map;
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
			} else {
				System.out.println(filePathAndName + " is not exists");
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
