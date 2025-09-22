package hypernova.voidedhopes.AzuraThingies.LazuliLib;

import net.minecraft.util.math.Box;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** Thread-safe store for every ghost AABB currently active. */
public final class CollisionsManager {

    private static final Set<Box> GHOSTS = ConcurrentHashMap.newKeySet();

    /* --------------- public API --------------- */

    public static void add(Box box)               { GHOSTS.add(box); }
    public static void addAll(Iterable<Box> boxes){ boxes.forEach(GHOSTS::add); }

    public static void remove(Box box)            { GHOSTS.remove(box); }
    public static void clear()                    { GHOSTS.clear(); }

    /** Used by the mixin – don’t mutate externally. */
    public static Set<Box> get()                  { return GHOSTS; }

    private CollisionsManager() {}   // util class
}
