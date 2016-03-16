package iscas.main.threads;

public class ThreadDemo2 {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
		Demo2 d = new Demo2();
		Thread t = new Thread(d,"Name"+i);
		t.start();
		}
		for (int x = 0; x < 60; x++) {
			System.out.println(Thread.currentThread().getName() + x);
		}
	}
}

class Demo2 implements Runnable {
	public void run() {
		for (int x = 0; x < 60; x++) {
			System.out.println(Thread.currentThread().getName() +"#"+ x);
		}
	}
}
