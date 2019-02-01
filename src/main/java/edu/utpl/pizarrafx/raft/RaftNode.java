/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.raft;

import com.google.common.eventbus.EventBus;
import edu.utpl.pizarrafx.event.RoleEvent;
import edu.utpl.pizarrafx.event.ShapeEvent;
import edu.utpl.pizarrafx.models.ShapeLine;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.inject.Inject;
import org.jgroups.JChannel;
import org.jgroups.raft.RaftHandle;
import org.jgroups.raft.blocks.ReplicatedStateMachine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgroups.protocols.raft.Role;

/**
 *
 * @author lojasoft2
 */
public class RaftNode {

    private ReplicatedStateMachine<String, ShapeLine> rsm;
    private static final Logger LOG = LogManager.getLogger(RaftNode.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final EventBus _eventBus;

    @Inject
    public RaftNode(EventBus eventBus) {
        this._eventBus = eventBus;
    }

    public Future<String> initialize(String id) {
        return executor.submit(() -> {
            LOG.info("Iniciando nodo raft para el id: {}", id);
            this._eventBus.post("String Event desde el servidor");
            JChannel ch = new JChannel("src/main/resources/config/raft.xml");
            rsm = new ReplicatedStateMachine<>(ch);
            RaftHandle handle = new RaftHandle(ch, rsm);
            handle.raftId(id);

            rsm.addRoleChangeListener((Role role) -> {
                _eventBus.post(new RoleEvent(role));
            });

            rsm.addNotificationListener(new ReplicatedStateMachine.Notification() {
                @Override
                public void put(Object k, Object v, Object v1) {
                    LOG.debug("Campo agregado");
                    LOG.info("Campo agregado: {}", k.toString());
                    ShapeLine shape = (v instanceof ShapeLine ? (ShapeLine)v : null);
                    //ShapeLine shape = ShapeLine.class.cast(v);
                    if(shape != null){
                        _eventBus.post(new ShapeEvent( shape));
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void remove(Object k, Object v) {
                    LOG.info("Campo removido por consenso");
                    LOG.debug("Campo removido por consenso");
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            try {
                ch.connect("raft-cluster");
            } catch (Exception e) {
                System.out.println("Error al arrancar raft node: " + e.getMessage());
            }
            
            Thread.sleep(1000);
            return "Proceso completado";
        });
    }

    public ShapeLine get(String key) {
        return rsm.get(key);
    }

    public void put(String key, ShapeLine value) throws Exception {
        rsm.put(key, value);
    }

    public void remove(String key) throws Exception {
        rsm.remove(key);
    }

    public boolean exists(String key) {
        return get(key) != null;
    }
}
