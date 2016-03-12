package com.dima.imback.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

	public Vector2 velocity;
	private Vector2 gravity = new Vector2(0, -0.8f);
	private boolean isJumping = false;

	//tmp
	private int floor = 10;
	//
	public Player(float x, float y, Texture texture) {
		super(x, y, texture);
		velocity = new Vector2(0, 0);
	}

	public Player(float x, float y, int width, int height, Texture texture) {
		super(x, y, width, height, texture);
		velocity = new Vector2(0, 0);
	}

	public void update(float dt) {
		this.position.add(velocity.x, velocity.y);
		fallIfPossible();

	}

	private void fallIfPossible() {
		if(isJumping)
			velocity.add(gravity);
		if(position.y + velocity.y <= floor){
			velocity.y = 0;
			position.y = floor;
			isJumping = false;
		}
	}

	public void jump() {
		if(!isJumping){
			isJumping = true;
			velocity.y = 10;
		}
	}
	

}
