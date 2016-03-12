package com.dima.imback.gsm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {

	protected GSM gsm;
	
	public State(GSM gsm){
		this.gsm = gsm;
	}
	
	public abstract void draw(SpriteBatch sb);
	public abstract void update(float dt);
	
}
