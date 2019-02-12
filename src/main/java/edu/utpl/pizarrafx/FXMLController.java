package edu.utpl.pizarrafx;

import com.google.common.eventbus.Subscribe;
import edu.utpl.pizarrafx.constant.CommandEnum;
import edu.utpl.pizarrafx.constant.Utils;
import edu.utpl.pizarrafx.event.CommandEvent;
import edu.utpl.pizarrafx.event.RoleEvent;
import edu.utpl.pizarrafx.event.ShapeEvent;
import edu.utpl.pizarrafx.guice.FxmlLoaderService;
import edu.utpl.pizarrafx.models.ButtonNode;
import edu.utpl.pizarrafx.models.Config;
import edu.utpl.pizarrafx.models.ShapeLine;
import edu.utpl.pizarrafx.properties.Defaults;
import edu.utpl.pizarrafx.properties.Properties;
import edu.utpl.pizarrafx.raft.RaftNode;
import edu.utpl.pizarrafx.services.UtilConfig;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;

public class FXMLController implements Initializable {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(FXMLController.class);
    private final String TITLE_BASE = "Estado actual: {0}-{1}";

    @FXML
    private HBox hboxNodes;

    @FXML
    private VBox top;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Canvas canvasPane;

    private final RaftNode _raftNode;

    private final FxmlLoaderService _fxmlLoaderService;

    //private VBox top;
    @FXML
    private HBox bottom;
    @FXML
    private Region spacerL;
    @FXML
    private Region spacerR;
    @FXML
    private Label locationLbl;

    @FXML
    private Label zoomLabel;

    @FXML
    private Label statusRaftLbl;

    @FXML
    private Slider zoomSlider;

    @FXML
    private Label widthLabel;

    @FXML
    private Slider widthSlider;

    @FXML
    private HBox topControls;

    @FXML
    private Label backLabel;

    @FXML
    private ColorPicker backColorPicker;

    @FXML
    private ColorPicker foreColorPicker;

    //atributos propios para el canvas
    private double locationX = 0.0;
    private double locationY = 0.0;
    private GraphicsContext gc;

    @Inject
    public FXMLController(final FxmlLoaderService fxmlLoaderService, RaftNode raftNode) {
        this._fxmlLoaderService = fxmlLoaderService;
        this._raftNode = raftNode;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOG.info("Prueba de inicio de escena: {}", Color.BLACK.toString());

        this.setupTop();
        this.setupBottom();
        this.setupCanvas();
    }

