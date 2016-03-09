package iscas.main.preparedata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import iscas.bean.Attachment;
import iscas.bean.Filelist;
import iscas.bean.Labelinfo;
import iscas.util.DatabaseManager;
import iscas.util.POIUtil;

/**
 * @author baimei
 *从电子表格中抽取Label信息
 */
public class ExtractLabelInfo  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			extract();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void initFileList() {
		List<String> md5s = DatabaseManager.getAllAttachmentMD5();
		int count = 0;
		for (String md5 : md5s) {
			System.out.println(count++);
			Filelist list = new Filelist();
			list.setFileMd5(md5);
			list.setStatus(0);
			DatabaseManager.saveOrUpdateFileList(list);
		}
	}

	public static void extract() throws IOException {
		List<Filelist> filelistes = DatabaseManager.getFilelistUndoMD5s();
		int count = 0;
		for (Filelist list : filelistes) {
			list.setStatus(-1);
			DatabaseManager.saveOrUpdateFileList(list);
			System.out.println(count++ + "/" + filelistes.size());
			List<Attachment> attachments = DatabaseManager.getAttachmentByMd5(list.getFileMd5());
			if (attachments == null || attachments.size() == 0)
				continue;
			Attachment attachment = attachments.get(0);
			if (attachment.getHasformula() < 0)
				continue;
			boolean result = extract(attachment);
			if (result) {
				list.setStatus(1);
				DatabaseManager.saveOrUpdateFileList(list);
			}
		}
	}

	private static boolean extract(Attachment attachment) {
		String fileMD5 = attachment.getFilemd5();
		System.out.println(attachment.getSavename());
		HSSFWorkbook book = POIUtil.openSpreadsheet("E:\\AllExcel\\" + attachment.getSavename());
		if (book == null)
			return false;
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		for (int sheetIndex = 0; sheetIndex < book.getNumberOfSheets(); sheetIndex++) {
			
			HSSFSheet sheet = book.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName().trim();
			System.out.println(sheet.getSheetName());
			for (int r = 0; r <= sheet.getLastRowNum(); r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null)
					continue;
				for (int c = 0; c <= row.getLastCellNum(); c++) {
					HSSFCell cell = row.getCell(c);
					String value = getCellValueAsString(cell);
					if (value == null)
						continue;
					value=value.replaceAll(";", "");
					if (words.keySet().contains(value)) {
						words.put(value, words.get(value) + 1);
					} else {
						words.put(value, 1);
					}
				}
			}
		}
		// save to database
		Iterator<String> keys = words.keySet().iterator();
		String labelString="";
		while (keys.hasNext()) {
			String key = keys.next();
			labelString+=key+";"+words.get(key)+";";
		}
		Labelinfo info = new Labelinfo();
		info.setFileMd5(fileMD5);	
		info.setLabelString(labelString);
	//	info.setOccurrenceNum();
		DatabaseManager.saveOrUpdateLabelinfo(info);
		try {
			book.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String getCellValueAsString(HSSFCell cell) {
		String strCell = null;
		if (cell == null) {
			return strCell;
		}
		if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING)
			return null;
		strCell = cell.getStringCellValue();
		if (strCell == null || strCell.trim().length() == 0)
			return null;
		strCell = strCell.trim();
		return strCell;

	}

}
