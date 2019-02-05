

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.models;


import java.io.*;

/**
 *
 * @author lojasoft2
 */

public class ShapeLine implements Serializable {
    private String ColorRgb;
    private double StartX;
    private double StartY;
    private double EndX;
    private double EndY;
    private double Width;

    public ShapeLine(String ColorRgb, double StartX, double StartY, double EndX, double EndY, double Width) {
        this.ColorRgb = ColorRgb;
        this.StartX = StartX;
        this.StartY = StartY;
        this.EndX = EndX;
        this.EndY = EndY;
        this.Width = Width;
    }
    
    
    
    /**
     * @return the ColorRgb
     */
    public String getColorRgb() {
        return ColorRgb;
    }

    /**
     * @param ColorRgb the ColorRgb to set
     */
    public void setColorRgb(String ColorRgb) {
        this.ColorRgb = ColorRgb;
    }

    /**
     * @return the StartX
     */
    public double getStartX() {
        return StartX;
    }

    /**
     * @param StartX the StartX to set
     */
    public void setStartX(double StartX) {
        this.StartX = StartX;
    }

    /**
     * @return the StartY
     */
    public double getStartY() {
        return StartY;
    }

    /**
     * @param StartY the StartY to set
     */
    public void setStartY(double StartY) {
        this.StartY = StartY;
    }

    /**
     * @return the EndX
     */
    public double getEndX() {
        return EndX;
    }

    /**
     * @param EndX the EndX to set
     */
    public void setEndX(double EndX) {
        this.EndX = EndX;
    }

    /**
     * @return the EndY
     */
    public double getEndY() {
        return EndY;
    }

    /**
     * @param EndY the EndY to set
     */
    public void setEndY(double EndY) {
        this.EndY = EndY;
    }

    /**
     * @return the Width
     */
    public double getWidth() {
        return Width;
    }

    /**
     * @param Width the Width to set
     */
    public void setWidth(double Width) {
        this.Width = Width;
    }
    
}
