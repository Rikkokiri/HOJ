import java.io.IOException;

public class X{
	public static void main(String[] args) throws IOException{
		
		// Argumenttina ip osoite, ilman yhdistää local hostiin
		
		if (args.length == 0){
			Yhteydenmuodostus uusiyhteys = new Yhteydenmuodostus();
			boolean vastausSaatu = uusiyhteys.lahetaPortti();
			
			if(vastausSaatu == false){
				System.out.println("Suljetaan sovellus.");
				System.exit(2000);
			}
			
		}
		else{
			Yhteydenmuodostus uusiyhteys = new Yhteydenmuodostus(args[0]);
			boolean vastausSaatu = uusiyhteys.lahetaPortti();
			
			if(vastausSaatu == false){
				System.out.println("Suljetaan sovellus.");
				System.exit(2000);
			}
			
		}
		
	}//main

}//X

