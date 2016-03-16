
package iscas.main.process;

import java.util.List;

import iscas.util.FileManager;

public class Test {
	public static void main(String[] args) {
		String data=
"6eaffa73ae67acf4417966c7abc0469a-5f873176a6353a37669f85547706419c=0.75";
    System.out.println(data.substring(0,32));
    System.out.println(data.substring(33,65));
    System.out.println(data.substring(66));
	}
}
