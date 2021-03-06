package powers;

import engine.EventStream;
import engine.Input;
import game.Player;
import static org.lwjgl.input.Keyboard.KEY_LSHIFT;
import util.Vec2;

public class WallClimbPower extends Power {

    public WallClimbPower() {
        whileActive.forEach(dt -> Player.player.velocity.edit(v -> v.withZ(Math.min(v.z + 25 * dt, 8))));
        whileActive.filter(Player.player.wallSlide.map(b -> !b)).forEach(dt -> finish());
        whileActive.forEach(dt -> {
            if (!spendEnergy(dt * 50)) {
                finish();
            }
        });
        onFinish.onEvent(() -> Player.player.velocity.edit(v -> v.withZ(Math.min(v.z, 4))));
    }

    @Override
    boolean canAct() {
        return !Player.player.moveDir.get().equals(new Vec2(0)) && time.get() > .1 && Player.player.wallSlide.get();
    }

    @Override
    EventStream getAttemptAct() {
        return Input.whenKey(KEY_LSHIFT, true);
    }

    @Override
    EventStream getDeact() {
        return Input.whenKey(KEY_LSHIFT, false);
    }
}
