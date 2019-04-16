package org.atomic.rendering;

import org.atomic.model.RawModel;
import org.atomic.window.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Tr1ple-F on the 16.04.2019
 */
public class Renderer {

    public void prepare(){
        GL11.glClearColor(Window.getnBaseRGB()[0], Window.getnBaseRGB()[1], Window.getnBaseRGB()[2], Window.getnBaseRGB()[3]);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(RawModel model){
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertices());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

}
