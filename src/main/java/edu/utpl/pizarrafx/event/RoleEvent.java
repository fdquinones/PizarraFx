/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.event;

import org.jgroups.protocols.raft.Role;

/**
 *
 * @author lojasoft2
 */
public final class RoleEvent {
    private final Role role;

    public RoleEvent(final Role role) {
        this.role = role;
    }

    public boolean isLeader() {
        return role.equals(Role.Leader);
    }
    
    public  Role getRole(){
        return this.role;
    }
    
}