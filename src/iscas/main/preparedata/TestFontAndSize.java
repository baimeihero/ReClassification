package iscas.main.preparedata;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.CellTypes;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import iscas.util.POIUtil;

public class TestFontAndSize {
	private static final String FilePath = "F:\\01-38-PK_tables-figures.xls";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 text() ;
	}

	public static void text() {
		HSSFWorkbook book = POIUtil.openSpreadsheet(FilePath);
		HSSFSheet sheet = book.getSheetAt(0);
		for (int i = 4; i <= sheet.getLastRowNum()&&i<=5; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null)
					continue;
				if(cell.getCellType()!=HSSFCell.CELL_TYPE_STRING)
					continue;
				String accountName = cell.getStringCellValue();				
				CellStyle eStyle = cell.getCellStyle();
				Font eFont = book.getFontAt(eStyle.getFontIndex());
				
				System.out.println("value:" + accountName);
				System.out.println("eFont.getColor();-" + eFont.getColor());
				System.out.println("eFont.getBoldweight();-" + eFont.getBoldweight());
				System.out.println("eFont.getFontName();-" + eFont.getFontName());
				System.out.println("eFont.getBold();-" + eFont.getBold());
				System.out.println("eFont.getBold();-" + eFont.getBold());
				System.out.println("eFont.getBold();-" + eFont.getItalic());
			}
		}
	}

}
