import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

class ClockTask extends TimerTask {
    public void run() {
        Toolkit.getDefaultToolkit().beep();
        System.out.println("RISE AND SHINE \\0o0/");
    }
}

public class MainClass extends JFrame {
    private JTextField hourFields = new JTextField(2);
    private JTextField minuteFields = new JTextField(2);
    private JTextField secondFields = new JTextField(2);
    private Activator activator = new Activator();

    MainClass() {
        setTitle("Lab 1 SO: Timers");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel inputPanel = new JPanel();
        inputPanel.add(hourFields);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(minuteFields);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(secondFields);
        
        JButton clockButton = new JButton("Set CLOCK");
        JPanel clockButtonPanel = new JPanel();
        clockButtonPanel.add(clockButton);
        
        JButton delayedButton = new JButton("Delayed Stop");
        JButton periodicTimerButton = new JButton("Periodic Timer");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(delayedButton);
        buttonPanel.add(periodicTimerButton);
        
        mainPanel.add(inputPanel);
        mainPanel.add(clockButtonPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.NORTH);
        
        ClockTask ct = new ClockTask();
        Timer ctTimer = new Timer();

        clockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {    
                    int hours = parseIntFromField(hourFields);
                    int minutes = parseIntFromField(minuteFields);
                    int seconds = parseIntFromField(secondFields);

                    if (hours >= 0 && hours <= 23 
                    && minutes >= 0 && minutes < 60 
                    && seconds >= 0 && seconds < 60) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hours);
                        calendar.set(Calendar.MINUTE, minutes);
                        calendar.set(Calendar.SECOND, seconds);

                        Date date = calendar.getTime();

                        ctTimer.schedule(ct, date);
                        System.out.println("Timer set for " + date);
                    } else {
                        System.out.println("Incorrect values");
                    } 
                } catch (IllegalStateException ex) {
                    System.out.println("Timer is already working or closed");
                }
        }});

        delayedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    activator.activateDelayedTimer(10000);
                } catch (IllegalStateException ex) {
                    System.out.println("Timer is already working or closed");
                }
            }
        });

        periodicTimerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    activator.activateClockTimer(0, 2000);
                } catch (IllegalStateException ex) {
                    System.out.println("Timer is already working or closed");
                }
            }
        });
    }

    private Integer parseIntFromField (JTextField field) {
        try {
            return Integer.parseInt(field.getText().trim().length() == 0 ? "0" : field.getText().trim());
        } catch (NumberFormatException e) {
            return -1; 
        }
    }
    public static void main(String[] args) throws Exception {
        MainClass app = new MainClass();
        app.setVisible(true);
    }
}