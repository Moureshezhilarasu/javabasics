import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class TMain extends JFrame {

    // UI Components
    private final JLabel timeLabel;
    private final JLabel dateLabel;

    // Formats
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");

    public TMain() {
        // 1. Window Setup
        setTitle("Java Digital Clock");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY); // Dark mode style

        // 2. Create Labels
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Verdana", Font.BOLD, 60));
        timeLabel.setForeground(Color.CYAN);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 3. Add to Window
        add(timeLabel, BorderLayout.CENTER);
        add(dateLabel, BorderLayout.SOUTH);

        // 4. Start the Timer (Updates every 1000ms / 1 second)
        Timer timer = new Timer(1000, e -> updateClock());
        timer.setInitialDelay(0); // Start immediately
        timer.start();
    }

    // The Logic Loop
    private void updateClock() {
        Date now = new Date();
        timeLabel.setText(timeFormat.format(now));
        dateLabel.setText(dateFormat.format(now));
    }

    public static void main(String[] args) {
        // Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new TMain().setVisible(true);
        });
    }
}