    private void setupCanvas() {
        //se establece un color aleatorio para cada pincel de canvas
        foreColorPicker.setValue(Utils.getRamdomColorPreset());
        //por defecto se desabilita el panel hasta no tener un leader
        canvasPane.setDisable(true);
        gc = canvasPane.getGraphicsContext2D();

        Line line = new Line();
        canvasPane.setOnMousePressed(e -> {
            gc.setLineWidth(Properties.getWidth());
            gc.setStroke(foreColorPicker.getValue()); //se puede hacer dinamico ese paso
            line.setStartX(e.getX());
            line.setStartY(e.getY());
        });
        /*
        canvas.setOnMouseDragged(e->{
            if(drowbtn.isSelected()) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if(rubberbtn.isSelected()){
                double lineWidth = gc.getLineWidth();
                gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
            }
        });*/
        canvasPane.setOnMouseReleased(e -> {
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

            try {
                this._raftNode.put("Linea",
                        new ShapeLine(foreColorPicker.getValue().toString(),
                                line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), Properties.getWidth()));
            } catch (Exception ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //para verificar cada 2 segundos si la pantalla ya esta activa
        this.startTaskFindLeader();

    }

    public void runTask() {
        while (true) {
            try {
                if (this._raftNode.getLeaderInfo() != null) {
                    LOG.info("YA EXISTE LEDER SE ACTIVA LA PANTALLA - >");
                    canvasPane.setDisable(false);
                } else {
                    canvasPane.setDisable(true);
                }
                System.out.println("Scheduling: " + System.nanoTime());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public void startTaskFindLeader() {
        // Create a Runnable
        Runnable task = () -> {
            runTask();
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    @FXML
    private void handleButtonClear(ActionEvent event) {
        try {
            System.out.println("Eliminando nodo");
            this._raftNode.remove("Linea");
        } catch (Exception ex) {
            System.out.println("Error al eliminar: " + ex.getLocalizedMessage());
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void updateSliderFromCanvas(ScrollEvent event) {
        zoomSlider.setValue(zoomSlider.getValue() + event.getDeltaY() * 1.5);
    }

    @FXML
    private void updateLocationFromCanvas(MouseEvent event) {
        int x = (int) (this.getLocationX() - event.getX()) * -1;
        int y = (int) (this.getLocationY() - event.getY()) * -1;
        locationLbl.setText("Location: (X: " + x + ", Y: " + y + ")");
    }

    @FXML
    private void updateBackgroundToCanvas(ActionEvent event) {
        setBackgroundToCanvas(backColorPicker.getValue());
    }

    @Subscribe
    public void changedText(final String txt) {
        LOG.info("cambio el texto desde el bus: " + txt);
    }

    @Subscribe
    public void changedRole(final RoleEvent evt) {
        LOG.info("Cambio el rol desde el bus ");
        if (evt != null) {
            LOG.info("Cambio el rol desde el bus: " + evt.getRole().toString());
            Platform.runLater(
                    () -> {
                        if (evt.isLeader()) {
                            bottom.setBackground(new Background(new BackgroundFill(Defaults.BG_COLOR_LEADER, null, null)));
                            try {
                                //si es el lider enviar un mensaje al resto
                                LOG.info("Leader notifica a todos que habiliten sus pantallas.");
                                _raftNode.sendMessage(CommandEnum.HABILITAR_PANTALLA.getCommand());
                            } catch (Exception ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            bottom.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
                        }
                        statusRaftLbl.setText(
                                MessageFormat.format(TITLE_BASE, _raftNode.getId(), evt.getRole().toString()));
                    }
            );

        }
    }

    @Subscribe
    public void changedShape(final ShapeEvent evt) {
        if (evt != null) {
            LOG.info("Se agrego un shape desde el bus: ");
            Platform.runLater(
                    () -> {
                        gc.setLineWidth(evt.getShape().getWidth());
                        gc.setStroke(Color.web(evt.getShape().getColorRgb(), 1));
                        gc.strokeLine(evt.getShape().getStartX(),
                                evt.getShape().getStartY(),
                                evt.getShape().getEndX(), evt.getShape().getEndY());
                    }
            );
        }
    }

    @Subscribe
    public void changedCommand(final CommandEvent evt) {
        if (evt != null) {
            LOG.info("Se recivio un comando desde el bus: ");
            Platform.runLater(
                    () -> {
                        switch (evt.getCommand()) {
                            case HABILITAR_PANTALLA:
                                LOG.info("Habilitando pantalla en el nodo cliente.");
                                canvasPane.setDisable(false);
                                break;
                        }
                    }
            );
        }
    }

    private void setupBottom() {
        HBox.setHgrow(spacerL, Priority.ALWAYS);
        HBox.setHgrow(spacerR, Priority.ALWAYS);
        // Add the components.
        bottom.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
        zoomLabel.setTextFill(Defaults.TEXT_COLOR);
        locationLbl.setTextFill(Defaults.TEXT_COLOR);

        // Setup the listener for the slider.
        zoomSlider.valueProperty().addListener(
                (ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
                    double zoom = newVal.doubleValue();
                    setZoomToCanvas(zoom / 100.0);
                    // Display zoom as an int because it looks nicer.
                    zoomLabel.setText("Zoom: " + (int) zoom + "%");
                });

    }

    private void setupTop() {
        List<ButtonNode> buttonlist = new ArrayList<>(); //our Collection to hold newly created Buttons
        hboxNodes.setSpacing(15);
        try {
            Config config = UtilConfig.obtenerConfiguracion();
            for (String member : config.getMembers()) {
                ButtonNode buttonItem = new ButtonNode(member, "Nodo: " + member);
                buttonItem.getStyleClass().add("button-node");
                buttonItem.setOnAction((ActionEvent e) -> {
                    LOG.info("Proceso de iniciar el servidor empezado...");
                    buttonlist.forEach((btn) -> {
                        btn.setDisable(true);
                    });

                    //ejecutar asincronamente el inicio del nodo en modo raft
                    //esto se realiza para liberar el hilo principal
                    Platform.runLater(() -> {
                        _raftNode.initialize(buttonItem.getIdNode());
                    });

                });
                buttonlist.add(buttonItem);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        hboxNodes.getChildren().clear(); //remove all Buttons that are currently in the container
        hboxNodes.getChildren().addAll(buttonlist); //then add all your Buttons that you just created
        hboxNodes.applyCss();

        top.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));

        topControls.setSpacing(10);
        topControls.setPadding(new Insets(5));
        //a los controles de la pizarra
        widthSlider.setValue(Properties.getWidth());
        widthLabel.setText("Width: " + (int) widthSlider.getValue());
        widthLabel.setTextFill(Defaults.TEXT_COLOR);

        // Setup what happens when the slider changes.
        widthSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            // Make it look good with a format.
            int val = newVal.intValue();
            widthLabel.setText("Width: " + val);
            Properties.setWidth(val);
        });

        //paleta de colores
        backLabel.setTextFill(Defaults.TEXT_COLOR);
    }

    /**
     * Set the zoom of this Canvas.
     *
     * @param zoom
     */
    public void setZoomToCanvas(double zoom) {
        canvasPane.setScaleX(zoom);
        canvasPane.setScaleY(zoom);
    }

    /**
     * Get the X distance this Canvas is from the origin
     */
    public double getLocationX() {
        return locationX;
    }

    /**
     * Get the Y distance this Canvas is from the origin
     *
     * @return
     */
    public double getLocationY() {
        return locationY;
    }

    /**
     * Set the X and Y distance this Canvas is from the origin.
     *
     * Should be done when changing the translation.
     *
     * @param x
     * @param y
     */
    public void setLocation(double x, double y) {
        locationX = x;
        locationY = y;
    }

    /**
     * Set the background color of this Canvas.
     */
    private void setBackgroundToCanvas(Color color) {
        /*canvasPane.setBackground(new Background(new BackgroundFill(
		color, new CornerRadii(0), new Insets(0))));*/

        GraphicsContext gc = canvasPane.getGraphicsContext2D();
        gc.setFill(color
        );
        gc.fillRect(0, 0, canvasPane.getWidth(), canvasPane.getHeight());
    }

}
