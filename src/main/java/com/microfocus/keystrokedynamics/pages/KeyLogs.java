package com.microfocus.keystrokedynamics.pages;

/**
 * Created by CKhadija on 8/7/2016.
 */
public class KeyLogs {
	private String keyCode;
    private float hold;
    private float keydown_keydown;
    private float keydown_keyup;
    
    

    public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public float getHold() {
        return hold;
    }

    public void setHold(float hold) {
        this.hold = hold;
    }

    public float getKeydown_keydown() {
        return keydown_keydown;
    }

    public void setKeydown_keydown(float keydown_keydown) {
        this.keydown_keydown = keydown_keydown;
    }

    public float getKeydown_keyup() {
        return keydown_keyup;
    }

    public void setKeydown_keyup(float keydown_keyup) {
        this.keydown_keyup = keydown_keyup;
    }
}
