/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.util;

/**
 *
 * @author Lars
 */
public class Map {
    
    private static final String BACKGROUND_PREFIX = "resources/mapdata/backgrounds/";
    
    private final String name;
    private final int xHeight;
    private final int yHeight;
    private final String backgroundMap;
    private final int[][] field;

    public Map(String name, int xHeight, int yHeight, String backgroundMap, int[][] field) {
        this.name = name;
        this.xHeight = xHeight;
        this.yHeight = yHeight;
        this.backgroundMap = backgroundMap;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public int getxHeight() {
        return xHeight;
    }

    public int getyHeight() {
        return yHeight;
    }

    public String getBackgroundMap() {
        return BACKGROUND_PREFIX + backgroundMap;
    }

    public int[][] getField() {
        return field;
    }
    
    public int getField(int x, int y) {
        return field[x][y];
    }
    
}
