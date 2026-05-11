package App;

import view.Choisir;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Choisir frame = new Choisir();
            frame.setVisible(true);
        });
    }
}
