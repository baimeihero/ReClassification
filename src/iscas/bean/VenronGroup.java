package iscas.bean;
// Generated 2016-3-9 17:29:24 by Hibernate Tools 3.4.0.CR1

/**
 * VenronGroup generated by hbm2java
 */
public class VenronGroup implements java.io.Serializable {

	private Integer id;
	private String groupOrder;
	private String groupName;
	private String groupPath;
	private Integer fileCount;

	public VenronGroup() {
	}

	public VenronGroup(String groupOrder, String groupName, String groupPath, Integer fileCount) {
		this.groupOrder = groupOrder;
		this.groupName = groupName;
		this.groupPath = groupPath;
		this.fileCount = fileCount;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupOrder() {
		return this.groupOrder;
	}

	public void setGroupOrder(String groupOrder) {
		this.groupOrder = groupOrder;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupPath() {
		return this.groupPath;
	}

	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}

	public Integer getFileCount() {
		return this.fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

}
