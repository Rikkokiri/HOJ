import java.io.*;
import java.net.*;

public class Yhteydenmuodostus {

	
	private static final int YPORT = 3129; //Y:n portti, johon sovellus ottaa yhteyttä
	private static final int XPORT = 3000; //X:n portti, johon Y:n halutaan ottavan yhteyttä
	private DatagramSocket udpSocket; 
	private DatagramPacket udpPacket;
	
	//Kohteen osoite
	private InetAddress kohdeosoite;

	//TCP-soketit
	private Socket socket;
	private ServerSocket server;
	
	//Konstruktori, joka vaatii parametriksi ip-osoitteen (String)
	public Yhteydenmuodostus(String iposoite) throws IOException{

		//-- UDP --------
		//Luodaan UDP-soketti
		udpSocket = new DatagramSocket();
		//Valmistellaan udp-pakettiin tarvittavat tiedot, data ja kohdeosoite
		byte[] data = Integer.toString(XPORT).getBytes();
		kohdeosoite = InetAddress.getByName(iposoite);
		//Luodaan UDP-paketti
		udpPacket = new DatagramPacket(data, data.length, kohdeosoite, YPORT);
		
		//-- TCP --------
		server = new ServerSocket(XPORT);

	}//Konstruktori

	//Konstruktori, jota kutsutaan, kun suoritetaan Y:tä ja X:ää samalla koneella
	//attribuutti kohdeosoite = localhost
		public Yhteydenmuodostus() throws IOException{

			//-- UDP --------
			//Luodaan UDP-soketti
			udpSocket = new DatagramSocket();
			//Valmistellaan udp-pakettiin tarvittavat tiedot, data ja kohdeosoite
			byte[] data = Integer.toString(XPORT).getBytes();
			kohdeosoite = InetAddress.getLocalHost();
			//Luodaan UDP-paketti
			udpPacket = new DatagramPacket(data, data.length, kohdeosoite, YPORT);
			
			//-- TCP --------
			server = new ServerSocket(XPORT);

		}//Konstruktori
	
	//-------------------------------------------------	
		
	/* lahetaPortti: Lähettää Y:lle UDP-paketin, joka sisältää X:n portin,
	 * johon Y:n halutaan ottavan yhteyttä */
	public boolean lahetaPortti() throws IOException{
		
		int toistoja = 0;
		boolean vastausSaatu = false;

		//Asetetaan ServerSocketin timeout = 5 s
		server.setSoTimeout(5000);

		while(toistoja < 5 && !vastausSaatu){
			
			udpSocket.send(udpPacket);

			try{
				System.out.println("Päästäänkö tänne?");
				socket = server.accept();
				
				//Testausta varten
				System.out.println("Connection established to port" + socket.getPort());
				//COMMENT
				new Kommunikaattori(socket).start();
				
				vastausSaatu = true;

			}catch(SocketTimeoutException e){
				//Vastausta ei saatu, jatketaan while-loopin suoritusta
				toistoja++;
				System.out.println(toistoja + " yritys takana");
			}
		}
		//Kerrotaan lopuksi saatiinko vastaus eli onko TCP-yhteys muodostettu
		return vastausSaatu;
	}

}//Kommunikaattori



