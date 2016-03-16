package iscas.main.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import iscas.util.POIUtil;

public class TestTemplate {
	private static final String FilePath = "F:\\01-38-PK_tables-figures.xls";

	public static void main(String[] args) {

	}

	public static void test() {
		HSSFWorkbook book = POIUtil.openSpreadsheet(FilePath);
		HSSFSheet sheet = book.getSheetAt(0);
		for (int i = 0; i <= sheet.getLastRowNum() ; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;

			}
		}

	}
}
