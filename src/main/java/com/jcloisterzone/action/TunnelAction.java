package com.jcloisterzone.action;

import java.awt.Image;

import com.jcloisterzone.Player;
import com.jcloisterzone.board.pointer.FeaturePointer;
import com.jcloisterzone.ui.resources.DisplayableEntity;
import com.jcloisterzone.wsio.RmiProxy;


@DisplayableEntity("actions/tunnel")
public class TunnelAction extends SelectFeatureAction {

    private final boolean secondTunnelPiece;

    public TunnelAction(boolean secondTunnelPiece) {
        this.secondTunnelPiece = secondTunnelPiece;
    }

    @Override
    public Image getImage(Player player, boolean active) {
        if (active && isSecondTunnelPiece()) {
            return getImage(player.getColors().getTunnelBColor());
        } else {
            return super.getImage(player, active);
        }
    }

    public boolean isSecondTunnelPiece() {
        return secondTunnelPiece;
    }

    @Override
    public void perform(RmiProxy server, FeaturePointer bp) {
        server.placeTunnelPiece(bp, secondTunnelPiece);

    }

    @Override
    protected int getSortOrder() {
        return secondTunnelPiece ? 41 : 40;
    }

    @Override
    public String toString() {
        return "place tunnel";
    }

}
