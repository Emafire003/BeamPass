package me.emafire003.dev.beampass.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.beampass.BeamPass;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class RemoveBlocksCommand implements BeamCommand {
    CommandRegistryAccess commandRegistryAccess;

    public RemoveBlocksCommand(CommandRegistryAccess registryAccess) {
        this.commandRegistryAccess = registryAccess;
    }

    private int addBlock(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockState blockState = BlockStateArgumentType.getBlockState(context, "block").getBlockState();
        ServerCommandSource source = context.getSource();
        BeamPass.removeBlock(blockState.getBlock());
        source.sendFeedback(Text.literal("The §c" + blockState.getBlock().getName().getString() + " §rblock has been removed from the list beam-passable blocks!" ), false);
        return 1;
    }

    public LiteralCommandNode<ServerCommandSource> getNode() {
        return CommandManager
                .literal("remove")
                .then(
                        CommandManager.argument("block", BlockStateArgumentType.blockState(commandRegistryAccess))
                                .executes(this::addBlock)
                )
                .build();
    }
}
