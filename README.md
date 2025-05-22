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

**Controls**:

WASD / Arrows: Directional movement

Mouse: Aiming for abilities

Left Click: Basic attack

Q / E / R: Use abilities

Shift: Dodge ability

H: Heal ability

**Aura**: The main unique mechanic in our game is that rather than consuming mana, abilities consume a resource called "aura", which can only be collected by performing certain actions. For instance, successfully dodging attacks, landing certain abilities, and killing minions all increase the player's aura amount.

**Dodge**: The player's dodge ability has a 30 second cooldown, but allows the player to completely mitigate any source of damage. This ability is available by default and cannot be leveled up.

**Heal**: The player's heal ability also has a 30 second cooldown and allows the player to regain 10% of their health. This ability is available by default and cannot be leveled up.

**Leveling**: Characters start the game at level 1, and can reach up to a maximum level of 9. At each level, characters gain a minor stat boost, as well as a skill point that can be used to unlock and upgrade abilities.

**Characters**: Before starting any stage, players must select a character to use. This allows for a wide variety of playstyles built around different characters and / or team compositions.

## ELEMENTS

**Characters**: ~ we have ideas but none of them are finalized

**Boss Enemies**: ~ same as above

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