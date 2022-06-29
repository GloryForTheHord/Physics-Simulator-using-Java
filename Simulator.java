
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class Simulator {

    public static void main(String[] args) {

        final int FPS = 60;
        final double dt = 1/(double)FPS;
        final int subComputations = 8;

        MoveableEntity entity1 = new MoveableEntity("0xFF0000", 100, 200, 15, 0, 500, dt/(double)subComputations, 1, 0);
        MoveableEntity entity2 = new MoveableEntity("0x00FF00", 100, 200, 15, 75, 500, dt/(double)subComputations, 1, 0);
        MoveableEntity entity3 = new MoveableEntity("0x0000FF", 100, 200, 15, 150, 500, dt/(double)subComputations, 1, 0);

        SetUp setUp = new SetUp(new Renderer("Simulator", 500, 500));

        setUp.add_entity(entity1);
        setUp.add_entity(entity2);
        setUp.add_entity(entity3);

        setUp.set_gravityField(new Vec2D(0, -481));

        try {
            Thread.sleep(4000);
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
