package hypernova.voidedhopes.AzuraThingies.LazuliLib;
/** Simple position and rotation transform. */

import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform3D {
    public Vec3d position;
    public Quaternionf rotation;

    public static final Transform3D ZERO = new Transform3D(
            Vec3d.ZERO,
            new Quaternionf() // Identity quaternion
    );

    public static Transform3D fromPosition(Vec3d pos) {
        return new Transform3D(pos, new Quaternionf()); // Identity quaternion
    }

    public static Transform3D fromRotation(Quaternionf rot) {
        return new Transform3D(Vec3d.ZERO, rot);
    }

    public Transform3D(Vec3d position, Quaternionf rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Transform3D() {
        this.position = Vec3d.ZERO;
        this.rotation = new Quaternionf(); // Identity quaternion
    }

    public Transform3D copy() {
        return new Transform3D(position, new Quaternionf(rotation));
    }

    public Vec3d transformPoint(Vec3d point) {
        Vector3f p = new Vector3f((float) point.x, (float) point.y, (float) point.z);
        p.rotate(rotation);
        return position.add(p.x, p.y, p.z);
    }
}