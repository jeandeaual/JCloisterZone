package com.jcloisterzone.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jcloisterzone.board.Location;
import com.jcloisterzone.board.Position;
import com.jcloisterzone.board.pointer.FeaturePointer;
import com.jcloisterzone.ui.grid.ActionLayer;
import com.jcloisterzone.ui.grid.layer.FeatureAreaLayer;

public abstract class SelectFeatureAction extends PlayerAction<FeaturePointer> {


    public SelectFeatureAction() {
        super();
    }

    public SelectFeatureAction(io.vavr.collection.Set<FeaturePointer> ptrs) {
        super(ptrs);
    }

    @Override
    protected Class<? extends ActionLayer<?>> getActionLayerType() {
        return FeatureAreaLayer.class;
    }


    public Map<Position, Set<Location>> groupByPosition() {
        Map<Position, Set<Location>> map = new HashMap<>();
        for (FeaturePointer fp: options) {
            Set<Location> locations = map.get(fp.getPosition());
            if (locations == null) {
                locations = new HashSet<>();
                map.put(fp.getPosition(), locations);
            }
            locations.add(fp.getLocation());
        }
        return map;
    }

    //TODO direct implementation
    public Set<Location> getLocations(Position p) {
        return groupByPosition().get(p);
    }


}
