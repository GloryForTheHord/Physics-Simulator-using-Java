
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class Simulator {

    public static void main(String[] args) {

        final int FPS = 400;
        final double dt = 1/(double)FPS;
        final int subComputations = 10;
        final double timeDilation = 1;

        SetUp setUp = new SetUp(new Renderer("Simulator", 800, 600), dt, subComputations);

        MoveableEntity[] moveableEntityTab = new MoveableEntity[50];

        for (int i = 0; i < moveableEntityTab.length; i++) {
            moveableEntityTab[i] = new MoveableEntity("FF0000", 100 + 50 * i/5, 400 - 50 * (i%5), 15 + 5 * (i%2), 10 * i, i, dt/(double)subComputations, 1 + 9* i%2, 0, .5);
        }
        
        setUp.add_entity(moveableEntityTab);

        setUp.set_centralGravityField(300, 300, 10000000);
        setUp.set_collisionOnRenderer();
        setUp.set_collision_between_entities();
        //setUp.set_gravityField(new Vec2D(0, -981));


        for (int i = 0;; i++){
            setUp.update();
            if(i%subComputations == 0){
                try {
                    Thread.sleep((long)(1000.0*dt*timeDilation));
                } catch (Exception e) {
                    throw new InternalError(e.getMessage());
                }

                setUp.update_renderer();
            }
        }
    }
}
