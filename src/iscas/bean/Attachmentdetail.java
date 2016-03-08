package iscas.bean;
// Generated 2016-3-7 17:33:00 by Hibernate Tools 3.4.0.CR1

/**
 * Attachmentdetail generated by hbm2java
 */
public class Attachmentdetail implements java.io.Serializable {

	private Integer id;
	private String filemd5;
	private String sheetname;
	private Integer sheetposition;
	private Integer sheetnum;
	private Integer rownum;
	private Integer columnnum;
	private Integer totalnum;
	private Integer booleannum;
	private Integer booleanformulanum;
	private Integer datenum;
	private Integer dateformulanum;
	private Integer emptynum;
	private Integer errornum;
	private Integer formulaerrornum;
	private Integer labelnum;
	private Integer numbernum;
	private Integer numberformulanum;
	private Integer stringformulanum;
	private Integer unemptynum;
	private Integer hyperlinksnum;
	private Integer emptyrownum;
	private Integer emptycolumnsnum;
	private Integer emptysheet;

	public Attachmentdetail() {
	}

	public Attachmentdetail(String filemd5, String sheetname, Integer sheetposition, Integer sheetnum, Integer rownum,
			Integer columnnum, Integer totalnum, Integer booleannum, Integer booleanformulanum, Integer datenum,
			Integer dateformulanum, Integer emptynum, Integer errornum, Integer formulaerrornum, Integer labelnum,
			Integer numbernum, Integer numberformulanum, Integer stringformulanum, Integer unemptynum,
			Integer hyperlinksnum, Integer emptyrownum, Integer emptycolumnsnum, Integer emptysheet) {
		this.filemd5 = filemd5;
		this.sheetname = sheetname;
		this.sheetposition = sheetposition;
		this.sheetnum = sheetnum;
		this.rownum = rownum;
		this.columnnum = columnnum;
		this.totalnum = totalnum;
		this.booleannum = booleannum;
		this.booleanformulanum = booleanformulanum;
		this.datenum = datenum;
		this.dateformulanum = dateformulanum;
		this.emptynum = emptynum;
		this.errornum = errornum;
		this.formulaerrornum = formulaerrornum;
		this.labelnum = labelnum;
		this.numbernum = numbernum;
		this.numberformulanum = numberformulanum;
		this.stringformulanum = stringformulanum;
		this.unemptynum = unemptynum;
		this.hyperlinksnum = hyperlinksnum;
		this.emptyrownum = emptyrownum;
		this.emptycolumnsnum = emptycolumnsnum;
		this.emptysheet = emptysheet;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilemd5() {
		return this.filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public String getSheetname() {
		return this.sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public Integer getSheetposition() {
		return this.sheetposition;
	}

	public void setSheetposition(Integer sheetposition) {
		this.sheetposition = sheetposition;
	}

	public Integer getSheetnum() {
		return this.sheetnum;
	}

	public void setSheetnum(Integer sheetnum) {
		this.sheetnum = sheetnum;
	}

	public Integer getRownum() {
		return this.rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

	public Integer getColumnnum() {
		return this.columnnum;
	}

	public void setColumnnum(Integer columnnum) {
		this.columnnum = columnnum;
	}

	public Integer getTotalnum() {
		return this.totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}

	public Integer getBooleannum() {
		return this.booleannum;
	}

	public void setBooleannum(Integer booleannum) {
		this.booleannum = booleannum;
	}

	public Integer getBooleanformulanum() {
		return this.booleanformulanum;
	}

	public void setBooleanformulanum(Integer booleanformulanum) {
		this.booleanformulanum = booleanformulanum;
	}

	public Integer getDatenum() {
		return this.datenum;
	}

	public void setDatenum(Integer datenum) {
		this.datenum = datenum;
	}

	public Integer getDateformulanum() {
		return this.dateformulanum;
	}

	public void setDateformulanum(Integer dateformulanum) {
		this.dateformulanum = dateformulanum;
	}

	public Integer getEmptynum() {
		return this.emptynum;
	}

	public void setEmptynum(Integer emptynum) {
		this.emptynum = emptynum;
	}

	public Integer getErrornum() {
		return this.errornum;
	}

	public void setErrornum(Integer errornum) {
		this.errornum = errornum;
	}

	public Integer getFormulaerrornum() {
		return this.formulaerrornum;
	}

	public void setFormulaerrornum(Integer formulaerrornum) {
		this.formulaerrornum = formulaerrornum;
	}

	public Integer getLabelnum() {
		return this.labelnum;
	}

	public void setLabelnum(Integer labelnum) {
		this.labelnum = labelnum;
	}

	public Integer getNumbernum() {
		return this.numbernum;
	}

	public void setNumbernum(Integer numbernum) {
		this.numbernum = numbernum;
	}

	public Integer getNumberformulanum() {
		return this.numberformulanum;
	}

	public void setNumberformulanum(Integer numberformulanum) {
		this.numberformulanum = numberformulanum;
	}

	public Integer getStringformulanum() {
		return this.stringformulanum;
	}

	public void setStringformulanum(Integer stringformulanum) {
		this.stringformulanum = stringformulanum;
	}

	public Integer getUnemptynum() {
		return this.unemptynum;
	}

	public void setUnemptynum(Integer unemptynum) {
		this.unemptynum = unemptynum;
	}

	public Integer getHyperlinksnum() {
		return this.hyperlinksnum;
	}

	public void setHyperlinksnum(Integer hyperlinksnum) {
		this.hyperlinksnum = hyperlinksnum;
	}

	public Integer getEmptyrownum() {
		return this.emptyrownum;
	}

	public void setEmptyrownum(Integer emptyrownum) {
		this.emptyrownum = emptyrownum;
	}

	public Integer getEmptycolumnsnum() {
		return this.emptycolumnsnum;
	}

	public void setEmptycolumnsnum(Integer emptycolumnsnum) {
		this.emptycolumnsnum = emptycolumnsnum;
	}

	public Integer getEmptysheet() {
		return this.emptysheet;
	}

	public void setEmptysheet(Integer emptysheet) {
		this.emptysheet = emptysheet;
	}

}