package org.atomic.rendering;

import org.atomic.model.RawModel;
import org.atomic.shaders.TerrainShader;
import org.atomic.terrain.Terrain;
import org.atomic.textures.TerrainTexturePack;
import org.atomic.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class TerrainRenderer {

    private TerrainShader shaderProgram;
    private ViewConfig viewConfig;
    private Matrix4f projectionMatrix;

    public TerrainRenderer(TerrainShader shaderProgram, ViewConfig viewConfig){
        this.shaderProgram = shaderProgram;
        this.viewConfig = viewConfig;
        projectionMatrix = Maths.createProjectionMatrix(viewConfig.getFOV(), viewConfig.getNEAR_PLANE(), viewConfig.getFAR_PLANE());
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.loadSampler();
        shaderProgram.stop();
    }

    public void render(List<Terrain> terrains){
        for(Terrain terrain : terrains){
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getMesh().getVertices(), GL11.GL_UNSIGNED_INT, 0);
            unbindModel();
        }
    }

    private void prepareTerrain(Terrain terrain){
        //Model data
        RawModel model = terrain.getMesh();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        //Texture stuff
        bindTextures(terrain);
        shaderProgram.loadShineValues(1, 0);
    }

    private void bindTextures(Terrain terrain) {
        TerrainTexturePack ttp = terrain.getTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getrTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getgTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getbTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ttp.getBaseTexture().getTextureID());

    }

    private void unbindModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f m = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), new Vector3f(0,0,0), new Vector3f(1,1,1));
        shaderProgram.loadTransformationMatrix(m);
    }

    public ViewConfig getViewConfig() {
        return viewConfig;
    }

}
