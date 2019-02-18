package eventhub.consumer;
import io.axual.beb.client.converter.JsonConverter;
import io.axual.beb.client.general.consumer.Message;
import io.axual.beb.client.general.consumer.Processor;
import io.axual.beb.exception.SerializationException;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
* Processor to log the key and value at info level
*/
public class GenericLoggingProcessor implements Processor<GenericRecord, GenericRecord> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericLoggingProcessor.class);
    private static final String SEPARATOR = "=== %tF %tT =======================%n";
    private static final String ID_FORMAT = "ID: %s%n";
    private static final String KEY_FORMAT = "KEY: %s%n";
    private static final String VALUE_FORMAT = "VALUE: %s%n";
    private static final String METADATA_FORMAT = "SER: %tF %tT, DESER: %tF, %tT, delay: %d%n";
    private final JsonConverter jsonConverter;

    public GenericLoggingProcessor(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public void processMessage(Message<GenericRecord, GenericRecord> message) {
        long now = System.currentTimeMillis();
        String keyString;
        String valueString;

        try {
            keyString = jsonConverter.fromRecord(message.getKey());
        } catch (SerializationException e) {
            LOG.error("Error on serializing to JSON", e);
            keyString = message.getKey().toString();
        }

        try {
            valueString = jsonConverter.fromRecord(message.getValue());
        } catch (SerializationException e) {
            LOG.error("Error on serializing to JSON", e);
            valueString = message.getValue().toString();
        }

        System.out.println(String.format(SEPARATOR, now, now));
        System.out.println(String.format(ID_FORMAT, message.getId()));
        System.out.println(String.format(KEY_FORMAT, keyString));
        System.out.println(String.format(VALUE_FORMAT, valueString));
        System.out.println(String.format(METADATA_FORMAT,
                message.getSerializationTime(), message.getSerializationTime(),
                message.getDeserializationTime(), message.getDeserializationTime(),
                message.getDeserializationTime() - message.getSerializationTime()));
    }
}
