package hypernova.voidedhopes.AzuraThingies.LazuliLib;

public class LazuliFreeze {
    private static float time = 0;

    public static boolean doRender(){
        return time > 0;
    }

    public static void updateTime(float delta){
        time += delta;
    }

    public static void freeze(float duration){
        time = Math.min(-duration, time);
    }
}
