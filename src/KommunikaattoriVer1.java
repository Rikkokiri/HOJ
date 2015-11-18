package src;

import java.io.*;
import java.net.*;

public class Kommunikaattori {

	//Y:n portti, johon sovellus ottaa yhteyttä
	private static final int YPORT = 3126; //Suojausmääre? 
	private static final int int XPORT = 3000; //TODO määrittele/vaihda
	private DatagramSocket udpSocket;
	public DatagramPacket udpPacket;

	//TCP
	private Socket socket;
	private ServerSocket server;

	
	//Konstruktori
	public Kommunikaattori(){

		//-- UDP --------
		//Luodaan UDP-soketti
		udpSocket = new DatagramSocket();
		byte[] data = port.getBytes();
		//Luodaan UDP-paketti
		udpPacket = new DatagramPacket(data, data.length, y, YPORT);

		//-- TCP --------
		ServerSocket server = new ServerSocket(XPORT);

	}//Konstruktori

	//TODO Nimeä metodi fiksummin
	//lahetaPortti: Lähettää Y:lle UDP-paketin, joka 
	public boolean lahetaPortti(){
		
		int toistoja = 0;
		boolean vastausSaatu = false;

		//Asetetaan ServerSocketin timeout = 5 s
		server.setSoTimeout(5000);

		while(toistoja < 5 && !vastausSaatu){
			
			udpSocket.send(udpPacket);

			try{
				socket = server.accept();
				//Testausta varten
				System.out.println("Connection established to port" + socket.getPort());
				vastausSaatu = true;

			}catch(SocketTimeoutException e){
				//Vastausta ei saatu, jatketaan while-loopin suoritusta
				toistoja++;
			}
		}
		//Kerrotaan lopuksi saatiinko vastaus eli onko TCP-yhteys muodostettu
		return vastausSaatu;
	}



}//Kommunikaattori


