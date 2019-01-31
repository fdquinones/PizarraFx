/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.constant;

/**
 *
 * @author lojasoft2
 */
public enum FxmlEnum {
    MAIN("/fxml/Scene.fxml");

    private final String path;

    FxmlEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}