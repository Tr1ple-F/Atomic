package org.atomic.shaders;

import org.atomic.entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TerrainShader extends ShaderProgram {

    public static final String baseVS = "res/shader/terrainVShader.glsl";
    public static final String baseFS = "res/shader/terrainFShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_reflectivity;
    private int location_shineDamper;
    private int location_skyColor;
    private int location_blendMap;
    private int location_baseMap;
    private int location_rMap;
    private int location_gMap;
    private int location_bMap;

    public TerrainShader(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_skyColor = super.getUniformLocation("skyColor");
        location_blendMap = super.getUniformLocation("blendMap");
        location_baseMap = super.getUniformLocation("baseMap");
        location_rMap = super.getUniformLocation("rMap");
        location_gMap = super.getUniformLocation("gMap");
        location_bMap = super.getUniformLocation("bMap");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoord");
        super.bindAttribute(2, "normal");
    }

    public void loadShineValues(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadSampler() {
        super.loadInt(location_blendMap, 0);
        super.loadInt(location_rMap, 1);
        super.loadInt(location_gMap, 2);
        super.loadInt(location_bMap, 3);
        super.loadInt(location_baseMap, 4);
    }

    public void loadSkyColor(float r, float g, float b){
        super.loadVector(location_skyColor, new Vector3f(r,g,b));
    }

    public void loadViewMatrix(Matrix4f matrix) {
        super.loadMatrix(location_viewMatrix, matrix);
    }

    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

}
