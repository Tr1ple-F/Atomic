package org.atomic.terrain;

import org.atomic.model.RawModel;
import org.atomic.rendering.Loader;
import org.atomic.textures.TerrainTexture;
import org.atomic.textures.TerrainTexturePack;

public class Terrain {

    private static final float SIZE = 800;
    private static final int verticesCount = 128;

    private float x;
    private float z;
    private RawModel mesh;
    private TerrainTexturePack texture;
    private TerrainTexture blendMap;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texture, TerrainTexture blendMap) {
        this.texture = texture;
        this.blendMap = blendMap;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.mesh = generateTerrain(loader);
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    private RawModel generateTerrain(Loader loader){
        int count = verticesCount * verticesCount;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(verticesCount-1)*(verticesCount-1)];
        int vertexPointer = 0;
        for(int i=0;i<verticesCount;i++){
            for(int j=0;j<verticesCount;j++){
                vertices[vertexPointer*3] = (float)j/((float)verticesCount - 1) * SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)verticesCount - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)verticesCount - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)verticesCount - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<verticesCount-1;gz++){
            for(int gx=0;gx<verticesCount-1;gx++){
                int topLeft = (gz*verticesCount)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*verticesCount)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

    public static float getSIZE() {
        return SIZE;
    }

    public static int getVerticesCount() {
        return verticesCount;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getMesh() {
        return mesh;
    }

    public TerrainTexturePack getTexture() {
        return texture;
    }
}
