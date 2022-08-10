package me.emafire003.dev.beampass.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.emafire003.dev.beampass.BeamPass;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BeaconBlockEntity.class)
public class BeaconMixin {

	private static BlockState bstate;

	@ModifyExpressionValue(
			method = "tick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getOpacity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)I"
					)
	)
	private static int onlyIfNotBeamPassable(int original) {
		if(bstate == null){
			return original;
		}
		//If the block met by the beam is in the list of bypassable blocks,
		//it returns 0 as its opacity, (only in this case it never changes the opacity of the block itself)
		//so the beam code thinks it should let the beam through that block it met as well.
		if(BeamPass.bypassableBlocks.contains(bstate.getBlock())){
			return 0;
		}
		return original;
	}

	//Gets the block
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), locals = LocalCapture.CAPTURE_FAILSOFT)
	private static void getBlockState(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci, int i, int j, int k, BlockPos blockPos, BeaconBlockEntity.BeamSegment beamSegment, int l, int m, BlockState blockState){
		bstate = blockState;
	}
}


