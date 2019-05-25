package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static final int BLOCK_SIZE = 30;
    public static final int APP_W = 20 * BLOCK_SIZE;
    public static final int APP_H = 15 * BLOCK_SIZE;

    private Direction direction = Direction.RIGHT;

    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    private boolean running = false;
    private boolean moved = false;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();


        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.BLUE);
        food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> {
            if (!running) return;

            boolean hasOnlyHead = snake.size() > 1;

            Node head = hasOnlyHead ? snake.remove(snake.size() - 1) : snake.get(0);

            double headX = head.getTranslateX();
            double headY = head.getTranslateY();

            switch (direction) {
                case UP:
                    head.setTranslateX(snake.get(0).getTranslateX());
                    head.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    head.setTranslateX(snake.get(0).getTranslateX());
                    head.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    head.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    head.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    head.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    head.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (hasOnlyHead)
                snake.add(0, head);

            for (Node node : snake) {
                if (node != head && head.getTranslateX() == node.getTranslateX()
                        && head.getTranslateY() == node.getTranslateY()) {
                    restartGame();
                    break;
                }
            }

            if (head.getTranslateX() < 0 || head.getTranslateX() >= APP_W
                    || head.getTranslateY() < 0 || head.getTranslateY() >= APP_H) {
                restartGame();
            }

            if (head.getTranslateX() == food.getTranslateX()
                    && head.getTranslateY() == food.getTranslateY()) {
                int x = (int) food.getTranslateX();
                int y = (int) food.getTranslateY();
                food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
                food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);


                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setTranslateX(headX);
                rect.setTranslateY(headY);

                snake.add(rect);
            }

        });


        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(food, snakeBody);

        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (!moved)
                return;

            switch (event.getCode()) {
                case UP:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case DOWN:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case LEFT:
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case RIGHT:
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
            }

            moved = false;
        });


        /*Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();*/

        primaryStage.setTitle("app.Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();

    }


    public static void main(String[] args) {
        /*Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.setDaemon(true);*/
        launch(args);
    }

    private void restartGame() {
        stopGame();
        startGame();
    }

    private void stopGame() {
        running = false;
        timeline.stop();
        snake.clear();
    }

    private void startGame() {
        direction = Direction.RIGHT;
        Rectangle snakeHead = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        snake.add(snakeHead);
        timeline.play();
        running = true;

        //TODO Here
        /*SnakeClient client = new SnakeClient();
        client.startConnection("127.0.0.1", 6666);*/
    }

}
