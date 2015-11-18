import java.io.*;
import java.net.*;

public class Kommunikaattori extends Thread{

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private int portteja;
	
	public Kommunikaattori(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try{
			
			System.out.println("Käynnistetään Kommunikaattori-thread..."); //REMOVE

			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			
			ObjectOutputStream out = new ObjectOutputStream(output);
			ObjectInputStream in = new ObjectInputStream(input);
			
			
		}catch(IOException e){
			//TODO
		}
		
			
			while(true){
				try{
					
					portteja = in.readInt();
					
				}catch(IOException e){
					//TODO
				}
			}
		
	}
	
}//Kommunikaattori