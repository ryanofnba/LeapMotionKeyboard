package leap_keyboard;

import java.util.Observable;;

public class AppState extends Observable {
	private int fingerX;
	private int fingerY;
	private int fingerZ;
	private char curKey;
	private String curWord;
	private String curSentence;
	
	public AppState() {
		fingerX = fingerY = fingerZ = 0;
		this.curKey = '*';
		this.curWord = "";
		this.curSentence = "";
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
	
	public String getCurWord() {
		return this.curWord;
	}
	
	public String getCurSentence() {
		return this.curSentence;
	}
	
	public void updatedCurrentCharacter(char key) {
		if (key != this.curKey) {
			curWord += String.valueOf(key);
		}	
		
		this.curKey = key;
		
		if (key == '\u2713') {
			curSentence += " " + curWord.substring(0, curWord.length() - 1);
			curWord = "";
			this.curKey = '*';
		}
		
		setChanged();
	}
}
