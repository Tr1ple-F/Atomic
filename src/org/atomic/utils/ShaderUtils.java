package org.atomic.utils;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public class ShaderUtils {

    public static CharSequence loadShaderFile(String filePath){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        return shaderSource;
    }

    public static FloatBuffer loadMatrix4f(Matrix4f matrix){
        FloatBuffer buffer = matrix.get(BufferUtils.createFloatBuffer(16));
        return buffer;
    }

}
