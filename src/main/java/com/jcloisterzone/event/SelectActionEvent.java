package com.jcloisterzone.event;

import com.jcloisterzone.Player;
import com.jcloisterzone.action.PlayerAction;

import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Vector;

@Idempotent
public class SelectActionEvent extends PlayEvent {

    private final boolean passAllowed;
    private final IndexedSeq<? extends PlayerAction<?>> actions;


    public SelectActionEvent(Player targetPlayer, IndexedSeq<? extends PlayerAction<?>> actions, boolean passAllowed) {
        super(null, targetPlayer);
        this.actions = actions;
        this.passAllowed = passAllowed;
    }

    public SelectActionEvent(Player player, PlayerAction<?> action, boolean passAllowed) {
        this(player, Vector.of(action), passAllowed);
    }

    public IndexedSeq<? extends PlayerAction<?>> getActions() {
        return actions;
    }

    public boolean isPassAllowed() {
        return passAllowed;
    }

    @Override
    public String toString() {
        return super.toString() + " passAllowed:" + passAllowed + " actions:" + actions;
    }
}
