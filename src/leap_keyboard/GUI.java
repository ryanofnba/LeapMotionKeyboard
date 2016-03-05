package leap_keyboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GUI implements Observer {
	private JFrame frame;
	private KeyBoardPanel keyboardPanel;
	private AppState state;
	private JPanel charPanel;
	private JLabel textLabel;
	
	public static final int kWidth = 800;
	public static final int kHeight = 600;
	public static final int kCharPanelHeight = 75;
	
	private ArrayList<Key> keys;
	private ArrayList<Point> points;
	
	public GUI(AppState state) {
		keys = new ArrayList<>();
		points = new ArrayList<>();
		
		this.state = state;
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(300, 150, kWidth, kHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		charPanel = new JPanel();
		charPanel.setBounds(0, 0, kWidth, kCharPanelHeight);
		charPanel.setPreferredSize(new Dimension(kWidth , kCharPanelHeight));
		charPanel.setBorder(new LineBorder(Color.black, 2));
		textLabel = new JLabel();
		textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		charPanel.add(textLabel);
		
        keyboardPanel = new KeyBoardPanel();
        //Setting this layout to circular for every time a new
        //component is added.
        keyboardPanel.setLayout(new CircleLayout(true));
        keyboardPanel.setBounds(0, 0, kWidth, kHeight);
        
        //Setting a key for every letter in the alphabet
        Key key;
        for (int i = 0; i < 26; i++) {
        	key = new Key("" + (char)('A' + i));
        	keyboardPanel.add(key);
        	keys.add(key);
        }
        
        //This is the "OK" key, the "!" is so I know where it is located
        key = new Key("\u2713");
        keyboardPanel.add(key);
        keys.add(key);
                
        frame.getContentPane().add(charPanel);
		frame.getContentPane().add(keyboardPanel);
		frame.setVisible(true);
		
		for (Key skey : keys) {
			if (skey != null) {
				points.add(skey.getLocation());
			}
		}
	}
	
	public static void main(String[] args) {
		new GUI(new AppState());
	}

	@Override
	public void update(Observable o, Object arg) {
		keyboardPanel.xPos = state.getFingerX();
		keyboardPanel.yPos = state.getFingerY();
		
		textLabel.setText(String.valueOf(state.getCurKey()));
		
		keyboardPanel.repaint();
	}
	
	public List<Point> getKeyPositions() {
		return points;
	}
	
	public char getKey(int pos) {
		return keys.get(pos).toString().charAt(0);
	}
}

class KeyBoardPanel extends JComponent {
	int xPos;
	int yPos;
		
	public KeyBoardPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, GUI.kWidth, GUI.kHeight);

		Point2D center = new Point2D.Float(20,20);
		float radius = 25;
		float[] distribution = {0.0f, 1.0f};
		float[] hsbcolor = Color.RGBtoHSB(255, 220, 178, null);
		float[] hsbcolor2 = Color.RGBtoHSB(231, 158, 109, null);
		Color[] colors = {Color.getHSBColor(hsbcolor[0], hsbcolor[1], hsbcolor[2]),Color.getHSBColor(hsbcolor2[0], hsbcolor2[1], hsbcolor2[2])};
		RadialGradientPaint gradient = new RadialGradientPaint(center,radius,distribution,colors);

		if(!(xPos==0 && yPos==0)){
			g2d.setPaint(gradient);
			g2d.fillOval(xPos, yPos, (int)radius, (int)radius);
		}
	}
}
