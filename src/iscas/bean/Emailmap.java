package iscas.bean;
// Generated 2016-3-8 14:44:16 by Hibernate Tools 3.4.0.CR1

/**
 * Emailmap generated by hbm2java
 */
public class Emailmap implements java.io.Serializable {

	private Integer id;
	private String groupname;
	private Integer sendernum;
	private Integer usernum;
	private Integer longestpath;
	private Integer maxedgenum;
	private Integer tonum;
	private Integer ccnum;
	private Integer bcnum;
	private Integer spreadsheetnum;

	public Emailmap() {
	}

	public Emailmap(String groupname, Integer sendernum, Integer usernum, Integer longestpath, Integer maxedgenum,
			Integer tonum, Integer ccnum, Integer bcnum, Integer spreadsheetnum) {
		this.groupname = groupname;
		this.sendernum = sendernum;
		this.usernum = usernum;
		this.longestpath = longestpath;
		this.maxedgenum = maxedgenum;
		this.tonum = tonum;
		this.ccnum = ccnum;
		this.bcnum = bcnum;
		this.spreadsheetnum = spreadsheetnum;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Integer getSendernum() {
		return this.sendernum;
	}

	public void setSendernum(Integer sendernum) {
		this.sendernum = sendernum;
	}

	public Integer getUsernum() {
		return this.usernum;
	}

	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}

	public Integer getLongestpath() {
		return this.longestpath;
	}

	public void setLongestpath(Integer longestpath) {
		this.longestpath = longestpath;
	}

	public Integer getMaxedgenum() {
		return this.maxedgenum;
	}

	public void setMaxedgenum(Integer maxedgenum) {
		this.maxedgenum = maxedgenum;
	}

	public Integer getTonum() {
		return this.tonum;
	}

	public void setTonum(Integer tonum) {
		this.tonum = tonum;
	}

	public Integer getCcnum() {
		return this.ccnum;
	}

	public void setCcnum(Integer ccnum) {
		this.ccnum = ccnum;
	}

	public Integer getBcnum() {
		return this.bcnum;
	}

	public void setBcnum(Integer bcnum) {
		this.bcnum = bcnum;
	}

	public Integer getSpreadsheetnum() {
		return this.spreadsheetnum;
	}

	public void setSpreadsheetnum(Integer spreadsheetnum) {
		this.spreadsheetnum = spreadsheetnum;
	}

}
