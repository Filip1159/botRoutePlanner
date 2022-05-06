# Bot Route Planner
### About
Problem: with finding the shortest path inside Cartesian system <br>
Solution: the <b>floodfill</b> algorithm <br>
### How it works:
Floodfill algorithm is widely used by robotic enthusiasts during competition called micromouse.
Judging by its meaningful name it tries to pretend like a water filing free spaces.
We need a dwo-dimensional array that keeps timestamps, when "water" or algorithm reached their point locations.
Starting from the current bot location, we go further - as long as our destination hasn't been reached yet.
### Modifications
First, in this task we meet slower cells - just like in real life water meets obstacles.
Having this in mind algorithm prevents "flooding" neighbor cells immediately, but it checks first if transition time between two cells elapsed.
Second, when bot is given a task to find product he does not know where to go.
Required modification assumes, that everytime algorithm - or "water" - reaches new cell, it checks whether there is wanted product underneath.
If so, location is saved, and the process of picking it up starts.
Algorithm ends when the first product got picked.
### Reproducing path
After our Cartesian system is filled, we need to find our way back.
Past step - or more precisely neighbor that flooded currently considered point - is being investigated by comparing transition costs and differences between two adjacent cells.
### Features
- reliability - path can be found, no matter what is the shape of out of service cells<br>
- error checking - algorithm can recognize getting stuck - means that out of service cells blocked its way to direction<br>
- file input validation - malformed files are rejected