/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.event;

/**
 *
 * @author lojasoft2
 */
public final class ShowHideEvent {
    private final boolean show;

    public ShowHideEvent(final boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }
}