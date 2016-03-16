package iscas.main.threads;

public class ThreadDemo1 {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
		Demo d = new Demo();
		d.start();
		}
		for (int i = 0; i < 60; i++) {
			System.out.println(Thread.currentThread().getName() + i);
		}

	}
}

class Demo extends Thread {
	public void run() {
		for (int i = 0; i < 60; i++) {
			System.out.println(Thread.currentThread().getId()+"#"+ i);
		}
	}
}
