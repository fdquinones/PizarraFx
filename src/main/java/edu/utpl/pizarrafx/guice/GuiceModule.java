/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.guice;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import edu.utpl.pizarrafx.raft.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author lojasoft2
 */
public class GuiceModule extends AbstractModule {
    private static final Logger LOG = LogManager.getLogger(GuiceModule.class);
    
    @Override
    protected void configure() {
        LOG.info("Inicializando el modulo de injeccion");
        bind(RaftNode.class).asEagerSingleton();
        bind(EventBus.class).asEagerSingleton();
        bind(FxmlLoaderService.class).to(FxmlLoaderServiceImpl.class).asEagerSingleton();
    }
}
