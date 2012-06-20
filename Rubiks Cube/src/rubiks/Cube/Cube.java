package rubiks.Cube;

public class Cube {
	int[] top = new int[9];
	int[] left = new int[9];
	int[] front = new int[9];
	int[] right = new int[9];
	int[] bottom = new int[9];
	int[] back = new int[9];
	
	public Cube() {
		for(int i = 0; i < 9; i++) {
			top[i] = 0;
			left[i] = 1;
			front[i] = 2;
			right[i] = 3;
			bottom[i] = 4;
			back[i] = 5;
		}
	}
	
	
}