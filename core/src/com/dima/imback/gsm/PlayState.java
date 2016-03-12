package com.dima.imback.gsm;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dima.imback.entities.Entity;
import com.dima.imback.entities.Foe;
import com.dima.imback.entities.GameObject;
import com.dima.imback.entities.Player;
import com.dima.imback.utilities.MapGenerator;

public class PlayState extends State {

	Player player;
	Entity winPole;
	LinkedList<GameObject> walls;
	LinkedList<GameObject> fakeWalls;
	LinkedList<Foe> foes;

	Texture playerTexture;
	Texture wallTexture;
	Texture winPoleTexture;
	Texture backgroundTexture;
	Texture foeSheet;
	OrthographicCamera cam;

	MapGenerator mg;

	public PlayState(GSM gsm) {
		super(gsm);

		mg = new MapGenerator();
		walls = new LinkedList<GameObject>();
		fakeWalls = new LinkedList<GameObject>();
		foes = new LinkedList<Foe>();

		try {
			playerTexture = new Texture(Gdx.files.internal("player.png"));
			wallTexture = new Texture(Gdx.files.internal("block.png"));
			backgroundTexture = new Texture(Gdx.files.internal("bg.png"));
			foeSheet = new Texture(Gdx.files.internal("foe.png"));
			winPoleTexture= new Texture(Gdx.files.internal("win.png"));

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
		points = mg.getObjectPoints(0x00fe00);
		for (int i = 0; i < points.size(); i++) {
			fakeWalls.add(new GameObject(points.get(i).x * 32,
					points.get(i).y * 32, 32, 32, wallTexture));
		}
		points = mg.getObjectPoints(0xffff00);
		for (int i = 0; i < points.size(); i++) {
			foes.add(new Foe(points.get(i).x * 32,
					points.get(i).y * 32, 32, 32, foeSheet));
		}
		
		Vector2 playerPoint = mg.getObjectPoints(0x00ff00).get(0); // green
		player = new Player(playerPoint.x * 32, playerPoint.y * 33,
				playerTexture);
		
		Vector2 winPoint = mg.getObjectPoints(0x0000ff).get(0); // green
		winPole = new Player(winPoint.x * 32, winPoint.y * 32,
				winPoleTexture);

	}

	public void draw(SpriteBatch sb) {
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		sb.draw(backgroundTexture, cam.position.x - cam.viewportWidth / 2
				- player.position.x, cam.position.y - cam.viewportHeight / 2,
				walls.size() * 8, backgroundTexture.getHeight());

		player.draw(sb);
		for(Foe foe: foes){
			foe.draw(sb);
		}
		for (GameObject wall : walls) {
			wall.draw(sb);
		}
		for (GameObject wall : fakeWalls) {
			wall.draw(sb);
		}
		winPole.draw(sb);
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


		for(Foe foe : foes){
			for (GameObject wall : walls) {
				foe.collidesWith(wall,true);
				player.collidesWith(wall, true);

			}
			if(foe.collidesWith(player, true))
				player.dies();
			foe.update(dt);
			
		}

		player.update(dt);
		cam.position.set(player.position.x, 150 + player.position.y / 2, 0);
	}
}
