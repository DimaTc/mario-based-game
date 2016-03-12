package com.dima.imback.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

	public Vector2 velocity;
	private Vector2 gravity = new Vector2(0, -0.8f);
	private boolean isJumping = false;

	// tmp
	private int floor = 50;

	//
	public Player(float x, float y, Texture texture) {
		super(x, y, texture);
		velocity = new Vector2(0, 0);
	}

	public Player(float x, float y, int width, int height, Texture texture) {
		super(x, y, width, height, texture);
		velocity = new Vector2(0, 0);
	}

	public boolean collidesWith(Entity entity, boolean interact) {
		boolean collides;
		Rectangle rect = entity.getRectangle();
		float tmpx = rect.x;
		float tmpy = rect.y;
		rect.x -= velocity.x;
		rect.y -= velocity.y;
		collides = rect.overlaps(getRectangle());
		if (collides) {
			if (velocity.x > 0 || velocity.x < 0)
				if (getRectangle().y < tmpy + rect.height
						&& getRectangle().y + getHeight() > tmpy)
					velocity.x = 0;
			if (velocity.y < 0 || velocity.y > 0)
				if (getRectangle().x < tmpx + rect.width
						&& getRectangle().x + getWidth() > tmpx) {
					if(velocity.y > 0){
						isJumping = true;
						velocity.y = -1.4f;
					}
					else
						isJumping = false;
					velocity.y = 0;
				}

		}
		return collides;
	}

	public void update(float dt) {

		this.position.add(velocity.x, velocity.y);
		fallIfPossible();

	}

	private void fallIfPossible() {
		boolean falling = false;
		if (position.y + velocity.y <= floor) {
			velocity.y = 0;
			position.y = floor;
			isJumping = false;
		} else
			falling = true;
		if (isJumping || falling)
			velocity.add(gravity);
	}

	public void jump() {
		if (!isJumping) {
			isJumping = true;
			velocity.y = 10;
		}
	}

}
