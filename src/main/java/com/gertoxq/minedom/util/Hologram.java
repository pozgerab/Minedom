package com.gertoxq.minedom.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Hologram {

    ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    /**
     * Counts entities, used to generate unique ids.
     */
    private static int counter = Integer.MIN_VALUE;

    /**
     * Player to receive the packets.
     */
    private final Player reciever;

    public Hologram(Player reciever) {
        this.reciever = reciever;
    }
    /**
     * Increments the counter by one.
     * @return The pre-incremented counter value.
     */
    private int getCounter() {
        return counter++;
    }

    public int spawn(Location location, String msg) {

        UUID standID = UUID.randomUUID();
        int id = getCounter();

        PacketContainer spawnedStand = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
        spawnedStand.getModifier()
                .write(0, id)
                .write(2, EntityTypes.d);
        spawnedStand.getUUIDs().write(0, standID);
        spawnedStand.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        manager.sendServerPacket(reciever, spawnedStand);

        PacketContainer invStand = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        final List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
        wrappedDataValueList.add(new WrappedDataValue(
                0,
                WrappedDataWatcher.Registry.get(Byte.class),
                (byte) (0x20)
        ));
        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(msg)[0].getHandle());
        wrappedDataValueList.add(new WrappedDataValue(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true), opt));
        wrappedDataValueList.add(new WrappedDataValue(3, WrappedDataWatcher.Registry.get(Boolean.class), true));
        invStand.getDataValueCollectionModifier().write(0, wrappedDataValueList);
        invStand.getModifier().write(0, id);
        manager.sendServerPacket(reciever, invStand);
        return id;
    }

    public void destroy(int id) {
        IntList list = new IntArrayList();
        list.add(id);
        PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroy.getModifier().write(0, list);
        manager.sendServerPacket(reciever, destroy);
    }
}
