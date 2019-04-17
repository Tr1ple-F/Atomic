package org.atomic.rendering;

import org.atomic.model.RawModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    public static RawModel loadObjModel(String fileName, Loader loader){
        RawModel rawModel = null;
        try {
            FileReader fr = new FileReader(new File(fileName));
            BufferedReader reader = new BufferedReader(fr);
            String line;
            List<Vector3f> vertices = new ArrayList<>();
            List<Vector2f> textures = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();
            float[] verticesArray;
            float[] normalsArray;
            float[] textureArray;
            int[] indicesArray;

            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                }else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                }else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                }else if(line.startsWith("f ")){
                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }

            while(line != null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = reader.readLine();
            }
            reader.close();
            verticesArray = new float[vertices.size() * 3];
            indicesArray = new int[indices.size()];
            int vP = 0;
            for(Vector3f vertex:vertices){
                verticesArray[vP++] = vertex.x;
                verticesArray[vP++] = vertex.y;
                verticesArray[vP++] = vertex.z;
            }
            for (int i =0; i<indices.size(); i++){
                indicesArray[i] = indices.get(i);
            }
            rawModel = loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rawModel;
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] texturesArray, float[] normalsArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        texturesArray[currentVertexPointer*2] = currentTex.x;
        texturesArray[currentVertexPointer*2+1] = 1 - currentTex.y;
        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer*3] = currentNormal.x;
        normalsArray[currentVertexPointer*3+1] = currentNormal.y;
        normalsArray[currentVertexPointer*3+2] = currentNormal.z;
    }

}
