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
        cube.turnAroundX(1, 2);
        cube.turnAroundY(1, 2);
        cube.turnAroundZ(1, 2);
        cube.printCube();
        cube.printCubeFaces();
        cube.shuffleCube();
        cube.printCube();
        cube.printCubeFaces();
    }
}