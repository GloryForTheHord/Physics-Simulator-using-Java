
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class Simulator {

    public static void main(String[] args) {

        final int FPS = 120;
        final double dt = 1/(double)FPS;
        final int subComputations = 8;

        MoveableEntity entity1 = new MoveableEntity("0xFF0000", 100, 300, 15, 0, 0, dt/(double)subComputations, 1, 0, 1);
        MoveableEntity entity2 = new MoveableEntity("0x00FF00", 200, 300, 15, 0, 0, dt/(double)subComputations, 1, 0, .8);
        MoveableEntity entity3 = new MoveableEntity("0x0000FF", 300, 300, 15, 0, 0, dt/(double)subComputations, 1, 0, .2);
        
        

        SetUp setUp = new SetUp(new Renderer("Simulator", 400, 400));

        setUp.add_entity(entity1);
        setUp.add_entity(entity2);
        setUp.add_entity(entity3);

        setUp.set_gravityField(new Vec2D(0, -481));
        setUp.set_collisionOnRenderer();
        

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
