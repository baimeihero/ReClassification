package iscas.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.FieldPosition;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static HSSFWorkbook createSpreadsheet() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		return workbook;
	}

	public static HSSFWorkbook openSpreadsheet(String pathAndFileName) {
		File file = new File(pathAndFileName);
		if (!file.exists()) {
			System.out.println("openSpreadsheet Info: Spreadsheet is not existed");
			return null;
		}
		FileInputStream in;
		HSSFWorkbook workbook = null;
		try {
			in = new FileInputStream(pathAndFileName);
			workbook = new HSSFWorkbook(in);
			return workbook;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public static void saveSpreadsheet(HSSFWorkbook workbook, String pathAndFileName) {
		pathAndFileName = pathAndFileName.trim();
		if (!pathAndFileName.endsWith(".xls")) {
			System.out.println("saveSpreadsheet Info: File Name must end with '.xls'");
		}
		pathAndFileName = pathAndFileName.replaceAll("\\\\", "/");
		String dirPath = pathAndFileName.substring(0, pathAndFileName.lastIndexOf("/"));

		try {
			File dir = new File(dirPath);
			if (!dir.exists())
				dir.mkdirs();

			File file = new File(pathAndFileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream excel = new FileOutputStream(file);
			workbook.write(excel);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
