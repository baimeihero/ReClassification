package iscas.main.preparedata;

import java.util.List;

import iscas.bean.Attachment;
import iscas.bean.Filelist;
import iscas.util.Constants;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;

public class CreateFileList {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		create();
		System.exit(0);
	}
	public static void create(){
		List<Filelist> filelist = DatabaseManager.getFilelists();
	    String contents="";
	    for(Filelist efile:filelist){
	    	List<Attachment> attachments = DatabaseManager.getAttachmentByMd5(efile.getFileMd5());
			if (attachments == null || attachments.size() == 0)
				continue;
			Attachment attachment = attachments.get(0);
	    	contents+=efile.getId()+";"+efile.getFileMd5()+";"+attachment.getSavename()+"\r\n";
	    }
	    FileManager.createTxt(Constants.OutPutPath, "AllFiles.txt", contents);
	}

}
