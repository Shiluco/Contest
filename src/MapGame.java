import javafx.application.Application;
import javafx.stage.Stage;

public class MapGame extends Application {
  Stage stage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    TimeController timeController = new TimeController();
    stage = primaryStage;
    stage.hide();
    StageDB.setMainClass(getClass());
    StageDB.getMainStage().show();
    // StageDB.getMainSound().play();
    TimeController.timeOver();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
