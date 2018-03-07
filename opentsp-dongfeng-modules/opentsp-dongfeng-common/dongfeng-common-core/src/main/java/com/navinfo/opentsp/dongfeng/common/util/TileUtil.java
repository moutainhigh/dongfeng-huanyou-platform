package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.pojo.BoundingBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 瓦片工具
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-13
 * @modify
 * @copyright Navi Tsp
 */
public class TileUtil {

    public static List<Long> getTileIds4Rectangle(final long lat, final long lon) {
        List<Long> tileids = new ArrayList<Long>();
        // 矩形，点按顺时针方向放入
        int[] tilezxy1 = getTile(lat, lon, 13);
        int[] tilezxy2 = getTile(lat, lon, 13);
        int[] tilezxy3 = getTile(lat, lon, 13);
        for (int i = tilezxy1[1]; i <= tilezxy2[1]; i++) {
            for (int j = tilezxy1[2]; j <= tilezxy3[2]; j++) {
                long tileId = xyzToTileId(tilezxy1[0], i, j);
                tileids.add(tileId);
            }
        }
        return tileids;
    }

    public static List<Long> getTileIds4Circle(final long lat, final long lon, final String radius) {
        List<Long> tileids = new ArrayList<Long>();
        int n = 0;
        int[] tilezxy = getTile(lat, lon, 13);
        int roadnum = (int) Math.ceil(Double.parseDouble(radius));
        if (roadnum % 4800 != 0) {
            n = roadnum / 4800 + 1;
        } else {
            n = roadnum / 4800;
        }
        for (int i = tilezxy[1] - (n - 1); i <= tilezxy[1] + (n - 1); i++) {
            for (int j = tilezxy[2] - (n - 1); j <= tilezxy[2] + (n - 1); j++) {
                long tileId = xyzToTileId(tilezxy[0], i, j);
                tileids.add(tileId);
            }
        }
        return tileids;
    }

    /**
     * 热力图瓦片ID
     * @param latitude  format : xx.xxxxxx
     * @param longitude format : xx.xxxxxx
     * @param heatMapRadius format : xxxxxxx
     * @return
     */
    public static List<Long> getTileIds(final String latitude, final String longitude, final String heatMapRadius) {
        int n = 0;
        List<Long> list = new ArrayList<>();
        BigDecimal x = new BigDecimal(latitude);
        BigDecimal lat = x.multiply(new BigDecimal("1000000"));
        BigDecimal y = new BigDecimal(longitude);
        BigDecimal lon = y.multiply(new BigDecimal("1000000"));
        int radius = (int) Math.ceil(Double.parseDouble(heatMapRadius));
        int[] tilezxy = getTile(lat.longValue(), lon.longValue(), 13);
        if (radius % 4800 != 0) {
            n = radius / 4800 + 1 + 1;
        } else {
            n = radius / 4800 + 1;
        }
        for (int i = tilezxy[1] - (n - 1); i <= tilezxy[1] + (n - 1); i++) {
            for (int j = tilezxy[2] - (n - 1); j <= tilezxy[2] + (n - 1); j++) {
                long tileId = xyzToTileId(tilezxy[0], i, j);
                list.add(tileId);
            }
        }
        return list;
    }

    //根据经纬度获得瓦片zxy
    private static int[] getTile(final long latitude, final long longitude, final int zoom) {
        double lat = ((double) latitude) / Math.pow(10, 6);
        double lon = ((double) longitude) / Math.pow(10, 6);
        int[] zxy = new int[3];
        zxy[0] = zoom;
        zxy[1] = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
        zxy[2] = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat))
                + 1 / Math.cos(Math.toRadians(lat)))
                / Math.PI)
                / 2 * (1 << zoom));
        if (zxy[1] < 0) {
            zxy[1] = 0;
        }
        if (zxy[1] >= (1 << zoom)) {
            zxy[1] = ((1 << zoom) - 1);
        }
        if (zxy[2] < 0) {
            zxy[2] = 0;
        }
        if (zxy[2] >= (1 << zoom)) {
            zxy[2] = ((1 << zoom) - 1);
        }
        return zxy;
    }

    //根据zxy获取瓦片id
    private static long xyzToTileId(final int z, final int x, final int y) {
        return (long) (z * Math.pow(10, 10)) + (long) (x * Math.pow(10, 5)) + y;
    }

    //根据瓦片id获取zxy
    private static int[] tileIdTozxy(final long tileId) {
        int[] zxy = new int[3];
        zxy[0] = (int) (tileId / Math.pow(10, 10));
        zxy[1] = (int) (tileId / Math.pow(10, 5)) % ((int) Math.pow(10, 5));
        zxy[2] = (int) (tileId % Math.pow(10, 5));
        return zxy;
    }

    // 根据瓦片id获得经纬度
    public static BoundingBox tile2boundingBox(final int x, final int y,
                                               final int zoom) {
        BoundingBox bb = new BoundingBox();
        bb.setNorth(tile2lat(y, zoom));
        bb.setSouth(tile2lat(y + 1, zoom));
        bb.setWest(tile2lon(x, zoom));
        bb.setEast(tile2lon(x + 1, zoom));
        return bb;
    }

    private static double tile2lon(final int x, final int z) {
        return x / Math.pow(2.0, z) * 360.0 - 180;
    }

    private static double tile2lat(final int y, final int z) {
        double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
        return Math.toDegrees(Math.atan(Math.sinh(n)));
    }
}
