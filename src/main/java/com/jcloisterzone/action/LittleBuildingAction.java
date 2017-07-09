package com.jcloisterzone.action;

import com.jcloisterzone.LittleBuilding;
import com.jcloisterzone.ui.grid.ActionLayer;
import com.jcloisterzone.ui.grid.layer.LittleBuildingActionLayer;
import com.jcloisterzone.ui.resources.DisplayableEntity;
import com.jcloisterzone.wsio.RmiProxy;

// TODO generic token action ?

@DisplayableEntity("actions/building")
public class LittleBuildingAction extends PlayerAction<LittleBuilding> {

    @Override
    protected Class<? extends ActionLayer<?>> getActionLayerType() {
        return LittleBuildingActionLayer.class;
    }

    @Override
    public void perform(RmiProxy server, LittleBuilding target) {
       server.placeLittleBuilding(target);
    }

}
