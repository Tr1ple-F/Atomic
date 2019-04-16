package org.atomic.model;

/**
 * Created by Tr1ple-F on the 16.04.2019
 */
public class RawModel {

    private int vaoID;
    private int vertices;

    public RawModel(int vaoID, int vertices){
        this.vaoID = vaoID;
        this.vertices = vertices;
    }

    public int getVaoID(){
        return vaoID;
    }

    public int getVertices(){
        return vertices;
    }

}
