/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.event;

import edu.utpl.pizarrafx.models.ShapeLine;

/**
 *
 * @author lojasoft2
 */
public final class ShapeEvent {
    private final ShapeLine shape;

    public ShapeEvent(final ShapeLine shape) {
        this.shape = shape;
    }
    
    public  ShapeLine getShape(){
        return this.shape;
    }
    
}