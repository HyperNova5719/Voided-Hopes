package hypernova.voidedhopes.LazuliLib;
/** Simple position and rotation transform. */

import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;


public class Transform3D {
    public Vec3d position;
    public Quaternion rotation;

    public static final Transform3D ZERO = new Transform3D(
            Vec3d.ZERO, Quaternion.IDENTITY
    );

    public static Transform3D fromPosition(Vec3d pos) {
        return new Transform3D(pos, Quaternion.IDENTITY);
    }

    public static Transform3D fromRotation(Quaternion rot) {
        return new Transform3D(Vec3d.ZERO, rot);
    }

    public Transform3D(Vec3d position, Quaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Transform3D() {
        this.position = Vec3d.ZERO;
        this.rotation = Quaternion.IDENTITY;
    }

    public Transform3D copy() {
        return new Transform3D(position, rotation.copy());
    }
    

    public Vec3d transformPoint(Vec3d point) {
        Vec3f p = new Vec3f((float) point.x, (float) point.y, (float) point.z);
        p.rotate(rotation);
        return position.add(p.getX(), p.getY(), p.getZ());
    }

}
