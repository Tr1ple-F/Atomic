package org.atomic.game;

import org.atomic.model.RawModel;
import org.atomic.model.TexturedModel;
import org.atomic.rendering.Loader;
import org.atomic.rendering.Renderer;
import org.atomic.shaders.StaticShader;
import org.atomic.textures.ModelTexture;
import org.atomic.window.Window;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Game {

    private static float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0 };
    private static int[] indices = { 0, 1, 3, 2, 0, 3};
    private static float[] texC = { 0, 0, 0, 1, 1, 1, 1, 0};

    public static void main(){
        Window.init();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        renderer.init();
        StaticShader shader = new StaticShader(StaticShader.baseVS, StaticShader.baseFS);
        RawModel model = loader.loadToVAO(vertices,texC, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/textures/texture.png"));
        TexturedModel tM = new TexturedModel(model, texture);

        while(!GLFW.glfwWindowShouldClose(Window.getWindow())) {
            renderer.prepare();
            shader.start();
            renderer.render(tM);
            shader.stop();
            Window.update();
        }

        Window.exit();
        loader.clean();
    }

}
