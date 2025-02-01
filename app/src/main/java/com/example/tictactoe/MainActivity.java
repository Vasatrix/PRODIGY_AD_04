package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean playerXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private TextView statusText;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        restartButton = findViewById(R.id.restartButton);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                buttons[i][j] = (Button) gridLayout.getChildAt(index);
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }

        restartButton.setOnClickListener(view -> restartGame());
    }

    private void onButtonClick(View view) {
        Button clickedButton = (Button) view;

        if (!clickedButton.getText().toString().equals("")) {
            Toast.makeText(this, "Cell already occupied!", Toast.LENGTH_SHORT).show();
            return;
        }

        clickedButton.setText(playerXTurn ? "X" : "O");
        if (checkWin()) {
            statusText.setText("Player " + (playerXTurn ? "X" : "O") + " wins!");
            disableButtons();
            return;
        }

        playerXTurn = !playerXTurn;
        statusText.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");

        if (isBoardFull()) {
            statusText.setText("It's a draw!");
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals(""))
                return true;
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals(""))
                return true;
        }

        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals(""))
            return true;

        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals(""))
            return true;

        return false;
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void restartGame() {
        playerXTurn = true;
        statusText.setText("Player X's Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}
