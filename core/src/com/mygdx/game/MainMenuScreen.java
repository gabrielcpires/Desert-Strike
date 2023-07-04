package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MainMenuScreen implements Screen {
    final DesertStrike game;
    private Texture background;
    private Texture botao;
    private Texture botao_fechar;
    Array<Rectangle> boxes;
    ShapeRenderer shape;
    private InputProcessor inputProcessor;

    // Construtor, inicializando as texturas e variaveis
    public MainMenuScreen(final DesertStrike gam) {
        game = gam;
        background = new Texture("assets/background_inicio.jpg");
        botao = new Texture("assets/botao1.png");
        botao_fechar = new Texture("assets/botao2.png");
        // Criar os retângulos que presentam os quadrados na tela.
        boxes = new Array<Rectangle>();
        boxes.add(new Rectangle(540, 205, 200, 50));
        boxes.add(new Rectangle(540, 105, 200, 50));

        // Inicializa o construtor de formas para desenhar o retângulo.
        shape = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        for (Rectangle box : boxes) {
            if (box.contains(mouseX, mouseY)) {
                shape.setColor(Color.DARK_GRAY);
                shape.begin(ShapeType.Filled);
                shape.rect(box.x, box.y, box.width, box.height);
            } else {
                shape.setColor(Color.BLACK);
                shape.begin(ShapeType.Line);
                shape.rect(box.x, box.y, box.width, box.height);
            }
            shape.end();
        }

        game.batch.begin();
        game.batch.draw(botao, 540, 205, 198, 48);
        game.batch.draw(botao_fechar, 540, 105, 198, 48);

        inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int mouseX = screenX;
                int mouseY = Gdx.graphics.getHeight() - screenY;

                // Percorre os retângulos para saber qual foi clicado.
                for (Rectangle box : boxes) {
                    if (box.contains(mouseX, mouseY)) {
                        if (mouseY > 205 && mouseY < 255) {
                            // Chama a classe onde está a lógica do jogo, com a dificuldade escolhida
                            game.setScreen(new HistoriaScreen(game));
                        } else if (mouseY > 105 && mouseY < 155) {
                            Gdx.app.exit();
                        }
                        break;
                    }
                }
                return true;
            }
        };

        Gdx.input.setInputProcessor(inputProcessor);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
