package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MyScene.fxml"));//�޸���
            //BorderPane root = new BorderPane();
            //����Scene�Ĵ�С(SceneBuilder�е��AnchorPane�ұ�Layout�л���ʾ��С,��һ���Ŀ���)
            Scene scene = new Scene(root,1100,760);//�޸���
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);//���ò��ܴ��ڸı��С
            primaryStage.setTitle("ѧ���ɼ��鿴��");//���ñ���
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
