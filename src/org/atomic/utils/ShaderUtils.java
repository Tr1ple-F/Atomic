package org.atomic.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

}
