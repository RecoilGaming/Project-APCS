import com.apcs.disunity.app.App;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.game.nodes.Scene;
import com.apcs.disunity.game.nodes.sprite.ImageLocation;
import com.apcs.disunity.game.nodes.sprite.Sprite;
import com.apcs.disunity.math.Transform;
import com.apcs.disunity.math.Vector2;

public class TransformTest {
    public static void main(String[] args) {
        // Create the game scenes
        Scene scene = new Scene("test", Circle.chain(3));


        // Create game application
        Game game = new Game(Vector2.of(480, 270));
        game.addScene(scene);
        game.setScene("test");

        new App("transform test", 800, 450, game);

        game.start();
    }

    private static class Circle extends Sprite {
        public Circle() {
            super(new ImageLocation("circle.png"));
        }

        @Override
        public void update(Transform offset, double delta) {
            setRotation(getRotation()+0.01);
            super.update(offset, delta);
        }

        public static Circle chain(int num) {
            Circle circle = new Circle();
            if(num > 1) {
                Circle child = chain(num-1);
                child.setScale(Vector2.of(0.5));
                child.setPosition(Vector2.of(47.5,0));
                circle.addChild(child);
                return circle;
            }
            return circle;
        }
    }
}
