---
navigation:
  title: Translocator
  icon: infinity_nexus_mod:translocator
  position: 164
categories:
  - machines
item_ids:
  - infinity_nexus_mod:translocator
  - infinity_nexus_mod:translocator_link
---

# Translocator

## Info
- The Translocator is a tool that can be used to transfer items between two points.
- **Step 1:** Insert a Pusher Upgrade <ItemImage id="infinity_nexus_core:pusher_upgrade" /> into the Translocator using right-click to configure it to send items.
- **Step 2:** Create a translocator link <ItemImage id="infinity_nexus_mod:translocator_link" /> and use it on a translocator while holding **Shift** to save the destination coordinates. Then use it in the sender Translocator without using the **Shift** key to paste the coordinates.
- To change selection mod of Translocator Link you need click on ground holding **Shift** with the item in your hand. Selected Translators up to 9.

## Filter
- You can filter the items that are sent and received by the Translocator simply by clicking on them with the item in your hand.
- To remove a filter, simply click on them while holding the **Shift** key with no item in your hand.
- **This filter type doesn't work with tags/data.**


<RecipeFor id="infinity_nexus_mod:translocator" />
<RecipeFor id="infinity_nexus_mod:translocator_link" />
<RecipeFor id="infinity_nexus_core:pusher_upgrade" />

## Structure Visualization

- The default maximum distance for sending items between Translocators is **100 blocks**.
- The sending process takes approximately **20 ticks** by default. This applies both to extracting the item from the connected inventory and sending it to another Translocator—these processes are separate.
- When Translocator receives an item from another Translocator the sending process is slowed down to 5 ticks by default.

<GameScene zoom="2" interactive={true}>
  <ImportStructure src="structures/translocator_setup.nbt" />
  <IsometricCamera  yaw="30" pitch="30" />
</GameScene>
