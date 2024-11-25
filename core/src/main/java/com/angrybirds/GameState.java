package com.angrybirds;

import com.angrybirds.BodyData;
import com.angrybirds.PolygonBodyData;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<BodyData> bodyDataList;
    private List<PolygonBodyData> polygonBodyDataList;

    public GameState(List<BodyData> bodyDataList, List<PolygonBodyData> polygonBodyDataList) {
        this.bodyDataList = bodyDataList;
        this.polygonBodyDataList = polygonBodyDataList;
    }

    public List<BodyData> getBodyDataList() {
        return bodyDataList;
    }

    public List<PolygonBodyData> getPolygonBodyDataList() {
        return polygonBodyDataList;
    }
}
