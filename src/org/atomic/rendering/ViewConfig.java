package org.atomic.rendering;

public class ViewConfig {

    private final float FOV;
    private final float NEAR_PLANE;
    private final float FAR_PLANE;

    public ViewConfig(float FOV, float NEAR_PLANE, float FAR_PLANE) {
        this.FOV = FOV;
        this.NEAR_PLANE = NEAR_PLANE;
        this.FAR_PLANE = FAR_PLANE;
    }

    public float getFOV() {
        return FOV;
    }

    public float getNEAR_PLANE() {
        return NEAR_PLANE;
    }

    public float getFAR_PLANE() {
        return FAR_PLANE;
    }
}
