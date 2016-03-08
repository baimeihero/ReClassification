package iscas.bean;
// Generated 2016-3-7 17:33:00 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Emaildetail generated by hbm2java
 */
public class Emaildetail implements java.io.Serializable {

	private Integer id;
	private String messageid;
	private String relativepath;
	private String foldername;
	private String sender;
	private String toaddress;
	private String ccaddress;
	private String bcaddress;
	private String subject;
	private String emailcontent;
	private String filemd5;
	private String senddate;
	private Integer privileged;
	private Integer emailsize;
	private Integer attachmentnum;
	private Integer dmodification;
	private Integer dproblems;
	private Integer dspreadsheets;
	private Integer readed;
	private Integer extracted;
	private Date addtime;
	private Date modifytime;

	public Emaildetail() {
	}

	public Emaildetail(String messageid, String relativepath, String foldername, String sender, String toaddress,
			String ccaddress, String bcaddress, String subject, String emailcontent, String filemd5, String senddate,
			Integer privileged, Integer emailsize, Integer attachmentnum, Integer dmodification, Integer dproblems,
			Integer dspreadsheets, Integer readed, Integer extracted, Date addtime, Date modifytime) {
		this.messageid = messageid;
		this.relativepath = relativepath;
		this.foldername = foldername;
		this.sender = sender;
		this.toaddress = toaddress;
		this.ccaddress = ccaddress;
		this.bcaddress = bcaddress;
		this.subject = subject;
		this.emailcontent = emailcontent;
		this.filemd5 = filemd5;
		this.senddate = senddate;
		this.privileged = privileged;
		this.emailsize = emailsize;
		this.attachmentnum = attachmentnum;
		this.dmodification = dmodification;
		this.dproblems = dproblems;
		this.dspreadsheets = dspreadsheets;
		this.readed = readed;
		this.extracted = extracted;
		this.addtime = addtime;
		this.modifytime = modifytime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessageid() {
		return this.messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getRelativepath() {
		return this.relativepath;
	}

	public void setRelativepath(String relativepath) {
		this.relativepath = relativepath;
	}

	public String getFoldername() {
		return this.foldername;
	}

	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getToaddress() {
		return this.toaddress;
	}

	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}

	public String getCcaddress() {
		return this.ccaddress;
	}

	public void setCcaddress(String ccaddress) {
		this.ccaddress = ccaddress;
	}

	public String getBcaddress() {
		return this.bcaddress;
	}

	public void setBcaddress(String bcaddress) {
		this.bcaddress = bcaddress;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailcontent() {
		return this.emailcontent;
	}

	public void setEmailcontent(String emailcontent) {
		this.emailcontent = emailcontent;
	}

	public String getFilemd5() {
		return this.filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
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

	public Integer getEmailsize() {
		return this.emailsize;
	}

	public void setEmailsize(Integer emailsize) {
		this.emailsize = emailsize;
	}

	public Integer getAttachmentnum() {
		return this.attachmentnum;
	}

	public void setAttachmentnum(Integer attachmentnum) {
		this.attachmentnum = attachmentnum;
	}

	public Integer getDmodification() {
		return this.dmodification;
	}

	public void setDmodification(Integer dmodification) {
		this.dmodification = dmodification;
	}

	public Integer getDproblems() {
		return this.dproblems;
	}

	public void setDproblems(Integer dproblems) {
		this.dproblems = dproblems;
	}

	public Integer getDspreadsheets() {
		return this.dspreadsheets;
	}

	public void setDspreadsheets(Integer dspreadsheets) {
		this.dspreadsheets = dspreadsheets;
	}

	public Integer getReaded() {
		return this.readed;
	}

	public void setReaded(Integer readed) {
		this.readed = readed;
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

	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

}
