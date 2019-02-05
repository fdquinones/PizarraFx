/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.event;

import edu.utpl.pizarrafx.constant.CommandEnum;

/**
 *
 * @author lojasoft2
 */
public final class CommandEvent {
    private final CommandEnum command;

    public CommandEvent(final CommandEnum command) {
        this.command = command;
    }

    
    public  CommandEnum getCommand(){
        return this.command;
    }
    
}