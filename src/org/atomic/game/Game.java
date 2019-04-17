package org.atomic.game;

import org.atomic.entities.Camera;
import org.atomic.entities.Entity;
import org.atomic.entities.Light;
import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.rendering.Loader;
import org.atomic.rendering.OBJLoader;
import org.atomic.rendering.Renderer;
import org.atomic.shaders.StaticShader;
import org.atomic.textures.ModelTexture;
import org.atomic.utils.Maths;
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

        Loader loader = new Loader();
        StaticShader shader = new StaticShader(StaticShader.baseVS, StaticShader.baseFS);
        Renderer renderer = new Renderer(shader, 90, 0.1f, 1000);
        RawModel model = OBJLoader.loadObjModel("res/models/dragon.obj", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/textures/texture.png"));
        TexturedModel tM = new TexturedModel(model, texture);
        Entity entity = new Entity(tM, new Vector3f(0, -5, -5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Light light = new Light(new Vector3f(0, 0, -4), new Vector3f(1, 1, 1));

        while(!GLFW.glfwWindowShouldClose(Window.getWindow())) {
            Window.update();

            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(Maths.createViewMatrix(camera));
            renderer.render(entity);
            entity.increaseRotation(-0.01f, 1f,0f);

            shader.stop();
        }

        Window.exit();
        loader.clean();
    }

}
