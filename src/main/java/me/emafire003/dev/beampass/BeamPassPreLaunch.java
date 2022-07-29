package me.emafire003.dev.beampass;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class BeamPassPreLaunch implements PreLaunchEntrypoint {

	/**
	 * Runs the entrypoint.
	 */
	@Override
	public void onPreLaunch() {
		BeamPass.LOGGER.info("Starting BeamPass mod...");
		MixinExtrasBootstrap.init();
		BeamPass.LOGGER.info("Done!");
	}
}
