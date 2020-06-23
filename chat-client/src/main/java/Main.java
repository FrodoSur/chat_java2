import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Messenger");
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.setScene(new Scene(root, 743, 738));
        primaryStage.setScene(new Scene(root, 743, 767));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}