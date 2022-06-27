
import entities.MoveableEntity;
import gui.Renderer;


public class Simulator {

    public static void main(String[] args) {

        final int FPS = 60;
        final double dt = 1/(double)FPS;

        MoveableEntity entity = new MoveableEntity("ball", 0, 0, 15, 1, 1, dt, 0, 0);

        SetUp process = new SetUp(new Renderer("Simulator", 500, 500));

        process.add_entity(entity);

        while(true){
            try {
                Thread.sleep((long)(1000*dt));
                entity.move(1, 1);
                process.update_renderer();
            } catch (Exception e) {
                throw new InternalError(e.getMessage());
            }
        }
    }
}
