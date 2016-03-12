package com.dima.imback.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	public Vector2 position = new Vector2(0,0);
	private Texture texture;
	private float width;
	private float height;

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

	public void draw(SpriteBatch sb) {
		sb.draw(texture, position.x, position.y, width, height);
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidht() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
