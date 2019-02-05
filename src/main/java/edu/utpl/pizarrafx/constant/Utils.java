/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.constant;

import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author lojasoft2
 */
public class Utils {
    public static Color getRamdomColor(){
        Random random = new Random();
        final float hue = random.nextFloat();
        // Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        return Color.hsb(hue, saturation, luminance);
    }
}
