import java.io.*;
import java.net.*;

public class Summauspalvelija extends Thread{
	
	final int PORT;
	Lokero lokero;
	ServerSocket server;
	Socket s;
	int t;						//Väliaikainen muuttuja summattavalle luvule
	private volatile boolean stop;
	
	//Konstruktori
	public Summauspalvelija(int portti, Lokero l){
		super();
		this.PORT = portti;
		this.lokero = l;
		t = 1;
	}//konstruktori
	
	public void run(){
		try{
			server = new ServerSocket(PORT);
			s = server.accept();
			InputStream iS = s.getInputStream();
			ObjectInputStream in = new ObjectInputStream(iS);
			s.setSoTimeout(3000);
			
			while(true && !stop){
				try{
					t = in.readInt();
					if(t == 0){
						//requestStop();
						break;
					}
				} catch (EOFException eof){
					System.out.println("Tapahtuuko EOF?");
					break;
				}
				lokero.lisaaLuku(t, PORT);
			}//while		
		} catch (Exception e){
			throw new Error(e.toString());
		}
		
		System.out.println("Mennäänkö tänne? Onko elossa? " + Thread.currentThread().isAlive());
		/*
		try{
			s.close();
			server.close();
			Thread.currentThread().interrupt();
			
		} catch (IOException a){
			throw new Error(a.toString());
		}
		*/
	}//run
	
	public void requestStop(){ //TODO
		stop = true;
		//s.close();
		//server.close();
		Thread.currentThread().interrupt();
		try{
			
			s.close();
			server.close();
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
}//Summauspalvelija