import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel panel;
    JButton button;
    JLabel label;
    int count = 0;

    public GUI(){
        frame = new JFrame();
        frame.setSize(1200, 800);

        panel = new JPanel();

        frame = setFrameParameters(frame, panel);
    } // Contructor

    private JFrame setFrameParameters(JFrame frame, JPanel panel){
        frame.add(panel);
        button = new JButton("Open Sub Instance");
        button.addActionListener(this);

        label = new JLabel("Number of Clicks is: " + count);
        
        panel.add(button);
        panel.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Test Frame");
        frame.setVisible(true);
        return frame;
    } // Setter for the Frame

    @Override
    public void actionPerformed(ActionEvent e){
        count++;
        label.setText("Number of Clicks is: " + count);
        System.out.print("Number of Clicks is: " + count);
    } //Action Listener for the Button


}
