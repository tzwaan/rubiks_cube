/*
 * Cube.java
 * 
 * Dit is een virtuele rubiks cube. Hij wordt gerepresenteerd door
 * een 4-dimensionale array, met in iedere dimensie 3 mogelijke
 * posities.
 * De eerste 3 dimensies geven aan waar ieder blokje zich bevindt.
 * Dit is dus een letterlijke weergave van hoe de rubiks cube
 * blokjes in het echt ook zijn geordent.
 * De 4e dimensie geeft aan welke kleuren de kanten van het blokje
 * hebben. Hierbij zijn er 3 plaatsen waar de kleur zich kan
 * bevinden;
 * 		0 - boven, onder
 * 		1 - voor, achter
 * 		2 - links, rechts
 * Omdat de blokjes van een rubiks cube altijd alleen maar een
 * kleur kunnen hebben aan de boven, of de onderkant, is dit 
 * genoeg om aan te geven hoe de blokjes gekleurd zijn.
 * De kleuren zelf zijn zo aangegeven:
 * 1 = wit
 * 2 = rood
 * 3 = groen
 * 4 = oranje
 * 5 = blauw
 * 6 = geel
 * (0 = zwart)
 * 
 * Waarbij zwart aangeeft dat er geen kleur is. Dit gebeurt bij 
 * alle niet-hoek blokjes, omdat deze allemaal minder dan 2 
 * vlakjes hebben.
 * 
 * ErrorCodes:
 * 		 1 - Wrong cube inserted. the given cube does not exist/is not possible
 * 		37 - The returned edge block has an impossible location
 * 		38 - The returned corner block has an impossible location
 */
package rubiks.Cube;
import java.util.Random;


public class Cube {
	int[][][][] blocks = new int[3][3][3][3];
	String solution = "";
	
