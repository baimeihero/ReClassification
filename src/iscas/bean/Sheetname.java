package iscas.bean;
// Generated 2016-3-9 10:27:31 by Hibernate Tools 3.4.0.CR1

/**
 * Sheetname generated by hbm2java
 */
public class Sheetname implements java.io.Serializable {

	private int id;
	private String fileMd5;
	private String sheetName;
	private Integer sheetPosition;
	private Integer sheetNum;
	private Integer rowNum;
	private Integer columnNum;
	private String refinedName;

	public Sheetname() {
	}

	public Sheetname(int id) {
		this.id = id;
	}

	public Sheetname(int id, String fileMd5, String sheetName, Integer sheetPosition, Integer sheetNum, Integer rowNum,
			Integer columnNum, String refinedName) {
		this.id = id;
		this.fileMd5 = fileMd5;
		this.sheetName = sheetName;
		this.sheetPosition = sheetPosition;
		this.sheetNum = sheetNum;
		this.rowNum = rowNum;
		this.columnNum = columnNum;
		this.refinedName = refinedName;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileMd5() {
		return this.fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getSheetPosition() {
		return this.sheetPosition;
	}

	public void setSheetPosition(Integer sheetPosition) {
		this.sheetPosition = sheetPosition;
	}

	public Integer getSheetNum() {
		return this.sheetNum;
	}

	public void setSheetNum(Integer sheetNum) {
		this.sheetNum = sheetNum;
	}

	public Integer getRowNum() {
		return this.rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getColumnNum() {
		return this.columnNum;
	}

	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}

	public String getRefinedName() {
		return this.refinedName;
	}

	public void setRefinedName(String refinedName) {
		this.refinedName = refinedName;
	}

}
