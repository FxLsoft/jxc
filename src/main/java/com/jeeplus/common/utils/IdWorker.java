package com.jeeplus.common.utils;

public class IdWorker {
	/**
	 * workerID must be unique for each node in cluster.
	 */
	private long workerID;

	private long sequence = 0;
	private long mark = 0;

	private static final int MAXSEQUENCE = 128;
	private static final int MAXWORKERID = 128;

	private long lastTimeGen;

	private final int timeStampBitWidth = 40;
	private final int sequenceBitWidth = 7;

	private long sequenceLeftShift = 0;
	private long timeStampLeftShift = sequenceBitWidth;
	private long workerIDLeftShift = sequenceBitWidth + timeStampBitWidth;
	
	private static IdWorker worker;
	/**
	 * MillSeconds from January 1, 1970 to November 3rd 2010
	 */
	private final long TIMEBASELINE = 1288834974657L;

	public long nextId() {
		synchronized (this) {
			long timeGen = timeGen();
			mark++;
			sequence = (++sequence) % MAXSEQUENCE;
			if (timeGen == lastTimeGen) {
				if (mark >= MAXSEQUENCE) {
					timeGen = tillNextMill();
					mark = 0;
				}
			} else {
				mark = 0;
			}
			lastTimeGen = timeGen;

			return sequence << sequenceLeftShift | (timeGen - TIMEBASELINE) << timeStampLeftShift
					| workerID << workerIDLeftShift;
		}

	}

	public void setWorkerID(long id) {
		if (validateWorkerID(id)) {
			workerID = id;
		} else {
			throw new IllegalStateException("invalid id:" + id);
		}
	}

	private boolean validateWorkerID(long id) {
		if (id > MAXWORKERID || id < 0) {
			return false;
		} else {
			return true;
		}
	}

	private long tillNextMill() {
		long timeGen = timeGen();
		while (timeGen <= lastTimeGen) {
			timeGen = timeGen();
		}
		// System.out.println("i'm in tillNextMill");
		return timeGen;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static Long getId() {
		if (worker == null) {
			worker = new IdWorker();
			worker.setWorkerID(1);
		}
		return worker.nextId();
	}
}
