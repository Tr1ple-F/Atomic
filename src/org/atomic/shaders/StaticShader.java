package org.atomic.shaders;

public class StaticShader extends ShaderProgram {

    public static final String baseVS = "res/shader/vertexShader.glsl";
    public static final String baseFS = "res/shader/fragmentShader.glsl";

    public StaticShader(String vertexShader, String fragmentShader){
         super(vertexShader, fragmentShader);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
