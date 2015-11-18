import java.io.*;
import java.net.*;

public class Summauspalvelija extends Thread{
	
	final int PORT;
	Lokero lokero;
	
	public Summauspalvelija(int portti, Lokero l){
		super();
		this.PORT = portti;
		this.lokero = l;
	}//konstruktori
	
	public void run(){
		try{
		int t;
		ServerSocket server = new ServerSocket(PORT);
		Socket s = server.accept();
		s.setSoTimeout(3000);
		InputStream iS = s.getInputStream();
		ObjectInputStream in = new ObjectInputStream(iS);
		while(true){
			t = in.readInt();
			if (t == 0){ break;}
			lokero.lisaaLuku(t, PORT);
		}
		s.close();
		} catch (Exception e){
			throw new Error(e.toString());
		}
	}//run
	
	
}//Summauspalvelija