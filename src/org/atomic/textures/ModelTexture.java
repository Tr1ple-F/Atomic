package org.atomic.textures;

public class ModelTexture {

    private int textureID;
    private float shineDamper = 1;
    private float reflectivity = 0;

    private boolean hasTransparency = false;

    public ModelTexture(int id) {
        this.textureID = id;
    }

    public boolean isTransparent() {
        return hasTransparency;
    }

    public void hasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public int getTextureID(){
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
