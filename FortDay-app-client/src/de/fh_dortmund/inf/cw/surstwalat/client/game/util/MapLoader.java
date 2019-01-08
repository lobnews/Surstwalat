/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.util;

import java.util.List;
import org.yaml.snakeyaml.Yaml;

import static de.fh_dortmund.inf.cw.surstwalat.client.game.util.MapFormatConstants.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Lars
 */
public class MapLoader {

    private final Yaml yaml;
    private final java.util.Map maps;
    private final Iterator<String> mapNames;
    private final LinkedList<Map> mapList = new LinkedList<>();

    public MapLoader(String file) {
        this.yaml = new Yaml();
        maps = yaml.load(MapLoader.class.getClassLoader().getResourceAsStream(file));
        if (maps != null) {
            mapNames = maps.keySet().iterator();
        } else {
            mapNames = null;
        }
    }

    private Map readMap(String name, java.util.Map mapYaml) throws MapFormatException {
        int xHeight = (int) mapYaml.get(X_HEIGHT_KEY);
        int yHeight = (int) mapYaml.get(Y_HEIGHT_KEY);
        String backgroundImage = (String) mapYaml.get(BACKGROUND_KEY);
        int[][] field = new int[xHeight][yHeight];
        List fieldList = (List) mapYaml.get(FIELD_KEY);
        for (int y = 0; y < yHeight; y++) {
            for (int x = 0; x < xHeight; x++) {
                field[x][y] = (int) ((List) fieldList.get(y)).get(x);
            }
        }
        return new Map(name, xHeight, yHeight, backgroundImage, field);
    }

    public List<Map> getMaps() {
        return mapList;
    }

    public Map getNextMap() throws MapFormatException {
        String name = mapNames.next();
        Map map = readMap(name, (java.util.Map) maps.get(name));
        mapList.add(map);
        return map;
    }

    public boolean hasNextMap() {
        return mapNames.hasNext();
    }

    public int loadAll() throws MapFormatException {
        while (mapNames.hasNext()) {
            getNextMap();
        }
        return mapList.size();
    }

}
