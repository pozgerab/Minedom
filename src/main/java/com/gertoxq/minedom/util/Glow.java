package com.gertoxq.minedom.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.InternalStructure;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.gertoxq.minedom.Minedom;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class Glow {
    ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    /**
     * Stores the glowing block ids with their location
     */
    private final HashMap<Location, Integer> glowingBlocks = new HashMap<>();
    /**
     * Counts entities, used to generate unique ids.
     */
    private int counter;
    /**
     * Player to receive the packets.
     */
    private final Player reciever;

    /**
     * Config, sets the receiver.
     * @param reciever
     */
    public Glow(Player reciever) {
        this.reciever = reciever;
    }

    /**
     * Increments the counter by one.
     * @return The pre-incremented counter value.
     */
    private int getCounter() {
        return counter++;
    }

    /**
     * Glows the block on the given location.
     * @param location Glow location
     * @param color Glow color
     */
    public void setBlockGlow(Location location, ChatColor color) {
        UUID blockID = UUID.randomUUID();
        int id = - reciever.getEntityId() - getCounter();
        glowingBlocks.put(location, id);
        PacketContainer spawnShulker = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
        spawnShulker.getModifier()
                .write(0, id)
                .write(2, EntityTypes.aG);
        spawnShulker.getUUIDs().write(0, blockID);
        spawnShulker.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        manager.sendServerPacket(reciever, spawnShulker);
        PacketContainer invShulker = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        final List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
        wrappedDataValueList.add(new WrappedDataValue(
                0,
                WrappedDataWatcher.Registry.get(Byte.class),
                (byte) (0x40 | 0x20)
        ));
        invShulker.getDataValueCollectionModifier().write(0, wrappedDataValueList);
        invShulker.getModifier().write(0, id);
        manager.sendServerPacket(reciever, invShulker);

        PacketContainer teamPacket = manager.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        teamPacket.getStrings().write(0, reciever.getEntityId() + "." + id); // Team name
        teamPacket.getIntegers().write(0, 0); // Clarify that this is a new team
        Optional<InternalStructure> optStruct = teamPacket.getOptionalStructures().read(0);
        if (optStruct.isPresent()) {
            InternalStructure struct = optStruct.get();
            struct.getChatComponents().write(0, WrappedChatComponent.fromText(""));
            struct.getModifier().write(6,2);
            struct.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, color);
            teamPacket.getOptionalStructures().write(0, Optional.of(struct));
        }
        teamPacket.getModifier().write(2, Lists.newArrayList(blockID.toString()));
        manager.sendServerPacket(reciever, teamPacket);
    }

    /**
     * Glows a block.
     * @param block Glowing block
     * @param color Glow color
     */
    public void setBlockGlow(Block block, ChatColor color) {
        setBlockGlow(block.getLocation(), color);
    }

    /**
     * Glows an entity
     * @param entity Glowing entity
     * @param color Glow color
     */
    public void setEntityGlow(Entity entity, ChatColor color) {
        UUID uuid = entity.getUniqueId();
        int id = entity.getEntityId();
        PacketContainer setGlow = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        final List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
        wrappedDataValueList.add(new WrappedDataValue(
                0,
                WrappedDataWatcher.Registry.get(Byte.class),
                (byte) (0x40)
        ));
        setGlow.getDataValueCollectionModifier().write(0, wrappedDataValueList);
        setGlow.getModifier().write(0, id);
        manager.sendServerPacket(reciever, setGlow);

        PacketContainer teamPacket = manager.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        teamPacket.getStrings().write(0, reciever.getEntityId() + "_" + id); // Team name
        teamPacket.getIntegers().write(0, 0); // Clarify that this is a new team
        Optional<InternalStructure> optStruct = teamPacket.getOptionalStructures().read(0);
        if (optStruct.isPresent()) {
            InternalStructure struct = optStruct.get();
            struct.getChatComponents().write(0, WrappedChatComponent.fromText(""));
            struct.getModifier().write(6,2);
            struct.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, color);
            teamPacket.getOptionalStructures().write(0, Optional.of(struct));
        }
        teamPacket.getModifier().write(2, Lists.newArrayList(uuid.toString()));
        manager.sendServerPacket(reciever, teamPacket);
    }

    /**
     * Set the entity to not glow
     * @param entity Entity to set
     */
    public void unsetEntityGlow(Entity entity) {
        int id = entity.getEntityId();
        PacketContainer setGlow = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        final List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
        wrappedDataValueList.add(new WrappedDataValue(
                0,
                WrappedDataWatcher.Registry.get(Byte.class),
                (byte) 0
        ));
        setGlow.getDataValueCollectionModifier().write(0, wrappedDataValueList);
        setGlow.getModifier().write(0, id);
        manager.sendServerPacket(reciever, setGlow);

        PacketContainer teamPacket = manager.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        teamPacket.getStrings().write(0, reciever.getEntityId() + "_" + id); // Team name
        teamPacket.getIntegers().write(0, 1); // Remove team
        manager.sendServerPacket(reciever, teamPacket);
    }

    /**
     * Set a block on a location to not glow
     * @param location Location to not glow
     */
    public void unsetBlockGlow(Location location) {
        if (!glowingBlocks.containsKey(location)) return;
        int id = glowingBlocks.get(location);
        IntList list = new IntArrayList();
        list.add(id);
        PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroy.getModifier().write(0, list);
        manager.sendServerPacket(reciever, destroy);

        PacketContainer teamPacket = manager.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        teamPacket.getStrings().write(0, reciever.getEntityId() + "_" + id); // Team name
        teamPacket.getIntegers().write(0, 1); // Remove team
        manager.sendServerPacket(reciever, teamPacket);

        glowingBlocks.remove(location);
    }

    /**
     * Make an entity glow for a duration
     * @param entity Entity to be glown
     * @param color Glow color
     * @param duration Duration in ticks
     */
    public void setEntityGlowDuration(Entity entity, ChatColor color, int duration) {
        setEntityGlow(entity, color);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> unsetEntityGlow(entity), duration);
    }
    /**
     * Make a block on a location glow for a duration
     * @param location Location to be glown
     * @param color Glow color
     * @param duration Duration in ticks
     */
    public void setBlockGlowDuration(Location location, ChatColor color, int duration) {
        setBlockGlow(location, color);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> unsetBlockGlow(location), duration);
    }
}
