package com.dima.imback.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	public Vector2 position = new Vector2(0, 0);
	private Texture texture;
	private float width;
	private float height;
	private boolean flip = false;

	public Entity(float x, float y, Texture texture) {
		position.x = x;
		position.y = y;
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	public Entity(float x, float y, float width, float height, Texture texture) {
		position.x = x;
		position.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}

	public Rectangle getRectangle() {
		return new Rectangle(position.x, position.y, width, height);
	}

	public boolean collidesWith(Entity entity) {
		Rectangle rect = entity.getRectangle();
		return rect.overlaps(getRectangle());
	}

	public void draw(SpriteBatch sb) {
		if (flip)
			sb.draw(texture, position.x + width /2 , position.y, -width, height);
		else
			sb.draw(texture, position.x , position.y, width, height);

	}

	public void setFlip(boolean flip){
		this.flip = flip;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setPosition(int x, int y) {
		this.position.x = x;
		this.position.y = y;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
