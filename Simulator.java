
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class Simulator {

    public static void main(String[] args) {

        final int FPS = 120;
        final double dt = 1/(double)FPS;
        final int subComputations = 8;

        MoveableEntity entity1 = new MoveableEntity("0xFF0000", 100, 100, 15, 160, 100, dt/(double)subComputations, 1, 0);

        SetUp setUp = new SetUp(new Renderer("Simulator", 200, 200));

        setUp.add_entity(entity1);

        setUp.set_collisionOnRenderer();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            return;
        }

        for (int i = 0;; i++){
            setUp.update();
            if(i%subComputations == 0){
                try {
                    Thread.sleep((long)(1000*dt));
                } catch (Exception e) {
                    throw new InternalError(e.getMessage());
                }
                setUp.update_renderer();
            }
        }
    }
}
