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
        cube.turnFaceTop(1);
        cube.printCube();
        cube.shuffleCube();
        cube.printCube();
    }
}