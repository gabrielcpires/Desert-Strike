package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Cenario {
    private Texture backgroundTexture;
    private float width;
    private float height;

    public Cenario() {
        backgroundTexture = new Texture("assets/backgroundnovo.png");
        width = backgroundTexture.getWidth();
        height = backgroundTexture.getHeight();
    }

    public void render(Batch batch) {
        batch.draw(backgroundTexture, 0, 0, width, height);
    }

    public void dispose() {
        backgroundTexture.dispose();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}