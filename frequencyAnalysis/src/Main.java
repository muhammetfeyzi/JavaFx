import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application{
    // local variables declaration
   private Stage window;
   private String str;
   private String txt="";
   private TextArea txtResult;
   private int size;
   private char[] s;
   private Label lblText,lblResult;

   public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window= primaryStage;
        window.setTitle("Frequency Analysis of Text");

        // we create individual design such as scene
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(10); // horizontal pixel
        gridPane.setVgap(8); // vertical pixel

        // definition of javaFx control


        lblText= new Label("Choose Text");
        GridPane.setConstraints(lblText,0,0);

        TextArea txtMetin= new TextArea();
        GridPane.setConstraints(txtMetin,0,1);

        TextField txtPath= new TextField("This is your absolute path of choose text");
        GridPane.setConstraints(txtPath,0,2);

        Button btnChoose= new Button();
        btnChoose.setText("Choose Text");
        GridPane.setConstraints(btnChoose,2,1);
        btnChoose.setOnAction(e->{
            txtMetin.setText("");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            str=fileChooser.showOpenDialog(window).getAbsolutePath();
            txtPath.setText(str);
            String fileName=str;

            // read file
            FileReader file;
            try {
                file= new FileReader(fileName);
                BufferedReader bf= new BufferedReader(file);
                String line="";
                while ((line = bf.readLine())!= null)
                {
                    txt+=line;
                }
                txtMetin.setText(txt);
                bf.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        });
        lblResult = new Label("Result Of Text");
        GridPane.setConstraints(lblResult,0,3);

        txtResult= new TextArea();
        GridPane.setConstraints(txtResult,0,4);

        Button btnAnaliz = new Button("Analysis");
        btnAnaliz.setMinWidth(500);
        GridPane.setConstraints(btnAnaliz,0,5);
        btnAnaliz.setOnAction(e->{
            frequencyAnalysis();
        });
        gridPane.getChildren().addAll(lblText,txtMetin,txtPath,btnChoose,lblResult,txtResult,btnAnaliz);
        // load scene on window
        Scene scene = new Scene(gridPane,650,300);
        window.setScene(scene);
        window.show();

    }
    public void frequencyAnalysis(){
        String formatted= txt.replaceAll(" ", "");// throw gaps
        formatted=formatted.replaceAll("\\p{P}","");// throws punctuation
        formatted=formatted.toLowerCase();
        s= formatted.toCharArray();
        size= s.length;

        int counter;
        for (int i = 0; i < s.length; i++) {
            counter=0;
            for (int j = 0; j < s.length; j++) {

                if(j<i && s[i]==s[j])
                {
                    break;//distinct same value
                }
                if(s[j]==s[i])
                {
                    counter++;
                }
                if(j== size-1)
                {
                    txtResult.appendText(s[i]+" : Repeat Frequency -> "+counter+"\n" );

                }
            }

        }
    }

}
