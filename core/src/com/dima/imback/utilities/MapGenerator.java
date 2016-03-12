package com.dima.imback.utilities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class MapGenerator {

	Pixmap pixels;

	HashMap<Integer, LinkedList<Vector2>> objects;

	public MapGenerator() {
		objects = new HashMap<Integer, LinkedList<Vector2>>();
	}

	public void loadMap(Texture texture) {
		texture.getTextureData().prepare();
		pixels = texture.getTextureData().consumePixmap();
		int height = pixels.getHeight();
		for (int i = 0; i < pixels.getWidth() * height; i++) {
			int x = i % pixels.getWidth();
			int y = i / pixels.getWidth();
			int pixel = pixels.getPixel(x, y);
			if (objects.get(pixel) == null)
				objects.put(pixel, new LinkedList<Vector2>());
			objects.get(pixel).add(new Vector2(x, height - y));

		}
		System.out.print("");
	}

	public LinkedList<Vector2> getObjectPoints(int color) {
		int newColor = (color << 8) | 0xff;
		if(objects.get(newColor) != null) 
			return objects.get(newColor);
		else
			return null;
			
	}

	public void dispose() {
		pixels.dispose();
	}
}
