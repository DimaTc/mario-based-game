package com.dima.imback.gsm;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dima.imback.entities.GameObject;
import com.dima.imback.entities.Player;
import com.dima.imback.utilities.MapGenerator;

public class PlayState extends State {

	Player player;
	LinkedList<GameObject> walls;
	Texture playerTexture;
	Texture wallTexture;

	OrthographicCamera cam;

	MapGenerator mg;

	public PlayState(GSM gsm) {
		super(gsm);

		mg = new MapGenerator();
		walls = new LinkedList<GameObject>();

		try {
			playerTexture = new Texture(Gdx.files.internal("player.png"));
			wallTexture = new Texture(Gdx.files.internal("block.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		initMap();
		player = new Player(20, 20, playerTexture);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(player.position, 0);

	}

	private void initMap() {
		mg.loadMap(new Texture(Gdx.files.internal("map.png")));
		LinkedList<Vector2> points = mg.getObjectPoints(0xff0000); // red
		for (int i = 0; i < points.size(); i++) {
			walls.add(new GameObject(points.get(i).x + 32 * i, points.get(i).y + 32 * i, 32, 32,
					wallTexture));
		}
	}

	public void draw(SpriteBatch sb) {
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		for (GameObject wall : walls) {
			wall.draw(sb);
		}
		player.draw(sb);
	}

	public void update(float dt) {

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			player.jump();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			player.velocity.x = -10;
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			player.velocity.x = 10;
		else
			player.velocity.x = 0;

		player.update(dt);
		cam.position.set(player.position.x, 10, 0);
	}
}
