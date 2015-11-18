import java.io.*;
import java.net.*;

public class Summauspalvelija extends Thread{
	
	final int PORT;
	Lokero lokero;
	
	//Konstruktori
	public Summauspalvelija(int portti, Lokero l){
		super();
		this.PORT = portti;
		this.lokero = l;
	}//konstruktori
	
	public void run(){
		try{
		int t;						//Väliaikainen muuttuja summattavalle luvule
		ServerSocket server = new ServerSocket(PORT);
		Socket s = server.accept();
		InputStream iS = s.getInputStream();
		ObjectInputStream in = new ObjectInputStream(iS);
		//Lisätään lukuja kunnes tulee 0
		while(true){
			t = in.readInt();
			if (t == 0){ break;}
			lokero.lisaaLuku(t, PORT);
		}
		s.close();
		server.close();
		} catch (Exception e){
			throw new Error(e.toString());
		}
	}//run
	
	
}//Summauspalvelija