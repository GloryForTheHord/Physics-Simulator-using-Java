package interactions;

import entities.Entity;
import utilities.Vec2D;

public interface MassEffects {
    public Vec2D compute_mass_acceleration(Entity entity);
}