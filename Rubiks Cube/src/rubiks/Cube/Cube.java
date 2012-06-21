/*
 * Cube.java
 * 
 * dit is een virtuele rubiks cube. Hij bestaat uit 6 arrays van 8 getallen. Deze
 * representeren de verschillende vlakjes op de kanten van de kubus. Hierbij is
 * iedere array 1 kant van de kubus waarbij met een ronddraaiende beweging met de
 * klok mee over het vlak wordt gegaan.
 * Het middelste blokje van het vlak staat vast, dus staat niet in de array.
 * 
 * de "turnFace*" functies kunnen gebruikt worden om de desbetreffende kanten te
 * draaien. Hierbij draait "turnFaceFront" het voorste vlak, "turnFaceRight" het
 * rechtervlak, etcetera.
 * de parameter van deze functies geeft de draairichting aan met;
 * 		0 = linksom
 * 		1 = rechtsom
 */
package rubiks.Cube;
//import java.util.Random;

/*
 * kleuren:
 * 1 = wit
 * 2 = rood
 * 3 = groen
 * 4 = oranje
 * 5 = blauw
 * 6 = geel
 * (0 = zwart)
 */
public class Cube {
	int[][][][] blocks = new int[3][3][3][3];
	
	public Cube() {
		for (int z = 0; z < 3; z++) {
			for (int y = 0; y < 3; y++) {	
				for (int x = 0; x < 3; x++) {
					for (int color = 0; color < 3; color++) {
						blocks[x][y][z][color] = 0;
					}
				}
			}
		}
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
	
	public void turnAroundZ(int z, int amount) {
		int[][][] temp = new int[3][3][3];
		
		if (amount == 0) return;
		
		for (int j = 0; j < amount; j++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					temp[2-y][x][0] = blocks[x][y][z][0];
					temp[2-y][x][1] = blocks[x][y][z][2];
					temp[2-y][x][2] = blocks[x][y][z][1];
				}
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
	
	public void turnAroundY(int y, int amount) {
		
	}
	
	public void turnAroundX(int x, int amount) {
		
	}
	
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
