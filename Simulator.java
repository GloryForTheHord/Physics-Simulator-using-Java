
import entities.MoveableEntity;
import gui.Renderer;


public class Simulator {

    public static void main(String[] args) {

        final int FPS = 200;
        final double dt = 1/(double)FPS;
        final int subComputations = 8;
        final double timeDilation = 2;

        SetUp setUp = new SetUp(new Renderer("Simulator", 800, 600));

        MoveableEntity[] moveableEntityTab = new MoveableEntity[50];

        for (int i = 0; i < moveableEntityTab.length; i++) {
            moveableEntityTab[i] = new MoveableEntity("FF0000", 100 + 50 * i/5, 400 - 50 * (i%5), 15 + 5 * (i%2), 10 * i, i, dt/(double)subComputations, 1 + 9* i%2, 0, 1);
            setUp.add_entity(moveableEntityTab[i]);
        }
        
        //setUp.set_central_gravity_field(300, 300, 10000000);
        setUp.set_collisionOnRenderer();
        setUp.set_collision_between_entities();

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
