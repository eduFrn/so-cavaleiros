package controller;

import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {

	private static boolean pedraColetada;
	private static boolean tochaColetada;
	
	private static int ordemChegada;
	
	private static int[] portas = new int[4];
	
	private Semaphore semaphore;
	
	private int id;
	private boolean coletouPedra;
	private boolean coletouTocha;
	private int saida;
	
	public ThreadCavaleiro(int id, Semaphore semaphore, int saida) {
		this.id = id;
		this.semaphore = semaphore;
		this.saida = saida;
	}
	
	public void run() {
		caminhar();
		
		try {
			this.semaphore.acquire();
			cruzar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			this.semaphore.release();
		}
	}
	
	private void caminhar() {
		int distanciaPercorrida = 0;
		
		while(distanciaPercorrida < 2000) {
			
			int distancia = (int)(Math.random()*3)+2;

			if(coletouTocha) distancia+=2;
			if(coletouPedra) distancia+=2;
			
			System.out.println("O cavaleiro "+id+" caminhou "+distancia+" metros. Faltam "+(2000-distanciaPercorrida)+" metros");

			if(distanciaPercorrida >= 500 && !tochaColetada) {
				tochaColetada = true;
				this.coletouTocha = true;
				System.out.println("O cavaleiro "+id+" coletou a tocha!");
			}
			

			if(distanciaPercorrida >= 1500 && !pedraColetada && !this.coletouTocha) {
				pedraColetada = true;
				this.coletouPedra = true;
				System.out.println("O cavaleiro "+this.id+" coletou a pedra!");
			}
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			distanciaPercorrida+=distancia;
		}
		
		System.out.println("O cavaleiro "+this.id+" foi o "+(++ordemChegada)+"o a chegar no fim do percurso!");
	}
	
	private void cruzar() {
		System.out.println("O cavaleiro "+this.id+" vai cruzar pela porta!");
		
		int portaEscolhida;
		
		boolean portaDiferente;
		
		do {
			portaDiferente = true;
			portaEscolhida = (int)(Math.random()*4)+1;
			
			for(int i = 0; i < ordemChegada;i++) {
				if(portas[i] == portaEscolhida) {
					portaDiferente = false;
				}
			}
		}while(!portaDiferente);
		
		portas[ordemChegada - 1] = portaEscolhida;
		
		System.out.println("O cavaleiro "+this.id+" escolheu a porta: "+portaEscolhida+"!");
		
		if(portaEscolhida != saida) {
			System.out.println("DESCANSE EM PAZ: Oh não! O cavaleiro "+this.id+" escolheu a porta errada foi devorado por monstros!");
		}else {
			System.out.println("Deu tudo certo! O cavaleiro "+this.id+" escolheu a saída verdadeira, e está são e salvo!");
		}
	}
	
}
