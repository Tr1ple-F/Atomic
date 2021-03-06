package org.atomic.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class ImageUtils {

    public static BufferedImage readImage(String filepath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (image);
    }

    public static BufferedImage[] spriteSheet(BufferedImage bufferedImage, int width, int height){
        if(bufferedImage.getWidth() % width != 0 || bufferedImage.getHeight() % height != 0){
            throw new IllegalStateException("Cannot load spritesheet with format: " + width + "x" + height);
        }
        BufferedImage[] images = new BufferedImage[bufferedImage.getWidth()/width * bufferedImage.getHeight()/height];
        int iterator = 0;
        for(int y = 0; y < bufferedImage.getHeight()/height; y++){
            for(int x = 0; x < bufferedImage.getWidth()/width; x++){
                images[iterator] = bufferedImage.getSubimage(x * width, y * height, width, height);
                iterator++;
            }
        }
        return images;
    }

    public static BufferedImage crop(BufferedImage bI, int x0, int y0, int width, int height){
        return bI.getSubimage(x0, y0, width, height);
    }

    public static ByteBuffer convertToByteBuffer(BufferedImage bufferedImage){
        int imwidth = bufferedImage.getWidth();
        int imheight = bufferedImage.getHeight();
        ByteBuffer pixels = BufferUtils.createByteBuffer(imwidth * imheight * 4);
        for (int y = 0; y < imheight; y++) {
            for (int x = 0; x < imwidth; x++) {
                Color color = new Color(bufferedImage.getRGB(x, y), true);
                pixels.put((byte) color.getRed());
                pixels.put((byte) color.getGreen());
                pixels.put((byte) color.getBlue());
                pixels.put((byte) color.getAlpha());
            }
        }
        pixels.flip();
        return pixels;
    }

    public static GLFWImage convertToGLFW(BufferedImage bufferedImage){
        GLFWImage image = GLFWImage.malloc();
        image.set(bufferedImage.getWidth(), bufferedImage.getHeight(), convertToByteBuffer(bufferedImage));
        return image;
    }

    public static GLFWImage cursor (){
        GLFWImage cursor = convertToGLFW(crop(readImage("res/textures/icons.png"), 128+64, 0, 32, 32));
        return cursor;
    }

    public static GLFWImage.Buffer icons(){
        GLFWImage.Buffer icons = GLFWImage.malloc(4);
        icons.put(convertToGLFW(crop(readImage("res/textures/icons.png"), 128+64+32, 0, 16, 16)));
        icons.put(convertToGLFW(crop(readImage("res/textures/icons.png"), 128+64, 0, 32, 32)));
        icons.put(convertToGLFW(crop(readImage("res/textures/icons.png"), 128, 0, 64, 64)));
        icons.put(convertToGLFW(crop(readImage("res/textures/icons.png"), 0, 0, 128, 128)));
        icons.flip();
        return icons;
    }

}
