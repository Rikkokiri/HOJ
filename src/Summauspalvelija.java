import java.io.*;
import java.net.*;

public class Summauspalvelija extends Thread{
	
	final int PORT; // Portti, jota summauspalvelija kuuntelee
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
			s = server.accept(); // Yhteyden muodostus
			
			//Datavirtojen luonti
			InputStream iS = s.getInputStream();
			ObjectInputStream in = new ObjectInputStream(iS);
			
			s.setSoTimeout(3000);
			
			// Looppi lukujen vastaanottamiseen/lukemiseen
			while(!stop){
				try{
					t = in.readInt(); //Luetaan datavirrasta seuraava summattava luku
					if(t == 0){
						break;
					}
				} catch (EOFException eof){
					break;
				}
				// Summattavan luvun lisäys lokero-olioon
				lokero.lisaaLuku(t, PORT);
			}//while		
			
		} catch (Exception e){
			throw new Error(e.toString());
		}
	}//run
	
	// Metodi, jolla tapetaan thread (suljetaan soketit)
	public void requestStop(){ //TODO
		stop = true;
		
		try{
			s.close();
			server.close();
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
}//Summauspalvelija