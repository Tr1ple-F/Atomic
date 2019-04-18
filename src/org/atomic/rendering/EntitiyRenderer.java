package org.atomic.rendering;

import org.atomic.entities.Entity;
import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.shaders.StaticShader;
import org.atomic.textures.ModelTexture;
import org.atomic.utils.Maths;
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
public class EntitiyRenderer {

    private StaticShader shaderProgram;
    private Matrix4f projectionMatrix;
    private ViewConfig viewConfig;

    public EntitiyRenderer(StaticShader shaderProgram, ViewConfig viewConfig){
        this.shaderProgram = shaderProgram;
        this.viewConfig = viewConfig;
        projectionMatrix = Maths.createProjectionMatrix(viewConfig.getFOV(), viewConfig.getNEAR_PLANE(), viewConfig.getFAR_PLANE());
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
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
        if(texture.isTransparent()){
            MasterRenderer.disableCulling();
        }
        shaderProgram.loadShineValues(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
    }

    private void unbindModel(){
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f m = Maths.createTransformationMatrix(entity.getTranslation(), entity.getRotation(), entity.getScale());
        shaderProgram.loadTransformationMatrix(m);
    }

    public ViewConfig getViewConfig() {
        return viewConfig;
    }
}
