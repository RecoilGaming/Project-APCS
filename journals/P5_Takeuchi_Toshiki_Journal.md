# Development Journal
P5 Toshiki Takeuchi

## 05/22/25 ~30 min
Cloned the repo to see what changed in the repo while I was busy. I ran the current game and realized there were some issues with rendering. I fixed the bug (7f28c69528c67642076e7380cb12ccf696b21cbf), so I can make myself familiar with the latest version of engine. Hopefully, I can contribute as much as I used to.

## 5/25/25 ~180 min
I did a rework on physics engine (3a37093ee56de7de4f22bdb628ddbb0c1f8b8715) so the node detecting the collision can access what is colliding on them. This would be useful for cases like bullet changing character's health etc.

## 5/27/25 ~180 min
I made a Transform test (1804ed2d07f87acd9518e5f8cde49940920ba2bf) since I have encountered many issues stemming from it and wanted to get it working for sure. Sharvil found there was an affine transform class in java, so I made the transform compatible with java's affine transform to make it easier to use in rendering (f5f4d9b13d57a7002ba5f81dc525ad13cd9a7ae0, f102e366f94fd41cc28ed6c9d12e60a26e2a601b). I fixed many issues around rendering (), so our engine can now render more accurately scaled image correctly.