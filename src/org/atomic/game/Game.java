package org.atomic.game;

import org.atomic.model.RawModel;
import org.atomic.rendering.Loader;
import org.atomic.rendering.Renderer;
import org.atomic.window.Window;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Game {

    private static Loader loader = new Loader();
    private static Renderer renderer = new Renderer();
    private static List<RawModel> models = new ArrayList<>();
    private static float[] vertices = { 0.5f, 0.4f, 0f, -0.2f, -0.5f, 0f, 0.3f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.7f, 0f, -0.6f, 0.5f, 0f };

    public static void init(){
        Window.init();
        models.add(loader.loadToVAO(vertices));
    }

    public static void main(){
        while(!GLFW.glfwWindowShouldClose(Window.getWindow())) {
            renderer.prepare();

            for (RawModel model : models){
                renderer.render(model);
            }

            Window.update();
        }
        exit();
    }

    public static void exit(){
        Window.exit();
    }

}
