/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.pizarrafx.guice;

import com.google.common.eventbus.EventBus;
import com.google.common.io.Resources;
import com.google.inject.Injector;
import edu.utpl.pizarrafx.constant.FxmlEnum;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import javax.inject.Inject;
import java.io.IOException;

public final class FxmlLoaderServiceImpl implements FxmlLoaderService {
    private final Injector injector;
    private final EventBus eventBus;

    @Inject
    public FxmlLoaderServiceImpl(final Injector injector, final EventBus eventBus) {
        this.injector = injector;
        this.eventBus = eventBus;
    }

    /**
     * When creating the controller, use Guice to inject dependencies into it first and then register it to the event bus
     * before returning it.
     *
     * @param fxmlEnum FXML Enum
     * @return Node
     */
    @Override
    public Parent load(final FxmlEnum fxmlEnum) {
        try {
            return FXMLLoader.load(getClass().getResource(fxmlEnum.getPath()),
                                   null,
                                   new JavaFXBuilderFactory(),
                                   clazz -> {
                                       final Object controller = injector.getInstance(clazz);
                                       eventBus.register(controller);
                                       return controller;
                                   });
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load FXML from path: " + fxmlEnum.getPath(), e);
        }
    }
}