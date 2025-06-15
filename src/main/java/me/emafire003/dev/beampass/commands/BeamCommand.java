package me.emafire003.dev.beampass.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;


//Based on Factions' code https://github.com/ickerio/factions
public interface BeamCommand {
    LiteralCommandNode<ServerCommandSource> getNode();

}
