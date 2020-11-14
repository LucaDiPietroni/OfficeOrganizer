package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Worker;

public class MainWindowController implements CoreMethods{
	private Main main;
	@FXML private ChoiceBox roomNrChoice;
	
	@FXML private TextField nameField;
	@FXML private TextField lastNameField;
	@FXML private TextField startHourField;
	@FXML private TextField endHourField;
	@FXML private Canvas planCanvas;
	
	@FXML private Button addButton;
	
	@FXML private TableView<Worker> workerTable;
	
	@FXML private TableColumn<Worker, String> nameColumn;
	@FXML private TableColumn<Worker, String> lastNameColumn;
	@FXML private TableColumn<Worker, String> roomColumn;
	private String loadedFileSource;
	
	private ObservableList<Worker> workerList = FXCollections.observableArrayList();

	public void setMain(Main main){
		this.main=main;
		workerTable.setItems(workerList);
	}
	
	@FXML
	public void initialize() {
		roomNrChoice.getItems().removeAll();
		roomNrChoice.getItems().addAll("001", "002", "003", "004", "005");
		
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<Worker, String>("firstName"));
		lastNameColumn.setCellValueFactory(
				new PropertyValueFactory<Worker, String>("lastName"));
		roomColumn.setCellValueFactory(
				new PropertyValueFactory<Worker, String>("room"));
		
		setImage("/view/biuro_z_numerami.png", planCanvas);
	}
	
	public void addWorker() {
		try {
			workerList.add(new Worker(nameField.getText(), 
					lastNameField.getText(), 
					roomNrChoice.getValue().toString(),
					Integer.parseInt(startHourField.getText()),
					Integer.parseInt(endHourField.getText())));
			if (!workerList.get(workerList.size() - 1).isWorkerCorrect()) {
				workerList.remove(workerList.size() - 1);
				throw new Exception();
			}
		}catch(Exception e) {
			setAlarm("Operacja nie powiod³a siê. Nie wszystkie atrybuty zosta³y poprawnie wype³nione");
		}
	}
	
	public void loadFile() {
		Scanner in = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
				     new FileChooser.ExtensionFilter("TXT", "*.txt")
				);
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(new Stage());
			loadedFileSource = file.getAbsolutePath();

			in = new Scanner(Paths.get(loadedFileSource));			
			while (in.hasNext()) {
				workerList.add(new Worker(in.next(), 
						in.next(), 
						in.next(), 
						in.nextInt(),
						in.nextInt()));
				System.out.println(workerList.get(workerList.size() - 1).getRoom());
				if (!workerList.get(workerList.size() - 1).isWorkerCorrect()) {
					workerList.remove(workerList.size() - 1);
					throw new Exception();
				}
			}
			
		} catch(Exception e) {
			setAlarm("Operacja nie powiod³a siê. B³êdna zawartoœæ pliku.");
			loadedFileSource = null;
		} finally {
			if (in != null) in.close();
		}
	} 
	
	public void saveFile() {
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(loadedFileSource);
			
			for (Worker i: workerList) {
				out.printf("%s %s %s %d %d", i.getFirstName(), i.getLastName(), i.getRoom(), i.getStartHour(), i.getEndHour());
				out.println();
			}
			
		} catch(Exception e) {
			setAlarm("Operacja nie powiod³a siê.");
		} finally {
			if (out != null) out.close();
		}
		
	}
	
	public void reportFile() {
		PrintWriter out = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
				     new FileChooser.ExtensionFilter("TXT", "*.txt")
				);
			fileChooser.setTitle("Save Resource File");
			File file = fileChooser.showSaveDialog(new Stage());
			
			String source = file.getAbsolutePath();

			out = new PrintWriter(source);
			
			Worker[] sortedOffice = sortByTime(workerList);
			
			for (Worker i: sortedOffice) {
				out.printf("%s %s %s %d %d", i.getFirstName(), i.getLastName(), i.getRoom(), i.getStartHour(), i.getEndHour());
				out.println();
			}
		} catch(Exception e) {
			setAlarm("Operacja nie powiod³a siê.");
		} finally {
			if (out != null) out.close();
		}
	}
	
	public void showRecord() {
		try {
			Worker selected = workerTable.getSelectionModel().getSelectedItem();
			nameField.setText(selected.getFirstName());
			lastNameField.setText(selected.getLastName());
			//roomNrChoice.getSelectionModel().select(selected.getRoom());
			roomNrChoice.setValue(selected.getRoom());
			startHourField.setText(String.valueOf(selected.getStartHour()));
			endHourField.setText(String.valueOf(selected.getEndHour()));
			
			if (selected.getRoom().equals("001")) {
				setImage("/view/p001.png", planCanvas);
			}
			
			if (selected.getRoom().equals("002")) {
				setImage("/view/p002.png", planCanvas);
			}
			
			if (selected.getRoom().equals("003")) {
				setImage("/view/p003.png", planCanvas);
			}
			
			if (selected.getRoom().equals("004")) {
				setImage("/view/p004.png", planCanvas);
			}
			
			if (selected.getRoom().equals("005")) {
				setImage("/view/p005.png", planCanvas);
			}
		} catch (Exception e) {
			setAlarm("B³êdny rekord lub jego brak");
		}	
	}
}