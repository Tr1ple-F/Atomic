package org.atomic.game;

import org.atomic.entities.Camera;
import org.atomic.entities.Entity;
import org.atomic.entities.Light;
import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.rendering.Loader;
import org.atomic.rendering.MasterRenderer;
import org.atomic.rendering.OBJLoader;
import org.atomic.rendering.ViewConfig;
import org.atomic.terrain.Terrain;
import org.atomic.textures.ModelTexture;
import org.atomic.textures.TerrainTexture;
import org.atomic.textures.TerrainTexturePack;
import org.atomic.utils.ImageUtils;
import org.atomic.window.Window;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Game {

    public static Camera camera = new Camera(new Vector3f(0, -10, 10));
    public static final ViewConfig config = new ViewConfig(90, 0.1f, 1000f);

    public static void main(){
        Window.init();

        //Model
        Loader loader = new Loader();
        RawModel model = OBJLoader.loadObjModel("res/models/grassModel.obj", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/textures/texture.png"));
        texture.setUseFakeLighting(true);
        texture.setReflectivity(1);
        texture.setShineDamper(10);
        TexturedModel tM = new TexturedModel(model, texture);

        //Entities
        Entity entity = new Entity(tM, new Vector3f(0, -2, -5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Light light = new Light(new Vector3f(200, 200, 100), new Vector3f(0.8f, 0.8f, 0.8f));
        TerrainTexture t0 = new TerrainTexture(loader.loadTexture(ImageUtils.readImage("res/textures/blend_map.png")));
        TerrainTexture t1 = new TerrainTexture(loader.loadTexture(ImageUtils.crop(ImageUtils.readImage("res/textures/icons.png"), 128 + 64, 0, 32, 32)));
        TerrainTexture t2 = new TerrainTexture(loader.loadTexture(ImageUtils.crop(ImageUtils.readImage("res/textures/icons.png"), 128, 0, 64, 64)));
        TerrainTexture t3 = new TerrainTexture(loader.loadTexture(ImageUtils.crop(ImageUtils.readImage("res/textures/icons.png"), 0, 0, 128, 128)));
        TerrainTexture t4 = new TerrainTexture(loader.loadTexture(ImageUtils.readImage("res/textures/texture.png")));
        TerrainTexturePack ttp = new TerrainTexturePack(t4, t1, t2, t3);
        Terrain terrain = new Terrain(0, 0, loader, ttp, t0);

        //EntitiyRenderer
        MasterRenderer masterRenderer = new MasterRenderer(config);

        //Game loop
        while(!GLFW.glfwWindowShouldClose(Window.getWindow())) {
            Window.update();

            masterRenderer.processTerrain(terrain);
            masterRenderer.processEntity(entity);
            masterRenderer.render(light, camera);
        }

        //Clean
        masterRenderer.cleanUp();
        Window.exit();
        loader.clean();
    }

}
