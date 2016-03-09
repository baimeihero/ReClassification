package iscas.main.preparedata;

import java.util.ArrayList;
import java.util.List;

import iscas.bean.Labelinfo;
import iscas.util.DatabaseManager;
import iscas.util.FileManager;

/**
 * @author baimei
 * 替换其中的数字和时间
 */
public class RefineLabelInfo {
	
	private final int MaxLongth=10;
	private final String numString="NAM";
	private final String DateString="YMD";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		statisticDifferentLabelInfo();
		System.exit(0);
	}
	public static void statisticDifferentLabelInfo(){
		List<String>  words=new ArrayList<String>();
		List<Labelinfo> infos=DatabaseManager.getAllLabelinfo();
		int count=0;
		for(Labelinfo info:infos){
			System.out.println(count+++"/"+infos.size());
			if(info.getLabelString()==null||info.getLabelString().trim().length()==0)
				continue;
			String Labels[]=info.getLabelString().split(";");
			for(int i=0;i<Labels.length-1;){
				String  Label=Labels[i];
				i=i+2;
				if(Label.trim().length()==0)
					continue;
				if(!words.contains(Label))
					words.add(Label);
			}
		}
		System.out.println("words:"+words.size());
		String content="";
		int i=1;
		for(String word:words){
			content+=i+++".   "+word+"\r\n";
		}
		FileManager.createTxt("E:\\", "LabelInfo.txt",content);
	}

}
