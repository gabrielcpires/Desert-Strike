package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MainMenuScreen implements Screen {
    final BatalhaNaval game;
    private Texture background;
    private Texture botao;
    private Texture botao_fechar;
    Array<Rectangle> boxes;
    ShapeRenderer shape;
    int dificuldade;
    int vez;
    private Music background_music;

    // Construtor, inicializando as texturas e variaveis
    public MainMenuScreen(final BatalhaNaval gam, int vez) {
        this.vez = vez;
        game = gam;
        background = new Texture("assets/background_inicio.jpg");
        botao = new Texture("assets/botao.jpg");
        botao_fechar = new Texture("assets/botao1.jpg");
        // Criar os retângulos que presentam os quadrados na tela.
        boxes = new Array<Rectangle>();
        boxes.add(new Rectangle(175, 125, 200, 50));
        boxes.add(new Rectangle(375, 125, 200, 50));
        boxes.add(new Rectangle(575, 125, 200, 50));
        boxes.add(new Rectangle(375, 55, 200, 50));
        boxes.add(new Rectangle(750, 450, 125, 50));
        
        // Inicializa o construtor de formas para desenhar o retângulo.
        shape = new ShapeRenderer();
        background_music = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
        // Confere se é a primeira vez que é chamado, se sim começa a musica
        // Isso evita de quando o usuario jogar novamente, ficas duas musicas ao mesmo
        // tempo
        if (vez == 1) {
            background_music.setLooping(true);
            background_music.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Pegar a posição atual do mouse
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        // inicializa o batch, para desenhar na tela
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.font.draw(game.batch, "BEM VINDO AO BATALHA NAVAL!", 260, 350);
        game.font.draw(game.batch, "DIFICULDADE:", 175, 225);
        game.batch.end();
        // desenhar o retangulo de acordo com onde esta o mouse
        for (Rectangle box : boxes) {
            if (box.contains(mouseX, mouseY)) { // Testa se mouse está sobre o retangulo
                shape.setColor(Color.DARK_GRAY);
                shape.begin(ShapeType.Filled);
                shape.rect(box.x, box.y, box.width + 2, box.height + 2);

            } else {
                shape.setColor(Color.BLACK);
                shape.begin(ShapeType.Line);
                shape.rect(box.x, box.y, box.width, box.height);
            }
            shape.end();
        }
        // inicializa o batch, para desenhar na tela
        game.batch.begin();
        game.batch.draw(botao, 176, 126, 198, 48);
        game.batch.draw(botao, 376, 126, 198, 48);
        game.batch.draw(botao, 576, 126, 198, 48);
        game.batch.draw(botao, 376, 56, 198, 48);
        game.batch.draw(botao_fechar, 751, 451, 123, 48);
        game.font.draw(game.batch, "FACIL", 235, 157);
        game.font.draw(game.batch, "MEDIA", 435, 157);
        game.font.draw(game.batch, "DIFICIL", 630, 157);
        game.font.draw(game.batch, "REGRAS", 425, 88);
        game.font.draw(game.batch, "FECHAR", 762, 482);

        game.batch.end();
        //Se a tela for clicada, confere qual a dificuldade selecionada
        if (Gdx.input.isTouched()) {
            // Percorre os retângulos para saber qual foi clicado.
            for (Rectangle box : boxes) {
                if (box.contains(mouseX, mouseY)) { // Vifica se o x,y está no retangulo.
                    if (mouseX > 175 && mouseX < 375) {
                        dificuldade = 0;
                        //Chama a classe onde esta a logica do jogo, com a dificuldade escolhida
                        game.setScreen(new GameScreen(game, dificuldade));
                    }
                    else if (mouseX > 375 && mouseX < 575) {
                        if(mouseY > 125){
                            dificuldade = 1;
                            game.setScreen(new GameScreen(game, dificuldade));
                        }else{
                            game.setScreen(new RulesScreen(game));
                        }
                    } else if (mouseX > 575 && mouseX < 775) {
                        dificuldade = 2;
                        game.setScreen(new GameScreen(game, dificuldade));
                    } else if(mouseY > 450 && mouseY < 500){
                        Gdx.app.exit();
                    }
                    break;
                }
            }
            dispose();
        }

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

