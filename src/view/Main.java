package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCavaleiro;

public class Main {
	public static void main(String[] args) {
		
		Semaphore semaphore = new Semaphore(1);
		
		int saida = (int)(Math.random()*4)+1;
		
		for(int i = 0; i < 4; i++) {
			Thread cavaleiro = new ThreadCavaleiro(i+1, semaphore, saida);
			cavaleiro.start();
		}
	}
}
