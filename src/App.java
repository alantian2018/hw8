import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.beans.binding.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.beans.property.*;
import javafx.scene.paint.*;
public class App extends Application {
    public boolean is_win (char[][]board ){
        for (int i=0;i<3;i++){
            if (board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0]!='-'){
                return true;
            }
            if (board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i]!='-'){
                return true;
            }
        }
        if (board[0][0]==board[1][1] && board[0][0]==board[2][2] && board[0][0]!='-'){return true;}
        if (board [1][1]==board[0][2] && board[1][1]==board[2][0] && board[1][1]!='-'){return true;}
        return false;
    }
    public void start (Stage stage){
        VBox globe = new VBox();
        IntegerProperty count = new SimpleIntegerProperty(0);
        IntegerProperty player = new SimpleIntegerProperty(1);
        char[][]board = new char[3][3];
        for (char[] i:board)
            Arrays.fill (i,'-');
        Button reset = new Button ("Play again?");
        Label title = new Label ("Play tic tac toe!");
        title.setFont (new Font (20));
        Label turn = new Label ("Player "+player.get()+"'s turn");
        turn.setFont (new Font (40));
        HBox[] hb = new HBox[3];
        CustomButton[][] btn = new CustomButton[3][3];
        VBox vb = new VBox();
        vb.setAlignment (Pos.CENTER);
        Button close = new Button ("Close");
        close.setOnAction (new EventHandler<ActionEvent>(){
            public void handle (ActionEvent e){
                stage.close();
            }

        });
        reset.setStyle("-fx-background-color: #24a0ed");
        reset.requestFocus();
        reset.setTextFill (Color.WHITE);
        reset.setPrefSize(100, 50);
    
        
        close.setStyle ("-fx-background-color: grey");
        close.setPrefSize(100,50);
        BooleanProperty haswinner = new SimpleBooleanProperty(false);
        HBox resetgame = new HBox(reset,close);
        resetgame.setSpacing (10);
        resetgame.setAlignment (Pos.CENTER);
        for (int i = 0; i < hb.length; i++) {
            hb[i] = new HBox();
            hb[i].setAlignment (Pos.CENTER);
            for (int j = 0; j < btn.length; j++) {
                
                btn[i][j] = new CustomButton(i,j); 
                btn[i][j].setOnAction ((event) -> {
                    int row = ((CustomButton)(event.getSource())).getx();
                    int col = ((CustomButton)(event.getSource())).gety();
                    if (board[row][col] == '-'){
                        if (player.get() == 1) {
                            btn[row][col].setText ("X");
                            board[row][col]='X';
                        }
                        else {
                            btn[row][col].setText ("O");
                            board[row][col]='O';    
                        }
                    }
                    else return;
                    count.setValue(count.get() + 1);
                    if (is_win(board)){
                        haswinner.setValue(true);
                        turn.setText ("Player "+player.get()+" has won!");
                        turn.setFont (new Font (30));
                        globe.getChildren().add (resetgame);
                        return;
                    }
                    player.setValue (player.get()%2+1);
                    if (!haswinner.get() && count.get() == 9){
                        turn.setText ("Tie game");
                        globe.getChildren().add (resetgame);
                        return;
                    }else {turn.setText ("Player "+player.get()+"'s turn");}
                });
                btn[i][j].disableProperty().bind(Bindings.equal(count, 9).or(haswinner));
                hb[i].getChildren().addAll(btn[i][j]);
                hb[i].setSpacing(2);
            }
            vb.getChildren().add(hb[i]);
            vb.setStyle ("-fx-background-color: black");
            vb.setSpacing(2);
        }         
        reset.setOnAction ((event) -> {
            for (char[] i: board)Arrays.fill (i,'-');
            for (CustomButton []b:btn){
               for (CustomButton i:b)i.setText ("");
            }
            count.setValue (0);
            player.setValue(1);
            haswinner.setValue (false);
            globe.getChildren().remove(globe.getChildren().size()-1);

        });
        
        Group g= new Group();
        g.getChildren().add(vb);
        globe.setAlignment (Pos.CENTER);
        globe.getChildren().addAll (title,turn,g);
        stage.setScene (new Scene (globe));
        stage.setTitle ("Low quality tic-tac-toe");
        stage.show();   
    }
    public static void main(String[] args){
        Application.launch (args);
    }
}
class CustomButton extends Button {
    private int i;
    private int j;
    CustomButton (int i,int j){
        this.i = i;
        this.j=j;
        this.setPrefSize(100,100);
        this.setFont (new Font (40));
        this.setStyle ("-fx-background-color: white;-fx-background-radius: 0");
    }
    int getx(){return i;}
    int gety(){return j;}
}
