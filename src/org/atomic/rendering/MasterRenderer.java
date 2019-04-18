package org.atomic.rendering;

import org.atomic.entities.Camera;
import org.atomic.entities.Entity;
import org.atomic.entities.Light;
import org.atomic.model.TexturedModel;
import org.atomic.shaders.StaticShader;
import org.atomic.utils.Maths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private StaticShader shader = new StaticShader(StaticShader.baseVS, StaticShader.baseFS);
    private EntitiyRenderer entitiyRenderer = new EntitiyRenderer(shader, new ViewConfig(90, 0.1f, 1000f));

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    public void render(Light sun, Camera camera){
        entitiyRenderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(Maths.createViewMatrix(camera));
        entitiyRenderer.render(entities);
        shader.stop();
        entities.clear();
    }

    public void processEntity(Entity entity){
        TexturedModel tM = entity.getTexturedModel();
        List<Entity> batch = entities.get(tM);
        if(batch!=null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(tM, newBatch);
        }
    }

    public void cleanUp(){
        shader.clean();
    }

}
