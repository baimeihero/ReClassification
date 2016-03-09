package iscas.main.preparedata;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import iscas.bean.Attachment;
import iscas.bean.Sheetname;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;
import iscas.util.POIUtil;

public class ExtractSheetName {
	private final static String OutputPath="D:\\GitHub\\ReClassification\\src\\iscas\\data\\";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		extractIntoDataBase();
		System.exit(0);
	}
	public static void extractIntoDataBase(){
		List<String>md5s=DatabaseManager.getFilelistCanBeAnalysis();
		int i=0;

		for(String md5:md5s){
			System.out.println(++i+"/"+md5s.size());
			List<Attachment> attachments = DatabaseManager.getAttachmentByMd5(md5);
			if (attachments == null || attachments.size() == 0)
				continue;
			Attachment attachment = attachments.get(0);			
			extractIntoDataBase(attachment);
		}
	
	}
	

	private static void  extractIntoDataBase(Attachment attachment) {
		
		HSSFWorkbook book = POIUtil.openSpreadsheet("E:\\AllExcel\\" + attachment.getSavename());
		if (book == null)
			return ;		
		int sheetnum=book.getNumberOfSheets();
		for (int sheetIndex = 0; sheetIndex < sheetnum; sheetIndex++) {			
			HSSFSheet sheet = book.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName().trim();
			int maxRow=sheet.getLastRowNum();
			int maxColumn=0;
			for (int r = 0; r <= sheet.getLastRowNum(); r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null)
					continue;
				maxColumn=maxColumn>row.getLastCellNum()?maxColumn:row.getLastCellNum();			
			}
		Sheetname sheetname=new Sheetname();
		sheetname.setFileMd5(attachment.getFilemd5());
		sheetname.setSheetName(sheetName);
		sheetname.setSheetPosition(sheetIndex);
		sheetname.setRowNum(maxRow);
		sheetname.setColumnNum(maxColumn);
		sheetname.setSheetNum(sheetnum);
		DatabaseManager.saveOrUpdateSheetName(sheetname);
		}
		
	}
	public static void extractIntoTxt(){
		List<String>md5s=DatabaseManager.getFilelistCanBeAnalysis();
		int i=0;
		String content="";
		for(String md5:md5s){
			System.out.println(++i+"/"+md5s.size());
			List<Attachment> attachments = DatabaseManager.getAttachmentByMd5(md5);
			if (attachments == null || attachments.size() == 0)
				continue;
			Attachment attachment = attachments.get(0);			
			content+=extractIntoTxt(attachment);
		}
		FileManager.createTxt(OutputPath,"SheetName.txt", content);
	}
	

	private static String  extractIntoTxt(Attachment attachment) {
		String count="";
		HSSFWorkbook book = POIUtil.openSpreadsheet("E:\\AllExcel\\" + attachment.getSavename());
		if (book == null)
			return count;		
		int sheetnum=book.getNumberOfSheets();
		for (int sheetIndex = 0; sheetIndex < sheetnum; sheetIndex++) {			
			HSSFSheet sheet = book.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName().trim();
			int maxRow=sheet.getLastRowNum();
			int maxColumn=0;
			for (int r = 0; r <= sheet.getLastRowNum(); r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null)
					continue;
				maxColumn=maxColumn>row.getLastCellNum()?maxColumn:row.getLastCellNum();			
			}
			count+=attachment.getFilemd5()+";";
			count+=sheetName+";";
			count+=sheetIndex+";";
			count+=maxRow+";";
			count+=maxColumn+";";
			count+=sheetnum+"\r\n";	
		}
		return count;		
	}

}
