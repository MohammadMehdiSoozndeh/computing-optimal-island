package main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.computation.WeightComputer;
import main.graph.Graph;
import main.graph.Vertex;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Controller {

    public static final Double SQUARE_BOARD_SIZE = 800.0;
    public static final Double SQUARE_BOARD_X = 380.0;
    public static final Double SQUARE_BOARD_Y = 25.0;
    private final Double BUTTONS_Y_SAFE_DISTANCE = 50.0;

    private final Group root;
    private final Scene scene;
    private final Stage primaryStage;

    private final boolean isVerticesConfirmed = false;
    private final Graph graph;

    private Rectangle rectangle;
    private Button randomVerticesGeneratorBtn;
    private Button colorOptionsBtn;
    private Button runAlgorithmBtn;
    private Button clearBtn;
    private Button saveBtn;
    private Button infoBtn;

    public Controller(Stage primaryStage, Scene scene, Group root) {
        this.primaryStage = primaryStage;
        this.scene = scene;
        this.root = root;
        graph = new Graph(new ArrayList<>());
    }

    public void setScene() {
        setupBoard();
        setupMenu();
        setBoardClickable();
        setGenerateRandomVerticesButton();
        setColorOptionsButton();
        setRunAlgorithmButton();
        setClearButton();
        setSaveDataButton();
    }

    private void setupBoard() {
        rectangle = new Rectangle(SQUARE_BOARD_X, SQUARE_BOARD_Y, SQUARE_BOARD_SIZE, SQUARE_BOARD_SIZE);
        rectangle.setStroke(Color.DARKGREY);
        rectangle.setStrokeWidth(2);
        rectangle.setFill(Color.gray(0.98));
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        root.getChildren().add(rectangle);
    }

    private void setupMenu() {
        randomVerticesGeneratorBtn = new Button("Generate Random Vertices");
        randomVerticesGeneratorBtn.setLayoutY(rectangle.getY() + 25);
        setupButtonView(randomVerticesGeneratorBtn);

        colorOptionsBtn = new Button("Color Options");
        colorOptionsBtn.setLayoutY(randomVerticesGeneratorBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(colorOptionsBtn);

        runAlgorithmBtn = new Button("Run Algorithm");
        runAlgorithmBtn.setLayoutY(colorOptionsBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(runAlgorithmBtn);

        clearBtn = new Button("Clear");
        clearBtn.setLayoutY(runAlgorithmBtn.getLayoutY() + 10.5 * BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(clearBtn);

        saveBtn = new Button("Save Data");
        saveBtn.setLayoutY(clearBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(saveBtn);

        infoBtn = new Button("Information & Documentation");
        infoBtn.setLayoutY(saveBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(infoBtn);

    }

    private void setBoardClickable() {
        scene.setOnMouseClicked(mouseEvent -> {
            if (!isVerticesConfirmed && isBoardClicked(mouseEvent) &&
                    !graph.isVertexCoordinationInvalid(mouseEvent.getX(), mouseEvent.getY())) {
                randomVerticesGeneratorBtn.setDisable(true);
                Vertex v = graph.addVertexOnClick(mouseEvent);
                addVertexAndLabel(v);
            }
        });
    }

    private void addVertexAndLabel(Vertex v) {
        root.getChildren().add(v.getCircle());
        Text label = new Text("p" + v.getGlobalLabel());
        label.setX(v.getCircle().getCenterX() + 5);
        label.setY(v.getCircle().getCenterY() + 5);
        label.setFill(Color.BLACK);
        v.setLabelText(label);
        root.getChildren().add(label);
    }

    private void setGenerateRandomVerticesButton() {
        randomVerticesGeneratorBtn.setOnMouseClicked(event -> {
            randomVerticesGeneratorBtn.setDisable(true);
            colorOptionsBtn.setDisable(true);
            graph.randomVertexGenerator();
            for (Vertex v : graph.getVertexList())
                addVertexAndLabel(v);
        });
    }

    private void setColorOptionsButton() {
        colorOptionsBtn.setOnMouseClicked(mouseEvent -> Graph.VertexColorGenerator.colorOptionDialog());
    }

    private void setClearButton() {
        clearBtn.setOnMouseClicked(event -> {
            for (Vertex vertex : graph.getVertexList()) {
                root.getChildren().remove(vertex.getCircle());
                root.getChildren().remove(vertex.getLabelText());
            }
            if (graph.getLineList() != null) {
                for (Line line : graph.getLineList()) {
                    root.getChildren().remove(line);
                }
            }
            graph.clearGraph();
            randomVerticesGeneratorBtn.setDisable(false);
            colorOptionsBtn.setDisable(false);
        });
    }

    private void setupButtonView(@NotNull Button button) {
        button.setLayoutX(25);
        button.setMaxWidth(rectangle.getX() - 50);
        button.setMinWidth(rectangle.getX() - 50);
        button.setStyle("-fx-font: 16 sans;");
        root.getChildren().add(button);
    }

    private boolean isBoardClicked(@NotNull MouseEvent mouseEvent) {
        return rectangle.contains(mouseEvent.getX() + 10, mouseEvent.getY() + 10)
                && rectangle.contains(mouseEvent.getX() - 10, mouseEvent.getY() - 10);
    }

    private void setSaveDataButton() {
        saveBtn.setOnMouseClicked(event -> {
            graph.sortY();
            String result = "Vertices: " + graph.getVertexList().toString();

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                saveTextToFile(result, file);
            }
        });
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setRunAlgorithmButton() {
        runAlgorithmBtn.setOnMouseClicked(event -> {
            WeightComputer weightComputer = new WeightComputer(graph);
            weightComputer.run();
            drawOptimalIsland();
        });
    }

    public void drawOptimalIsland() {
        if (graph.getLineList() == null) return;
        for (Line line : graph.getLineList()) {
            root.getChildren().add(line);
        }
    }
}
