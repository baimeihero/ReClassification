package iscas.main.bean;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class Table {
	private HSSFCell leftTopCell = null;
	private HSSFCell rightBottomCell = null;
	private String fileName = null;
	private int sheetIndex = -1;

	public HSSFCell getLeftTopCell() {
		return leftTopCell;
	}

	public void setLeftTopCell(HSSFCell leftTopCell) {
		this.leftTopCell = leftTopCell;
	}

	public HSSFCell getRightBottomCell() {
		return rightBottomCell;
	}

	public void setRightBottomCell(HSSFCell rightBottomCell) {
		this.rightBottomCell = rightBottomCell;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	@Override
	public String toString() {
		String result = "";
		result += "文件名：" + this.fileName + "\n";
		result += "Sheet Index:" + this.sheetIndex + "\n";
		result += "Table Array: " +this.leftTopCell.getRowIndex()+","+this.leftTopCell.getColumnIndex()+ "……" + this.rightBottomCell.getRowIndex()+","+this.rightBottomCell.getColumnIndex() ;
		return result;
	}

}
