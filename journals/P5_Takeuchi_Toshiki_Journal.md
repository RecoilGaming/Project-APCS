# Development Journal
P5 Toshiki Takeuchi

## 05/22/25 ~30 min
Cloned the repo to see what changed in the repo while I was busy. I ran the current game and realized there were some issues with rendering. I fixed the bug (7f28c69528c67642076e7380cb12ccf696b21cbf), so I can make myself familiar with the latest version of engine. Hopefully, I can contribute as much as I used to.

## 5/25/25 ~180 min
I did a rework on physics engine (3a37093ee56de7de4f22bdb628ddbb0c1f8b8715) so the node detecting the collision can access what is colliding on them. This would be useful for cases like bullet changing character's health etc.

## 5/27/25 ~180 min
I made a Transform test since I have encountered many issues stemming from it and wanted to get it working for sure. Sharvil found there was an affine transform class in java, so I made the transform compatible with java's affine transform to integrate it to the rendering pipeline. I also fixed some issues around rendering, so our engine can now render more accurately scaled image correctly.

## 5/28/25 ~10 min
I decided to use disunity transform as a wrapper of AffineTransform, so I replaced AffineTransform in Sprite.java to Transform.

## 5/30/25 ~15 min
I made the global location cached inside node 2D, since only tracking local transform created a lot of hassle when we needed global transform just for one time. Now, collision code can depend on global transform and physics engine does not have to calculate global transform for every tick.

## 6/2/25 >5 min
I made the collision layer an enum since there is only 32 of them, and it doesn't make sence to create multiple instances that represent the same layer. This has an additional bonus of allowing layers to be imported statically, so the future collision code will be less cumbersome to write.