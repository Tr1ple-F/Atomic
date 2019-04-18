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
import org.atomic.window.Window;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Game {

    public static Camera camera = new Camera(new Vector3f(0, 10, 0));
    public static final ViewConfig config = new ViewConfig(90, 0.1f, 1000f);

    public static void main(){
        Window.init();

        //Model
        Loader loader = new Loader();
        RawModel model = OBJLoader.loadObjModel("res/models/dragon.obj", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/textures/texture.png"));
        texture.setReflectivity(1);
        texture.setShineDamper(10);
        TexturedModel tM = new TexturedModel(model, texture);

        //Entities
        Entity entity = new Entity(tM, new Vector3f(0, -2, -5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Light light = new Light(new Vector3f(200, 200, 100), new Vector3f(0.8f, 0.8f, 0.8f));
        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("res/textures/texture.png")));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("res/textures/texture.png")));

        //EntitiyRenderer
        MasterRenderer masterRenderer = new MasterRenderer(config);

        //Game loop
        while(!GLFW.glfwWindowShouldClose(Window.getWindow())) {
            Window.update();

            masterRenderer.processTerrain(terrain);
            masterRenderer.processTerrain(terrain2);
            masterRenderer.processEntity(entity);
            masterRenderer.render(light, camera);
        }

        //Clean
        masterRenderer.cleanUp();
        Window.exit();
        loader.clean();
    }

}
