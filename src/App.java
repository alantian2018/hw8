import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.Arrays;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.paint.Color;

public class App extends Application {

    public boolean isWin(char[][]board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1]
                && board[i][1] == board[i][2] && board[i][0] != '-') {
                return true;
            }
            if (board[0][i] == board[1][i]
                && board[1][i] == board[2][i] && board[0][i] != '-') {
                return true;
            }
        }
        if (board[0][0] == board[1][1]
            && board[0][0] == board[2][2] && board[0][0] != '-') {
            return true;
        }
        if (board[1][1] == board[0][2]
            && board[1][1] == board[2][0] && board[1][1] != '-') {
            return true;
        }
        return false;
    }
    public void start(Stage stage) {
        char[][] board = new char[3][3];
        for (char[] i:board) {
            Arrays.fill(i, '-');
        }

        IntegerProperty count = new SimpleIntegerProperty(0);
        IntegerProperty player = new SimpleIntegerProperty(1);
        BooleanProperty haswinner = new SimpleBooleanProperty(false);

        Label title = new Label("Play tic tac toe!");
        title.setFont(new Font(20));

        Label turn = new Label("Player " + player.get() + "'s turn");
        turn.setFont(new Font(40));

        Button reset = new Button("Play again?");
        reset.setStyle("-fx-background-color: #24a0ed");
        reset.requestFocus();
        reset.setTextFill(Color.WHITE);
        reset.setPrefSize(100, 50);

        Button close = new Button("Close");
        close.setStyle("-fx-background-color: grey");
        close.setPrefSize(100, 50);
        close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stage.close();
            }

        });

        Group g = new Group();

        VBox globe = new VBox();
        HBox[] hb = new HBox[3];
        CustomButton[][] btn = new CustomButton[3][3];

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setStyle("-fx-background-color: black");
        vb.setSpacing(2);

        HBox resetgame = new HBox(reset, close);
        resetgame.setSpacing(10);
        resetgame.setAlignment(Pos.CENTER);


        for (int i = 0; i < hb.length; i++) {
            hb[i] = new HBox();
            hb[i].setAlignment(Pos.CENTER);
            hb[i].setSpacing(2);

            for (int j = 0; j < btn.length; j++) {

                btn[i][j] = new CustomButton(i, j);

                btn[i][j].setOnAction((event) -> {
                    CustomButton currentButton =
                        (CustomButton) event.getSource();
                    int row = currentButton.getx();
                    int col = currentButton.gety();

                    if (board[row][col] == '-') {
                        if (player.get() == 1) {
                            btn[row][col].setText("X");
                            board[row][col] = 'X';
                        } else {
                            btn[row][col].setText("O");
                            board[row][col] = 'O';
                        }
                    } else {
                        return;
                    }

                    count.setValue(count.get() + 1);

                    if (isWin(board)) {
                        haswinner.setValue(true);
                        turn.setText("Player " + player.get() + " has won!");
                        turn.setFont(new Font(30));
                        globe.getChildren().add(resetgame);
                        return;
                    }

                    player.setValue(player.get() % 2 + 1);

                    if (!haswinner.get() && count.get() == 9) {
                        turn.setText("Tie game");
                        globe.getChildren().add(resetgame);
                        return;
                    } else {
                        turn.setText("Player " + player.get() + "'s turn");
                    }
                });

                btn[i][j].disableProperty().bind(
                    Bindings.equal(count, 9).or(haswinner));

                hb[i].getChildren().addAll(btn[i][j]);
            }
            vb.getChildren().add(hb[i]);
        }

        reset.setOnAction((event) -> {

            for (char[] i: board) {
                Arrays.fill(i, '-');
            }
            for (CustomButton[] b : btn) {
                for (CustomButton i : b) {
                    i.setText("");
                }
            }

            count.setValue(0);
            player.setValue(1);
            haswinner.setValue(false);
            globe.getChildren().remove(globe.getChildren().size() - 1);

        });

        g.getChildren().add(vb);
        globe.setAlignment(Pos.CENTER);
        globe.getChildren().addAll(title, turn, g);
        stage.setScene(new Scene(globe));
        stage.setTitle("Low quality tic-tac-toe");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

class CustomButton extends Button {
    private int i;
    private int j;
    CustomButton(int i, int j) {
        this.i = i;
        this.j = j;
        this.setPrefSize(100, 100);
        this.setFont(new Font(40));
        this.setStyle("-fx-background-color: white;-fx-background-radius: 0");
    }
    int getx() {
        return i;
    }
    int gety() {
        return j;
    }
}
