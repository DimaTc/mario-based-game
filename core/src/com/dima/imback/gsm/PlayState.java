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
	Texture backgroundTexture;
	OrthographicCamera cam;

	MapGenerator mg;

	public PlayState(GSM gsm) {
		super(gsm);

		mg = new MapGenerator();
		walls = new LinkedList<GameObject>();

		try {
			playerTexture = new Texture(Gdx.files.internal("player.png"));
			wallTexture = new Texture(Gdx.files.internal("block.png"));
			backgroundTexture = new Texture(Gdx.files.internal("bg.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		initMap();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(player.position.x, 150, 0);

	}

	private void initMap() {
		mg.loadMap(new Texture(Gdx.files.internal("map.png")));
		LinkedList<Vector2> points = mg.getObjectPoints(0xff0000); // red
		for (int i = 0; i < points.size(); i++) {
			walls.add(new GameObject(points.get(i).x * 32,
					points.get(i).y * 32, 32, 32, wallTexture));
		}
		Vector2 playerPoint = mg.getObjectPoints(0x00ff00).get(0); // green
		player = new Player(playerPoint.x * 32, playerPoint.y * 33,
				playerTexture);

	}
	public void draw(SpriteBatch sb) {
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		sb.draw(backgroundTexture, cam.position.x - cam.viewportWidth / 2 - player.position.x,
				cam.position.y - cam.viewportHeight / 2, walls.size() * 8, backgroundTexture.getHeight());
		for (GameObject wall : walls) {
			wall.draw(sb);
		}
		player.draw(sb);
	}

	public void update(float dt) {

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			player.jump();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.velocity.x = -10;
			player.setFlip(true);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.velocity.x = 10;
			player.setFlip(false);
		} else
			player.velocity.x = 0;

		for (GameObject wall : walls) {
			player.collidesWith(wall, true);
		}

		player.update(dt);
		cam.position.set(player.position.x, 150 + player.position.y / 2, 0);
	}
}
