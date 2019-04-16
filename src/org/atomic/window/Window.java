package org.atomic.window;

import org.atomic.utils.ImageUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Window {

    private static long window;
    private static long context;
    private static long monitor;
    private static long cursor;
    private static int width = 1920;
    private static int height = 1080;
    private static float[] nBaseRGB = {0.1f, 0.3f, 0.5f, 1f};

    public static void init(){
        //Error
        WindowCallbacks.setErrorCallback();
        if(!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW!");

        //Monitor
        monitor = GLFW.glfwGetPrimaryMonitor();

        //Window
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        window = GLFW.glfwCreateWindow(width, height, "Atomic", MemoryUtil.NULL, MemoryUtil.NULL);

        //Window error check
        if(window == MemoryUtil.NULL){
            GLFW.glfwTerminate();
            throw new IllegalStateException("Unable to initialize window!");
        }

        //Window usages
        GLFW.glfwMakeContextCurrent(window);
        context = GLFW.glfwGetCurrentContext();
        GL.createCapabilities();
        WindowCallbacks.setResizeCallback(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwSetWindowIcon(window, ImageUtils.icons());
        cursor = GLFW.glfwCreateCursor(ImageUtils.cursor(), 0, 0);
        GLFW.glfwSetCursor(window, cursor);
    }

    public static void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public static void forceExit(){
        GLFW.glfwSetWindowShouldClose(window, true);
    }

    public static void exit(){
        GLFW.glfwDestroyCursor(cursor);
        GLFW.glfwTerminate();
    }

    public static long getWindow() {
        return window;
    }

    public static long getContext() {
        return context;
    }

    public static long getMonitor() {
        return monitor;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setWidth(int width) {
        Window.width = width;
    }

    public static void setHeight(int height) {
        Window.height = height;
    }

    public static void setnBaseRGB(float[] nBaseRGB) {
        if(nBaseRGB.length != 4){
            throw new IllegalArgumentException("Unable to force nBaseRBG at Window into format/length: " + nBaseRGB.length);
        }
        Window.nBaseRGB = nBaseRGB;
    }

    public static float[] getnBaseRGB() {
        return nBaseRGB;
    }
}
