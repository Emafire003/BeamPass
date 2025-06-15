package me.emafire003.dev.beampass.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.beampass.BeamPass;
import me.emafire003.dev.beampass.commands.permissions.PermissionsChecker;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class AddBlocksCommand implements BeamCommand {
    CommandRegistryAccess commandRegistryAccess;

    public AddBlocksCommand(CommandRegistryAccess registryAccess) {
        this.commandRegistryAccess = registryAccess;
    }


    private int addBlock(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockState blockState = BlockStateArgumentType.getBlockState(context, "block").getBlockState();
        ServerCommandSource source = context.getSource();
        BeamPass.addBlock(blockState.getBlock(), source.getServer());
        source.sendFeedback(() -> Text.literal("The §a" + blockState.getBlock().getName().getString() + " §rblock has been added to the list beam-passable blocks!" ), false);
        return 1;
    }

    public LiteralCommandNode<ServerCommandSource> getNode() {
        return CommandManager
                .literal("add")
                .requires(PermissionsChecker.hasPerms("beampass.commands.add", 2))
                .then(
                        CommandManager.argument("block", BlockStateArgumentType.blockState(commandRegistryAccess))
                                .executes(this::addBlock)
                )
                .build();
    }
}
