/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author lojasoft2
 */
public class Utils {

    public static Color getRamdomColor() {
        Random random = new Random();
        final float hue = random.nextFloat();
        // Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        return Color.hsb(hue, saturation, luminance);
    }

    public static Color getRamdomColorPreset() {
        List<String> listColors = Arrays.asList("#BF8306", "#7F5804", "#FFAF08", "#402C02");
        Random rand = new Random();
        String randomElement = listColors.get(rand.nextInt(listColors.size()));
        
        return Color.web(randomElement);
    }
}
