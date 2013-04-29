package dodgeCity;

import java.util.SortedSet;

/**
 * A thread for moving the cubes.
 * @author David
 */
public class CubeThread implements Runnable{

	SortedSet<Cube> myCubes;		// List of cubes to move
	int counter;					// A counter for thread cycles
	boolean running;
	int maxCubes = 512;				// Maximum possible cubes
	double cubeSize = 18;			// Default cube scale value
	double speed = .9;				// Beginning rate of cubes falling
	double maxSpeed = 5;
	double acceleration = 1.01;		// How fast the speed increases
	double spawnRate = 64;			// Rate to spawn new cubes (the higher the number, the less often they spawn)
	double spawnAcceleration = 0.05;	// Rate to accelerate spawning new cubes
	double minSpawnRate = 16;			// Minimum spawn rate
	long delay = 2;						// How long to wait between cube calculations
	double eye;
	Player player;
	DodgePanel myPanel;				// The DodgePanel that started this thread
	
	public CubeThread(DodgePanel _myPanel, SortedSet<Cube> _myCubes, Player _player, double _eye){
		myPanel = _myPanel;
		eye = _eye;
		myCubes = _myCubes;			// Get list of cubes
		counter = 0;				// Initialize counter
		player = _player;			// The player
		running = true;
	}
	
	@Override
	public void run() {
		while (running){				// Loop constantly
			try {
				Thread.sleep(delay);	// Wait delay  ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter--;				// Increment counter
			if (counter < 0){	// Spawn a new cube if counter is high enough
				if (speed < maxSpeed)
					speed *= acceleration;	// Increase speed if less than max speed
				if (spawnRate > minSpawnRate)
					spawnRate -= spawnAcceleration;			// Increase spawn rate slowly
				// Add a new cube in a random location with a random scale:
				double x = (Math.random() * DodgeCity.ROOM_SIZE_X)-(DodgeCity.ROOM_SIZE_X/2.0);
				double y = DodgeCity.ROOM_SIZE_Y;
				double z = (Math.random() * DodgeCity.ROOM_SIZE_Z) -(DodgeCity.ROOM_SIZE_Z/2.0);
				double scale = cubeSize + (Math.random()*cubeSize);
				
				synchronized(myCubes){	// Prevent concurrent access to myCubes
					Cube newCube = new Cube(x, y, z, scale, eye);// Create new cube
					myCubes.add(newCube);						// Add new cube
					if(myCubes.size() > maxCubes)
						myCubes.remove(myCubes.first());		// Remove farthest cube if there are too many
				}
				counter = (int)spawnRate;		// Reset counter
			}
			synchronized(myCubes){	// Prevent concurrent access to myCubes
				for (Cube c : myCubes){
					c.rotate(.01, 0, 0);
					c.translate(0, -speed, 0);
					
					// Calculate distance of center from player:
					double distance=0;
					for(int i=0; i<c.center.length; i++){
						distance += Math.pow((c.center[i] - player.center[i]),2);	// Square each component
					}
					distance = Math.sqrt(distance);	// Take square root of sum of squares
					if(distance < (c.scale*2 + player.scale)){
						running = false;
						myPanel.lose();
					}
				}
			}
		}
	}

	public void restart() {
		run();
	}
}
