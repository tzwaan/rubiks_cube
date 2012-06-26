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
	Cube cube;
	public static float x, y;
	public CubeRenderer(Cube cube)
	{
		this.cube = cube;
		cube.printCube();
		cube.printCubeFaces();
		cube.shuffleCube();
		cube.orientateCube();
		cube.solveTopCross();
		cube.solveTopCorners();
	}
	private Rubik mCube = new Rubik();
    private float mCubeRotation;
    private float[] rgb = new float[3]; 

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

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glLoadIdentity();
        
        drawGrid(gl);
    }
    
    public void drawGrid(GL10 gl)
    {
    	x += RubiksCubeActivity.deltaX; 
    	y += RubiksCubeActivity.deltaY;
    	gl.glTranslatef(0.0f, 0.0f, -15.0f);
        gl.glRotatef(y, 1, 0, 0);
        gl.glRotatef(x, 0, 1, 0);

        for(int i = 0; i < 3; i++)
        {
	        for(int j = 0; j < 3; j++)
	        {
	        	for(int k = 0; k < 3; k++)
	        	{
		        	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Rubik.mVertexBuffer);
		            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		            //--- FRONT RED
		        	rgb = cube.nrToRGB(cube.blocks[k][j][i][2]);
		        	gl.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
		            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
		            
		            //--- BACK ORANGE
		            gl.glDrawArrays(GL10.GL_TRIANGLES, 12, 6);
		            
		            //--- RIGHT BLUE 
		        	rgb = cube.nrToRGB(cube.blocks[k][j][i][0]);
		            gl.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
		            gl.glDrawArrays(GL10.GL_TRIANGLES, 6, 6);
		            
		            //--- LEFT GREEN
		            gl.glDrawArrays(GL10.GL_TRIANGLES, 18, 6);
		            
		            //--- TOP WHITE
		            rgb = cube.nrToRGB(cube.blocks[k][j][i][1]);
		            gl.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
		            gl.glDrawArrays(GL10.GL_TRIANGLES, 24, 6);
		            
		            //--- BOTTOM YELLOW
		            gl.glDrawArrays(GL10.GL_TRIANGLES, 30, 6);
		            //--
		            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		        	gl.glTranslatef(1.0f, 0.0f, 0.0f);
	        	}
	        	gl.glTranslatef(-3.0f, 1.0f, 0.0f);
	        }
        	gl.glTranslatef(0.0f, -3.0f, 1.0f);
        }
    }

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