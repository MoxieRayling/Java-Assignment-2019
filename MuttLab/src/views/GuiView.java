package views;

import java.io.File;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import matrix.Matrix;
import matrix.SimpleMatrix;
import matrix.Vector;
import model.Model;
import operations.Command;
import operations.Error;

public class GuiView extends Application implements View {

	private TextArea output;
	private ListView<String> lvList;
	private Controller c;
	private double width = 1000;
	private double height = 500;
	private Stage primaryStage;
	private ObservableList<String> matrices;
	private File file;
	private boolean ok;

	@Override
	public void start(Stage primaryStage) throws Exception {
		matrices = FXCollections.observableArrayList();
		file = new File(" ");
		ok = true;
		this.primaryStage = primaryStage;
		Model m = new Model();
		c = new Controller(m, this);
		m.setController(c);
		output = new TextArea();
		output.setEditable(false);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(tabs(), listPane());
		for (Node n : hbox.getChildren()) {
			HBox.setHgrow(n, Priority.ALWAYS);
		}
		Scene scene = new Scene(hbox, width, height);
		primaryStage.setTitle("MuttLab");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private TabPane tabs() {
		TabPane tabs = new TabPane();
		Tab vectorTab = new Tab();
		vectorTab.setText("Vectors");
		vectorTab.setContent(getVectorOptions());
		vectorTab.setClosable(false);
		Tab matrixTab = new Tab();
		matrixTab.setText("Matrices");
		matrixTab.setContent(getMatrixOptions());
		matrixTab.setClosable(false);

		tabs.setPrefWidth(width / 2);
		tabs.getTabs().addAll(vectorTab, matrixTab);
		return tabs;
	}

	private Pane listPane() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(0, 0, 0, 0));
		lvList = new ListView<String>();
		lvList.setItems(matrices);
		lvList.setMaxHeight(Control.USE_PREF_SIZE);
		lvList.setPrefWidth(width / 2);
		vbox.getChildren().addAll(lvList, output);

