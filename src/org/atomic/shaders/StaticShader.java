package org.atomic.shaders;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

    public static final String baseVS = "res/shader/vertexShader.glsl";
    public static final String baseFS = "res/shader/fragmentShader.glsl";

    private int location_transformationMatrix;

    public StaticShader(String vertexShader, String fragmentShader){
         super(vertexShader, fragmentShader);
    }

    @Override
    protected void getAllUniformLocations(){
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

}
