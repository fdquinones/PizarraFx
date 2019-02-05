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
public enum CommandEnum {
    HABILITAR_PANTALLA("HABILITAR_PANTALLA");
    
    
    private final String command;

    CommandEnum(final String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}