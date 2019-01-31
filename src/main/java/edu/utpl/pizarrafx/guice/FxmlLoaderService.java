/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.guice;

import edu.utpl.pizarrafx.constant.FxmlEnum;
import javafx.scene.Parent;

public interface FxmlLoaderService {
    Parent load(FxmlEnum fxmlEnum);
}
