package iscas.main.preparedata;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import iscas.bean.Attachment;
import iscas.main.bean.Table;
import iscas.util.Constants;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;
import iscas.util.POIUtil;
import iscas.util.Stemmer;

public class ExtractLabelInfoBasedonLocation {
	private final static String path=Constants.OutPutPath;
	private final static String fileName="StringbasedRefinedLabelInfo.txt";

	private static final String FilePath = "F:\\CellArray\\MrT.xls";
	private static final String FilePath1 = "F:\\test\\Budget.xls_2000-07-25-06-27_29eb52b0efb76960df41cda82ad4345e.xls";
	private static final String FilePath2 = "F:\\test\\Pick Sheet - Week 17.xls_2001-12-27-09-36_ec8fb590b7c487995a510943ea293aac.xls";
	private static final String FilePath3 = "F:\\test\\imball0112.xls_2001-12-13-12-50_2773df8a1d92befe6f2afa19a01a0916.xls";
	private static final String FilePath4 = "F:\\test\\Oxy.xls_2000-07-11-02-30_311b4564dda8b52b8ee618f4832d6544.xls";
	private static final String FilePath5 = "F:\\test\\Curves.xls_2001-09-07-05-52_7c28fabb7c286626a1d15f9cbf13fd37.xls";
	private static final String FilePath6 = "F:\\test\\Log as of 8 dec 00.xls_2000-12-12-01-27_692461444c469fea3b5e3df6ec70088c.xls";
	private static final String FilePath7 = "F:\\test\\EB_enron.xls_2000-02-25-02-12_403c38322522da2522131138dfa269b9.xls";
	private static final String FilePath8 = "F:\\test\\1-29act.xls_2002-01-30-21-35_3ca81109262011117dbafadf3447acdf.xls";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			extractLabelInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
		// MarkTableInSheet(tables);
	}

	public static void test() {
		HSSFWorkbook workbook = POIUtil.openSpreadsheet(FilePath8);

		if (workbook == null) {
			System.out.println(FilePath8 + "is not exist");
			return;
		}
		String md5 = "";
		String content = "";
		for (int i = 0; i < 1 && i < workbook.getNumberOfSheets(); i++) {
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Table> tables = recognizeTables(sheet, FilePath8, i);
			String labels = extractLabelInfo(tables);
		}
	}

	public static void extractLabelInfo() throws IOException {
		List<String> md5s = DatabaseManager.getFilelistCanBeAnalysis();

		String content = "";
		for (int count = 0; count < md5s.size(); count++) {
			System.out.println(count);
			String md5 = md5s.get(count);
			Attachment attachment = DatabaseManager.getAttachmentByMd5(md5).get(0);

			HSSFWorkbook workbook = POIUtil.openSpreadsheet("E:\\AllExcel\\" + attachment.getSavename());
			content += attachment.getSavename() + ";" + md5 + ";";
			if (workbook == null) {
				System.out.println(attachment.getSavename() + "is not exist");
				continue;
			}

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				List<Table> tables = recognizeTables(sheet, attachment.getSavename(), i);
				String labels = extractLabelInfo(tables);
				content += " " + labels;
			}
			content += "\r\n";
			if (count % 100 == 0 || count == md5s.size() - 1) {
				
				FileManager.appendTxt(path, fileName, content);
				content = "";
			}
			workbook.close();
		}

	}

	public static List<Table> recognizeTables(HSSFSheet sheet, String fileMd5, int sheetIndex) {

		List<Table> tables = new ArrayList<Table>();
		HashMap<String, Integer> stateMap = initStateMap(sheet);
		List<List<String>> cellArrays = recognizeCellArray(sheet, stateMap);
		for (List<String> cellArray : cellArrays) {
			Table table = new Table();
			table.setFileName(fileMd5);
			table.setSheetIndex(sheetIndex);
			for (String cellIndex : cellArray) {
				String temps[] = cellIndex.split("_");
				int row = Integer.parseInt(temps[0]);
				int col = Integer.parseInt(temps[1]);
				HSSFCell cell = sheet.getRow(row).getCell(col);
				updateTable(table, cell);
			}
			if (!tables.contains(table))
				tables.add(table);
		}
		// System.out.println(sheet.getSheetName() + ":" +
		// extractLabelInfo(tables));

		return tables;
	}

	private static void updateTable(Table table, HSSFCell cell) {
		if (cell == null)
			return;
		int row = cell.getRowIndex();
		int col = cell.getColumnIndex();

		if (table.getLeftTopCell() == null && table.getRightBottomCell() == null) {
			table.setLeftTopCell(cell);
			table.setRightBottomCell(cell);
		} else {
			int tr = table.getLeftTopCell().getRowIndex();
			int tc = table.getLeftTopCell().getColumnIndex();
			if (table.getLeftTopCell().getRowIndex() >= row) {
				tr = row;
			}
			if (table.getLeftTopCell().getColumnIndex() >= col) {
				tc = col;
			}
			HSSFSheet sheet = table.getLeftTopCell().getSheet();
			HSSFCell tcell = sheet.getRow(tr).getCell(tc);
			if (tcell == null)
				tcell = sheet.getRow(tr).createCell(tc);
			table.setLeftTopCell(tcell);

			int br = table.getRightBottomCell().getRowIndex();
			int bc = table.getRightBottomCell().getColumnIndex();
			if (table.getRightBottomCell().getRowIndex() <= row) {
				br = row;
			}
			if (table.getRightBottomCell().getColumnIndex() <= col) {
				bc = col;
			}
			HSSFCell bcell = sheet.getRow(br).getCell(bc);
			if (bcell == null)
				bcell = sheet.getRow(br).createCell(bc);
			table.setRightBottomCell(bcell);
		}

	}

	public static void MarkTableInSheet(List<Table> tables) {
		if (tables == null || tables.size() == 0)
			return;
		HashMap<String, HSSFWorkbook> books = new HashMap<String, HSSFWorkbook>();
		for (Table table : tables) {
			if (table == null)
				continue;
			String pathAndFileName = table.getFileName();
			HSSFWorkbook workbook = null;
			if (books.containsKey(pathAndFileName))
				workbook = books.get(pathAndFileName);
			else {
				workbook = POIUtil.openSpreadsheet(pathAndFileName);
				books.put(pathAndFileName, workbook);
			}
			if (workbook == null)
				return;
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			if (table.getSheetIndex() == -1)
				continue;
			HSSFSheet sheet = workbook.getSheetAt(table.getSheetIndex());
			int cs = table.getLeftTopCell().getColumnIndex();
			int rs = table.getLeftTopCell().getRowIndex();
			int ce = table.getRightBottomCell().getColumnIndex();
			int re = table.getRightBottomCell().getRowIndex();
			if (cs == -1 || rs == -1 || re == -1 || ce == -1)
				continue;

			for (int i = rs; i <= re; i++) {
				for (int j = cs; j <= ce; j++) {
					style.setFillForegroundColor(HSSFColor.GREEN.index);
					style.setTopBorderColor(HSSFColor.RED.index);
					HSSFRow row = sheet.getRow(i);
					if (row == null)
						continue;
					HSSFCell cell = row.getCell(j);

					if (cell == null)
						cell = row.createCell(j);

					cell.setCellStyle(style);
				}

			}
		}
		for (String key : books.keySet()) {
			String colorPathandName = key.substring(0, key.lastIndexOf(".xls")) + "_array.xls";
			HSSFWorkbook book = books.get(key);
			POIUtil.saveSpreadsheet(book, colorPathandName);
		}

	}

	/**
	 * @Title: initStateMap @Description:
	 *         使用一个Map标记，电子表格中所有包含数据的单元格，为以后判断是否已经划分做准备 @param @param
	 *         sheet @param @return @return HashMap<String,Integer> @throws
	 */
	private static HashMap<String, Integer> initStateMap(HSSFSheet sheet) {
		HashMap<String, Integer> stateMap = new HashMap<String, Integer>();
		int rows = sheet.getLastRowNum();
		for (int r = 0; r <= rows; r++) {
			HSSFRow row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			int cells = row.getLastCellNum();
			for (int c = 0; c <= cells; c++) {
				HSSFCell cell = row.getCell(c);

				if (isSeed(cell)) {
					stateMap.put(r + "_" + c, 0);
				}

			}

		}

		return stateMap;

	}

	/**
	 * @Title: getCellValueAsString @Description:
	 *         获取单元格包含的值，并以字符串的形式返回 @param @param cell @param @return @return
	 *         String @throws
	 */
	private static boolean isSeed(HSSFCell cell) {

		if (cell == null) {
			return false;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			return true;

		case HSSFCell.CELL_TYPE_STRING:
			return true;

		case HSSFCell.CELL_TYPE_NUMERIC:
			return true;

		case HSSFCell.CELL_TYPE_BOOLEAN:
			return true;

		case HSSFCell.CELL_TYPE_BLANK:
			return false;

		default:
			return false;

		}

	}

	/**
	 * @Title: recognizeCellArray @Description:
	 *         使用一个电子表格，和一个初始状态进行划分，选择一个没有划分的单元格，按上下左右进行扩展 @param @param
	 *         sheet @param @param stateMap @param @return @return List<List
	 *         <String>> @throws
	 */
	private static List<List<String>> recognizeCellArray(HSSFSheet sheet, HashMap<String, Integer> stateMap) {

		List<List<String>> cellArrays = new ArrayList<List<String>>();
		if (stateMap.isEmpty())
			return cellArrays;

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

				if (stateMap.containsKey(r + "_" + c) && stateMap.get(r + "_" + c) == 0) {
					stateMap.put(r + "_" + c, 1);
					List<String> cellArray = recognizeCellArray(sheet, stateMap, cell);
					// if (cellArray.size() > 1)
					cellArrays.add(cellArrays.size(), cellArray);
				}

			}

		}

		return cellArrays;
	}

	/**
	 * @Title: recognizeCellArray @Description:
	 *         以一个单元格为队列初始值，判断临近是否有新的单元格需要加入，知道队列不在添加新的单元格 @param @param
	 *         sheet @param @param stateMap @param @param
	 *         cell @param @return @return List<String> @throws
	 */
	private static List<String> recognizeCellArray(HSSFSheet sheet, HashMap<String, Integer> stateMap, HSSFCell cell) {
		List<String> cellArray = new ArrayList<String>();
		cellArray.add(cellArray.size(), cell.getRowIndex() + "_" + cell.getColumnIndex());
		for (int i = 0; i < cellArray.size(); i++) {
			String cellIndex = cellArray.get(i);
			String temps[] = cellIndex.split("_");
			int row = Integer.parseInt(temps[0]);
			int col = Integer.parseInt(temps[1]);
			expendArray(row, col, cellArray, stateMap);
		}

		return cellArray;
	}

	/**
	 * @Title: expendArray @Description: 以一个单元格为中心，判断临近是否有需要加入的单元格 @param @param
	 *         row @param @param col @param @param cellArray @param @param
	 *         stateMap @return void @throws
	 */

	private static void expendArray(int row, int col, List<String> cellArray, HashMap<String, Integer> stateMap) {

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				String index = (row + i) + "_" + (col + j);
				if (stateMap.containsKey(index) && stateMap.get(index) == 0) {
					cellArray.add(cellArray.size(), index);
					stateMap.put(index, 1);
				}
			}
		}

	}

	public static String extractLabelInfo(List<Table> tables) {
		String result = "";

		for (Table table : tables) {
			result += extractLabelInfo(table);
		}

		return result;
	}

	public static String extractLabelInfo(Table table) {
		// System.out.println(table.toString());
		String result = "";

		if (table.getLeftTopCell() == null || table.getRightBottomCell() == null)
			return result;
		HSSFSheet sheet = table.getLeftTopCell().getSheet();
		int cs = table.getLeftTopCell().getColumnIndex();
		// if (cs == 66)
		// System.out.println();
		int rs = table.getLeftTopCell().getRowIndex();
		int ce = table.getRightBottomCell().getColumnIndex();
		int re = table.getRightBottomCell().getRowIndex();
		int cindex = 0;
		int rcount = 1;
		int ccount = 1;
		for (int i = rs; i <= re; i++) {
			boolean isValiditicalLabel = true;
			String temp = "";
			for (int j = cs; j <= ce; j++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;
				isValiditicalLabel = isValiditicalLabel(cell);

				if (!isValiditicalLabel)
					break;
				temp += " " + getRefinedCellValue(cell);
			}
			if (!isValiditicalLabel) {
				cindex = i;
				if (rcount >= 4)
					break;
			} else {
				result += temp;
			}
			rcount++;
		}
		for (int j = cs; j <= ce && j < cs + 1; j++) {
			boolean isValiditicalLabel = true;
			String temp = "";
			int blinkCount = 0;
			for (int i = cindex; i <= re; i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;
				isValiditicalLabel = isValiditicalLabel(cell);

				if (!isValiditicalLabel)
					break;
				temp += " " + getRefinedCellValue(cell);
			}

			if (!isValiditicalLabel) {
				break;
			} else {
				result += temp;
			}

			ccount++;

		}
		// System.out.println(table.toString());
		// System.out.println(result);
		return result;
	}

	public static boolean isValiditicalLabel(HSSFCell cell) {
		boolean result = false;

		if (!(cell.getCellType() == HSSFCell.CELL_TYPE_STRING || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK))
			return result;

		String value = cell.getStringCellValue();

		if (value == null || value == "")
			return true;

		value = value.trim().toLowerCase();

		List<String> temps = Arrays.asList(value.replaceAll("[0-9]|_|__", " ").replaceAll("[\\W&&[^\\s]]", " ").split("\\W+"));
		List<String> words = new ArrayList<String>();
		for (String word : temps) {
			word = word.trim();
			word = Stemmer.StemWordWithWordNet(word);
			if (word.length() <= 1)
				continue;
			
			if (Constants.MonthStrings.contains(word) || Constants.WeekStrings.contains(word)
					|| Constants.ValueStrings.contains(word))
				continue;

			words.add(words.size(), word);
		}

		if (words.size() == 0)
			return result;
		return true;
	}

	public static String getRefinedCellValue(HSSFCell cell) {
		String result = "";

		if (!(cell.getCellType() == HSSFCell.CELL_TYPE_STRING || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK))
			return result;

		String value = cell.getStringCellValue();

		if (value == null || value == "")
			return "";

		value = value.trim().toLowerCase();

		List<String> temps = Arrays.asList(value.replaceAll("[0-9]|_|__", " ").replaceAll("[\\W&&[^\\s]]", " ").split("\\W+"));
		List<String> words = new ArrayList<String>();
		for (String word : temps) {
			word = word.trim();
			word = Stemmer.StemWordWithWordNet(word);
			if (word.length() <= 1)
				continue;
		
			if (Constants.MonthStrings.contains(word) || Constants.WeekStrings.contains(word)
					|| Constants.StopStrings.contains(word) || Constants.ValueStrings.contains(word)
					|| Constants.stopWord.contains(word))
				continue;

			
			words.add(words.size(), word);
		}

		if (words.size() == 0)
			return result;
		result = words.get(0);
		for (int i = 1; i < words.size(); i++)
			result += " " + words.get(i);
		return result;
	}

}
