package iscas.bean;
// Generated 2016-3-9 17:29:24 by Hibernate Tools 3.4.0.CR1

/**
 * Filelist generated by hbm2java
 */
public class Filelist implements java.io.Serializable {

	private Integer id;
	private String fileMd5;
	private Integer status;

	public Filelist() {
	}

	public Filelist(String fileMd5, Integer status) {
		this.fileMd5 = fileMd5;
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileMd5() {
		return this.fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
