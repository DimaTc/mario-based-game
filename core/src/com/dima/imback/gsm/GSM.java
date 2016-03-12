package com.dima.imback.gsm;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GSM {

	private LinkedList<State> states;

	public static final byte PLAY = 1;
	private int currentState = 0;
	private float TIME_STEMP = 1 / 30f; //30 updated pre second
	private float accum = 0;
	
	public GSM() {
		states = new LinkedList<State>();
		states.add(new PlayState(this));
	}

	public void draw(SpriteBatch sb) {
		states.get(currentState).draw(sb);
	}

	public void setState(int newState) {
		currentState = newState;
	}

	public void update(float dt) {
		accum += dt;
		while(accum >= TIME_STEMP){
			accum -= TIME_STEMP;
			states.get(currentState).update(dt);
		}
	}

}
