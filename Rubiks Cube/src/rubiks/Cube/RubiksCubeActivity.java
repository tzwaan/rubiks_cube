package rubiks.Cube;

import android.app.Activity;
import android.os.Bundle;

public class RubiksCubeActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Cube cube = new Cube();
		cube.printCube();
		cube.printCubeFaces();
		
		cube.shuffleCube();
		cube.printCubeFaces();
		
//		cube.solutionSolve();
		
		cube.orientateCube();
		cube.printCubeFaces();
		
		cube.solveTopCross();
		cube.solveTopCorners();
		
		cube.solveMiddleEdges();
		
		cube.printCubeFaces();
		
	//	do {
	//		System.out.println("turning a face");
	//		cube.turnAroundX(0,1);
	//	}
	//	while(!cube.isSolved());
	//	System.out.println("cube solved!");
    }
}