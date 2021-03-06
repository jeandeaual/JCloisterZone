package com.jcloisterzone.ui.resources;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcloisterzone.board.Location;
import com.jcloisterzone.board.Rotation;
import com.jcloisterzone.board.Tile;
import com.jcloisterzone.figure.Meeple;
import com.jcloisterzone.ui.Client;
import com.jcloisterzone.ui.ImmutablePoint;
import com.jcloisterzone.ui.plugin.Plugin;

/**
 * Delegates requests to child plugins
 */
public class PlugableResourceManager implements ResourceManager {

    protected final transient Logger logger = LoggerFactory.getLogger(getClass());
    private final List<ResourceManager> managers;


    public PlugableResourceManager(List<Plugin> plugins) {
        managers = new ArrayList<>();
        for (Plugin p: plugins) {
            if (p instanceof ResourceManager) {
                managers.add((ResourceManager) p);
            }
        }
        managers.add(new DefaultResourceManager());
    }

    @Override
    public TileImage getTileImage(Tile tile) {
        return getTileImage(tile, tile.getRotation());
    }

    @Override
    public TileImage getTileImage(Tile tile, Rotation rot) {
        for (ResourceManager manager : managers) {
            TileImage result = manager.getTileImage(tile, rot);
            if (result != null) return result;
        }
        logger.warn("Unable to load tile image for {}", tile.getId());
        return null;
    }

    @Override
    public TileImage getAbbeyImage(Rotation rot) {
        for (ResourceManager manager : managers) {
            TileImage result = manager.getAbbeyImage(rot);
            if (result != null) return result;
        }
        logger.warn("Unable to load tile Abbey image");
        return null;
    }

    @Override
    public Image getImage(String path) {
    	for (ResourceManager manager : managers) {
            Image result = manager.getImage(path);
            if (result != null) return result;
        }
    	logger.warn("Unable to load image {}", path);
        return null;
    }

    @Override
    public Image getLayeredImage(LayeredImageDescriptor lid) {
    	for (ResourceManager manager : managers) {
            Image result = manager.getLayeredImage(lid);
            if (result != null) return result;
        }
    	logger.warn("Unable to load layered image {}", lid.getBaseName());
        return null;
    }


    @Override
    public ImmutablePoint getMeeplePlacement(Tile tile, Class<? extends Meeple> type, Location loc) {
        for (ResourceManager manager : managers) {
            ImmutablePoint result = manager.getMeeplePlacement(tile, type, loc);
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public Map<Location, FeatureArea> getBarnTileAreas(Tile tile, int width, int height, Set<Location> corners) {
        for (ResourceManager manager : managers) {
            Map<Location, FeatureArea> result = manager.getBarnTileAreas(tile, width, height, corners);
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public Map<Location, FeatureArea> getBridgeAreas(Tile tile, int width, int height, Set<Location> locations) {
        for (ResourceManager manager : managers) {
            Map<Location, FeatureArea> result = manager.getBridgeAreas(tile, width, height, locations);
            if (result != null) return result;
        }
        return null;
    }


    @Override
    public Map<Location, FeatureArea> getFeatureAreas(Tile tile, int width, int height, Set<Location> locations) {
        for (ResourceManager manager : managers) {
            Map<Location, FeatureArea> result = manager.getFeatureAreas(tile, width, height, locations);
            if (result != null) return result;
        }
        return null;
    }

}
