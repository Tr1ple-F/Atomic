package org.atomic.rendering;

import org.atomic.entities.Camera;
import org.atomic.entities.Entity;
import org.atomic.entities.Light;
import org.atomic.model.TexturedModel;
import org.atomic.shaders.StaticShader;
import org.atomic.shaders.TerrainShader;
import org.atomic.terrain.Terrain;
import org.atomic.utils.Maths;
import org.atomic.window.Window;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private StaticShader sShader = new StaticShader(StaticShader.baseVS, StaticShader.baseFS);
    private TerrainShader tShader = new TerrainShader(TerrainShader.baseVS, TerrainShader.baseFS);
    private ViewConfig viewConfig;
    private EntitiyRenderer entitiyRenderer;
    private TerrainRenderer terrainRenderer;

    public MasterRenderer(ViewConfig viewConfig){
        this.viewConfig = viewConfig;
        entitiyRenderer = new EntitiyRenderer(sShader, viewConfig);
        terrainRenderer = new TerrainRenderer(tShader, viewConfig);
        enableCulling();
    }

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public void render(Light sun, Camera camera){
        prepare();
        sShader.start();
        tShader.loadSkyColor(Window.getnBaseRGB().x, Window.getnBaseRGB().y, Window.getnBaseRGB().z);
        sShader.loadSkyColor(Window.getnBaseRGB().x, Window.getnBaseRGB().y, Window.getnBaseRGB().z);
        sShader.loadLight(sun);
        sShader.loadViewMatrix(Maths.createViewMatrix(camera));
        entitiyRenderer.render(entities);
        sShader.stop();
        tShader.start();
        tShader.loadLight(sun);
        tShader.loadViewMatrix(Maths.createViewMatrix(camera));
        terrainRenderer.render(terrains);
        tShader.stop();
        entities.clear();
        terrains.clear();
    }

    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(Window.getnBaseRGB().x, Window.getnBaseRGB().y, Window.getnBaseRGB().z, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processEntity(Entity entity){
        TexturedModel tM = entity.getTexturedModel();
        List<Entity> batch = entities.get(tM);
        if(batch!=null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(tM, newBatch);
        }
    }

    public ViewConfig getViewConfig(){
        return viewConfig;
    }

    public void cleanUp(){
        sShader.clean();tShader.clean();
    }

}
