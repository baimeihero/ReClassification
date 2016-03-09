package iscas.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createTxt("E:\\aa\\", "bb.txt", "CCCCCCCCC");
	}
	
	public static void createTxt(String path, String fileName, String content) {
		/* 写入Txt文件 */
		try {

			File bpath = new File(path);

			if (!bpath.exists())
				bpath.mkdirs();

			File writename = new File(path + fileName); // 相对路径，如果没有则要建立一个新的output。txt文件
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(content); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件

	}
	
	public static void copyFile(String oldFilePathAndName, String newFilePathAndName) {
		oldFilePathAndName = oldFilePathAndName.trim();
		oldFilePathAndName = oldFilePathAndName.replaceAll("\\\\", "/");
		newFilePathAndName = newFilePathAndName.replaceAll("\\\\", "/");
		String newpath=newFilePathAndName.substring(0, newFilePathAndName.lastIndexOf("/"));
		int byteread = 0;
		try {
			File oldFile=new File(oldFilePathAndName);
			if(!oldFile.exists())
				return;
			File newp=new File(newpath);
			if(!newp.exists())
				newp.mkdirs();
			File newFile = new File(newFilePathAndName);
			newFile.createNewFile();
			FileInputStream inStream = new FileInputStream(oldFilePathAndName);
			FileOutputStream outStream = new FileOutputStream(newFile);
			byte buffer[] = new byte[1024];
			while ((byteread = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, byteread);
			}
			inStream.close();
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static List<String> readText(String filePathAndName) {
		 List<String> list=new ArrayList<String>();
		
		FileInputStream fs;
		try {
			File file = new File(filePathAndName);
			if (file.exists()) {
				fs = new FileInputStream(filePathAndName);
				InputStreamReader isr;
				isr = new InputStreamReader(fs);
				BufferedReader br = new BufferedReader(isr);
				String data = "";
				int i=0;
				while ((data = br.readLine()) != null) {
					list.add(i,data);
				}			
				fs.close();
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		
		}
		Collections.reverse(list);
		return list;

	}
	public static List<String> readText(String filePathAndName,String code) {
		List<String> list=new ArrayList<String>();
		
		FileInputStream fs;
		try {
			File file = new File(filePathAndName);
			if (file.exists()) {
				fs = new FileInputStream(filePathAndName);
				InputStreamReader isr;
				isr = new InputStreamReader(fs, code);
				BufferedReader br = new BufferedReader(isr);
				String data = "";
				int i=0;
				while ((data = br.readLine()) != null) {
					list.add(i,data);
				}			
				fs.close();
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		Collections.reverse(list);
		return list;
		
	}
	
	public static void copyFolder(String oldFolderPath, String newFolderPath) {
		oldFolderPath = oldFolderPath.trim();
		newFolderPath = newFolderPath.trim();
		File oldFolder = new File(oldFolderPath);
		File newFolder = new File(newFolderPath);
		if (!newFolder.exists()) {
			newFolder.mkdirs();
		}
		if (!oldFolder.exists()) {
			System.out.println("error info:"+oldFolderPath
					+ " is not exist");
			return;
		}
		if (newFolder.isFile()) {
			System.out.println("error info:"+ newFolderPath
					+ " is not directory");
			return;
		}
		if (oldFolder.isFile()) {
			System.out.println("error info:"+oldFolderPath
					+ " is not  not directory");
			return;
		}
		String fileList[] = oldFolder.list();
		for (int i = 0; i < fileList.length; i++) {
			File temp = null;
		
				temp = new File(oldFolderPath + fileList[i]);
			
			if (temp.isFile()) {
				copyFile(oldFolderPath + fileList[i], newFolderPath+ fileList[i]);
			}
			if (temp.isDirectory()) {
				copyFolder(oldFolderPath + fileList[i]+"/", newFolderPath
						+ fileList[i]+"/");
			}
		}

	}

}
