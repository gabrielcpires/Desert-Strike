package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class DesertStrike extends Game {

    SpriteBatch batch;// objeto de desenho de imagem
    BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        // A fonte usada será Arial(padrão da LibGDX)
        font = new BitmapFont(Gdx.files.internal("assets/fonte.fnt"));
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
       font.setColor(1, 1, 1, 1);
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
