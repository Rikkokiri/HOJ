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
			//Lopetetaan thread
			return;
		}
		
		
		//Porttien lukumäärä Y:ltä
		try{
				porttienLkm = in.readInt();
				
				//METODIKUTSU luo summauspalvelijat yms yms.
				alustaSummauspalvelijat();
				
		}catch(SocketTimeoutException e){ //Porttien lukumäärää ei saatu.
			//Lähetetään Y:lle -1
			try{
				out.writeInt(-1);
				out.flush();
				return;
			} catch (IOException exception){
				return;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
			
		try{
			socket.setSoTimeout(0);

			while(true){
				viesti = in.readInt();
			
				if(viesti == 0){
					break;
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
			return;
		}
		
		//break hyppää tänne
		
		//Lopetetaan summauspalvelijat
				for(int i = 0; i < porttienLkm; i++){
					summauspalvelijat[i].requestStop();
					System.out.println("Pysäytettiinkö thread " + summauspalvelijat[i].getName() + "?");
				}
		
		for(int i = 0; i < porttienLkm; i++){
			System.out.println("Is " + summauspalvelijat[i] + " alive? " + summauspalvelijat[i].isAlive());
		}
		
		try{
			out.close();
			in.close();
			socket.close();
			return;
		} catch(IOException e){
			return;
		}
			
	}// run
	
	public void alustaSummauspalvelijat(){
		
		//Alustetaan portit
		portit = new int[porttienLkm];
		
		for(int i = 0; i < porttienLkm; i++ ){
			portit[i] = 20189 + i;
		}
		
		//Luodaan tallenustila summauspalvelijoiden saamille luvuille
		lokero = new Lokero(porttienLkm, portit);
		
		//Alustetaan taulukko, johon summauspalvelijat tallennetaan
		summauspalvelijat = new Summauspalvelija[porttienLkm];
		
		try{
			for(int i = 0; i < porttienLkm; i++){
				summauspalvelijat[i] = new Summauspalvelija(20189 + i, lokero);
				summauspalvelijat[i].start();
				out.writeInt(portit[i]);
				out.flush();
			}
		} catch (IOException e){
			e.setStackTrace(getStackTrace());
		}	
	}

}//Kommunikaattori-luokka