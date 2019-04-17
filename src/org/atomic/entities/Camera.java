package org.atomic.entities;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;

    public Camera(Vector3f position) {
        this.position = position;
    }

    public void move(float dx, float dy, float dz){
        position.x+=dx;
        position.y+=dy;
        position.z+=dz;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
