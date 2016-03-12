package com.dima.imback;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dima.imback.gsm.GSM;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch sb;
	GSM gsm;
	@Override
	public void create () {
		sb = new SpriteBatch();
		gsm = new GSM();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		//main Game loop
		
		gsm.draw(sb);
		gsm.update(Gdx.graphics.getDeltaTime());
		//
		sb.end();
	}
}
