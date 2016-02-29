package leap_keyboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GUI implements Observer {
	private JFrame frame;
	private KeyBoardPanel keyboardPanel;
	private AppState state;
	
	public static final int kWidth = 800;
	public static final int kHeight = 600;
	
	public GUI(AppState state) {
		this.state = state;
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(300, 150, kWidth, kHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		keyboardPanel = new KeyBoardPanel();
		keyboardPanel.setBounds(0, 0, kWidth, kHeight);
		frame.getContentPane().add(keyboardPanel);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI(new AppState());
	}

	@Override
	public void update(Observable o, Object arg) {
		keyboardPanel.xPos = state.getFingerX();
		keyboardPanel.yPos = state.getFingerY();
		if (state.getFingerZ() < - 100) {
			keyboardPanel.label.setText("Numbers Keyboard");
		}
		else {
			keyboardPanel.label.setText("Characters Keyboard");
		}
		
		keyboardPanel.repaint();
	}
}

class KeyBoardPanel extends JComponent {
	int xPos;
	int yPos;
	JLabel label;
		
	public KeyBoardPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, GUI.kWidth, GUI.kHeight);

		Point2D center = new Point2D.Float(20,20);
		float radius = 40;
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
