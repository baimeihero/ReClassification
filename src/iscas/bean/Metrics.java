package iscas.bean;
// Generated 2016-3-18 8:34:52 by Hibernate Tools 3.4.0.CR1

/**
 * Metrics generated by hbm2java
 */
public class Metrics implements java.io.Serializable {

	private Integer id;
	private Float metricValue;
	private String pairBasedSheetName;

	public Metrics() {
	}

	public Metrics(Float metricValue, String pairBasedSheetName) {
		this.metricValue = metricValue;
		this.pairBasedSheetName = pairBasedSheetName;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getMetricValue() {
		return this.metricValue;
	}

	public void setMetricValue(Float metricValue) {
		this.metricValue = metricValue;
	}

	public String getPairBasedSheetName() {
		return this.pairBasedSheetName;
	}

	public void setPairBasedSheetName(String pairBasedSheetName) {
		this.pairBasedSheetName = pairBasedSheetName;
	}

}
