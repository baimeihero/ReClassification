package iscas.main.preparedata;

import java.util.List;

import iscas.bean.Attachment;
import iscas.bean.Filelist;
import iscas.util.DatabaseManager;

/**
 * @author baimei
 *根据名称改变FileList中的status，已达到跳过多个可能低版本的电子表格
 *
 */
public class ResetFlieListStatus { 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		removeSomeByFileNmae("ENRON.xls");
		System.exit(0);
	}
	public static void removeSomeByFileNmae(String filename){
		List<Attachment> attachments=DatabaseManager.getAttachmentsByName(filename);
		if(attachments==null)
			return;
		for(Attachment attachment:attachments){
			String md5=attachment.getFilemd5();
			Filelist list=DatabaseManager.getFilelistByFileMD5(md5);
			list.setStatus(-1);
			DatabaseManager.saveOrUpdateFileList(list);
		}
	}

}
