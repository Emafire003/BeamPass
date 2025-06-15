package me.emafire003.dev.beampass;

import me.emafire003.dev.beampass.networking.SyncBlocksPayloadS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

import java.util.NoSuchElementException;

public class BeamPassClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerSyncPacket();
    }

    private void registerSyncPacket(){
        ClientPlayNetworking.registerGlobalReceiver(SyncBlocksPayloadS2C.ID, (payload, context) -> {
            MinecraftClient client = context.client();
            client.execute(() -> {
                try{
                    BeamPass.bypassableBlocks = BeamPass.convertToBlockList(payload.ids());
                    BeamPass.bypassableBlocksIds = payload.ids();
                }catch (NoSuchElementException e){
                    BeamPass.LOGGER.warn("No value in the packet, probably not a big problem");
                }catch (Exception e){
                    BeamPass.LOGGER.error("There was an error while getting the packet!");
                    e.printStackTrace();
                }
            });
        });
    }
}
