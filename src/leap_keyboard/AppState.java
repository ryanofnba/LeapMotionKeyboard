package leap_keyboard;

import java.util.Observable;;

public class AppState extends Observable {
	private int fingerX;
	private int fingerY;
	private int fingerZ;
	private char curKey;
	
	public AppState() {
		fingerX = fingerY = fingerZ = 0;
	}
	
	public int getFingerX() {
		return this.fingerX;
	}
	
	public int getFingerY() {
		return this.fingerY;
	}
	
	public int getFingerZ() {
		return this.fingerZ;
	}
	
	public void setFingerX(int xPos) {
		fingerX = xPos;
		setChanged();
	}
	
	public void setFingerY(int yPos) {
		fingerY = yPos;
		setChanged();
	}
	
	public void setFingerZ(int zPos) {
		fingerZ = zPos;
		setChanged();
	}
	
	public char getCurKey() {
		return curKey;
	}
	
	public void updatedCurrentCharacter(char key) {
		this.curKey = key;
		setChanged();
	}
}