	public Cube() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				blocks[x][y][0][0] = 1;
				blocks[x][y][2][0] = 6;
			}
		}
		for (int y = 0; y < 3; y++) {
			for (int z = 0; z < 3; z++) {
				blocks[0][y][z][2] = 3;
				blocks[2][y][z][2] = 5;
			}
		}
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				blocks[x][0][z][1] = 4;
				blocks[x][2][z][1] = 2;
			}
		}
	}
	
	public Cube(int[][][][] blocks) {
		int[] rubiksCheck = new int[7];
		boolean faulty = false;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					for (int colors = 0; colors < 3; colors++) {
						this.blocks[x][y][z][colors] = blocks[x][y][z][colors];
						rubiksCheck[blocks[x][y][z][colors]]++;
					}
				}
			}
		}
		for (int i = 1; i <= 6; i ++) {
			if (rubiksCheck[i] != 9) faulty = true;
		}
		if (faulty) {
			System.out.println("this is not a correct rubiks cube. One ore more squares have the wrong or no colour");
			System.exit(1);
		}
	}
	
	public float[] nrToRGB(int a) {
		switch (a) {
		case 1: {float[] r = {1,1,1}; return r;}
		case 2: {float[] r = {1,0,0}; return r;}
		case 3: {float[] r = {0,1,0}; return r;}
		case 4: {float[] r = {1,0.5f,0}; return r;}
		case 5: {float[] r = {0,0,1}; return r;}
		case 6: {float[] r = {1,1,0}; return r;}
		}
		float[] r = {0,0,0};
		return r;
	}
	
	/*
	 * draai een van de (x,y)-vlakken rond de z as.
	 * hierbij is 'z' het vlak dat je draait getelt vanaf
	 * bovenaf, en is 'amount' het aantal kwartslagen dat
	 * hij maakt. (4 zorgt dus dat hij weer in de
	 * beginpositie uitkomt)
	 */
	public void turnAroundZ(int z, int amount, boolean push) {
		int[][][] temp = new int[3][3][3];
		
		if (amount % 4 == 0) return;
		
		for (int j = 0; j < amount; j++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					temp[2-y][x][0] = blocks[x][y][z][0];
					temp[2-y][x][1] = blocks[x][y][z][2];
					temp[2-y][x][2] = blocks[x][y][z][1];
				}
			}
			
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					blocks[x][y][z][0] = temp[x][y][0];
					blocks[x][y][z][1] = temp[x][y][1];
					blocks[x][y][z][2] = temp[x][y][2];
				}
			}
		}
		if (push) {
			solution = ("z-"+z+"-"+(4-amount)+",").concat(solution);
		}
	}
	
	/*
	 * draait de hele kubus rond de z-as
	 */
	public void rotateAroundZ(int amount) {
			turnAroundZ(0, amount, true);
			turnAroundZ(1, amount, true);
			turnAroundZ(2, amount, true);
	}
	
	/*
	 * basically hetzelfde als turnAroundZ(), maar
	 * dan rond de y-as
	 */
	public void turnAroundY(int y, int amount, boolean push) {
		int[][][] temp = new int[3][3][3];
		
		if (amount % 4 == 0) return;
		
		for (int j = 0; j < amount; j++) {
			for (int x = 0; x < 3; x++) {
				for (int z = 0; z < 3; z++) {
					temp[2-z][x][1] = blocks[x][y][z][1];
					temp[2-z][x][0] = blocks[x][y][z][2];
					temp[2-z][x][2] = blocks[x][y][z][0];
				}
			}
			
			for (int x = 0; x < 3; x++) {
				for (int z = 0; z < 3; z++) {
					blocks[x][y][z][0] = temp[x][z][0];
					blocks[x][y][z][1] = temp[x][z][1];
					blocks[x][y][z][2] = temp[x][z][2];
					
				}
			}
		}
		if (push) {
			solution = ("y-"+y+"-"+(4-amount)+",").concat(solution);
		}
	}
	
	/*
	 * draait de hele kubus rond de y-as
	 */
	public void rotateAroundY(int amount) {
		turnAroundY(0, amount, true);
		turnAroundY(1, amount, true);
		turnAroundY(2, amount, true);
	}
	
	/*
	 * basically hetzelfde als turnAroundZ(), maar
	 * dan rond de x-as
	 */
	public void turnAroundX(int x, int amount, boolean push) {
		int[][][] temp = new int[3][3][3];
		
		if (amount == 0) return;
		
		for (int j = 0; j < amount; j++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					temp[2-z][y][2] = blocks[x][y][z][2];
					temp[2-z][y][0] = blocks[x][y][z][1];
					temp[2-z][y][1] = blocks[x][y][z][0];
				}
			}
			
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					blocks[x][y][z][0] = temp[y][z][0];
					blocks[x][y][z][1] = temp[y][z][1];
					blocks[x][y][z][2] = temp[y][z][2];
				}
			}
		}
		if (push) {
			solution = ("x-"+x+"-"+(4-amount)+",").concat(solution);
		}
	}
	
	/*
	 * draait de hele kubus rond de x-as
	 */
	public void rotateAroundX(int amount) {
		turnAroundX(0, amount, true);
		turnAroundX(1, amount, true);
		turnAroundX(2, amount, true);
	}
	
	/*
	 * print de kubus naar LogCat op de manier zoals
	 * hij wordt opgeslagen in de 4-dimensionale array
	 */
	public void printCube() {
		String output = "";
		for (int z = 0; z < 3; z++) {
			for (int y = 0; y < 3; y++) {	
				for (int x = 0; x < 3; x++) {
					for (int color = 0; color < 3; color++) {
						output = output.concat("" + blocks[x][y][z][color]);
					}
					output = output.concat(" ");
				}
				output = output.concat("\n");
			}
			output = output.concat(" \n");
		}
		System.out.println(output);
	}
	
	/*
	 * print de kubus naar LogCat als een uitvouw
	 * van hoe de kubus in het echt eruit zou zien
	 */
	public void printCubeFaces() {
		String output = "";
		for (int y = 0; y < 3; y++) {
			output = output.concat("    ");
			for (int x = 0; x < 3; x++) {
				output = output.concat("" + blocks[x][y][0][0]);
			}
			output = output.concat("\n");
		}
		for (int z = 0; z < 3; z++) {
			for (int y = 0; y < 3; y++) {
				output = output.concat("" + blocks[0][y][z][2]);
			}
			output = output.concat(" ");
			for (int x = 0; x < 3; x++) {
				output = output.concat("" + blocks[x][2][z][1]);
			}
			output = output.concat(" ");
			for (int y = 2; y >= 0; y--) {
				output = output.concat("" + blocks[2][y][z][2]);
			}
			output = output.concat(" ");
			output = output.concat("" + blocks[2][0][z][1]);
			output = output.concat("" + blocks[1][0][z][1]);
			output = output.concat("" + blocks[0][0][z][1]);
			output = output.concat("\n");
		}
		for (int y = 2; y >= 0; y--) {
			output = output.concat("    ");
			for (int x = 0; x < 3; x++) {
				output = output.concat("" + blocks[x][y][2][0]);
			}
			output = output.concat("\n");
		}
		System.out.println(output + " \n");
	}
	
	/*
	 * gooit de kubus door de war door middel van
	 * 20 willekeurige draaiingen.
	 * Er is bewezen dat je iedere mogelijke rubiks cube
	 * door middel van 20 stappen op kan lossen, dus andersom
	 * is het mogelijk in 20 stappen bij iedere mogelijke
	 * staat van de rubiks cube te komen.
	 */
	public void shuffleCube() {
		Random gen = new Random();
		int a, b, c;
		for (int i = 0; i < 20; i++) {
			a = gen.nextInt(3);
			b = gen.nextInt(3);
			c = gen.nextInt(3) + 1;
			switch(a) {
			case 0: turnAroundX(b, c, true); break;
			case 1: turnAroundY(b, c, true); break;
			case 2: turnAroundZ(b, c, true); break;
			}
		}
	}
	
	/*
	 * kijkt of de rubiks cube opgelost is.
	 * returnt true als hij is opgelost en anders returnt hij false.
	 */
	public boolean isSolved() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (blocks[x][y][0][0] != 1 || blocks[x][y][2][0] != 6) return false;
			}
		}
		for (int y = 0; y < 3; y++) {
			for (int z = 0; z < 3; z++) {
				if (blocks[0][y][z][2] != 3 || blocks[2][y][z][2] != 5) return false;
			}
		}
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				if (blocks[x][0][z][1] != 4 || blocks[x][2][z][1] != 2) return false;
			}
		}
		return true;
	}
	
	public void orientateTop() {
		if (blocks[1][1][0][0] == 1) return;
		if (blocks[1][2][1][1] == 1) {
			rotateAroundX(3); return;
		}
		if (blocks[0][1][1][2] == 1) {
			rotateAroundY(1); return;
		}
		if (blocks[1][0][1][1] == 1) {
			rotateAroundX(1); return;
		}
		if (blocks[2][1][1][2] == 1) {
			rotateAroundY(3); return;
		}
		if (blocks[1][1][2][0] == 1) {
			rotateAroundX(2); return;
		}
	}
	
	public void orientateSides() {
		if (blocks[1][2][1][1] == 2) return;
		if (blocks[0][1][1][2] == 2) {
			rotateAroundZ(3); return;
		}
		if (blocks[1][0][1][1] == 2) {
			rotateAroundZ(2); return;
		}
		if (blocks[2][1][1][2] == 2) {
			rotateAroundZ(1); return;
		}
	}
	
	public void orientateCube() {
		orientateTop();
		orientateSides();
	}
	
	/*
	 * checkt of er op het bovenste vlak een kloppend kruis
	 * is gemaakt. returned de waarde van het eerste vlak
	 * dat hij tegen komt waarin een fout blok zit. (of het
	 * blok is gedraaid)
	 * als hij klopt, wordt 0 returned
	 */
	public int topFaceCrossCheck() {
		if (blocks[1][2][0][0] != 1) return 2;
		else if (blocks[1][2][0][1] != 2) return 2;
		
		if (blocks[0][1][0][0] != 1) return 3;
		else if (blocks[0][1][0][2] != 3) return 3;
		
		if (blocks[1][0][0][0] != 1) return 4;
		else if (blocks[1][0][0][1] != 4) return 4;
		
		if (blocks[2][1][0][0] != 1) return 5;
		else if (blocks[2][1][0][2] != 5) return 5;
		
		return 0;
	}
	
	public int topFaceCornerCheck() {
		if (!(blocks[0][2][0][0] == 1 && blocks[0][2][0][1] == 2 && blocks[0][2][0][2] == 3)) return 2;
		if (!(blocks[0][0][0][0] == 1 && blocks[0][0][0][1] == 4 && blocks[0][0][0][2] == 3)) return 3;
		if (!(blocks[2][0][0][0] == 1 && blocks[2][0][0][1] == 4 && blocks[2][0][0][2] == 5)) return 4;
		if (!(blocks[2][2][0][0] == 1 && blocks[2][2][0][1] == 2 && blocks[2][2][0][2] == 5)) return 5;
		
		return 0;
	}
	
	public int middleEdgesCheck() {
		if (!(blocks[0][2][1][1] == 2 && blocks[0][2][1][2] == 3)) return 2;
		if (!(blocks[0][0][1][1] == 4 && blocks[0][0][1][2] == 3)) return 3;
		if (!(blocks[2][0][1][1] == 4 && blocks[2][0][1][2] == 5)) return 4;
		if (!(blocks[2][2][1][1] == 2 && blocks[2][2][1][2] == 5)) return 5;
		
		return 0;
	}
	
	public int[] findEdge(int a, int b) {
		if ((blocks[1][2][0][0] == a && blocks[1][2][0][1] == b) || (blocks[1][2][0][0] == b && blocks[1][2][0][1] == a)) {int[] r = {1,2,0}; return r;};
		if ((blocks[0][1][0][0] == a && blocks[0][1][0][2] == b) || (blocks[0][1][0][0] == b && blocks[0][1][0][2] == a)) {int[] r = {0,1,0}; return r;};
		if ((blocks[1][0][0][0] == a && blocks[1][0][0][1] == b) || (blocks[1][0][0][0] == b && blocks[1][0][0][1] == a)) {int[] r = {1,0,0}; return r;};
		if ((blocks[2][1][0][0] == a && blocks[2][1][0][2] == b) || (blocks[2][1][0][0] == b && blocks[2][1][0][2] == a)) {int[] r = {2,1,0}; return r;};
		
		if ((blocks[0][2][1][1] == a && blocks[0][2][1][2] == b) || (blocks[0][2][1][1] == b && blocks[0][2][1][2] == a)) {int[] r = {0,2,1}; return r;};
		if ((blocks[0][0][1][1] == a && blocks[0][0][1][2] == b) || (blocks[0][0][1][1] == b && blocks[0][0][1][2] == a)) {int[] r = {0,0,1}; return r;};
		if ((blocks[2][0][1][1] == a && blocks[2][0][1][2] == b) || (blocks[2][0][1][1] == b && blocks[2][0][1][2] == a)) {int[] r = {2,0,1}; return r;};
		if ((blocks[2][2][1][1] == a && blocks[2][2][1][2] == b) || (blocks[2][2][1][1] == b && blocks[2][2][1][2] == a)) {int[] r = {2,2,1}; return r;};
		
		if ((blocks[1][2][2][0] == a && blocks[1][2][2][1] == b) || (blocks[1][2][2][0] == b && blocks[1][2][2][1] == a)) {int[] r = {1,2,2}; return r;};
		if ((blocks[0][1][2][0] == a && blocks[0][1][2][2] == b) || (blocks[0][1][2][0] == b && blocks[0][1][2][2] == a)) {int[] r = {0,1,2}; return r;};
		if ((blocks[1][0][2][0] == a && blocks[1][0][2][1] == b) || (blocks[1][0][2][0] == b && blocks[1][0][2][1] == a)) {int[] r = {1,0,2}; return r;};
		if ((blocks[2][1][2][0] == a && blocks[2][1][2][2] == b) || (blocks[2][1][2][0] == b && blocks[2][1][2][2] == a)) {int[] r = {2,1,2}; return r;};
		int[] r = {0,0,0};
		return r;
	}
	
	public int[] findCorner(int a, int b, int c) {
		if ((blocks[0][2][0][0] == a || blocks[0][2][0][0] == b || blocks[0][2][0][0] == c) &&
			(blocks[0][2][0][1] == a || blocks[0][2][0][1] == b || blocks[0][2][0][1] == c) &&
			(blocks[0][2][0][2] == a || blocks[0][2][0][2] == b || blocks[0][2][0][2] == c)) {int[] r = {0,2,0}; return r;}
		
		if ((blocks[0][0][0][0] == a || blocks[0][0][0][0] == b || blocks[0][0][0][0] == c) &&
			(blocks[0][0][0][1] == a || blocks[0][0][0][1] == b || blocks[0][0][0][1] == c) &&
			(blocks[0][0][0][2] == a || blocks[0][0][0][2] == b || blocks[0][0][0][2] == c)) {int[] r = {0,0,0}; return r;}
		
		if ((blocks[2][0][0][0] == a || blocks[2][0][0][0] == b || blocks[2][0][0][0] == c) &&
			(blocks[2][0][0][1] == a || blocks[2][0][0][1] == b || blocks[2][0][0][1] == c) &&
			(blocks[2][0][0][2] == a || blocks[2][0][0][2] == b || blocks[2][0][0][2] == c)) {int[] r = {2,0,0}; return r;}
		
		if ((blocks[2][2][0][0] == a || blocks[2][2][0][0] == b || blocks[2][2][0][0] == c) &&
			(blocks[2][2][0][1] == a || blocks[2][2][0][1] == b || blocks[2][2][0][1] == c) &&
			(blocks[2][2][0][2] == a || blocks[2][2][0][2] == b || blocks[2][2][0][2] == c)) {int[] r = {2,2,0}; return r;}

		
		if ((blocks[0][2][2][0] == a || blocks[0][2][2][0] == b || blocks[0][2][2][0] == c) &&
			(blocks[0][2][2][1] == a || blocks[0][2][2][1] == b || blocks[0][2][2][1] == c) &&
			(blocks[0][2][2][2] == a || blocks[0][2][2][2] == b || blocks[0][2][2][2] == c)) {int[] r = {0,2,2}; return r;}
		
		if ((blocks[0][0][2][0] == a || blocks[0][0][2][0] == b || blocks[0][0][2][0] == c) &&
			(blocks[0][0][2][1] == a || blocks[0][0][2][1] == b || blocks[0][0][2][1] == c) &&
			(blocks[0][0][2][2] == a || blocks[0][0][2][2] == b || blocks[0][0][2][2] == c)) {int[] r = {0,0,2}; return r;}
		
		if ((blocks[2][0][2][0] == a || blocks[2][0][2][0] == b || blocks[2][0][2][0] == c) &&
			(blocks[2][0][2][1] == a || blocks[2][0][2][1] == b || blocks[2][0][2][1] == c) &&
			(blocks[2][0][2][2] == a || blocks[2][0][2][2] == b || blocks[2][0][2][2] == c)) {int[] r = {2,0,2}; return r;}
		
		if ((blocks[2][2][2][0] == a || blocks[2][2][2][0] == b || blocks[2][2][2][0] == c) &&
			(blocks[2][2][2][1] == a || blocks[2][2][2][1] == b || blocks[2][2][2][1] == c) &&
			(blocks[2][2][2][2] == a || blocks[2][2][2][2] == b || blocks[2][2][2][2] == c)) {int[] r = {2,2,2}; return r;}
		
		int[] r = {1,1,1};
		return r;
	}
	
	public void solveTopCross() {
		//check of het top-face-cross gesolved is. anders wordt het blok gegeven dat gesolved moet worden
		int topFaceCrossSolved = topFaceCrossCheck();
		
		while(topFaceCrossSolved != 0) {
			System.out.println(topFaceCrossSolved);
			//vindt het blokje dat gesolved moet worden, en stop de xyz coordinaten in array 'b'
			int[] b = findEdge(1, topFaceCrossSolved);
			if (b[0] == 0 && b[1] == 0 && b[2] == 0) {System.out.println("impossible blocklocation found"); System.exit(37);}
			System.out.println("" + b[0] + "-" + b[1] + "-" + b[2]);
			//als het blokje in de bovenste laag ligt, word het naar beneden gedraait, zonder de rest van het kruis te veranderen.
			//er wordt ook voor gezorgt dat het witte vlakje altijd naar beneden wijst.
			if (b[2] == 0) {
				if (blocks[b[0]][b[1]][b[2]][0] == 1) {
					if (b[0] == 1) {
						turnAroundY(b[1], 2, true);
					}
					else {
						turnAroundX(b[0], 2, true);
					}
				}
				else {
					if (b[0] == 1) {
						turnAroundX(0, b[1]+1, true);
						turnAroundY(b[1], 3, true);
						turnAroundX(0, 3-b[1], true);
					}
					else {
						turnAroundY(0, b[0]+1, true);
						turnAroundX(b[0], 3, true);
						turnAroundY(0, 3-b[0], true);
					}
				}
			}
			//blokjes in de middelste laag worden naar de onderste laag gedraaid, waardoor
			//het witte vlakje onder komt te liggen
			else if (b[2] == 1) {
				if (blocks[b[0]][b[1]][b[2]][1] == 1) {
					turnAroundX(b[0], 3-b[1], true);
					turnAroundZ(2, 1, true);
					turnAroundX(b[0], b[1]+1, true);
				}
				else {
					turnAroundY(b[1], 3-b[0], true);
					turnAroundZ(2, 1, true);
					turnAroundY(b[1], b[0]+1, true);
				}
			}
			//blokjes in de onderste laag waarbij het witte blokje niet naar onderen wijst
			//worden zo georienteerd dat die dat wel doet.
			else if (b[2] == 2) {
				if (blocks[b[0]][b[1]][b[2]][0] != 1) {
					if (b[0] == 1) {
						turnAroundX(0, b[1]+1, true);
						turnAroundY(b[1], 1, true);
						turnAroundX(0, 3-b[1], true);
						turnAroundY(b[1], 3, true);
					}
					else if (b[1] == 1) {
						turnAroundY(0, b[0]+1, true);
						turnAroundX(b[0], 1, true);
						turnAroundY(0, 3-b[0], true);
						turnAroundX(b[0], 3, true);
					}
				}
			}
			//blokje wordt opnieuw gezocht/coordinaten geupdate
			b = findEdge(1, topFaceCrossSolved);
			if (b[0] == 0 && b[1] == 0 && b[2] == 0) {System.out.println("impossible blocklocation found"); System.exit(37);}
			//als het blokje in de onderste laag ligt (wat als het goed is altijd het geval is)
			//wordt het blokje naar de goede kant gedraaid, en daarna naar de bovenste laag verplaatst
			if (b[2] == 2) {
				if (b[0] == 1 && b[1] == 2) turnAroundZ(2, topFaceCrossSolved+2, true);
				else if (b[0] == 0 && b[1] == 1) turnAroundZ(2, topFaceCrossSolved+1, true);
				else if (b[0] == 1 && b[1] == 0) turnAroundZ(2, topFaceCrossSolved, true);
				else if (b[0] == 2 && b[1] == 1) turnAroundZ(2, topFaceCrossSolved+3, true);

				if (topFaceCrossSolved == 2) turnAroundY(2, 2, true);
				else if (topFaceCrossSolved == 3) turnAroundX(0, 2, true);
				else if (topFaceCrossSolved == 4) turnAroundY(0, 2, true);
				else if (topFaceCrossSolved == 5) turnAroundX(2, 2, true);
			}
			//check of het top-face-cross gesolved is. anders wordt het geheel herhaald.
			topFaceCrossSolved = topFaceCrossCheck();
			printCubeFaces();
		}
	}
	
	public void solveTopCorners() {
		int topFaceCornersSolved = topFaceCornerCheck();
		
		while(topFaceCornersSolved != 0) {
			System.out.println(topFaceCornersSolved);
			int[] b = findCorner(1, topFaceCornersSolved, (topFaceCornersSolved - 1) % 4 + 2);
			if (b[0] == 1) {System.out.println("impossible blocklocation found"); System.exit(38);}
			System.out.println("" + b[0] + "-" + b[1] + "-" + b[2]);
			printCubeFaces();
			if (b[2] == 0) {
				turnAroundY(b[1], 3-b[0], true);
				turnAroundZ(2, 1, true);
				turnAroundY(b[1], b[0]+1, true);
			}
			b = findCorner(1, topFaceCornersSolved, (topFaceCornersSolved - 1) % 4 + 2);
			System.out.println("" + b[0] + "-" + b[1] + "-" + b[2]);
			if (b[0] == 1) {System.out.println("impossible blocklocation found"); System.exit(38);}
			//check for debugging (should not be necessary)
			if (b[2] == 2) {
				if (b[0] == 0 && b[1] == 2) turnAroundZ(2, topFaceCornersSolved + 2, true);
				else if (b[0] == 0 && b[1] == 0) turnAroundZ(2, topFaceCornersSolved + 1, true);
				else if (b[0] == 2 && b[1] == 0) turnAroundZ(2, topFaceCornersSolved, true);
				else if (b[0] == 2 && b[1] == 2) turnAroundZ(2, topFaceCornersSolved + 3, true);
			}
			b = findCorner(1, topFaceCornersSolved, (topFaceCornersSolved - 1) % 4 + 2);
			if (b[0] == 1) {System.out.println("impossible blocklocation found"); System.exit(38);}
			//check for debugging (should not be necessary)
			if (b[2] == 2) {
				if (blocks[b[0]][b[1]][b[2]][0] == 1) {
					turnAroundY(b[1], 3-b[0], true);
					turnAroundZ(2, 2, true);
					turnAroundY(b[1], b[0]+1, true);
					if (b[0] == b[1]) turnAroundZ(2, 1, true);
					else turnAroundZ(2, 3, true);
					turnAroundY(b[1], 3-b[0], true);
					if (b[0] == b[1]) turnAroundZ(2, 3, true);
					else turnAroundZ(2, 1, true);
					turnAroundY(b[1], b[0]+1, true);
				}
				else if (blocks[b[0]][b[1]][b[2]][1] == 1) {
					turnAroundY(b[1], 3-b[0], true);
					if (b[0] == b[1]) turnAroundZ(2, 3, true);
					else turnAroundZ(2, 1, true);
					turnAroundY(b[1], b[0]+1, true);
				}
				else if (blocks[b[0]][b[1]][b[2]][2] == 1) {
					turnAroundX(b[0], 3-b[1], true);
					if (b[0] == b[1]) turnAroundZ(2, 1, true);
					else turnAroundZ(2, 3, true);
					turnAroundX(b[0], b[1]+1, true);
				}
			}
			topFaceCornersSolved = topFaceCornerCheck();
		}
	}
	
	public void solveMiddleEdges() {
		int middleEdgesSolved = middleEdgesCheck();
		
		while (middleEdgesSolved != 0) {
			int[] b = findEdge(middleEdgesSolved, (middleEdgesSolved - 1) % 4 + 2);
			
			
			
			middleEdgesSolved = middleEdgesCheck();
		}
	}
	
	
	/*
	 * gebruikt de opgeslagen moves in omgekeerde volgorde om zo uiteindelijk
	 * weer op de originele kubus uit te komen.
	 * (bij het scrambelen van een opgeloste kubus, is dit dus ook een van de
	 * mogelijke oplossingen) 
	 */
	public void solutionSolve() {
		String[] temp;
		
		temp = solution.split(",");
		for (int i = 0; i < temp.length; i++) {
			String[] temp2;
			System.out.println(temp[i]);
			temp2 = temp[i].split("-");
			System.out.println(temp2[0]);
			if (temp2[0].equals("x")) turnAroundX(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]), false);
			if (temp2[0].equals("y")) turnAroundY(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]), false);
			if (temp2[0].equals("z")) turnAroundZ(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]), false);
			printCubeFaces();
		}
	}
}

