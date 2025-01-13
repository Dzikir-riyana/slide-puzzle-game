import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SlidePuzzleGame extends JFrame {
    private static final int GRID_SIZE = 4;
    private JButton[][] buttons = new JButton[4][4];
    private int emptyRow = 3;
    private int emptyCol = 3;

    public SlidePuzzleGame() {
        this.setTitle("Slide Puzzle Game");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel gridPanel = new JPanel(new GridLayout(4, 4));
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 1; i < 16; i++) {
            numbers.add(i);
        }
        numbers.add(0);
        Collections.shuffle(numbers);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int number = numbers.remove(0);
                this.buttons[row][col] = this.createButton(number);
                gridPanel.add(this.buttons[row][col]);
                if (number == 0) {
                    this.emptyRow = row;
                    this.emptyCol = col;
                }
            }
        }

        this.setLayout(new BorderLayout());
        this.add(gridPanel, BorderLayout.CENTER);
    }

    private JButton createButton(int number) {
        final JButton button = new JButton(number == 0 ? "" : String.valueOf(number));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveTile(button);
            }
        });
        return button;
    }

    private void moveTile(JButton button) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (this.buttons[row][col] == button &&
                        (Math.abs(row - this.emptyRow) == 1 && col == this.emptyCol ||
                                Math.abs(col - this.emptyCol) == 1 && row == this.emptyRow)) {

                    this.buttons[this.emptyRow][this.emptyCol].setText(button.getText());
                    button.setText("");
                    this.emptyRow = row;
                    this.emptyCol = col;

                    if (this.isSolved()) {
                        JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    return;
                }
            }
        }
    }

    private boolean isSolved() {
        int count = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (row == 3 && col == 3) {
                    return true;
                }
                if (!this.buttons[row][col].getText().equals(String.valueOf(count++))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SlidePuzzleGame app = new SlidePuzzleGame();
            app.setVisible(true);
        });
    }
}