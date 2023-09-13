package me.emafire003.dev.beampass.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.beampass.BeamPass;
import net.minecraft.block.Block;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class GetBlocksCommand implements BeamCommand {
    CommandRegistryAccess commandRegistryAccess;

    public GetBlocksCommand(CommandRegistryAccess registryAccess) {
        this.commandRegistryAccess = registryAccess;
    }


    private int getBlock(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(() -> Text.literal("Sending a list of current BeamPassable blocks, other than vanilla ones:"), false);

        for(Block block : BeamPass.bypassableBlocks){
            source.sendFeedback(() ->Text.literal(block.getName().getString()), false);
        }
        return 1;
    }

    public LiteralCommandNode<ServerCommandSource> getNode() {
        return CommandManager
                .literal("get").executes(this::getBlock)
                .build();
    }
}
