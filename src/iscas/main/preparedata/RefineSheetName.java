package iscas.main.preparedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import iscas.bean.Sheetdetail;
import iscas.util.Constants;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;

public class RefineSheetName {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// refineSheetNames();
		createRefineStringBasedonSheeNames();
		System.exit(0);
		;
	}

	public static void refineSheetNames() {
		List<Sheetdetail> sheetNames = DatabaseManager.getAllSheetnames();

		String sheetNameWithSomeMeaning = "";
		String sheetNameWithoutSomeMeaning = "";
		int i = 1;
		for (Sheetdetail sheet : sheetNames) {
			String sname = sheet.getSheetName();
			String rname = sname.toLowerCase().trim();
			String rtname = "";

			List<String> temps = Arrays.asList(rname.replaceAll("[\\W&&[^\\s]]", "").split("\\W+"));
			List<String> words = new ArrayList<String>();
			for (String word : temps) {
				word = word.replaceAll("[0-9]|-|_|__|/|>|<|&|~|\\+|\\$|'|\\(|\\)|\\.|#|,|，|", "").trim();
				if (word.length() == 0)
					continue;
				if (Constants.MonthStrings.contains(word) || Constants.WeekStrings.contains(word)
						|| Constants.StopStrings.contains(word) || Constants.ValueStrings.contains(word)
						|| Constants.stopWord.contains(word))
					continue;

				words.add(words.size(), word);
			}
			for (String word : words)
				rtname += " " + word;

			if (rtname.trim().length() == 0) {
				sheetNameWithoutSomeMeaning += sheet.getFileMd5() + ";" + sname + "\r\n";
			} else {
				sheetNameWithSomeMeaning += sheet.getFileMd5() + ";" + sname + ";" + rtname.trim() + "\r\n";
			}

			i++;
			if (i % 100 == 0)
				System.out.println(i);
		}
		FileManager.createTxt(Constants.OutPutPath, "SheetNamesWithoutMeaningWords.txt", sheetNameWithoutSomeMeaning);
		FileManager.createTxt(Constants.OutPutPath, "SheetNameWithMeaningWords.txt", sheetNameWithSomeMeaning);

	}

	public static void createRefineStringBasedonSheeNames() {
		HashMap<String, List<String>> words = new HashMap<String, List<String>>();
		List<String> records = FileManager.readText(Constants.OutPutPath + "SheetNameWithMeaningWords.txt");
		for (String record : records) {
			if (record == null || record.indexOf(";") == -1)
				continue;
			String temp[] = record.split(";");
			if (words.containsKey(temp[0])) {
				List<String> word = words.get(temp[0]);
				word.add(word.size(), temp[2]);
			} else {
				List<String> word = new ArrayList<String>();
				word.add(word.size(), temp[2]);
				words.put(temp[0], word);
			}
		}
		Iterator<String> keys = words.keySet().iterator();
		String contents = "";
		while (keys.hasNext()) {
			String key = keys.next();
			List<String> word = words.get(key);
			contents += key + ";";
			for (String temp : word) {
				contents += " " + temp;
			}
			contents += "\r\n";
		}
		FileManager.createTxt(Constants.OutPutPath, "StringbasedRefinedSheetName.txt", contents);
	}

}
