package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

/**
 * @author zuxinrui
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //显示Javafx窗口，所有ui控件设计均在'sample.fxml'中
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Shanghai Railway Path Finder");
        primaryStage.setScene(new Scene(root, 636, 316));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
