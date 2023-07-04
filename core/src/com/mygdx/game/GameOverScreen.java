package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class GameOverScreen implements Screen {
    final DesertStrike game;
    private Texture background;
    ShapeRenderer shape;
    private InputProcessor inputProcessor;

    // Construtor, inicializando as texturas e variáveis
    public GameOverScreen(final DesertStrike gam) {
        game = gam;
        background = new Texture("assets/background_gameOver.jpg");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.draw(game.batch,"O MEGA BATALHAO DE DESTRUICAO MAXIMA AEREA foi bem sucedido e agora nenhum maldito\n comunista tera um teto sobre sua cabeca. \n\nClique Novamente para ir para o Menu!", 50,170);
        game.batch.end();

        inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                // Lógica para trocar de tela quando uma tecla for pressionada
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        };

        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
