package org.atomic.window;

import org.atomic.game.Game;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class WindowCallbacks {

    public static void setErrorCallback(){
        GLFW.glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                System.out.println("Unfortunately there was a GLFW Error callback. There are most likely issues with the display.");
            }
        });
    }

    public static void setKeyCallbacks(long window){
        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(key == GLFW.GLFW_KEY_W){
                    Game.camera.move(0, 0, -0.1f);
                }
                if(key == GLFW.GLFW_KEY_S){
                    Game.camera.move(0, 0, 0.1f);
                }
                if(key == GLFW.GLFW_KEY_A){
                    Game.camera.move(-0.1f, 0,0);
                }
                if(key == GLFW.GLFW_KEY_D){
                    Game.camera.move(0.1f, 0,0);
                }
                if(key == GLFW.GLFW_KEY_UP){
                    Game.camera.move(0f, 1f,0);
                }
            }
        });
    }

    public static void setResizeCallback(long window){
        GLFW.glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                GL11.glViewport(0, 0, width, height);
                Window.setWidth(width);
                Window.setHeight(height);
            }
        });
    }

}
