import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class KommunikaattoriTesti {

	//Y:n portti, johon sovellus ottaa yhteyttä
	private static final int YPORT = 3126; //Suojausmääre? 
	private static final int XPORT = 3000; //TODO määrittele/vaihda
	private DatagramSocket udpSocket;
	private DatagramPacket udpPacket;
	//Testausta varten
	private InetAddress y;

	//TCP
	private Socket socket;
	private ServerSocket server;

	
	//Konstruktori
	public KommunikaattoriTesti() throws IOException{

		//-- UDP --------
		//Luodaan UDP-soketti
		udpSocket = new DatagramSocket();
		
		//Luodaan UDP-paketti
		byte[] data = Integer.toString(XPORT).getBytes();
		y = InetAddress.getLocalHost();
		udpPacket = new DatagramPacket(data, data.length, y, YPORT);
		//-- TCP --------
		server = new ServerSocket(XPORT);

	}//Konstruktori

	//TODO Nimeä metodi fiksummin
	//lahetaPortti: Lähettää Y:lle UDP-paketin, joka 
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



