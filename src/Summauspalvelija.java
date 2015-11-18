import java.io.*;
import java.net.*;

public class Summauspalvelija extends Thread{
	
	final int PORT;
	Lokero lokero;
	ServerSocket server;
	Socket s;
	int t;						//Väliaikainen muuttuja summattavalle luvule
	
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
			//Lisätään lukuja kunnes tulee 0
			while(t!=0){
				t = in.readInt();
				lokero.lisaaLuku(t, PORT);
			}//while		
		} catch (Exception e){
			throw new Error(e.toString());
		}
		try{
			s.close();
			server.close();
		} catch (IOException a){
			throw new Error(a.toString());
		}
	}//run
	
	//Sammutusmetodi
	public void close(){
	t = 0;
	}
	
}//Summauspalvelija