package iscas.main.venron;

import java.io.File;
import java.util.List;

import iscas.bean.Attachment;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;

public class CopyFileAndRenanme {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void copyAttachmentAndRename() {

		int i = 0;
		List<Attachment> attachments = DatabaseManager.getAllAttachments();
		for (Attachment attachment : attachments) {
			i++;
			System.out.println(i);
			File file = new File("F:/excel/" + attachment.getSavename());
			String dataTime = attachment.getSenddate();
			dataTime = dataTime.replace("年", "-");
			dataTime = dataTime.replace("月", "-");
			dataTime = dataTime.replace("日", "-");
			dataTime = dataTime.replace("时", "-");
			dataTime = dataTime.replace("分", "");
			attachment.setSenddate(dataTime);
			if (file.exists()) {
				FileManager.copyFile("F:/excel/" + attachment.getSavename(), "E:\\AllExcel\\" + attachment.getFilename()
						+ "_" + dataTime + "_" + attachment.getFilemd5() + ".xls");
			}
			attachment.setSavename(attachment.getFilename() + "_" + dataTime + "_" + attachment.getFilemd5() + ".xls");
			attachment.setStorepath("E:/AllExcel/");
			DatabaseManager.saveOrUpdateAttachment(attachment);
		}
	
	}

}
