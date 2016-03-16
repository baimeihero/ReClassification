package iscas.main.test;

import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String  value="adadasdfsdfsdfs";
		 List<String> words=Arrays.asList(value.split(" |-"));
		 for(String word:words){
		 System.out.println(word);
		 }
	}

}
