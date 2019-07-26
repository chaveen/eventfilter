package org.devchavez.eventfilter.event.op;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.devchavez.eventfilter.op.OutputTarget;

/**
 * This encapsulates a Event target to a File
 *
 */
public class EventTarget implements OutputTarget<byte[]> {

	private File file;
	private FileOutputStream outputSteam;
	private boolean hasWritten;

	public EventTarget(File file) {
		this.file = file;
	}
	
	public String getFileName() {
		return this.file.getName();
	}
	
	@Override
	public void write(byte[] input) {
		try {
			if (this.outputSteam == null) {
				this.outputSteam = new FileOutputStream(file);
			}
			
			if (!this.hasWritten) {
				this.hasWritten = true;
			}
			
			this.outputSteam.write(input);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public void close() {
		if (this.outputSteam != null) {
			try {
				this.outputSteam.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	@Override
	public boolean hasWritten() {
		return this.hasWritten;
	}
}
