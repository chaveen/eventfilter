package org.devchavez.eventfilter.op.writer;

import java.nio.charset.Charset;
import java.util.Iterator;

import org.devchavez.eventfilter.op.OutputTarget;
import org.devchavez.eventfilter.op.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * This is a factory class for serialize data into CSV data and write to the output target
 */
@Component("csvOutputStreamWriter")
public class CSVOutputStreamWriterBuilder<O extends OutputTarget<byte[]>> {

	private final CsvMapper mapper;

	@Autowired
	public CSVOutputStreamWriterBuilder(CsvMapper csvMapper) {
		this.mapper = csvMapper;
	}
	
	/**
	 * This returns a writer to serialize data of given class
	 */
	public <I> CSVOutputStreamWriter<I> forClass(Class<I> clazz) {
		return new CSVOutputStreamWriter<>(clazz);
	}
	
	/**
	 * This returns a writer to serialize data of given class in a specific CSV schema
	 */
	public <I> CSVOutputStreamWriter<I> forClass(Class<I> clazz, CsvSchema csvSchema) {
		return new CSVOutputStreamWriter<>(clazz, csvSchema);
	}
	
	/**
	 * This inner class is generates a XML writer for a particular source class instance
	 */
	public class CSVOutputStreamWriter<I> implements Writer<I, O> {
		private final ObjectWriter writer;
		private CsvSchema schema;

		private CSVOutputStreamWriter(Class<I> sourceClass) {
			this(sourceClass, CSVOutputStreamWriterBuilder.this.mapper
					.typedSchemaFor(sourceClass)
					.withoutHeader()
					.withColumnSeparator(','));
		}
		
		private CSVOutputStreamWriter(Class<I> sourceClass, CsvSchema csvSchema) {
			this.schema = csvSchema.withoutHeader();
			
			this.writer = CSVOutputStreamWriterBuilder.this.mapper
					.writer().forType(sourceClass).with(this.schema);
		}
		
		@Override
		public void accept(O target, I input) {
			byte[] recordBytes;

			try {
				// if it is the beginning of the writing process then write the header
				if (!target.hasWritten()) {
					this.writeHeader(target);
				}
				
				recordBytes = this.writer.writeValueAsBytes(input);
				
				target.write(recordBytes);
			} catch (JsonProcessingException e) {
				throw new IllegalStateException(e);
			}
		}
		
		/**
		 * This writes the header into the output target
		 */
		protected void writeHeader(O target) {
			Charset charset = Charset.defaultCharset();
			
			String columnSeperator = String.valueOf(this.schema.getColumnSeparator());
			
			Iterator<CsvSchema.Column> itr = this.schema.iterator();
			while (itr.hasNext()) {
				target.write(itr.next().getName().getBytes(charset ));
	
				if (itr.hasNext()) {
					target.write(columnSeperator.getBytes(charset));
				}
			}
			
			target.write(System.lineSeparator().getBytes(charset));
		}
	}
}
