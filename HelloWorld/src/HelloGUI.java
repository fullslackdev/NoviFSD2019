import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HelloGUI {
    //private static String test = "5";
    //static Functions functions = new Functions();
    private JFrame frame = new JFrame("Circle");
    private JPanel panel;
    private Clip clip;

    public static void main (String[] args) {
        HelloGUI helloGUI = new HelloGUI();
        helloGUI.run();
    }

    public void run () {
        //JFrame frame = new JFrame("Circle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250,20,100,20);
        JTextField inputField = new JTextField(5);
        inputField.setBounds(100,20,100,20);
        JLabel inputLabel = new JLabel("Diameter:");
        inputLabel.setBounds(20,20,70,20);

        JButton hiddenButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/resources/rooster.bmp"));
            hiddenButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        hiddenButton.setBounds(10,250,83,100);
        hiddenButton.setMargin(new Insets(0,0,0,0));
        hiddenButton.setBorder(null);
        hiddenButton.setVisible(false);
        frame.add(hiddenButton);

        frame.add(submitButton);
        frame.add(inputField);
        frame.add(inputLabel);

        frame.setBounds(50,50,400,400);
        frame.setLayout(null);

        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panel != null) {
                    frame.remove(panel);
                    hiddenButton.setVisible(false);
                }
                String inputText = inputField.getText();
                float diameter = 0f;
                try {
                    diameter = Float.valueOf(inputText);
                } catch (NumberFormatException ex) {
                    diameter = 0f;
                    JOptionPane.showMessageDialog(null,"Please enter a number");
                }
                if (diameter == 42.0f)
                    hiddenButton.setVisible(true);
                else
                    hiddenButton.setVisible(false);
                //System.out.println("Circumference: " + value);
                panel = drawCircle(diameter, panel);


                panel = addLabels(diameter, panel);

                frame.add(panel);
                frame.revalidate();
                frame.repaint();
            }
        });

        hiddenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hidden button clicked");
                if (clip != null)
                    if (!clip.isActive())
                        clip.start();
                    else
                        clip.stop();
                else
                    playSound();
            }
        });
    }

    private JPanel drawCircle(float diameter, JPanel panel) {
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Shape fullCircle = new Ellipse2D.Double(50,50,200,200);
                Shape dottedCircle = new Ellipse2D.Double(55,55,190,190);
                float[] dash1 = { 2f, 0f, 2f };
                BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
                //g2d.setPaint(new Color(255, 255, 0));
                //g2d.fill(fullCircle);
                g2d.setColor(Color.BLACK);
                g2d.draw(fullCircle);
                g2d.setStroke(bs1);
                g2d.setColor(Color.RED);
                g2d.draw(dottedCircle);
            }
        };
        panel.setBounds(50,50,300,300);
        panel.setLayout(null);

        return panel;
    }

    private JPanel addLabels(float diameter, JPanel panel) {
        Functions functions = new Functions();
        JLabel circumLabel = new JLabel("Circumference: " + functions.circleCircumference(diameter));
        JLabel surfaceLabel = new JLabel("Surface area: " + functions.circleSurfaceArea(diameter));

        circumLabel.setBounds(10,10,150,20);
        circumLabel.setForeground(Color.RED);
        surfaceLabel.setBounds(10,30,150,20);
        surfaceLabel.setForeground(Color.BLACK);
        panel.add(circumLabel);
        panel.add(surfaceLabel);

        return panel;
    }

    private void playSound() {
        try {
            //URL url = new URL("file:/resources/WagnerTheRideOfTheValkyries.wav");
            URL url = this.getClass().getResource("/resources/WagnerTheRideOfTheValkyries.wav");
            AudioInputStream audioInputStream;
            //Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(url);
                try {
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.loop(20000);
                    clip.start();
                } catch (LineUnavailableException ex) {
                    System.out.println(ex);
                }
            } catch (UnsupportedAudioFileException | IOException ex) {
                System.out.println(ex);
            }
        } catch (Exception ex) {
            System.out.println("URL: "+ex);
        }
    }
}
