/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leap_keyboard;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author jchoi30
 */
public class Key extends JPanel {
    
    private String keyName;
    
    public Key(String keyName)
    {
        this.keyName = keyName;
        
        //So i know where the JPanel as located, can ge removed if you want to
        //use images instead.
        JLabel label = new JLabel(keyName);
        this.add(label);
        label.setForeground(new Color(199, 32, 49));
        this.setOpaque(false);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
    }
}
