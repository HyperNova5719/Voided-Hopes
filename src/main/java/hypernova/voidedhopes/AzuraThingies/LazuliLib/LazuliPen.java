package hypernova.voidedhopes.AzuraThingies.LazuliLib;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class LazuliPen {
    public int mode;
    public Vec3d screenNormal;
    public LazuliVertex model;
    public List<Vec3d> POINTS = new ArrayList<>();
    public List<Double> THICKNESS = new ArrayList<>();
    public List<LazuliVertex> MODELS = new ArrayList<>();
    public double lastThickness = 0;

    //Constructors
    public LazuliPen() {
        this.mode = 0;
        this.screenNormal = new Vec3d(0, 1, 0);
        this.model = new LazuliVertex();
    }

    public LazuliPen(int mod) {
        this.mode = mod;
        this.screenNormal = new Vec3d(0, 1, 0);
        this.model = new LazuliVertex();
    }

    public LazuliPen(LazuliVertex Model) {
        this.mode = 0;
        this.screenNormal = new Vec3d(0, 1, 0);
        this.model = Model;
    }

    public LazuliPen(Vec3d ScreenNormal){
        this.mode = 0;
        this.screenNormal = ScreenNormal.normalize();
        this.model = new LazuliVertex();
    }

    public LazuliPen(int mod, LazuliVertex Model) {
        this.mode = mod;
        this.screenNormal = new Vec3d(0, 1, 0);
        this.model = Model;
    }

    public LazuliPen(LazuliVertex Model, Vec3d ScreenNormal) {
        this.mode = 0;
        this.screenNormal = ScreenNormal.normalize();
        this.model = Model;
    }

    public LazuliPen(int mod, Vec3d ScreenNormal){
        this.mode = mod;
        this.screenNormal = ScreenNormal.normalize();
        this.model = new LazuliVertex();
    }

    public LazuliPen(int mod, LazuliVertex Model, Vec3d ScreenNormal){
        this.mode = mod;
        this.screenNormal = ScreenNormal.normalize();
        this.model = Model;
    }
    //Done with the constructor mess lol, now the setters
    public LazuliPen setModel(LazuliVertex mod){
        model = mod;
        return this;
    }

    public LazuliPen screenPlaneMode() {
        mode = 0;
        return this;
    }

    public LazuliPen screenPlaneMode(Vec3d normal) {
        mode = 0;
        screenNormal = normal;
        return this;
    }

    //Now for the real logic!
    public LazuliPen point(Vec3d p, double Thickness, LazuliVertex model){
        lastThickness = Thickness;
        POINTS.add(p);
        THICKNESS.add(Thickness);
        MODELS.add(model.copy());
        return this;
    }

    public LazuliPen point(double x, double y, double z){
        point(new Vec3d(x, y, z), lastThickness, model);
        return this;
    }

    public LazuliPen point(Vec3d p){
        point(p, lastThickness, model);
        return this;
    }

    public LazuliPen point(Vec3d p, double thickeness){
        point(p, thickeness, model);
        return this;
    }

    public LazuliPen point(Vec3d p, LazuliVertex model){
        point(p, lastThickness, model);
        return this;
    }

    public LazuliPen point(double x, double y, double z, double thickeness){
        point(new Vec3d(x, y, z), thickeness, model);
        return this;
    }

    public LazuliPen point(double x, double y, double z, LazuliVertex model){
        point(new Vec3d(x, y, z), lastThickness, model);
        return this;
    }

    public LazuliPen eraseAll(){
        MODELS.clear();
        POINTS.clear();
        THICKNESS.clear();
        return this;
    }

    public LazuliPen draw(LazuliBufferBuilder bb, Vec3d displace){
        if (POINTS.size() < 2) return this;
        for (int i = 0; i < POINTS.size(); i++) {
            Vec3d p0 = POINTS.get(i);
            Vec3d p1;
            if (i != POINTS.size() - 1) {
                p1 = POINTS.get(i + 1);
            } else {
                p1 = p0.add(p0.subtract(POINTS.get(i - 1)));
            }
            Vec3d segment = p1.subtract(p0).normalize();
            Vec3d thisDir = LazuliMathUtils.rotateAroundAxis(segment, screenNormal, 90);
            Vec3d dir = thisDir;
            if (i > 0) {
                Vec3d prevSegment = p0.subtract(POINTS.get(i - 1)).normalize();
                Vec3d prevDir = LazuliMathUtils.rotateAroundAxis(prevSegment, screenNormal, 90);
                dir = prevDir.add(thisDir).normalize();
            }
            if (i < POINTS.size() - 2) {
                Vec3d nextSegment = POINTS.get(i + 1).subtract(p1).normalize();
                Vec3d nextDir = LazuliMathUtils.rotateAroundAxis(nextSegment, screenNormal, 90);
                dir = dir.add(nextDir).normalize();
            }
            double comp = LazuliMathUtils.getComponentAlongAxis(dir, thisDir);
            if (Math.abs(comp) < 1e-6) comp = 1e-6; // avoid collapse
            double finalThick = THICKNESS.get(i) / comp;
            Vec3d offset = dir.multiply(finalThick * 0.5);
            LazuliVertex modelCopy = MODELS.get(i).copy();
            if (i != 0) {
                bb.addVertex(modelCopy.pos(p0.add(displace).add(offset)).uv(modelCopy.u, 1));
                bb.addVertex(modelCopy.pos(p0.add(displace).subtract(offset)).uv(modelCopy.u, 0));
            }

            if (i != POINTS.size() - 1){
                bb.addVertex(modelCopy.pos(p0.add(displace).subtract(offset)).uv(modelCopy.u, 0));
                bb.addVertex(modelCopy.pos(p0.add(displace).add(offset)).uv(modelCopy.u, 1));
            }
        }
        return this;
    }


    public LazuliPen draw(LazuliBufferBuilder bb){
        draw(bb, Vec3d.ZERO);
        return this;
    }
}
