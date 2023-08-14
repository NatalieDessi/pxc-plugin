package de.natalie.pxc.commands;


import de.natalie.pxc.model.HomeLocation;
import de.natalie.pxc.utils.HomeUtils;
import lombok.extern.java.Log;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static de.natalie.pxc.utils.HomeUtils.getHomeLocation;
import static de.natalie.pxc.utils.HomeUtils.getHomes;
import static de.natalie.pxc.utils.HomeUtils.toHomeLocation;
import static de.natalie.pxc.utils.bukkit.MessageUtils.component;
import static de.natalie.pxc.utils.bukkit.MessageUtils.send;
import static de.natalie.pxc.utils.bukkit.MessageUtils.sendError;
import static de.natalie.pxc.utils.bukkit.MessageUtils.sendSuccess;
import static de.natalie.pxc.utils.bukkit.MessageUtils.success;
import static java.util.Optional.ofNullable;

@Log
public final class Home implements ICommand {

    private final Consumer<Player> invalidArguments = sendError("The arguments you have specified are not valid, use /help home for more information!");
    private final Consumer<Player> fileNotFound = sendError("Cannot set home since the home.json has errors or does not exist!");
    private final Consumer<Player> teleportSuccessful = sendSuccess("Successfully teleported to your home!");
    private final Consumer<Player> homeSetSuccessful = sendSuccess("Successfully set your home!");
    private final Consumer<Player> noHomeFound = sendError("Cannot teleport to home since you haven't set one. Use /home set for setting a home!");
    private final BiConsumer<Player, HomeLocation> homeSpot = (player, location) -> send(success("Your home location is: '").append(component("world: %s, x: %.3f, y: %.3f, z: %.3f", location.world(), location.x(), location.y(), location.z()))
                                                                                                                            .append(success("'"))).accept(player);

    @Override
    public boolean onCommand(@NotNull final CommandSender sender,
                             @NotNull final Command command,
                             @NotNull final String label,
                             final @NotNull String[] args) {
        if (sender instanceof Player player) {
            try {
                switch (args.length) {
                    case 0 -> teleport(player);
                    case 1 -> {
                        switch (args[0].toLowerCase()) {
                            case "set" -> setHome(player);
                            case "get" -> getHome(player);
                            default -> invalidArguments.accept(player);
                        }
                    }
                    default -> invalidArguments.accept(player);
                }
            } catch (IOException exception) {
                log.warning(exception.getMessage());
                fileNotFound.accept(player);
            }
        }

        return false;
    }

    private void getHome(final Player player) throws IOException {
        final Optional<HomeLocation> optionalHomeLocation = ofNullable(getHomeLocation(player));
        optionalHomeLocation.ifPresentOrElse(homeLocation -> homeSpot.accept(player, homeLocation),
                                             () -> noHomeFound.accept(player));
    }

    private void teleport(final Player player) throws IOException {
        final Optional<Location> optionalLocation = ofNullable(getHomes().get(player.getUniqueId())).map(HomeLocation::getLocation);
        optionalLocation.ifPresentOrElse(location -> {
            player.teleport(location);
            teleportSuccessful.accept(player);
        }, () -> noHomeFound.accept(player));
    }

    private void setHome(final Player player) throws IOException {
        final HomeLocation homeLocation = toHomeLocation(player.getLocation());
        HomeUtils.setHome(player.getUniqueId(), homeLocation);
        homeSetSuccessful.accept(player);
    }
}
