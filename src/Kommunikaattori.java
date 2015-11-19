import java.io.*;
import java.net.*;

public class Kommunikaattori extends Thread{

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int viesti;
	
	private int porttienLkm;
	private int[] portit;
	
	private Lokero lokero;
	private Summauspalvelija[] summauspalvelijat;
	
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
				out.flush();
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
			
			
			
			while(true){
				viesti = in.readInt();
			
				if(viesti == 0){
					break;
					//TODO sammutus
				}
				
				//Palautetaan välitettyjen lukujen kokonaissumma
				else if(viesti == 1){
					out.writeInt(lokero.kokonaissumma());
					out.flush();
				}
				
				else if(viesti == 2){
					out.writeInt(lokero.suurinSumma());
					out.flush();
				}
				
				else if(viesti == 3){
					out.writeInt(lokero.lukujenKokonaismaara());
					out.flush();
				}
				else{
					out.writeInt(-1);
					out.flush();
				}
				
			} // while
			
		}catch(IOException e){
			
		}
		
		//break hyppää tänne
		
		System.out.println("Ohjelman suorituksen tulisi päättyä."); //REMOVE
		
		/*
		//Lopetetaan summauspalvelijat
		for(int i = 0; i < porttienLkm; i++){
			summauspalvelijat[i].interrupt();
			System.out.println("Pysäytettiinkö thread?");
			//summauspalvelijat[i].close();
		}
		*/
		
		//Lopetetaan summauspalvelijat
				for(int i = 0; i < porttienLkm; i++){
					summauspalvelijat[i].requestStop();
					System.out.println("Pysäytettiinkö thread " + summauspalvelijat[i].getName() + "?");
					//System.out.println(summauspalvelijat[i].isInterrupted());
					//summauspalvelijat[i].close();
				}
		
		for(int i = 0; i < porttienLkm; i++){
			System.out.println("Is " + summauspalvelijat[i] + " alive? " + summauspalvelijat[i].isAlive());
		}
		
		try{
			out.close();
			in.close();
			socket.close();
		} catch(IOException e){
			//TODO
		}
		
		
		
	}// run
	
	public void alustaSummauspalvelijat(){ //Nimeä uudelleen
		
		//Alustetaan portit //TODO
		portit = new int[porttienLkm];
		
		for(int i = 0; i < porttienLkm; i++ ){
			portit[i] = 20189 + i;
		}
		
		//Luodaan tallenustila summauspalvelijoiden saamille luvuille
		lokero = new Lokero(porttienLkm, portit);
		
		summauspalvelijat = new Summauspalvelija[porttienLkm];
		
		try{
			for(int i = 0; i < porttienLkm; i++){
				summauspalvelijat[i] = new Summauspalvelija(20189 + i, lokero);
				summauspalvelijat[i].start();
				out.writeInt(portit[i]);
				out.flush();
			}
		} catch (IOException e){
			e.setStackTrace(getStackTrace()); //TODO
		}
		
	}
		
}//Kommunikaattori-luokka