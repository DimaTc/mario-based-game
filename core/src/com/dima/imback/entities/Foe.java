package com.dima.imback.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Foe extends Player {

	private Animation animation;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;

	private float accum = 0;
	protected Vector2 velocity;
	private boolean isFlip = false;

	private static final int SIZE_OF_FRAME = 32;
	private static final int ROWS = 1;
	private static final int COLS = 4;
	private static final float ANIMATION_SPEED = 1 / 6f;
	public Foe(float x, float y, float width, float height, Texture sheet) {
		super(x, y, width, height, sheet);
		TextureRegion[][] tmp = TextureRegion.split(sheet, SIZE_OF_FRAME,
				SIZE_OF_FRAME);
		frames = new TextureRegion[ROWS * COLS];
		for (int i = 0; i < ROWS * COLS; i++) {
			frames[i] = tmp[i / COLS][i % COLS];
		}

		animation = new Animation(ANIMATION_SPEED, frames);

		velocity = new Vector2(1, 0);

	}

	public void update(float dt) {
		position.add(velocity);
		fallIfPossible();
		if(!isFlip && velocity.x < 0)
			flip(true);
		else if(isFlip && velocity.x > 0)
			flip(false);
			
	}

	@Override
	public void draw(SpriteBatch sb) {
		accum += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(accum, true);
		if (isFlip)
			sb.draw(currentFrame, position.x + getWidth() , position.y, -getWidth(),
					getHeight());
		else
			sb.draw(currentFrame, position.x , position.y, getWidth(),
					getHeight());
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
					velocity.x = -velocity.x;
			if (velocity.y < 0 || velocity.y > 0)
				if (getRectangle().x < tmpx + rect.width
						&& getRectangle().x + getWidth() > tmpx) {
					if (velocity.y > 0) {
						velocity.y = -1.4f;
					} else
						velocity.y = 0;
				}

		}
		return collides;
	}

	protected void fallIfPossible() {
		boolean falling = false;
		if (position.y + velocity.y <= failingY) {
			velocity.y = 0;
			position.y = failingY;
			isJumping = false;
		} else
			falling = true;
		if (isJumping || falling)
			velocity.add(gravity);
	}

	public void setSpeed(Vector2 velocity) {
		this.velocity = velocity;
	}

	public void setSpeed(float x, float y) {
		velocity.x = x;
		velocity.y = y;
	}

	public Vector2 getSpeed() {
		return velocity;
	}
	
	public void flip(boolean isFlip){
		this.isFlip = isFlip;
	}

}
