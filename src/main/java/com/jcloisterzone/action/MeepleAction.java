package com.jcloisterzone.action;

import java.awt.Color;
import java.awt.Image;

import com.jcloisterzone.board.pointer.FeaturePointer;
import com.jcloisterzone.figure.BigFollower;
import com.jcloisterzone.figure.Builder;
import com.jcloisterzone.figure.Mayor;
import com.jcloisterzone.figure.Meeple;
import com.jcloisterzone.figure.Phantom;
import com.jcloisterzone.figure.Pig;
import com.jcloisterzone.figure.SmallFollower;
import com.jcloisterzone.figure.Wagon;
import com.jcloisterzone.ui.resources.DisplayableEntity;
import com.jcloisterzone.ui.resources.LayeredImageDescriptor;
import com.jcloisterzone.wsio.RmiProxy;

import io.vavr.collection.Set;

public class MeepleAction extends SelectFeatureAction {

    private final Class<? extends Meeple> meepleType;

    public MeepleAction(Class<? extends Meeple> meepleType) {
        super();
        this.meepleType = meepleType;
    }

    public MeepleAction(Class<? extends Meeple> meepleType, Set<FeaturePointer> ptrs) {
        super(ptrs);
        this.meepleType = meepleType;
    }

    protected Image getImage(Color color) {
        String name = meepleType.getSimpleName().toLowerCase();
        return client.getResourceManager().getLayeredImage(new LayeredImageDescriptor("actions/" + name, color));
    }


    public Class<? extends Meeple> getMeepleType() {
        return meepleType;
    }

    @Override
    public void perform(RmiProxy server, FeaturePointer bp) {
        server.deployMeeple(bp, meepleType);
    }

    @Override
    protected int getSortOrder() {
        if (meepleType.equals(SmallFollower.class)) return 9;
        if (meepleType.equals(BigFollower.class)) return 10;
        if (meepleType.equals(Wagon.class)) return 12;
        if (meepleType.equals(Mayor.class)) return 13;
        if (meepleType.equals(Builder.class)) return 14;
        if (meepleType.equals(Pig.class)) return 15;
        if (meepleType.equals(Phantom.class)) return 16;
        return 19;
    }

    @Override
    public String toString() {
        return "place " + meepleType.getSimpleName() + " ? " + getOptions();
    }
}
