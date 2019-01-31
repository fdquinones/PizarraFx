/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

/**
 *
 * @author lojasoft2
 */
public class Config {
    private String[] members;

    @JsonProperty("members=")
    public String[] getMembers() { return members; }
    @JsonProperty("members=")
    public void setMembers(String[] value) { this.members = value; }
    
    
    @Override
    public String toString() {
        return "Config{" + "members=" + Arrays.toString(members) + '}';
    }
}
