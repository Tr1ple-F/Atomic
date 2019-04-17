package org.atomic.rendering;

import org.atomic.entities.Entity;
import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.shaders.StaticShader;
import org.atomic.utils.Maths;
import org.atomic.window.Window;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Tr1ple-F on the 16.04.2019
 */
public class Renderer {

    private StaticShader shaderProgram;
    private final float FOV;
    private final float NEAR_PLANE;
    private final float FAR_PLANE;
    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shaderProgram, float FOV, float NEAR_PLANE, float FAR_PLANE) {
        this.shaderProgram = shaderProgram;
        this.FOV = FOV;
        this.NEAR_PLANE = NEAR_PLANE;
        this.FAR_PLANE = FAR_PLANE;
        projectionMatrix = Maths.createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(Window.getnBaseRGB()[0], Window.getnBaseRGB()[1], Window.getnBaseRGB()[2], Window.getnBaseRGB()[3]);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(Entity entity){
        TexturedModel texturedModel = entity.getTexturedModel();
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        Matrix4f m = Maths.createTransformationMatrix(entity.getTranslation(), entity.getRotation(), entity.getScale());
        shaderProgram.loadTransformationMatrix(m);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertices(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
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

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
