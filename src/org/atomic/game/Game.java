package org.atomic.game;

import org.atomic.entities.*;
import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.rendering.*;
import org.atomic.textures.ModelTexture;
import org.atomic.window.Window;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Game {

    public static Camera camera = new Camera(new Vector3f(0, 0, 0));

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

        //Renderer
        MasterRenderer masterRenderer = new MasterRenderer();

        //Game loop
        while(!GLFW.glfwWindowShouldClose(Window.getWindow())) {
            Window.update();
            masterRenderer.processEntity(entity);
            masterRenderer.render(light, camera);
        }

        //Clean
        masterRenderer.cleanUp();
        Window.exit();
        loader.clean();
    }

}
