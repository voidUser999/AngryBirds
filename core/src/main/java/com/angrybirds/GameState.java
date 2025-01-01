package com.angrybirds;

import com.angrybirds.BodyData;
import com.angrybirds.PolygonBodyData;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<BodyData> bodyDataList;
    private List<PolygonBodyData> polygonBodyDataList;
    private Map<String, Map<String, Object>> serializedMaps;
    public GameState(List<BodyData> bodyDataList, List<PolygonBodyData> polygonBodyDataList , Map<String, Map<String, Object>> serializedMaps) {
        this.bodyDataList = bodyDataList;
        this.polygonBodyDataList = polygonBodyDataList;
        this.serializedMaps = serializedMaps;
    }

    public List<BodyData> getBodyDataList() {
        return bodyDataList;
    }

    public List<PolygonBodyData> getPolygonBodyDataList() {
        return polygonBodyDataList;
    }

    public Map<String, Map<String, Object>> getSerializedMaps() {
        return serializedMaps;
    }
}
