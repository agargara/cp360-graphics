package dodgeCity;

import java.awt.Color;

/**
 * A player represented by a cube.
 * @author David
 *
 */
public class Player extends Cube implements Runnable{
	
	boolean switchX, switchY;		// Booleans for moving and switching directions
	Object lock;
	double xDir, zDir;				// Direction of movement
	double xSpeed, zSpeed;			// Speed of movement
	double slowDown = 1.08;			// How much to slow down when stopping
	double acceleration = 1.1;		// How much to accelerate / decelerate
	double minSpeed = 8;			// Minimum speed of movement
	double minStopSpeed=.1;			// Minimum speed of stopping movement
	double maxSpeed = 16;			// Maximum speed
	long delay = 10;				// Delay between movements
	double spin = 0.03;				// Angle to spin on movement
	
	/**
	 * Creates a new player at a given location.
	 * @param _location the location to place the player
	 */
	public Player(double i, double j, double k, double scale, double eye){
		super(i, j, k, scale, eye);
		xDir = 0;
		zDir = 0;
		xSpeed = minSpeed;
		zSpeed = minSpeed;
		switchX = false;
		switchY = false;
		(new Thread(this)).start();
		super.changeColor(Color.PINK);
	}
	
	/**
	 * Starts moving the player in the given direction.
	 * @param xDir the x-direction
	 * @param zDir the z-direction
	 */
	public void move(double _xDir, double _zDir){
		if(_xDir!=0){
			xDir = _xDir;
			xSpeed = minSpeed;
		}
		if(_zDir!=0){
			zDir = _zDir;
			zSpeed = minSpeed;
		}
		synchronized(this){
			notifyAll();		// Wake the thread up if it is waiting
		}
	}

	/** Stops the player from moving in x direction */
	public void stopX(){
		xDir = 0;
	}
	
	/** Stops the player from moving in z direction */
	public void stopZ(){
		zDir = 0;
	}
	
	@Override
	public void run() {
		while(true){
			// When movement stops in a direction, gradually screech to a halt:
			if (xDir==0){
				if (Math.abs(xSpeed) > minStopSpeed)
					xSpeed /= slowDown;
				else
					xDir = 0;
			}
			if (zDir==0){
				if (Math.abs(zSpeed) > minStopSpeed)
					zSpeed /= slowDown;
				else
					zDir = 0;
			}
			
			// When moving, accelerate until maxSpeed.
			// Calculations for x:
			if (xDir != 0){	// Rotate when moving x
				super.rotate(spin*xDir, 0, 0);
			}
			if (xDir > 0){						// Heading right:
				if (xSpeed>0){					// Continuing right:
					if (xSpeed < maxSpeed)
						xSpeed *= acceleration;	// Accelerate
				}else{							// Switching to left:
					if (xSpeed < -minSpeed)
						xSpeed /= acceleration;	// Decelerate toward minSpeed
					else
						xSpeed = -xSpeed;		// Once minSpeed is reached, change directions
				}
			}else if (xDir < 0){				// Heading left:
				if (xSpeed<0){					// Continuing left:
					if (-xSpeed < maxSpeed)
						xSpeed *= acceleration;	// Accelerate
				}else{							// Switching to left:
					if (xSpeed > minSpeed)
						xSpeed /= acceleration;	// Decelerate toward minSpeed
					else
						xSpeed = -xSpeed;		// Once minSpeed is reached, change directions
				}
			}
			// Calculations for z:
			if (zDir > 0){						// Heading right:
				if (zSpeed>0){					// Continuing right:
					if (zSpeed < maxSpeed)
						zSpeed *= acceleration;	// Accelerate
				}else{							// Switching to left:
					if (zSpeed < -minSpeed)
						zSpeed /= acceleration;	// Decelerate toward minSpeed\
					else
						zSpeed = -zSpeed;		// Once minSpeed is reached, change directions
				}
			}else if (zDir < 0){					// Heading left:
				if (zSpeed<0){					// Continuing left:
					if (-zSpeed < maxSpeed)
						zSpeed *= acceleration;	// Accelerate
				}else{							// Switching to left:
					if (zSpeed > minSpeed)
						zSpeed /= acceleration;	// Decelerate toward minSpeed
					else
						zSpeed = -zSpeed;		// Once minSpeed is reached, change directions
				}
			}
			
			super.translate(xSpeed, 0, zSpeed);	// Move!
			checkBounds();
			try {
				Thread.sleep(delay);				// Pause between cycles
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkBounds() {
		synchronized(this){		// Pause player changes while calculating bounds
			double xMax = DodgeCity.ROOM_SIZE_X/2;
			double zMax = DodgeCity.ROOM_SIZE_Z/2;
			if ((super.center[0] > xMax) || (super.center[0] < -xMax))	// Out of x bounds:
				super.setX(-super.center[0]);
			if ((super.center[2] > zMax) || (super.center[2] < -zMax))	// Out of z bounds
				super.setZ(-super.center[2]);
		}
	}
}
