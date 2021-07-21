package org.narses.entities.collision;

import net.minestom.server.collision.BoundingBox;
import net.minestom.server.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class OffsetBoundingBox extends BoundingBox {

    private final @NotNull Entity entity;
    private final double offsetX, offsetY, offsetZ;

    public OffsetBoundingBox(
            @NotNull Entity entity,
            double x, double y, double z,
            double offsetX, double offsetY, double offsetZ
    ) {
        super(entity, x, y, z);
        this.entity = entity;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    /**
     * Gets the max X based on {@link #getWidth()} and the {@link Entity} position.
     *
     * @return the max X
     */
    @Override
    public double getMaxX() {
        return super.getMaxX() + this.offsetX;
    }

    /**
     * Gets the max Y based on {@link #getHeight()} and the {@link Entity} position.
     *
     * @return the max Y
     */
    @Override
    public double getMaxY() {
        return super.getMaxY() + this.offsetY;
    }

    /**
     * Gets the max Z based on {@link #getDepth()} and the {@link Entity} position.
     *
     * @return the max Z
     */
    @Override
    public double getMaxZ() {
        return super.getMaxZ() + this.offsetZ;
    }

    /**
     * Gets the min X based on {@link #getWidth()} and the {@link Entity} position.
     *
     * @return the min X
     */
    @Override
    public double getMinX() {
        return super.getMinX() + this.offsetX;
    }

    /**
     * Gets the min Y based on the {@link Entity} position.
     *
     * @return the min Y
     */
    @Override
    public double getMinY() {
        return super.getMinY() + this.offsetY;
    }

    /**
     * Gets the min Z based on {@link #getDepth()} and the {@link Entity} position.
     *
     * @return the min Z
     */
    @Override
    public double getMinZ() {
        return super.getMinZ() + this.offsetZ;
    }
}
