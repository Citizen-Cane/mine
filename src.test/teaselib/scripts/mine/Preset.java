/**
 * 
 */
package teaselib.scripts.mine;

import pcm.controller.Player;

/**
 * @author Citizen-Cane
 *
 */
public class Preset {
    final Player player;

    public Preset(Player player) {
        super();
        this.player = player;
    }

    protected Preset set(int... n) {
        for (int i : n) {
            player.state.set(i);
        }
        return this;
    }

    public Preset submitted() {
        set(100, 101);
        return this;
    }

}
