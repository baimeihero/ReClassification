package iscas.bean;
// Generated 2016-3-7 17:33:00 by Hibernate Tools 3.4.0.CR1

/**
 * Eml2attachment generated by hbm2java
 */
public class Eml2attachment implements java.io.Serializable {

	private Integer id;
	private String emlmd5;
	private String attachmentmd5;
	private String attachmentname;

	public Eml2attachment() {
	}

	public Eml2attachment(String emlmd5, String attachmentmd5, String attachmentname) {
		this.emlmd5 = emlmd5;
		this.attachmentmd5 = attachmentmd5;
		this.attachmentname = attachmentname;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmlmd5() {
		return this.emlmd5;
	}

	public void setEmlmd5(String emlmd5) {
		this.emlmd5 = emlmd5;
	}

	public String getAttachmentmd5() {
		return this.attachmentmd5;
	}

	public void setAttachmentmd5(String attachmentmd5) {
		this.attachmentmd5 = attachmentmd5;
	}

	public String getAttachmentname() {
		return this.attachmentname;
	}

	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
	}

}
