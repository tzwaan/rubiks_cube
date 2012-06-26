package rubiks.Cube;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;



public class RubiksCubeActivity extends Activity implements OnTouchListener {
	public static Cube cube = new Cube(); 
	private float prevX, prevY;
	public static float deltaX, deltaY;
	public static CubeRenderer renderer = new CubeRenderer(cube);
    /*Button button_left, button_face, button_right;

    public void addListenerOnButton_left(){
    	button_left = (Button) findViewById(R.id.button_left);
    	button_left.setOnClickListener(new OnClickListener() {
	    	public void onClick(View arg0) 
	    	{
	    		System.out.println("GET TO DA CHOPPAHHHHH!");
		    }
    	});
    }
    	
	  public void addListenerOnButton_face(){
	    	button_face = (Button) findViewById(R.id.button_face);
	    	button_face.setOnClickListener(new OnClickListener() {
		    	public void onClick(View arg0) 
		    	{
		    		System.out.println("SAY HELLO TO MY LITTLE FRIEND!");
			    }
	    	});
	  }
	  
	    	
	  public void addListenerOnButton_right(){
	    	button_right = (Button) findViewById(R.id.button_right);
	    	button_right.setOnClickListener(new OnClickListener() {
		    	public void onClick(View arg0) 
		    	{
		    		System.out.println("CONJOOOO PATRICK!");
			    }
	    	});
	  }*/
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(renderer);
        setContentView(view);

        //addListenerOnButton_left();
        //addListenerOnButton_face();
        //addListenerOnButton_right();
        //System.out.println("POEP");

        view.setOnTouchListener(new OnTouchListener(){
	        @Override
	    	public boolean onTouch(View v, MotionEvent event) {
	        	float x = event.getX();
	    		float y = event.getY();
	    		
	    		switch (event.getAction()) {
	    	    	case MotionEvent.ACTION_MOVE: {
	    	    		deltaX = (x - prevX) / 2f;
	    	    		deltaY = (y - prevY) / 2f;
	    	    		break;
	    	    	}
	    	    	case MotionEvent.ACTION_UP: {
	    	    		deltaX = 0f;
	    	    		deltaY = 0f;
	    	    		break;
	    	    	}
	    		}
	    		prevX = x;
	    		prevY = y;
	    		return true;
	    	}
        });
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
