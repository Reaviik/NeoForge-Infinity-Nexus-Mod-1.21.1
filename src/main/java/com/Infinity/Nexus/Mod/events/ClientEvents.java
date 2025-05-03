package com.Infinity.Nexus.Mod.events;

//@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {
    /*
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) return;

        if (event.getKey() == KeyBindings.FLIGHT_KEY.getKey().getValue() && event.getAction() == 1) {
            if (hasFullSuitOfArmorOn(player)) {
                //FlightManager.handleFlightActivation(player);
            }
        }
    }

    public static boolean hasFullSuitOfArmorOn(Player player) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item breastplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();

        boolean infinity = boots == ModItemsAdditions.INFINITY_BOOTS.get()
                    && leggings == ModItemsAdditions.INFINITY_LEGGINGS.get()
                    && breastplate == ModItemsAdditions.INFINITY_CHESTPLATE.get()
                    && helmet == ModItemsAdditions.INFINITY_HELMET.get();
        boolean imperial = boots == ModItemsAdditions.IMPERIAL_INFINITY_BOOTS.get()
                    && leggings == ModItemsAdditions.IMPERIAL_INFINITY_LEGGINGS.get()
                    && breastplate == ModItemsAdditions.IMPERIAL_INFINITY_CHESTPLATE.get()
                    && helmet == ModItemsAdditions.IMPERIAL_INFINITY_HELMET.get();

        return imperial || infinity;
    }
     */
}