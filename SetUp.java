
import java.util.Vector;

import entities.Entity;
import entities.MoveableEntity;
import gui.Renderer;
import utilities.Vec2D;


public class SetUp {

    private Vector<Entity> entityVector;
    private Renderer       renderer;
    private final double   dt;
    
    private Vec2D          gravityField = new Vec2D(0, 0);

    private boolean        collisionOnRenderer = false;
    private boolean        collisionBetweenEntities = false;

    private double         gravityConstant = 0.0;
    private double         centralGravityX, centralGravityY;
    private final int      centerRadius = 5;

    private String         outputFile = null; // Give the possibility to store the results for better fluidity

    
    public SetUp(Renderer renderer, double dt, int subComputations){
        entityVector = new Vector<Entity>();
        this.renderer = renderer;
        this.dt = dt/(double)subComputations;
    }


    public void add_entity(Entity entity){
        entityVector.add(entity);
        renderer.add_entity(entity);
    }


    public void add_entity(Entity[] entityTab){
        for (Entity entity : entityTab) {
            add_entity(entity);
        }
    }


    public void update_renderer(){
        renderer.repaint();
        if(renderer.mouse_is_pressed()){
            Vec2D mousePosition = renderer.get_mouse_position();
            add_entity(new MoveableEntity("0x000000", (double) mousePosition.get_x(), (double) mousePosition.get_y(), 3*centerRadius, 0, 0, dt, 1, 0, 1));
        }
    }

    
    public void update(){

        for(int i = 0; i < entityVector.size(); i++){

            Entity entity = entityVector.get(i);

            if(entity instanceof MoveableEntity){

                MoveableEntity moveableEntity = (MoveableEntity) entity;
                apply_gravity_field(moveableEntity);
                moveableEntity.update(dt);

                for(int j = 0; j < i; j++){ //* Naive approach, should use trees

                    Entity other = entityVector.get(j);

                    if(distance_between_edges(moveableEntity, other) < 0){
                        collide_entities(moveableEntity, other);
                    }
                }
                collide_on_renderer(moveableEntity);
            }
        }
    }


    public void set_gravityField(Vec2D gravityField){
        this.gravityField = gravityField;
    }


    private void apply_gravity_field(MoveableEntity moveableEntity){
        if(!gravityField.isZero()){
            moveableEntity.count_force(gravityField.scale(moveableEntity.get_mass()));
        }

        if(gravityConstant != 0){
            Vec2D entityToCenter = new Vec2D(centralGravityX, centralGravityY).subtract(moveableEntity.get_position());
            Vec2D radial = entityToCenter.get_normalized();
            double distance = entityToCenter.get_norm();
            moveableEntity.count_force(radial.scale(gravityConstant * moveableEntity.get_mass() / (distance * distance))); // G m/r^2 (e_r)
        }
    }


    public void set_collisionOnRenderer(){
        collisionOnRenderer = true;
    }


    private void collide_on_renderer(MoveableEntity moveableEntity){

        if(collisionOnRenderer){

            int xMax = renderer.getWidth();
            int yMax = renderer.getHeight();
            double x = moveableEntity.get_position().get_x();
            double y = moveableEntity.get_position().get_y();
            int radius = moveableEntity.get_radius();

            if(x < radius){
                Vec2D positionCollisionOfCenter = new Vec2D(radius, 0);
                moveableEntity.collide_on_surface(positionCollisionOfCenter, new Vec2D(1, 0));
            }
            else if(x > xMax - radius){
                Vec2D positionCollisionOfCenter = new Vec2D(xMax - radius, 0);
                moveableEntity.collide_on_surface(positionCollisionOfCenter, new Vec2D(-1, 0));
            }

            if (y < radius){
                Vec2D positionCollisionOfCenter = new Vec2D(0, radius);
                moveableEntity.collide_on_surface(positionCollisionOfCenter, new Vec2D(0, 1));
            }
            else if (y > yMax - radius){
                Vec2D positionCollisionOfCenter = new Vec2D(0, yMax - radius);
                moveableEntity.collide_on_surface(positionCollisionOfCenter, new Vec2D(0, -1));
            }
        }
    }

    
    public void set_collision_between_entities(){
        collisionBetweenEntities = true;
    }


