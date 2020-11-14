package controller;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import model.Worker;

public interface CoreMethods {
	
	default Worker[] sortByTime(ObservableList<Worker> inputList) {
		Worker[] sortedWorkers = new Worker[inputList.size()];
		sortedWorkers[0] = inputList.get(0);
		
		for (int i = 1; i < inputList.size(); i++) {
			Worker movingWorker = inputList.get(i);
			Worker buffor;
			for (int j = 0; j < i; j++) {
				if ((movingWorker.getEndHour() - movingWorker.getStartHour()) <= (sortedWorkers[j].getEndHour() - sortedWorkers[j].getStartHour())) {
					
					if (sortedWorkers[j+1] != null) {
						buffor = movingWorker;
						movingWorker = sortedWorkers[j+1];
						sortedWorkers[j+1] = sortedWorkers[j];
						sortedWorkers[j] = buffor;
					}
					else {
			
						sortedWorkers[j+1] = sortedWorkers[j];
						sortedWorkers[j] = movingWorker;
					}
				}
				else {
					
					if (sortedWorkers[j+1] == null) {
						sortedWorkers[j+1] = movingWorker;
					}
					else {}
				}
			}
		}
		return sortedWorkers;
	}
	
	default void setImage(String imageLoc, Canvas inputCanvas) {
		Image plan = new Image(imageLoc, 340, 280, false, true);
		GraphicsContext planCtx = inputCanvas.getGraphicsContext2D();
		planCtx.drawImage(plan, 0, 0);
	}
	
	default void setAlarm(String message) {
		Alert AlarmWin = new Alert(AlertType.INFORMATION);
		AlarmWin.setTitle("B³¹d!");
		AlarmWin.setHeaderText(null);
		AlarmWin.setContentText(message);
		AlarmWin.showAndWait();
	}
}
