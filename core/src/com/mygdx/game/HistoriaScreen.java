package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class HistoriaScreen implements Screen {
    final DesertStrike game;
    private Texture background;
    ShapeRenderer shape;
    private InputProcessor inputProcessor;

    // Construtor, inicializando as texturas e variáveis
    public HistoriaScreen(final DesertStrike gam) {
        game = gam;
        background = new Texture("assets/background_historia.jpg");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.draw(game.batch,"Depois de anos de conflito no oriente medio,\numa repentina onda de humanitarismo fez com\nque a opiniao publica do povo americano fosse\ncontra a guerra. Com a situacao cada vez\nmais preocupante o alto escalao do exercito\namericano decidiu por criar um batalhao de\nforcas especiais para lutar nas guerras do\noriente medio de maneira menos violenta, assim\nfoi criado o MEGA BATALHAO DE DESTRUCAO MAXIMA\nAEREA, um grupo que tinha suas operacoes\naereas baseadas em apenas destruir a moradia\ndos inimigos e causar conflito interno nos\npaises inimigos.", 600,600);
        game.batch.end();

        inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                // Lógica para trocar de tela quando uma tecla for pressionada
                game.setScreen(new GameScreen(game));
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
