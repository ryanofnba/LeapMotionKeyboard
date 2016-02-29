package leap_keyboard;

import java.awt.Point;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

class MainListener extends Listener {
	private GUI display;
	private AppState state;
	private final int kXAxisMin = -200;
	private final int kXAxisMax = 200;
	private final int kYAxisMin = 20;
	private final int kYAxisMax = 250;

	public void onInit(Controller controller) {
		System.out.println("Initialized");
		state = new AppState();
		display = new GUI(state);
		state.addObserver(display);
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		// controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		// controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		// controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		// controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}

	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		Vector finger = frame.fingers().get(0).tipPosition();
		
		state.setFingerX(
				(int) map((long) finger.getX(), kXAxisMin, kXAxisMax, 0, 800));
		state.setFingerY(
				600 - (int) map((long) finger.getY(), kYAxisMin, kYAxisMax, 0, 600));
		
		state.setFingerZ((int)finger.getZ());
		
		System.out.println(finger.getZ());
		
		state.notifyObservers();
	}

	public void onDisconnect(Controller controller) {
		System.out.println("Disconnected");
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
	}

	public long map(long x, long in_min, long in_max, long out_min, long out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
}