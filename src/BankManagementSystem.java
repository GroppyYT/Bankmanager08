import javax.swing.SwingUtilities;

public class BankManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankGUI gui = new BankGUI();
            gui.setVisible(true);
        });
    }
}