package iscas.bean;
// Generated 2016-3-18 8:34:52 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Attachment generated by hbm2java
 */
public class Attachment implements java.io.Serializable {

	private Integer id;
	private String filename;
	private String savename;
	private Integer filesize;
	private String filemd5;
	private Integer extracted;
	private Date addtime;
	private String storepath;
	private String senddate;
	private Integer privileged;
	private int hasformula;

	public Attachment() {
	}

	public Attachment(int hasformula) {
		this.hasformula = hasformula;
	}

	public Attachment(String filename, String savename, Integer filesize, String filemd5, Integer extracted,
			Date addtime, String storepath, String senddate, Integer privileged, int hasformula) {
		this.filename = filename;
		this.savename = savename;
		this.filesize = filesize;
		this.filemd5 = filemd5;
		this.extracted = extracted;
		this.addtime = addtime;
		this.storepath = storepath;
		this.senddate = senddate;
		this.privileged = privileged;
		this.hasformula = hasformula;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSavename() {
		return this.savename;
	}

	public void setSavename(String savename) {
		this.savename = savename;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public String getFilemd5() {
		return this.filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public Integer getExtracted() {
		return this.extracted;
	}

	public void setExtracted(Integer extracted) {
		this.extracted = extracted;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getStorepath() {
		return this.storepath;
	}

	public void setStorepath(String storepath) {
		this.storepath = storepath;
	}

	public String getSenddate() {
		return this.senddate;
	}

	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	public Integer getPrivileged() {
		return this.privileged;
	}

	public void setPrivileged(Integer privileged) {
		this.privileged = privileged;
	}

	public int getHasformula() {
		return this.hasformula;
	}

	public void setHasformula(int hasformula) {
		this.hasformula = hasformula;
	}

}
