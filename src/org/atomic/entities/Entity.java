package org.atomic.entities;

import org.atomic.model.TexturedModel;
import org.joml.Vector3f;

public class Entity {

    private TexturedModel texturedModel;
    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    public Entity(TexturedModel texturedModel, Vector3f translation, Vector3f rotation, Vector3f scale) {
        this.texturedModel = texturedModel;
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void increaseTranslation(float dx, float dy, float dz){
        translation.x += dx;
        translation.y += dy;
        translation.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz){
        rotation.x += dx;
        rotation.y += dy;
        rotation.z += dz;
    }

    public void increaseScale(float dx, float dy, float dz){
        scale.x += dx;
        scale.y += dy;
        scale.z += dz;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }
}
