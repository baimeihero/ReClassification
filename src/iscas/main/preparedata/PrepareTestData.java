package iscas.main.preparedata;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import iscas.bean.Attachment;
import iscas.bean.VenronFilelist;
import iscas.bean.VenronGroup;
import iscas.util.Constants;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;
import iscas.util.POIUtil;

public class PrepareTestData {
	private final static String path = Constants.OutPutPath;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void checkAnalyzability() {
		List<VenronFilelist> vfiles = DatabaseManager.getAllVenronFilelist();
		List<String> BeAnalyzability = DatabaseManager.getFilelistCanBeAnalysis();
		List<String> md5s = new ArrayList<String>();

		int count = 1;
		for (VenronFilelist vfile : vfiles) {
			if (!BeAnalyzability.contains(vfile.getMd5())) {
				System.out.println(vfile.getGroupId() + "." + vfile.getFileOrderInGroup() + ":" + vfile.getFileName()
						+ ":" + vfile.getMd5());
				md5s.add(md5s.size(), vfile.getMd5());
				count++;
			}
		}
		for (String md5 : md5s) {
			checkOpen(md5);
		}
		System.out.println(count);
		System.exit(0);
	}

	public static void checkOpen(String md5) {
		List<Attachment> attachments = DatabaseManager.getAttachmentByMd5(md5);
		HSSFWorkbook book = POIUtil.openSpreadsheet("E:\\AllExcel\\" + attachments.get(0).getSavename());
	}

	public static void selectTestFiles() {
		List<String> BeAnalyzability = DatabaseManager.getFilelistCanBeAnalysis();
		List<String> md5s = new ArrayList<String>();		
	    String contents="";
		List<VenronGroup> groups = DatabaseManager.getAllVenronGroup();
		for (VenronGroup group : groups) {
			String groupId = group.getGroupOrder();
			List<VenronFilelist> files = DatabaseManager.getVenronFilelistbyGroupId(Integer.parseInt(groupId));
			for(int i=0;i<files.size()&&i<=5;i++){
				VenronFilelist vfile=files.get(i);
				if (BeAnalyzability.contains(vfile.getMd5())) {					
					contents+=vfile.getMd5()+";"+ groupId+"\r\n";					
				}
			}
			}
		FileManager.createTxt(path, "TestVersionInfo.txt", contents);
		System.exit(0);
	}
	


}
