import java.io.*;
import java.net.*;

public class Kommunikaattori extends Thread{

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private int porttienLkm;
	private int[] portit;
	
	private Lokero lokero;
	
	//Konstruktori
	public Kommunikaattori(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try{
			
			System.out.println("Käynnistetään Kommunikaattori-thread..."); //REMOVE

			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			
			out = new ObjectOutputStream(output);
			in = new ObjectInputStream(input);
			
			socket.setSoTimeout(5000);
			
		}catch(IOException e){
			//TODO
		}
		
		
		//Porttien lukumäärä Y:ltä
		try{
				porttienLkm = in.readInt();
				System.out.println("porttienLkm tarvitaan: " + porttienLkm); //TODO
				
				//METODIKUTSU luo summauspalvelijat yms yms.
				//TODO
				alustaSummauspalvelijat();
				
		}catch(SocketTimeoutException e){ //Porttien lukumäärää ei saatu.
			//Lähetetään Y:lle -1
			try{
				out.writeInt(-1);
			} catch (IOException exception){
				//TODO
			}
			
			System.out.println("Ei saa.");
			
			//TODO Sammuta ohjelma
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//TODO kommentoi
		
		//TODO kommentoi
			
		try{
			socket.setSoTimeout(0);
			
			int syöte;
			
			while(true){
				syöte = in.readInt();
			
				if(syöte == 0){
					break;
					//TODO sammutus
				}
				
				//Palautetaan välitettyjen lukujen kokonaissumma
				else if(syöte == 1){
					out.writeInt(lokero.kokonaissumma());
				}
				
				else if(syöte == 2){
					out.writeInt(lokero.suurinSumma());
				}
				
				else if(syöte == 3){
					out.writeInt(lokero.lukujenKokonaismaara());
				}
				else{
					out.writeInt(-1);
				}
				
			} // while
			
		}catch(IOException e){
			
		}
		
		//break hyppää tänne
		
	}// run
	
	public void alustaSummauspalvelijat(){ //Nimeä uudelleen
		
		//Alustetaan portit //TODO
		for(int i = 0; i < porttienLkm; i++ ){
			portit[i] = 20189 + i;
		}
		
		//Luodaan tallenustila summauspalvelijoiden saamille luvuille
		lokero = new Lokero(porttienLkm, portit);
		
		try{
			for(int i = 0; i < porttienLkm; i++){
				Summauspalvelija p = new Summauspalvelija(20189 + i, lokero);
				p.start();
				out.writeInt(20189 + i);
			}
		} catch (IOException e){
			e.setStackTrace(getStackTrace()); //TODO
		}
		
	}
		
}//Kommunikaattori-luokka