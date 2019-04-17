package org.atomic.rendering;

import org.atomic.entities.Entity;
import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.shaders.StaticShader;
import org.atomic.textures.ModelTexture;
import org.atomic.utils.Maths;
import org.atomic.window.Window;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

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
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
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

    public void render(Map<TexturedModel, List<Entity>> entities){
        for (TexturedModel model : entities.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity : batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertices(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindModel();
        }
    }

    private void prepareTexturedModel(TexturedModel texturedModel){
        //Model data
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        //Texture stuff
        ModelTexture texture = texturedModel.getTexture();
        shaderProgram.loadShineValues(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
    }

    private void unbindModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f m = Maths.createTransformationMatrix(entity.getTranslation(), entity.getRotation(), entity.getScale());
        shaderProgram.loadTransformationMatrix(m);
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
