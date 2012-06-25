/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rubiks.Cube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class CubeRenderer implements Renderer {

	private Rubik mCube = new Rubik();
    private float mCubeRotation;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 
            
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                  GL10.GL_NICEST);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glDisable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_CULL_FACE);
            
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glLoadIdentity();
        
        drawGrid(gl);    
        
        
        //mCube.draw(gl);
        //gl.glRotatef(mCubeRotation, 1.0f, 1.0f, 1.0f);

        //mCubeRotation -= 0.50f; 
    }
    
    public void drawGrid(GL10 gl)
    {
    	mCubeRotation += 1.5f;
    	float transX = 0.0f, transY = 2.0f;
    	gl.glTranslatef(-1.0f, 1.0f, -10.0f);
        gl.glScalef(0.3f, 0.3f, 0.3f);
        gl.glRotatef(mCubeRotation, 1, 1, 0);
        //gl.glTranslatef(-1, 0, 0);
        for(int j = 0; j < 3; j++)
        {
        	gl.glTranslatef(-6.0f, -4.0f, -2.0f);
        	
	        for(int i = 0; i < 9; i++)
	        {
	        	if(i == 3 || i == 6)
	        	{
	        		transX = -4.0f;
	        		transY = 2.0f;
	        	}
	           	gl.glTranslatef(transX, transY, 0.0f);
	        	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Rubik.mVertexBuffer);
	            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	            gl.glEnableClientState(GL10.GL_LINE_SMOOTH);
	            //--- FRONT
	        	//cube.nrToRGB(cube.block[j][i][k][0]);
	            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); //RED
	            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
	            gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
	            gl.glDrawArrays(GL10.GL_LINE_SMOOTH, 0, 6);
	            //--- RIGHT
	            gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f); // BLUE
	            gl.glDrawArrays(GL10.GL_TRIANGLES, 6, 6);
	            //--- BACK
	            gl.glColor4f(1.0f, 0.5f, 0.0f, 1.0f); // ORANGE
	            gl.glDrawArrays(GL10.GL_TRIANGLES, 12, 6);
	            //--- LEFT
	            gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); // GREEN
	            gl.glDrawArrays(GL10.GL_TRIANGLES, 18, 6);
	            //--- TOP
	            gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // WHITE
	            gl.glDrawArrays(GL10.GL_TRIANGLES, 24, 6);
	            //--- BOTTOM
	            gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f); // YELLOW 
	            gl.glDrawArrays(GL10.GL_TRIANGLES, 30, 6);
	            //--
	            gl.glDisableClientState(GL10.GL_LINE_SMOOTH);
	            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	            
	            
	            if(i != 2 || i != 5)
	            {
	            	transX = 2.0f;
	            	transY = 0.0f;
	            }
	            else
	            {
	            	transY = 2.0f;
	            	transX = 0.0f;
	            }
	        }
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}