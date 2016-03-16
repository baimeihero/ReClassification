package iscas.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class CellUtil {

	private static final String DateString[] = "january;jan;february;feb;march;mar;april;apr;may;may;june;jun;july;jul;august;aug;september;sep;october;oct;november;nov;december;dec"
			.split(";");
	private static final String ValueString[] = "na;-;—;".split(";");
	private static final String StopString[] = "total;totals".split(";");

	public static boolean isSeed(HSSFCell cell) {
		boolean result = false;
		if (cell == null) {
			return result;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			// System.out.println(cell.getSheet().getSheetName() + "!(" +
			// (cell.getRowIndex() + 1) + ","
			// + (cell.getColumnIndex() + 1) + ")" + ":" +
			// cell.getCellFormula());

			result = true;
			break;
		case HSSFCell.CELL_TYPE_STRING:
			String value = cell.getStringCellValue();
			if ("N. California".equals(value))
				System.out.println(value);
			if (value != null) {
				value = value.trim().toLowerCase();

				for (String vs : ValueString) {
					if (value.equals(vs)) {
						result = true;
						break;
					}
				}
				List<String> words = Arrays.asList(value.split(" |-"));
				for (String ds : DateString) {
					if (words.contains(ds)) {
						result = true;
						break;
					}
				}
				// System.out.println(cell.getSheet().getSheetName() + "!(" +
				// (cell.getRowIndex() + 1) + ","
				// + (cell.getColumnIndex() + 1) + ")" + ":" +
				// cell.getStringCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:

			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			} else { // 纯数字
				value = String.valueOf(cell.getNumericCellValue());
			}
			// System.out.println(cell.getSheet().getSheetName() + "!(" +
			// (cell.getRowIndex() + 1) + ","
			// + (cell.getColumnIndex() + 1) + ")" + ":" + value);

			result = true;
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			// System.out.println(cell.getSheet().getSheetName() + "!(" +
			// (cell.getRowIndex() + 1) + ","
			// + (cell.getColumnIndex() + 1) + ")" + ":" +
			// cell.getBooleanCellValue());

			result = true;
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			// System.out.println(cell.getSheet().getSheetName() + "!(" +
			// (cell.getRowIndex() + 1) + ","
			// + (cell.getColumnIndex() + 1) + ")" + ":" +
			// cell.getStringCellValue());
			result = true;
			break;
		default:

			break;
		}

		return result;
	}

	public static boolean isLabel(HSSFCell cell) {
		boolean result = false;
		if (cell == null) {
			return result;
		}
		if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING)
			return result;

		String value = cell.getStringCellValue();

		if (value == null)
			return result;

		value = value.trim().toLowerCase();
		boolean temp = false;
		for (String vs : ValueString) {
			if (value.equals(vs)) {
				temp = true;
				break;
			}
		}
		if (temp)
			return result;
		List<String> words = Arrays.asList(value.split(" |-"));
		for (String ds : DateString) {
			if (words.contains(ds)) {
				temp = true;
			}
		}
		if (temp)
			return result;

		return true;
	}

	public static boolean isValiditicalLabel(HSSFCell cell) {
		boolean result = false;
		if (cell == null) {
			return result;
		}
		if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING)
			return result;

		String value = cell.getStringCellValue();

		if (value == null)
			return result;

		value = value.trim().toLowerCase();

		boolean temp = false;

		for (String vs : ValueString) {
			if (value.equals(vs)) {
				temp = true;
				break;
			}
		}
		if (temp)
			return result;

		List<String> words = Arrays.asList(value.split(" |-"));
		for (String ds : DateString) {
			if (words.contains(ds)) {
				temp = true;
			}
		}
		if (temp)
			return result;

		for (String ss : StopString) {
			if (words.contains(ss)) {
				temp = true;
				break;
			}
		}
		if (temp)
			return result;
		return true;

	}

	/**
	 * @Title: getCellValueAsString @Description:
	 *         获取单元格包含的值，并以字符串的形式返回 @param @param cell @param @return @return
	 *         String @throws
	 */
	public static String getCellValueAsString(HSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			try {
				/*
				 * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)
				 * 判断过程中cell
				 * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常
				 */
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					strCell = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
					break;
				}
				strCell = String.valueOf(cell.getCellFormula());

			} catch (IllegalStateException e) {
				strCell = cell.getStringCellValue();
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				strCell = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			} else { // 纯数字
				strCell = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			strCell = cell.getStringCellValue();
			break;
		default:
			strCell = "";
			break;
		}
		return strCell;
	}

}
