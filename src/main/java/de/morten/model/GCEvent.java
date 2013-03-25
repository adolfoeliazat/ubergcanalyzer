package de.morten.model;

/**
 * Represents one minor or major garbage collection event of the JVM.
 * @author Christian Bannes
 */
public interface GCEvent {
	/**
	 * Indicates if the garbage collector uses stop-the-world, i.e. 
	 * if all application threads are halted or if it runs in parallel
	 * with the application threads.
	 * 
	 * @return true, if the GC uses stop-the-world, else false
	 */
	public boolean isStopTheWorld();

	/**
	 * Timing statistics, e.g. how long did this gc event take, etc.
	 * @return the statistics
	 */
	public GCTimeStats getTimeStats();
}
