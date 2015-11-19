import java.io.IOException;

public class X{
	public static void main(String[] args) throws IOException{
		
		// Argumenttina ip osoite, ilman yhdistää local hostiin
		
		if (args.length == 0){
			Yhteydenmuodostus uusiyhteys = new Yhteydenmuodostus();
			uusiyhteys.lahetaPortti();
		}
		else{
			Yhteydenmuodostus uusiyhteys = new Yhteydenmuodostus(args[0]);
			uusiyhteys.lahetaPortti();
		}
		
	}//main

}//X

