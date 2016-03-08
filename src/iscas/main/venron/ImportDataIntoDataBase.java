package iscas.main.venron;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import iscas.bean.VenronFilelist;
import iscas.bean.VenronGroup;
import iscas.util.DatabaseManager;
import iscas.util.POIUtil;

public class ImportDataIntoDataBase {
	private static final String fileOrderpath = "E:\\VEnron\\Version\\FileOrder.xls";


	public static void importGroupInfo() {
		HSSFWorkbook book = POIUtil.openSpreadsheet(fileOrderpath);
		HSSFSheet sheet = book.getSheetAt(0);
		int maxrow = sheet.getLastRowNum();
		for (int i = 1; i <= maxrow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			String GroupOrder = getCellValueAsString(row.getCell(0));
			String GroupName = getCellValueAsString(row.getCell(1));
			String FileCount = getCellValueAsString(row.getCell(2));
			String path = getCellValueAsString(row.getCell(3));
			System.out.println(path.substring(3));
			if (GroupOrder == "")
				continue;
			VenronGroup group = new VenronGroup();
			group.setGroupOrder(GroupOrder);
			group.setGroupName(GroupName);
			group.setFileCount(Integer.parseInt(FileCount.substring(0, FileCount.indexOf("."))));
			System.out.println(Integer.parseInt(FileCount.substring(0, FileCount.indexOf("."))));
			group.setGroupPath(path.substring(2));
			DatabaseManager.saveOrUpdateVenronGroup(group);

		}

	}

	public static void importFileListInfo() {
		HSSFWorkbook book = POIUtil.openSpreadsheet(fileOrderpath);
		for (int k =357; k < book.getNumberOfSheets(); k++) {
			HSSFSheet sheet = book.getSheetAt(k);
			int maxrow = sheet.getLastRowNum();
			for (int i = 1; i <= maxrow; i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;
				String fileorder = getCellValueAsString(row.getCell(0));
				String fileName = getCellValueAsString(row.getCell(1));
				String sendingTime = getCellValueAsString(row.getCell(2));
				String sender = getCellValueAsString(row.getCell(3));
				String md5 = getCellValueAsString(row.getCell(4));
				String filePath = getCellValueAsString(row.getCell(5));
				if(fileorder=="")
					continue;
				System.out.println(fileorder);
				VenronFilelist filelist=new VenronFilelist();
				filelist.setFileOrderInGroup(Integer.parseInt(fileorder.substring(0, fileorder.indexOf("."))));
				filelist.setFileName(fileName);
				filelist.setSendingTime(sendingTime);
				filelist.setSender(sender);
				filelist.setMd5(md5);
				filelist.setFilePath(filePath.substring(2));
				filelist.setGroupId(k);
				DatabaseManager.saveOrUpdateVenronFilelist(filelist);

			}
		}

	}

	private static String getCellValueAsString(HSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			// cell.getCellFormula();
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
				} else {
					strCell = String.valueOf(cell.getNumericCellValue());
				}
			} catch (IllegalStateException e) {
				strCell = String.valueOf(cell.getRichStringCellValue());
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
		default:
			strCell = "";
			break;
		}
		return strCell;
	}

}
