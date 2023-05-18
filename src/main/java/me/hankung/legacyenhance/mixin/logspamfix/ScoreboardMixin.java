package me.hankung.legacyenhance.mixin.logspamfix;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;

@Mixin(Scoreboard.class)
public abstract class ScoreboardMixin {

    @Shadow
    public abstract Team getTeam(String p_96508_1_);

    @Inject(method = "removeTeam", at = @At("HEAD"), cancellable = true)
    private void legacy$checkIfTeamIsNull(Team team, CallbackInfo ci) {
        if (team == null)
            ci.cancel();
    }

    @Redirect(method = "removeTeam", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
    private <K, V> V legacy$checkIfRegisteredNameIsNull(Map<K, V> instance, K o) {
        if (o != null)
            return instance.remove(o);
        return null;
    }

    @Inject(method = "removeObjective", at = @At("HEAD"), cancellable = true)
    private void legacy$checkIfObjectiveIsNull(ScoreboardObjective objective, CallbackInfo ci) {
        if (objective == null)
            ci.cancel();
    }

    @Redirect(method = "removeObjective", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
    private <K, V> V legacy$checkIfNameIsNull(Map<K, V> instance, K o) {
        if (o != null)
            return instance.remove(o);
        return null;
    }

    @Inject(method = "addTeam", at = @At(value = "CONSTANT", args = "stringValue=A team with the name '"), cancellable = true)
    private void legacy$returnExistingTeam(String name, CallbackInfoReturnable<Team> cir) {
        cir.setReturnValue(this.getTeam(name));
    }

    @Inject(method = "removePlayerFromTeam", at = @At(value = "CONSTANT", args = "stringValue=Player is either on another team or not on any team. Cannot remove from team '"), cancellable = true)
    private void legacy$silenceException(CallbackInfo ci) {
        ci.cancel();
    }

}
