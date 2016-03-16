package iscas.main.preparedata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import iscas.bean.Attachment;
import iscas.bean.Labelinfo;
import iscas.main.bean.Table;
import iscas.util.CellUtil;
import iscas.util.DatabaseManager;
import iscas.util.POIUtil;

public class ExtractLabelInfoBasedContents2 {
	private static final String FilePath1 = "F:\\test\\Budget.xls_2000-07-25-06-27_29eb52b0efb76960df41cda82ad4345e.xls";
	private static final String FilePath2 = "F:\\test\\Pick Sheet - Week 17.xls_2001-12-27-09-36_ec8fb590b7c487995a510943ea293aac.xls";
	private static final String FilePath3 = "F:\\test\\imball0112.xls_2001-12-13-12-50_2773df8a1d92befe6f2afa19a01a0916.xls";
	private static final String FilePath4 = "F:\\test\\Oxy.xls_2000-07-11-02-30_311b4564dda8b52b8ee618f4832d6544.xls";
	private static final String FilePath5 = "F:\\test\\Curves.xls_2001-09-07-05-52_7c28fabb7c286626a1d15f9cbf13fd37.xls";
	private static final String FilePath15 = "F:\\CellArray\\Fruit.xls";
	private static final int HorizontalMinLength = 0;
	private static final int VerticalMinLength = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HSSFWorkbook book = POIUtil.openSpreadsheet(FilePath5);
		if (book == null)
			return;

