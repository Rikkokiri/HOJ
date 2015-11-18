
public class LokeroTest {
		public static void main(String args[]){
			Lokero lokero = new Lokero(3);
			lokero.lisaaLuku(12, 1);
			lokero.lisaaLuku(34, 2);
			lokero.lisaaLuku(6, 3);
			lokero.lisaaLuku(8, 1);
			lokero.lisaaLuku(8, 2);
			lokero.lisaaLuku(8, 3);
			lokero.lisaaLuku(8, 1);
			System.out.println("lukujen kokonaismäärä: " + lokero.lukujenKokonaismaara() + 
					" Suurin summa: " + lokero.suurinSumma() + 
					" Kokonaissumma: " + lokero.kokonaissumma());
			
		}
}