		return vbox;
	}

	private VBox getVectorOptions() {
		VBox vbButtons = new VBox();
		vbButtons.setSpacing(10);
		vbButtons.getChildren().addAll(fileLoad(), buttonsLoad(), buttonsAdd(), buttonsMultiply(), buttonsLength());
		return vbButtons;
	}

	private VBox fileLoad() {
		FileChooser myChooser = new FileChooser();
		myChooser.getExtensionFilters().add(new ExtensionFilter("bullshit", "*.csv"));
		Button btnChoose = new Button("Choose file");
		Label fileName = new Label("No file selected");
		btnChoose.setOnAction(event -> {
			file = myChooser.showOpenDialog(primaryStage);

			if (file != null) {
				fileName.setText(file.toString());
			} else {
				file = new File(" ");
			}
		});
		Button btnOpen = new Button("Open file");
		btnOpen.setOnAction(x -> {
			if (file.exists()/* && fileCheck() */)
				command("LoadFile " + file.toString());
		});
		Button btnSave = new Button("Save file");
		btnSave.setOnAction(x -> {
			if (fileCheck())
				command("Save " + file.toString());
		});

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(btnChoose, btnOpen, btnSave);
		HBox.setHgrow(btnChoose, Priority.ALWAYS);
		HBox.setHgrow(btnOpen, Priority.ALWAYS);
		hbox.setPadding(new Insets(10, 20, 10, 20));
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 10, 10, 10));
		vbox.getChildren().addAll(hbox, fileName);
		return vbox;

	}

	private HBox buttonsLoad() {
		Button btnLoad = new Button("Load");
		btnLoad.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);

		final ToggleGroup group = new ToggleGroup();

		RadioButton rbSum = new RadioButton("Sum");
		rbSum.setToggleGroup(group);
		rbSum.setSelected(true);
		RadioButton rbMaximal = new RadioButton("Maximal");
		rbMaximal.setToggleGroup(group);
		RadioButton rbMinimal = new RadioButton("Minimal");
		rbMinimal.setToggleGroup(group);

		VBox radioButtons = new VBox();
		radioButtons.setSpacing(10);
		radioButtons.getChildren().addAll(rbSum, rbMaximal, rbMinimal);
		radioButtons.setPadding(new Insets(0, 0, 0, 50));

		btnLoad.setOnAction(x -> {
			if (file.exists() && fileCheck())
				command("LoadVector " + ((RadioButton) group.getSelectedToggle()).getText() + " " + file.toString());
		});

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(btnLoad, radioButtons);
		final Tooltip tooltip = new Tooltip(
				"Choose an output file and select an option:\n" + "Sum chooses the vector with the largest sum.\n"
						+ "Maximal chooses the vector with the largest maximal value.\n"
						+ "Minimal chooses the vector with the largest minimal value.");
		Tooltip.install(hbox, tooltip);
		HBox.setHgrow(btnLoad, Priority.ALWAYS);
		HBox.setHgrow(radioButtons, Priority.ALWAYS);
		hbox.setPadding(new Insets(10, 20, 10, 20));
		hbox.setAlignment(Pos.CENTER);
		btnLoad.setTooltip(tooltip);
		return hbox;

	}

	private HBox buttonsAdd() {

		Button btnAdd = new Button("Add");
		btnAdd.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);

		final ToggleGroup group = new ToggleGroup();

		RadioButton rbFirst = new RadioButton("First");
		rbFirst.setToggleGroup(group);
		rbFirst.setSelected(true);
		RadioButton rbLeft = new RadioButton("Left");
		rbLeft.setToggleGroup(group);
		RadioButton rbRight = new RadioButton("Right");
		rbRight.setToggleGroup(group);

		VBox radioButtons = new VBox();
		radioButtons.setSpacing(10);
		radioButtons.getChildren().addAll(rbFirst, rbLeft, rbRight);
		radioButtons.setPadding(new Insets(0, 0, 0, 50));

		btnAdd.setOnAction(x -> command(
				"AddVector " + ((RadioButton) group.getSelectedToggle()).getText() + " " + file.toString()));
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(btnAdd, radioButtons);
		HBox.setHgrow(btnAdd, Priority.ALWAYS);
		HBox.setHgrow(radioButtons, Priority.ALWAYS);
		hbox.setAlignment(Pos.CENTER);
		final Tooltip tooltip = new Tooltip("Choose an output file and select an option:\n"
				+ "First picks the first vector and adds all vector of the same size.\n"
				+ "Left pads the left side of the vector with zeros to make them the same size.\n"
				+ "Right pads the right side of the vector with zeros to make them the same size.");
		Tooltip.install(hbox, tooltip);
		btnAdd.setTooltip(tooltip);
		rbFirst.setTooltip(tooltip);
		rbLeft.setTooltip(tooltip);
		rbRight.setTooltip(tooltip);
		hbox.setPadding(new Insets(10, 20, 10, 20));
		return hbox;
	}

	private HBox buttonsMultiply() {

		Button btnMultiply = new Button("Multiply");
		btnMultiply.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		TextField tfMult = new TextField();
		NumberStringFilteredConverter converter = new NumberStringFilteredConverter();
		final TextFormatter<Number> formatter = new TextFormatter<>(converter, 0, converter.getFilter());
		tfMult.setTextFormatter(formatter);

		btnMultiply.setOnAction(x -> {
			if (file.exists() && fileCheck())
				command("MultiplyVector " + tfMult.getText() + " " + file.toString());
		});
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(btnMultiply, tfMult);
		HBox.setHgrow(btnMultiply, Priority.ALWAYS);
		HBox.setHgrow(tfMult, Priority.ALWAYS);
		final Tooltip tooltip = new Tooltip("Type a number to multiply the vectors by and type an output file.");
		Tooltip.install(hbox, tooltip);
		btnMultiply.setTooltip(tooltip);
		tfMult.setTooltip(tooltip);
		hbox.setPadding(new Insets(10, 20, 10, 20));
		return hbox;
	}

	// https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
	class NumberStringFilteredConverter extends NumberStringConverter {
		public UnaryOperator<TextFormatter.Change> getFilter() {
			return change -> {
				String newText = change.getControlNewText();
				if (newText.isEmpty()) {
					return change;
				}

				ParsePosition parsePosition = new ParsePosition(0);
				Object object = getNumberFormat().parse(newText, parsePosition);
				if (object == null || parsePosition.getIndex() < newText.length()) {
					return null;
				} else {
					return change;
				}
			};
		}
	}

	private HBox buttonsLength() {

		Button btnLength = new Button("Filter Length");
		btnLength.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		TextField tfLength = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.matches("[0-9]*")) {
					super.replaceText(start, end, text);
				}
			}

			@Override
			public void replaceSelection(String text) {
				if (text.matches("[0-9]*")) {
					super.replaceSelection(text);
				}
			}
		};

		btnLength.setOnAction(x -> {
			if (file.exists() && fileCheck())
				command("VectorLength " + tfLength.getText() + " " + file.toString());
		});
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(btnLength, tfLength);
		HBox.setHgrow(btnLength, Priority.ALWAYS);
		HBox.setHgrow(tfLength, Priority.ALWAYS);
		final Tooltip tooltip = new Tooltip("Choose a length to filter the vectors by and type an output file.");
		Tooltip.install(hbox, tooltip);
		btnLength.setTooltip(tooltip);
		tfLength.setTooltip(tooltip);
		hbox.setPadding(new Insets(10, 20, 10, 20));
		return hbox;
	}

	private VBox getMatrixOptions() {
		VBox vbButtons = new VBox();
		vbButtons.setSpacing(10);
		vbButtons.setPadding(new Insets(10, 20, 10, 20));

		Button create = new Button("Create Matrix");
		create.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		create.setOnAction(evt -> createDialog());
		create.setTooltip(new Tooltip("Opens the create-matrix dialog."));

		Button add = new Button("Add");
		add.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		add.setOnAction(x -> command(Command.ADD.toString()));
		add.setTooltip(new Tooltip("Adds the bottom two matrices if they are the same shape."));

		Button subtract = new Button("Subtract");
		subtract.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		subtract.setOnAction(x -> command(Command.SUBTRACT.toString()));
		subtract.setTooltip(new Tooltip("Subtracts the bottom two matrices if they are the same shape."));

		Button multiply = new Button("Matrix Multiplication");
		multiply.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		multiply.setOnAction(x -> command(Command.MULTIPLY.toString()));
		multiply.setTooltip(new Tooltip("Multiplies the bottom two matrices if they are compatible shapes."));

		Button pointwiseMultiplication = new Button("Pointwise Multiplication");
		pointwiseMultiplication.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		pointwiseMultiplication.setOnAction(x -> command(Command.POINTWISE.toString()));
		pointwiseMultiplication
				.setTooltip(new Tooltip("Pointwise-multiplies the bottom two matrices if they are the same shape."));

		Button duplicate = new Button("Duplicate");
		duplicate.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		duplicate.setOnAction(x -> command(Command.DUPLICATE.toString()));
		duplicate.setTooltip(new Tooltip("Duplicates the bottom matrix."));

		vbButtons.getChildren().addAll(create, add, subtract, multiply, scalarMultiplyButtons(),
				pointwiseMultiplication, duplicate, scriptButtons());

		return vbButtons;
	}

	private HBox scalarMultiplyButtons() {
		Button scalarMultiplication = new Button("Scalar Multiplication");
		scalarMultiplication.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		TextField tfMult = new TextField();
		NumberStringFilteredConverter converter = new NumberStringFilteredConverter();
		final TextFormatter<Number> formatter = new TextFormatter<>(converter, 0, converter.getFilter());
		tfMult.setTextFormatter(formatter);

		scalarMultiplication.setOnAction(x -> command(Command.MULTIPLY.toString() + " " + tfMult.getText()));

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(scalarMultiplication, tfMult);
		HBox.setHgrow(scalarMultiplication, Priority.ALWAYS);
		HBox.setHgrow(tfMult, Priority.ALWAYS);
		final Tooltip tooltip = new Tooltip("Multiplies the most recent matrix by the provided multiplier.");
		Tooltip.install(hbox, tooltip);
		scalarMultiplication.setTooltip(tooltip);
		tfMult.setTooltip(tooltip);
		return hbox;
	}

	private HBox scriptButtons() {
		Button scriptButton = new Button("Run Script");
		scriptButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		TextField scriptText = new TextField();
		scriptButton.setOnAction(x -> command(Command.SCRIPT.toString() + " " + scriptText.getText()));

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(scriptButton, scriptText);
		HBox.setHgrow(scriptButton, Priority.ALWAYS);
		HBox.setHgrow(scriptText, Priority.ALWAYS);
		final Tooltip tooltip = new Tooltip("Runs the script in the designated file.");
		Tooltip.install(hbox, tooltip);
		scriptButton.setTooltip(tooltip);
		scriptText.setTooltip(tooltip);
		return hbox;
	}

	public void createDialog() {
		final Stage dialog = new Stage();
		dialog.setTitle("Create Matrix");

		Label displayLabel = new Label("Type a matrix. Separate values by spaces and lines by new lines.");
		TextArea matrix = new TextArea();
		Button create = new Button("Create");
		Button cancel = new Button("Cancel");

		dialog.initModality(Modality.NONE);
		dialog.initOwner(primaryStage);

		HBox hbox = new HBox(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(create, cancel);

		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10, 20, 10, 20));
		vbox.getChildren().addAll(displayLabel, matrix, hbox);

		create.setOnAction(evt -> {
			command("[ " + matrix.getText().replace("\n", ";"));
			dialog.close();
		});
		cancel.setOnAction(evt -> dialog.close());
		Scene dialogScene = new Scene(vbox, 500, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	private void command(String command) {
		System.out.println(command);
		Task<ArrayList<Matrix>> t = new Task<ArrayList<Matrix>>() {
			@Override
			protected ArrayList<Matrix> call() throws Exception {
				return c.command(command);
			}
		};
		t.valueProperty().addListener(te -> {
			matrices = FXCollections
					.observableArrayList(t.getValue().stream().map(x -> x.toString()).collect(Collectors.toList()));
			lvList.setItems(matrices);
		});
		t.setOnRunning(x -> output.appendText("Running command: " + command + "\n"));
		t.setOnSucceeded(x -> output.appendText("Finished command: " + command + "\n"));
		t.setOnFailed(x -> output.appendText("Command failed: " + command + "\n"));
		Thread th = new Thread(t);
		th.setDaemon(true);
		th.start();
	}

	private boolean fileCheck() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("File Exists");
		alert.setHeaderText("The file " + file.toString() + " already exists.");
		alert.setContentText("Is it ok to overwrite this file?");
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		alert.getButtonTypes().setAll(yes, no);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yes) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void printError(Error e) {
		output.appendText(e.toString() + "\n");
	}

	@Override
	public void printMessage(String string) {
		output.appendText(string + "\n");
	}

	@Override
	public void help() {
	}

	@Override
	public void printMatrices(List<String> formattedMatrices) {
	}

	@Override
	public void quit() {
	}

	@Override
	public void setController(Controller c) {
		this.c = c;
	}

}