    private void collide_entities(MoveableEntity entity1, Entity entity2){

        if(collisionBetweenEntities){

            if(!(entity2 instanceof MoveableEntity)){
                Vec2D normal = vector_between_centers(entity2, entity1).get_normalized();
                Vec2D collisionPosition = entity2.get_position().add(normal.scale(entity1.get_radius()+centerRadius));
                entity1.collide_on_surface(collisionPosition, normal);
                return;
            }
                
            MoveableEntity moveableEntity2 = (MoveableEntity) entity2;

            double mass1 = entity1.get_mass(), mass2 = moveableEntity2.get_mass();
            
            Vec2D ds1 = entity1.get_position().subtract(entity1.get_positionPrev());
            Vec2D ds2 = moveableEntity2.get_position().subtract(moveableEntity2.get_positionPrev());
            Vec2D ds1New, ds2New;
            
            double combinedRestitutionCoefficient = (entity1.get_bouncingFactor() + moveableEntity2.get_bouncingFactor())/2;

            if(combinedRestitutionCoefficient < 0.5){ // Approximation to avoid heavy formula

                ds1New = ds1.scale(mass1/(mass1+mass2)).add(ds2.scale(mass2/(mass1+mass2)));
                ds2New = (Vec2D) ds1New.clone();
            }

            else{

                Vec2D normal = vector_between_centers(entity1, moveableEntity2).get_normalized();
                Vec2D tangent = normal.get_rotated_in_degrees(90);
    
                double ds1Normal = ds1.dot(normal);
                double ds1Tangent = ds1.dot(tangent);
                double ds2Normal = ds2.dot(normal);
                double ds2Tangent = ds2.dot(tangent);
    
                double ds1NormalNew = (mass1-mass2)/(mass1+mass2) * ds1Normal + 2*mass2/(mass1+mass2) * ds2Normal;
                double ds2NormalNew = (mass2-mass1)/(mass2+mass1) * ds2Normal + 2*mass1/(mass2+mass1) * ds1Normal;
    
                ds1New = normal.scale(ds1NormalNew).add(tangent.scale(ds1Tangent));
                ds2New = normal.scale(ds2NormalNew).add(tangent.scale(ds2Tangent));
            }

            Vec2D displacement = vector_between_centers(entity1, entity2).get_normalized().scale(distance_between_edges(entity1, entity2));
            
            entity1.set_positionPrev(entity1.get_position().add(displacement.scale(mass2/(mass1+mass2))));
            entity1.set_position(entity1.get_position().add(ds1New).add(displacement.scale(mass2/(mass1+mass2))));
            moveableEntity2.set_positionPrev(moveableEntity2.get_position().subtract(displacement.scale(mass1/(mass1+mass2))));
            moveableEntity2.set_position(moveableEntity2.get_position().add(ds2New).subtract(displacement.scale(mass1/(mass1+mass2))));
        }
    }


    private Vec2D vector_between_centers(Entity entity1, Entity entity2){
        return entity2.get_position().subtract(entity1.get_position());
    }


    private double distance_between_edges(Entity entity1, Entity entity2){
        return vector_between_centers(entity1, entity2).get_norm() - entity1.get_radius() - entity2.get_radius();
    }


    public void set_centralGravityField(double x, double y, double gravityConstant){
        this.gravityConstant = gravityConstant;
        centralGravityX = x;
        centralGravityY = y;
        Entity center = new Entity("0x000000", x, y, centerRadius, 0);
        entityVector.insertElementAt(center, 0);
        renderer.add_entity(center);
    }


    public double get_mechanical_energy(){
        double mechanicalEnergy = 0.0;
        for(Entity entity : entityVector){
            if(entity instanceof MoveableEntity){
                MoveableEntity moveableEntity = (MoveableEntity)entity;
                mechanicalEnergy += - moveableEntity.get_mass() * gravityField.dot(moveableEntity.get_position());
                mechanicalEnergy += 0.5 * moveableEntity.get_mass() * moveableEntity.get_velocity(dt).get_norm() * moveableEntity.get_velocity(dt).get_norm();
            }
        }
        return mechanicalEnergy;
    }
}