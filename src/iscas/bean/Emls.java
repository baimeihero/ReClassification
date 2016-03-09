package iscas.bean;
// Generated 2016-3-8 14:44:16 by Hibernate Tools 3.4.0.CR1

/**
 * Emls generated by hbm2java
 */
public class Emls implements java.io.Serializable {

	private Integer id;
	private String filename;
	private String filepath;
	private String filemd5;
	private Integer privileged;
	private int status;
	private int hasattachment;

	public Emls() {
	}

	public Emls(String filemd5, int status, int hasattachment) {
		this.filemd5 = filemd5;
		this.status = status;
		this.hasattachment = hasattachment;
	}

	public Emls(String filename, String filepath, String filemd5, Integer privileged, int status, int hasattachment) {
		this.filename = filename;
		this.filepath = filepath;
		this.filemd5 = filemd5;
		this.privileged = privileged;
		this.status = status;
		this.hasattachment = hasattachment;
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

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilemd5() {
		return this.filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public Integer getPrivileged() {
		return this.privileged;
	}

	public void setPrivileged(Integer privileged) {
		this.privileged = privileged;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getHasattachment() {
		return this.hasattachment;
	}

	public void setHasattachment(int hasattachment) {
		this.hasattachment = hasattachment;
	}

}
