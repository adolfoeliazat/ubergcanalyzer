package de.morten.model.gc;

/**
 * Statistics of a generational memory (younggen, oldgen or permgen) before and after a gc.
 * 
 * @author Christian Bannes
 */
public class GCMemStats {
	/** the occupancy of a generational memory (younggen, oldgen or permgen) before gc */
	private final int occupancyBeforeGc;
	/** the occupancy of a generational memory (younggen, oldgen or permgen) after gc */
	private final int occupancyAfterGc;
	/** not the occupancy but the total size of the generational memory (youngge, oldgen or permgen) */
	private final int totalSize;
	
	/**
	 * The occupancy and total size of a generational memory (younggen, oldgen or permgen). The
	 * values must all belong to the same generational memory.
	 */
	public GCMemStats(final int occupancyBeforeGc, final int occupancyAfterGc, final int totalSize) {
		this.occupancyAfterGc = occupancyAfterGc;
		this.occupancyBeforeGc = occupancyBeforeGc;
		this.totalSize = totalSize;
	}

	public int occupancyBeforeGc() {
		return this.occupancyBeforeGc;
	}

	public int occupancyAfterGc() {
		return this.occupancyAfterGc;
	}

	public int totalSize() {
		return this.totalSize;
	}
	
	
}
