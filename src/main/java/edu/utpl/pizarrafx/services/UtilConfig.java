/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utpl.pizarrafx.models.Config;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author lojasoft2
 */
public class UtilConfig {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(UtilConfig.class);

    public static Config obtenerConfiguracion() throws URISyntaxException {

        Config config = null;
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            String json = IOUtils.toString(UtilConfig.class.getResourceAsStream("/config/config.json"), StandardCharsets.UTF_8.name());
            config = mapper.readValue(json, Config.class);
            LOG.debug(config.toString());
        } catch (IOException ex) {
            Logger.getLogger(UtilConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

        return config;
    }

}