/*
 de oude code

public class Cube {
	int[] top = new int[8];
	int[] left = new int[8];
	int[] front = new int[8];
	int[] right = new int[8];
	int[] bottom = new int[8];
	int[] back = new int[8];
	
	public Cube() {
		for(int i = 0; i < 8; i++) {
			top[i] = 0;
			left[i] = 1;
			front[i] = 2;
			right[i] = 3;
			bottom[i] = 4;
			back[i] = 5;
		}
	}
	
	public void turnFaceTop(int direction) {
		int[] temp1 = new int[8];
		int temp2;
		
		for (int i = 0; i < 8; i++) {
			temp1[(i + 6 - (4*direction)) % 8] = top[i];
		}
		for (int i = 0; i < 8; i++) {
			top[i] = temp1[i];
		}
		
		if (direction == 1) {
			for(int i = 0; i < 3; i++) {
				temp2 = front[i];
				front[i] = right[i];
				right[i] = back[i];
				back[i] = left[i];
				left[i] = temp2;
			}
		}
		else {
			for(int i = 0; i < 3; i ++) {
				temp2 = front[i];
				front[i] = left[i];
				left[i] = back[i];
				back[i] = right[i];
				right[i] = temp2;
			}
		}	
	}
	
	public void turnFaceFront(int direction) {
		int[] temp1 = new int[8];
		int temp2;
		
		for (int i = 0; i < 8; i++) {
			temp1[(i + 6 - (4*direction)) % 8] = front[i];
		}
		for (int i = 0; i < 8; i++) {
			front[i] = temp1[i];
		}
		
		if (direction == 1) {
			for (int i = 0; i < 3; i++) {
				temp2 = bottom[i];
				bottom[i] = right[(i + 6) % 8];
				right[(i + 6) % 8] = top[i + 4];
				top[i + 4] = left[i + 2];
				left [i + 2] = temp2;
			}
		}
		else {
			for (int i = 0; i < 3; i++) {
				temp2 = bottom[i];
				bottom[i] = left[i + 2];
				left[i + 2] = top[i + 4];
				top[i + 4] = right[(i + 6) % 8];
				right[(i + 6) % 8] = temp2;
			}
		}
	}
	
	public void turnFaceBack(int direction) {
		int[] temp1 = new int[8];
		int temp2;
		
		for (int i = 0; i < 8; i++) {
			temp1[(i + 6 - (4*direction)) % 8] = back[i];
		}
		for (int i = 0; i < 8; i++) {
			back[i] = temp1[i];
		}
		
		if (direction == 1) {
			for (int i = 0; i < 3; i++) {
				temp2 = bottom[i + 4];
				bottom[i + 4] = left[(i + 6) % 8];
				left[(i + 6) % 8] = top[i];
				top[i] = right[i + 2];
				right[i + 2] = temp2;
			}
		}
		else {
			for (int i = 0; i < 3; i++) {
				temp2 = bottom[i + 4];
				bottom[i + 4] = right[i + 2];
				right[i + 2] = top[i];
				top[i] = left[(i + 6) % 8];
				left[(i + 6) % 8] = temp2;
			}
		}
	}
	
	public void turnFaceRight(int direction) {
		int[] temp1 = new int[8];
		int temp2;
		
		for (int i = 0; i < 8; i++) {
			temp1[(i + 6 - (4*direction)) % 8] = right[i];
		}
		for (int i = 0; i < 8; i++) {
			right[i] = temp1[i];
		}
		
		if (direction == 1) {
			for (int i = 2; i < 5; i++) {
				temp2 = bottom[i];
				bottom[i] = back[(i + 4) % 8];
				back[(i + 4) % 8] = top[i];
				top[i] = front[i];
				front[i] = temp2;
			}
		}
		else {
			for (int i = 2; i < 5; i++) {
				temp2 = bottom[i];
				bottom[i] = front[i];
				front[i] = top[i];
				top[i] = back[(i + 4) % 8];
				back[(i + 4) % 8] = temp2;
			}
		}
	}
	
	public void turnFaceLeft(int direction) {
		int[] temp1 = new int[8];
		int temp2;
		
		for (int i = 0; i < 8; i++) {
			temp1[(i + 6 - (4*direction)) % 8] = left[i];
		}
		for (int i = 0; i < 8; i++) {
			left[i] = temp1[i];
		}
		
		if (direction == 1) {
			for (int i = 6; i < 9; i++) {
				temp2 = bottom[i % 8];
				bottom[i % 8] = front[i % 8];
				front[i % 8] = top[i % 8];
				top[i % 8] = back[(i + 4) % 8];
				back[(i + 4) % 8] = temp2;
			}
		}
		else {
			for (int i = 6; i < 9; i++) {
				temp2 = bottom[i % 8];
				bottom[i % 8] = back[(i + 4) % 8];
				back[(i + 4) % 8] = top[i % 8];
				top[i % 8] = front[i % 8];
				front[i % 8] = temp2;
			}
		}
	}
	
	public void turnFaceBottom(int direction) {
		int[] temp1 = new int[8];
		int temp2;
		
		for (int i = 0; i < 8; i++) {
			temp1[(i + 6 - (4*direction)) % 8] = bottom[i];
		}
		for (int i = 0; i < 8; i++) {
			bottom[i] = temp1[i];
		}
		
		if(direction == 1) {
			for (int i = 4; i < 7; i ++) {
				temp2 = back[i];
				back[i] = right[i];
				right[i] = front[i];
				front[i] = left[i];
				left[i] = temp2;
			}
		}
		else {
			for (int i = 4; i < 7; i ++) {
				temp2 = back[i];
				back[i] = left[i];
				left[i] = front[i];
				front[i] = right[i];
				right[i] = temp2;
			}
		}
	}
	
	public void shuffleCube() {
		Random gen = new Random();
		for (int i = 0; i < 20; i ++) {
			int r = gen.nextInt(6);
			int d = gen.nextInt(2);
			switch(r) {
			case 0: turnFaceBottom(d); break;
			case 1: turnFaceTop(d); break;
			case 2: turnFaceFront(d); break;
			case 3: turnFaceRight(d); break;
			case 4: turnFaceLeft(d); break;
			case 5: turnFaceBack(d); break;
			}
		}
	}
	
	public void printCube() {
		System.out.println("top:\n" + top[0] + " " + top[1] + " " + top[2] + "\n" + top[7] + " 0 " + top[3] + "\n" + top[6] + " " + top[5] + " " + top[4]);
		System.out.println("front:\n" + front[0] + " " + front[1] + " " + front[2] + "\n" + front[7] + " 2 " + front[3] + "\n" + front[6] + " " + front[5] + " " + front[4]);
		System.out.println("bottom:\n" + bottom[0] + " " + bottom[1] + " " + bottom[2] + "\n" + bottom[7] + " 4 " + bottom[3] + "\n" + bottom[6] + " " + bottom[5] + " " + bottom[4]);
	}
}
 */
