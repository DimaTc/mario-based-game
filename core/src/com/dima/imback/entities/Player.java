package com.dima.imback.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

	public Vector2 velocity;
	protected Vector2 gravity = new Vector2(0, -0.8f);
	protected boolean isJumping = false;
	private boolean lost = false;

	protected int failingY = -250;

	private Vector2 spawnPoint;

	public Player(float x, float y, Texture texture) {
		super(x, y, texture);
		velocity = new Vector2(0, 0);
		spawnPoint = new Vector2(position);
	}

	public Player(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height, texture);
		velocity = new Vector2(0, 0);
		spawnPoint = new Vector2(position);
	}

	public boolean collidesWith(Entity entity, boolean interact) {
		boolean collides;
		Rectangle rect = entity.getRectangle();
		float tmpx = rect.x;
		float tmpy = rect.y;
		rect.x -= velocity.x;
		rect.y -= velocity.y;
		collides = rect.overlaps(getRectangle());
		if (collides && !lost) {
			if (velocity.x > 0 || velocity.x < 0)
				if (getRectangle().y < tmpy + rect.height
						&& getRectangle().y + getHeight() > tmpy)
					velocity.x = 0;
			if (velocity.y < 0 || velocity.y > 0)
				if (getRectangle().x < tmpx + rect.width
						&& getRectangle().x + getWidth() > tmpx) {
					if (velocity.y > 0) {
						isJumping = true;
						velocity.y = -1.4f;
					} else {
						position.y = tmpy + rect.height;
						isJumping = false;
					}
					velocity.y = 0;
				}

		}
		return collides;
	}

	public void update(float dt) {

		this.position.add(velocity.x, velocity.y);
		fallIfPossible();
		if (position.y <= failingY) {
			position.set(spawnPoint);
			lost = false;
		}

	}

	protected void fallIfPossible() {
		boolean falling = false;
		if (position.y + velocity.y <= failingY) {
			velocity.y = 0;
			position.y = failingY;
			isJumping = false;
			lost = true;
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

	public void dies() {
		if (!lost) {
			isJumping = false;
			velocity.x = 0;
			velocity.y = 0;
			jump();
		}
		lost = true;
	}

	public boolean getStatus() {
		return lost;
	}

}
