/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.models;

import javafx.scene.control.Button;

/**
 *
 * @author lojasoft2
 */
public class ButtonNode extends Button{
    private String idNode;

    public ButtonNode(String idNode, String text){
        super(text);
        this.idNode = idNode;
    }
    
    public String getIdNode() {
        return idNode;
    }

    public void setIdNode(String idNode) {
        this.idNode = idNode;
    }
    
    
}
