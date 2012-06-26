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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Rubik {
	public static FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer  mIndexBuffer;
    //private final static float CUBE_UNITY_COORD = 0.5f;

        
    private float vertices[] = {
    		
    		 //rood
    	    0.5f, 0.5f, 0.5f,
    	    0.5f,-0.5f,-0.5f,
    	    0.5f, 0.5f,-0.5f,
    	    
    	    //rood
    	    0.5f,-0.5f,-0.5f,
    	    0.5f, 0.5f, 0.5f,
    	    0.5f,-0.5f, 0.5f,
    	    
    	    //blauw
    	    0.5f, 0.5f, -0.5f, // triangle 2 : begin
    	    -0.5f,-0.5f,-0.5f,
    	    -0.5f, 0.5f,-0.5f, // triangle 2 : end
    	    
    	    //blauw
    	    0.5f, 0.5f,-0.5f,
    	    0.5f,-0.5f,-0.5f,
    	    -0.5f,-0.5f,-0.5f,
    	    
    		//oranje
    		-0.5f,-0.5f,-0.5f, // triangle 1 : begin
    	    -0.5f,-0.5f, 0.5f,
    	    -0.5f, 0.5f, 0.5f, // triangle 1 : end
    	    
    	    //oranje
    	    -0.5f,-0.5f,-0.5f,
    	    -0.5f, 0.5f, 0.5f,
    	    -0.5f, 0.5f,-0.5f,
    	    
    	    //groen
    	    -0.5f, 0.5f, 0.5f,
    	    -0.5f,-0.5f, 0.5f,
    	    0.5f,-0.5f, 0.5f,
    	    
    	    //groen
    	    0.5f, 0.5f, 0.5f,
    	    -0.5f, 0.5f, 0.5f,
    	    0.5f,-0.5f, 0.5f,
    	    
    	    //wit
    	    0.5f,-0.5f, 0.5f,
    	    -0.5f,-0.5f,-0.5f,
    	    0.5f,-0.5f,-0.5f,
  
    	    //wit
    	    0.5f,-0.5f, 0.5f,
    	    -0.5f,-0.5f, 0.5f,
    	    -0.5f,-0.5f,-0.5f,

    	    //geel
    	    0.5f, 0.5f, 0.5f,
    	    0.5f, 0.5f,-0.5f,
    	    -0.5f, 0.5f,-0.5f,
    	    
    	    //geel
    	    0.5f, 0.5f, 0.5f,
    	    -0.5f, 0.5f,-0.5f,
    	    -0.5f, 0.5f, 0.5f,
    	    
    	    
    };
    
    
                                   
    public float red[] = {1.0f, 0.0f, 0.0f};
    public float green[] = {0.0f, 1.0f, 0.0f};
    public float blue[] = {0.0f, 0.0f, 1.0f};
    public float yellow[] = {1.0f, 1.0f, 0.0f};
    public float white[] = {1.0f, 1.0f, 1.0f};
    public float orange[] = {1.0f, 0.5f, 0.0f};

    private float colors[] = {
        red[0], red[1], red[2],
        red[0], red[1], red[2],
        red[0], red[1], red[2],
        red[0], red[1], red[2],
        red[0], red[1], red[2],
        red[0], red[1], red[2],

        green[0], green[1], green[2],
        green[0], green[1], green[2],
        green[0], green[1], green[2],
        green[0], green[1], green[2],
        green[0], green[1], green[2],
        green[0], green[1], green[2],

        blue[0], blue[1], blue[2],
        blue[0], blue[1], blue[2],
        blue[0], blue[1], blue[2],
        blue[0], blue[1], blue[2],
        blue[0], blue[1], blue[2],
        blue[0], blue[1], blue[2],

        yellow[0], yellow[1], yellow[2],
        yellow[0], yellow[1], yellow[2],
        yellow[0], yellow[1], yellow[2],
        yellow[0], yellow[1], yellow[2],
        yellow[0], yellow[1], yellow[2],
        yellow[0], yellow[1], yellow[2],

        white[0], white[1], white[2],
        white[0], white[1], white[2],
        white[0], white[1], white[2],
        white[0], white[1], white[2],
        white[0], white[1], white[2],
        white[0], white[1], white[2],

        orange[0], orange[1], orange[2],
        orange[0], orange[1], orange[2],
        orange[0], orange[1], orange[2],
        orange[0], orange[1], orange[2],
        orange[0], orange[1], orange[2],
        orange[0], orange[1], orange[2],
    };
   
    private byte indices[] = {
                              0, 4, 5, 0, 5, 1,
                              1, 5, 6, 1, 6, 2,
                              2, 6, 7, 2, 7, 3,
                              3, 7, 4, 3, 4, 0,
                              4, 7, 6, 4, 6, 5,
                              3, 0, 1, 3, 1, 2
                              };
                
    public Rubik() {
            ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            mVertexBuffer = byteBuf.asFloatBuffer();
            mVertexBuffer.put(vertices);
            mVertexBuffer.position(0);
                
            byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            mColorBuffer = byteBuf.asFloatBuffer();
            mColorBuffer.put(colors);
            mColorBuffer.position(0);
                
            mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
            mIndexBuffer.put(indices);
            mIndexBuffer.position(0);
    }
}
