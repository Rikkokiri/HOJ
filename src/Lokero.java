
public class Lokero {

	private int[][] summat; //Summauspalvelijoiden summien lista ja niiden portit
	private int summaustenLkm;

	//Konstruktori
	//lkm = summauspalvelijoiden lukumäärä
	public Lokero(int lkm, int[] portit){
		this.summat = new int[lkm][2];
		this.summaustenLkm = 0;
		for (int i = 0; i < lkm; i++){
			summat[i][1] = portit[i];
		}
	}
	
	
	//Kaikkien summauspalvelijoiden kokonaissumma
	public int kokonaissumma(){
		int summa = 0;
		for (int i = 0; i < summat.length; i++){
			summa = summa + summat[i][0];
		}
		System.out.println("Summa on" + summa);
		return summa;
	}
	
	
	//Palauttaa summauspalvelijan portin, jossa laskettu suurin summa
	public int suurinSumma(){
		int suurin = 0;
		int index=0;
		for (int i = 0; i < summat.length; i++){
			if (summat[i][0] > suurin){
				suurin = summat[i][0];
				index = i;
			}
		}//for
		return index +1;
	}
	
	
	//Palauttaa summattujen lukujen kokonaismäärän
	public int lukujenKokonaismaara(){
		return this.summaustenLkm;
	}
	
	
	//Lisää summat -taulukkoon tietyn summauspalvelijan saaman luvun
	synchronized public void lisaaLuku(int luku, int portti){
		//summaustenLkm = summaustenLkm + 1;
		
		for (int i = 0; i < summat.length;i++){
			if (summat[i][1] == portti){
				summat[i][0] = summat[i][0] + luku;
				break;
			}
		}//for	
		
		summaustenLkm = summaustenLkm + 1;
		
	}
	
	
	//Turha, käytettiin testauksessa
	public int[][] getSummat(){
		return this.summat;
	}
	
}
