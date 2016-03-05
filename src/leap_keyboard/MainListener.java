package leap_keyboard;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

class MainListener extends Listener {
	private GUI display;
	private AppState state;
	private final int kXAxisMin = -200;
	private final int kXAxisMax = 200;
	private final int kYAxisMin = 20;
	private final int kYAxisMax = 250;
	private Point keys[];

	public void onInit(Controller controller) {
		System.out.println("Initialized");
		state = new AppState();
		display = new GUI(state);
		state.addObserver(display);
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		
		// Start a thread to check characters
		ScheduledExecutorService exec = Executors
				.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Character key = matchKeyPos();
				if (key != null && state != null) {
					state.updatedCurrentCharacter(key.charValue());
				}
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}

	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		Vector finger = frame.fingers().get(0).tipPosition();

		state.setFingerX((int) map((long) finger.getX(), kXAxisMin, kXAxisMax,
				0, 800));
		state.setFingerY(600 - (int) map((long) finger.getY(), kYAxisMin,
				kYAxisMax, 0, 600));
		state.setFingerZ((int) finger.getZ());

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

	public void setKeys(Point keys[]) {
		this.keys = keys;
	}

	public Character matchKeyPos() {
		List<Point> keyPos = display.getKeyPositions();
		if (keyPos == null || keyPos.size() != 27) {
			return null;
		}

		int range = 10;
		Point finger = new Point(state.getFingerX(), state.getFingerY());

		for (int index = 0; index < keyPos.size(); index++) {
			if (withinRange(keyPos.get(index), finger, range)) {
				return Character.valueOf(display.getKey(index));
			}
		}

		return null;
	}

	public boolean withinRange(Point one, Point two, int range) {
		int minX = two.x - range;
		int maxX = two.x + range;
		int minY = two.y - range;
		int maxY = two.y + range;

		if (one.x >= minX && one.x <= maxX && one.y >= minY && one.y <= maxY) {
			return true;
		}

		return false;
	}
}