		for (int sheetIndex = 0; sheetIndex < book.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet sheet = book.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName().trim();

			List<HSSFCell> seeds = cellectSeeds(sheet);
			HashMap<HSSFCell, Integer> HLabelSeeds = inferHorizontalLabelsSeeds(seeds);
			HashMap<String, Integer> HprintLabels = extendHorizontalLabels(HLabelSeeds);
			boolean extract=false;
			if (!HprintLabels.isEmpty()){
				printLabels(HprintLabels, sheetName + " Horizontal");
				extract=true;
			}

			HashMap<HSSFCell, Integer> VLabelSeeds = inferVerticalLabelsSeeds(seeds);
			HashMap<String, Integer> VprintLabels = extendVerticalLabels(VLabelSeeds);
			if (!VprintLabels.isEmpty()){
				printLabels(VprintLabels, sheetName + " Vertical");
				extract=true;
			}
			HashMap<String, Integer> map = getLabelByFontStyle(sheet);
			if (!map.isEmpty()) {
				Iterator<String> keys = map.keySet().iterator();
				String result = sheetName + "  Font: ";
				while (keys.hasNext()) {
					String key = keys.next();
					result += key + "(" + map.get(key) + ")" + ";";
				}
				System.out.println(result);
				extract=true;
			} 
			if(!extract)		
				System.out.println("================" + sheetName + ":Nothing================");

			
		}
	}

	public static void extract() {
		List<String> md5s = DatabaseManager.getFilelistCanBeAnalysis();
		for (String md5 : md5s) {
			List<Attachment> attachments = DatabaseManager.getAttachmentByMd5(md5);
			if (attachments == null || attachments.size() == 0)
				continue;
			Attachment attachment = attachments.get(0);
		}
	}

	public static String extract(Attachment attachment) {
		String Label = "";

		String fileMD5 = attachment.getFilemd5();
		System.out.println(attachment.getSavename());
		HSSFWorkbook book = POIUtil.openSpreadsheet("E:\\AllExcel\\" + attachment.getSavename());
		if (book == null)
			return "";
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		for (int sheetIndex = 0; sheetIndex < book.getNumberOfSheets(); sheetIndex++) {

			HSSFSheet sheet = book.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName().trim();
			System.out.println(sheet.getSheetName());
			List<HSSFCell> seeds = cellectSeeds(sheet);

		}

		return Label;
	}

	private static List<HSSFCell> cellectSeeds(HSSFSheet sheet) {
		List<HSSFCell> seeds = new ArrayList<HSSFCell>();
		int rows = sheet.getLastRowNum();
		for (int r = 0; r <= rows; r++) {
			HSSFRow row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			int cells = row.getLastCellNum();
			for (int c = 0; c <= cells; c++) {
				HSSFCell cell = row.getCell(c);

				if (cell == null)
					continue;
				if (CellUtil.isSeed(cell)) {
					seeds.add(seeds.size(), cell);
				}
			}
		}
		return seeds;

	}

	private static HashMap<HSSFCell, Integer> inferHorizontalLabelsSeeds(List<HSSFCell> seeds) {
		HashMap<HSSFCell, Integer> Labels = new HashMap<HSSFCell, Integer>();

		if (seeds == null || seeds.size() == 0)
			return Labels;
		for (HSSFCell cell : seeds) {
			HSSFSheet sheet = cell.getSheet();
			int cindex = cell.getColumnIndex();
			int rindex = cell.getRowIndex();
			for (int i = rindex - 1; i >= 0; i--) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;
				HSSFCell temp = row.getCell(cindex);
				if (CellUtil.isLabel(temp)) {
					if (Labels.containsKey(temp)) {
						Labels.put(temp, Labels.get(temp) + 1);
					} else {
						Labels.put(temp, 1);
					}
					break;
				}
			}
		}
		return Labels;
	}

	private static HashMap<String, Integer> extendHorizontalLabels(HashMap<HSSFCell, Integer> LabelSeeds) {

		HashMap<String, Integer> map = new HashMap<>();
		Iterator<HSSFCell> seeds = LabelSeeds.keySet().iterator();
		while (seeds.hasNext()) {
			HSSFCell cell = seeds.next();
			Integer weight = LabelSeeds.get(cell);
			List<HSSFCell> Labels = getHorizontalLabels(cell);
			for (HSSFCell lable : Labels) {
				if (LabelSeeds.containsKey(lable)) {
					if (weight < LabelSeeds.get(lable))
						weight = LabelSeeds.get(lable);
				}
			}
			List<String> words = new ArrayList<String>();
			for (HSSFCell lable : Labels) {
				if (!words.contains(lable.getStringCellValue()))
					words.add(words.size(), lable.getStringCellValue());
			}
			if (words.size() == 0)
				continue;
			String key = words.get(0);
			for (int i = 1; i < words.size(); i++)
				key += ";" + words.get(i);
			if (key.trim().length() == 0)
				continue;
			if (words.size() <= HorizontalMinLength)
				continue;
			if (map.containsKey(key)) {
				if (map.get(key) < weight)
					map.put(key, weight);
			} else {
				map.put(key, weight);
			}
		}
		return map;
	}

	private static List<HSSFCell> getHorizontalLabels(HSSFCell seed) {
		HashMap<Integer, HSSFCell> map = new HashMap<Integer, HSSFCell>();
		List<HSSFCell> Labels = new ArrayList<HSSFCell>();
		int cIndex = seed.getColumnIndex();
		if (CellUtil.isValiditicalLabel(seed))
			map.put(cIndex, seed);
		HSSFRow row = seed.getRow();
		for (int i = cIndex - 1; i >= 0; i--) {
			HSSFCell temp = row.getCell(i);
			if (CellUtil.isLabel(temp)) {
				if (CellUtil.isValiditicalLabel(temp))
					map.put(i, temp);
				else
					break;
			} else {
				break;
			}
		}

		for (int i = cIndex + 1; i <= row.getLastCellNum(); i++) {
			HSSFCell temp = row.getCell(i);
			if (CellUtil.isLabel(temp)) {
				if (CellUtil.isValiditicalLabel(temp))
					map.put(i, temp);
				else
					break;
			} else {
				break;
			}
		}

		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++)
			Labels.add(Labels.size(), map.get(keys[i]));
		return Labels;
	}

	private static void printLabels(HashMap<String, Integer> map, String SheetName) {
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String result = SheetName + ":";
			result += key + "  ;   ";
			result += "weight:" + map.get(key);
			System.out.println(result);
		}
	}

	// =================当Horizontal Label 返回为空时，采用Vertical Label
	private static HashMap<HSSFCell, Integer> inferVerticalLabelsSeeds(List<HSSFCell> seeds) {
		HashMap<HSSFCell, Integer> Labels = new HashMap<HSSFCell, Integer>();

		if (seeds == null || seeds.size() == 0)
			return Labels;
		for (HSSFCell cell : seeds) {
			HSSFSheet sheet = cell.getSheet();
			int cindex = cell.getColumnIndex();
			int rindex = cell.getRowIndex();
			HSSFRow row = sheet.getRow(rindex);
			for (int i = cindex - 1; i >= 0; i--) {
				HSSFCell temp = row.getCell(i);
				if (CellUtil.isLabel(temp)) {
					if (Labels.containsKey(temp)) {
						Labels.put(temp, Labels.get(temp) + 1);
					} else {
						Labels.put(temp, 1);
					}
					break;
				}
			}
		}
		return Labels;
	}

	private static HashMap<String, Integer> extendVerticalLabels(HashMap<HSSFCell, Integer> LabelSeeds) {

		HashMap<String, Integer> map = new HashMap<>();
		Iterator<HSSFCell> seeds = LabelSeeds.keySet().iterator();
		while (seeds.hasNext()) {
			HSSFCell cell = seeds.next();
			Integer weight = LabelSeeds.get(cell);
			List<HSSFCell> Labels = getVerticalLabels(cell);
			for (HSSFCell lable : Labels) {
				if (LabelSeeds.containsKey(lable)) {
					if (weight < LabelSeeds.get(lable))
						weight = LabelSeeds.get(lable);
				}
			}
			List<String> words = new ArrayList<String>();
			for (HSSFCell lable : Labels) {
				if (!words.contains(lable.getStringCellValue()))
					words.add(words.size(), lable.getStringCellValue());
			}
			if (words.size() == 0)
				continue;
			String key = words.get(0);
			for (int i = 1; i < words.size(); i++)
				key += ";" + words.get(i);
			if (key.trim().length() == 0)
				continue;
			if (words.size() <= VerticalMinLength)
				continue;
			if (map.containsKey(key)) {
				if (map.get(key) < weight)
					map.put(key, weight);
			} else {
				map.put(key, weight);
			}
		}
		return map;
	}

	private static List<HSSFCell> getVerticalLabels(HSSFCell seed) {
		// same columns
		HashMap<Integer, HSSFCell> map = new HashMap<Integer, HSSFCell>();
		List<HSSFCell> Labels = new ArrayList<HSSFCell>();
		int rIndex = seed.getRowIndex();
		int cIndex = seed.getColumnIndex();
		if (CellUtil.isValiditicalLabel(seed))
			map.put(rIndex, seed);
		HSSFSheet sheet = seed.getSheet();

		for (int i = rIndex - 1; i >= 0; i--) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			HSSFCell temp = row.getCell(cIndex);
			int tindex = cIndex;
			while (temp == null) {
				tindex--;
				if (tindex < 0)
					break;
				temp = row.getCell(tindex);
			}
			if (CellUtil.isLabel(temp)) {
				if (CellUtil.isValiditicalLabel(temp))
					map.put(i, temp);
				else
					break;
			} else {
				break;
			}
		}

		for (int i = rIndex + 1; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			HSSFCell temp = row.getCell(cIndex);
			if (CellUtil.isLabel(temp)) {
				if (CellUtil.isValiditicalLabel(temp))
					map.put(i, temp);
				else
					break;
			} else {
				break;
			}
		}

		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++)
			Labels.add(Labels.size(), map.get(keys[i]));
		return Labels;
	}

	private static HashMap<String, Integer> getLabelByFontStyle(HSSFSheet sheet) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;
				if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING)
					continue;
				String accountName = cell.getStringCellValue();
				CellStyle eStyle = cell.getCellStyle();
				if (eStyle == null)
					continue;
				Font eFont = sheet.getWorkbook().getFontAt(eStyle.getFontIndex());
				if (eFont == null)
					continue;
				if (!CellUtil.isValiditicalLabel(cell))
					continue;
				String value = cell.getStringCellValue().trim();
				String temp[] = value.split(" ");
				if (temp.length > 4)
					continue;
				if (eFont.getBold() || eFont.getItalic()) {
					if (map.entrySet().contains(value))
						map.put(value, map.get(value) + 1);
					else
						map.put(value, 1);
				}
			}
		}
		return map;
	}

}
