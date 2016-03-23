package iscas.util;

public class SQLStatements {
	public static final String Query_FileList_CanBeAnalysis="select fileMd5 from filelist where status=1";
	public static final String Query_PairBasedSheetName_GT_Value="select PairBasedSheetName from metrics where metricValue >=";
	public static final String Query_PairBasedSheetName_EQ_Value="select PairBasedSheetName from metrics where metricValue =";

}
