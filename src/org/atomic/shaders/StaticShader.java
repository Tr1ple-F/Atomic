package org.atomic.shaders;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

    public static final String baseVS = "res/shader/vertexShader.glsl";
    public static final String baseFS = "res/shader/fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;

    public StaticShader(String vertexShader, String fragmentShader){
         super(vertexShader, fragmentShader);
    }

    @Override
    protected void getAllUniformLocations(){
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

}
