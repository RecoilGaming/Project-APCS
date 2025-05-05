# Project-APCS

## TO RUN THIS: run build.sh or ljaag-1.0.0-jar-with-dependencies.jar

## Disunity Nodes

**Node** is the base node and has children.

**Node** is a Node that is not visible in the game. They can only have other Nodes as children.

**Camera** is a Node that controls the viewport.

**Controller** is an Node that controls a body node.

**MoveAction** is an Node that performs a movement action.

**Node** is a Node that is visible in the game.

**Node2D** is a Node with a transform. It renders its children relative to its own transform.

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