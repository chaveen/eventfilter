package org.devchavez.eventfilter.event.op;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.devchavez.eventfilter.op.InputSource;

/**
 * This encapsulates a Event source from a File
 *
 */
public class EventSource implements InputSource<InputStream> {

	private final File file;
	private FileInputStream inputStream;

	public EventSource(File file) {
		this.file = file;
	}
	
	public String getFileName() {
		return this.file.getName();
	}
	
	@Override
	public InputStream getData() {
		if (this.inputStream == null) {
			try {
				this.inputStream = new FileInputStream(this.file);
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}
		
		return this.inputStream;
	}

	@Override
	public void close() {
		if (this.inputStream != null) {
			try {
				this.inputStream.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
