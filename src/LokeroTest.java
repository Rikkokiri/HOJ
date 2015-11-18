
public class LokeroTest {
		public static void main(String args[]){
			int[] a = {1,2,3};
			Lokero lokero = new Lokero(3,a);
			lokero.lisaaLuku(12, 1);
			lokero.lisaaLuku(34, 2);
			lokero.lisaaLuku(6, 3);
			lokero.lisaaLuku(8, 1);
			lokero.lisaaLuku(8, 2);
			lokero.lisaaLuku(8, 3);
			lokero.lisaaLuku(8, 1);
			int[][] asd = lokero.getSummat();
			System.out.println("lukujen kokonaismäärä: " + lokero.lukujenKokonaismaara() + 
					" Suurin summa: " + lokero.suurinSumma() + 
					" Kokonaissumma: " + lokero.kokonaissumma());
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < 3;j++){
					System.out.print(asd[j][i] +" ");
				}
				System.out.println("");
			}
			
		}
}
