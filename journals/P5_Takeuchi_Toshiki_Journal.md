# Development Journal
P5 Toshiki Takeuchi

## 05/22/25 ~30 min
Cloned the repo to see what changed in the repo while I was busy. I ran the current game and realized there were some issues with rendering. I fixed the bug (7f28c69528c67642076e7380cb12ccf696b21cbf), so I can make myself familiar with the latest version of engine. Hopefully, I can contribute as much as I used to.

## 5/25/25 ~180 min
I did a rework on physics engine (3a37093ee56de7de4f22bdb628ddbb0c1f8b8715) so the node detecting the collision can access what is colliding on them. This would be useful for cases like bullet changing character's health etc.