
public class Lokero {

	int[][] summat; //Summauspalvelijoiden summien lista ja niiden portit
	int summaustenLkm;

	//Konstruktori
	//lkm = summauspalvelijoiden lukum‰‰r‰
	public Lokero(int lkm){
		this.summat = new int[lkm][2];
		this.summaustenLkm = 0;
	}//Konstruktori
	
	
	//Kaikkien summauspalvelijoiden kokonaissumma
	public int kokonaissumma(){
		int summa = 0;
		for (int i = 0; i < summat.length; i++){
			summa = summa + summat[i][0];
		}//for
		return summa;
	}//kokonaissumma
	
	
	//Palauttaa summauspalvelijan portin, jossa laskettu suurin summa
	public int suurinSumma(){
		int suurin = 0;
		int portti = 0;
		for (int i = 0; i < summat.length; i++){
			if (summat[i][0] > suurin){
				suurin = summat[i][0];
				portti = summat[i][1];
			}//if
		}//for
		return portti;
	}//suurinSumma
	
	
	//Palauttaa summattujen lukujen kokonaism‰‰r‰n
	public int lukujenKokonaismaara(){
		return this.summaustenLkm;
	}//lukujenKokonaismaara
	
	
	//Lis‰‰ summat -taulukkoon tietyn summauspalvelijan saaman luvun
	public void lisaaLuku(int luku, int portti){
		summaustenLkm = summaustenLkm + 1;
		for (int i = 0; i < summat.length;i++){
			if (summat[i][1] == portti){
				summat[i][0] = summat[i][0] + luku;
				break;
			}//if
		}//for
	}//lisaaLuku
	
}
