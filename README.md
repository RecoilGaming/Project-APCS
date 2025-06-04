# Project-APCS

## TEAM

**Period**: Ferrante 5

**Members**: Qinzhao Li, Sharvil Phadke, Toshiki Takeuchi

## GENERAL

**Title**: Heaven's Lament

**Genre**: Co-op Dungeon Crawler

**Camera**: Top down w/ pixel art

**Proposal**: Our game idea is to have a co-op dungeon crawler / boss rush game where the player chooses between multiple characters and cooperates with their friends to complete the objetive of defeating each stage's boss. They start the game in the boss room with a character at level 1, and can explore the dungeon to raise their level in order to gain abilities that will help them defeat the boss.

## MECHANICS

**General Concept**

Our general idea was to have a topdown dungeon crawler as explained below. We had to heavily downscope which unfortuately had us remove multiplayer components, but the idea of exploring stages and battling enemies still exists. The general idea for progression is simple: each level introduces a new mechanic or tests your skills and how well you were able to learn the abilities' functionalities and usecases. Kill all the enemies and enter the portal to reach the next stage.

**Terrian**

Lava -> hurts you if you step on it. Also damages enemies, but not as much

Portal -> a portal to the next level. It unlocks when you've killed all the enemies

**Enemies**

Demon -> basic melee enemy that follows you around

Wyrm -> a challenging, segmented enemy inspired by Terraria's Eater of Worlds

Spawner -> an enemy that spawns other enemies

**Controls**:

WASD / Arrows: Directional movement

Mouse: Aiming for abilities

Left Click -> Shotgun: basic attack with burst fire and mediocre piercing

Q -> Portal: Teleport yourself, projectiles, and enemies between portals, just like in Portal! Combining both portals in the same location allows you to lock enemies in place for prolonged periods of time.

E -> Push: Push enemies away from you. Be careful, it has a 3 second cooldown

F -> Sword Smash: call down a sword of light from the sky to stun enemies

R -> Restart the the current stage

## NOTES

<!-- 

# Engine Notes

## Disunity Nodes

**Node** is the base node and has children.

**UndrawnNode** is a Node that is not visible in the game. They can only have other UndrawnNodes as children.

**Camera** is a UndrawnNode that controls the viewport.

**Controller** is an UndrawnNode that controls a body node.

**MoveAction** is an UndrawnNode that performs a movement action.

**DrawnNode** is a Node that is visible in the game.

**Node2D** is a DrawnNode with a transform. It renders its children relative to its own transform.

**Sprite** is a Node2D that renders an image or animation.

**Body** is a Node2D that handles movement and collision.

## Game Nodes

**PlayerController** is a Controller that is controlled by player inputs.

**WalkAction** is a MoveAction that allows directional movement.

# Networking
To synchronize an object between server/client, register
it to a SyncHandler with `SyncHandler::register(Object)`.
## Codec
codec is predefined for primitives and objects with
annotated fields. If you want to sync classes you can
not modify, use `SyncableWrapper<T>`. If you want to
use a different codec for classes you can modify,
implement `SelfCodec<T>`.
### Predefined Codec
By annotating a field in a class with annotations in
`disunity.annotations.syncedfield`, you can specify
which fields should be included in the packet. 
### Defining Codec
by implementing `SelfCodec<T>`, you can override the
codec being used. There are primitive codecs available
in `CODEC` enum. Codec implementations are expected
to append and consume equal amounts of bytes after
encoding decoding.
`<T> T decode(T, InputStream)` is expected to return a
decoded value, which can also be the first argument
with mutated fields.
-->