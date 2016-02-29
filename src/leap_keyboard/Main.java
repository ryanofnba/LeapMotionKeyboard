package leap_keyboard;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import com.leapmotion.leap.Controller;

public class Main {
	public static void main(String[] args) throws MidiUnavailableException {
		MainListener listener = new MainListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		System.out.println("Press any key to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("quitting");
		controller.removeListener(listener);
	}
}
