package hypernova.voidedhopes.AzuraThingies.LazuliLib;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public final class QuadCollider {
    public static final double THICK = 0.03125;
    public static final double SPACING = THICK;

    public static void approach(Vec3d a, Vec3d b, Vec3d extrude) {
        double dist = a.distanceTo(b);
        if (dist < 1.0e-6) {
            addPillar(a, extrude);
            return;
        }

        Vec3d dir = b.subtract(a).normalize();
        int steps = Math.max(1, (int) Math.ceil(dist / SPACING));
        Vec3d step = dir.multiply(dist / steps);

        Vec3d pos = a;
        for (int i = 0; i <= steps; i++) {
            addPillar(pos, extrude);
            pos = pos.add(step);
        }
    }

    private static void addPillar(Vec3d center, Vec3d extrude) {
        Vec3d min = center.subtract(THICK, THICK, THICK);
        Vec3d max = center.add(THICK, THICK, THICK).add(extrude);
        CollisionsManager.add(new Box(min, max));
    }

    private QuadCollider() {}
}
