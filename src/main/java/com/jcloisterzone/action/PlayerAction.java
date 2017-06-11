package com.jcloisterzone.action;

import java.awt.Color;
import java.awt.Image;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.jcloisterzone.Player;
import com.jcloisterzone.ui.Client;
import com.jcloisterzone.ui.grid.ActionLayer;
import com.jcloisterzone.ui.grid.MainPanel;
import com.jcloisterzone.ui.resources.LayeredImageDescriptor;
import com.jcloisterzone.ui.view.GameView;
import com.jcloisterzone.wsio.RmiProxy;

public abstract class PlayerAction<T> implements Comparable<PlayerAction<?>>, Iterable<T> {

    @Deprecated
    private final String name;
    protected final Set<T> options = new HashSet<T>();

    protected Client client;
    protected MainPanel mainPanel;

    public PlayerAction(String name) {
        this.name = name;
    }

    public PlayerAction(String name, io.vavr.collection.Set<T> ptrs) {
        this.name = name;
        addAll(ptrs);
    }

    public abstract void perform(RmiProxy server, T target);

    @Override
    public Iterator<T> iterator() {
        return options.iterator();
    }

    public PlayerAction<T> add(T option) {
        options.add(option);
        return this;
    }

    public PlayerAction<T> addAll(io.vavr.collection.Set<T> options) {
        this.options.addAll(options.toJavaList());
        return this;
    }

    @Deprecated
    public PlayerAction<T> addAll(Collection<T> options) {
        this.options.addAll(options);
        return this;
    }

    public ImmutableSet<T> getOptions() {
        return ImmutableSet.copyOf(options);
    }

    public boolean isEmpty() {
        return options.isEmpty();
    }

    public String getName() {
        return name;
    }

    public Image getImage(Player player, boolean active) {
        return getImage(player != null && active ? player.getColors().getMeepleColor() : Color.GRAY);
    }


    protected final ActionLayer<?> getActionLayer(Class<? extends ActionLayer<?>> layerType) {
        return mainPanel.getGridPanel().findLayer(layerType);
    }

    abstract protected Class<? extends ActionLayer<?>> getActionLayerType();


    /** Called when user select action in action panel */
    public void select(boolean active) {
        @SuppressWarnings("unchecked")
        ActionLayer<? super PlayerAction<?>> layer = (ActionLayer<? super PlayerAction<?>>) getActionLayer(getActionLayerType());
        layer.setAction(active, this);
        mainPanel.getGridPanel().showLayer(layer);
    }

    /** Called when user deselect action in action panel */
    public void deselect() {
        @SuppressWarnings("unchecked")
        ActionLayer<? super PlayerAction<?>> layer = (ActionLayer<? super PlayerAction<?>>) getActionLayer(getActionLayerType());
        layer.setAction(false, null);
        mainPanel.getGridPanel().hideLayer(layer);
    }

    protected Image getImage(Color color) {
        return client.getResourceManager().getLayeredImage(new LayeredImageDescriptor("actions/" + getName(), color));
    }


    protected int getSortOrder() {
        return 1024;
    }

    @Override
    public int compareTo(PlayerAction<?> o) {
        return getSortOrder() - o.getSortOrder();
    }

    public void setClient(Client client) {
        this.client = client;
        this.mainPanel = ((GameView) client.getView()).getMainPanel();
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
