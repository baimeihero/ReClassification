package iscas.main.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import iscas.util.CellUtil;
import iscas.util.POIUtil;

public class IsSeedTest {
	
	private static final String FilePath = "F:\\test\\EES_Schedule.xls_2001-12-13-07-11_62ebf38615cce1e8d7c7fe7220c5eaf2.xls";

	public static void main(String[] args) {
		test();
	}

	public static void test() {
		HSSFWorkbook book = POIUtil.openSpreadsheet(FilePath);
		HSSFSheet sheet = book.getSheetAt(0);
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				CellUtil.isSeed(cell);
			}
		}

	}

	
}
