package eventhub.producer;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.io.FileReader;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class AvroProducer {

    public static final String USER_SCHEMA = "{"
            + "\"type\":\"record\","
            + "\"name\":\"distributionrecord\","
            + "\"fields\":["
            + "  { \"name\":\"kvkdescription\", \"type\":\"string\" },"
            + "  { \"name\":\"kvkname\", \"type\":\"string\" },"
            + "  { \"name\":\"kvkid\", \"type\":\"int\" }"
            + "]}";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new FileReader("src/main/resources/producer.config"));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(USER_SCHEMA);
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 1000; i++) {
            GenericData.Record avroRecord = new GenericData.Record(schema);
            avroRecord.put("kvkdescription", "Str 1-" + i);
            avroRecord.put("kvkname", "Str 2-" + i);
            avroRecord.put("kvkid", i);

            byte[] bytes = recordInjection.apply(avroRecord);

            ProducerRecord<String, byte[]> record = new ProducerRecord<>("rabo-ota-distribution-customerlogin", bytes);
            producer.send(record);
            System.out.println(record);
            Thread.sleep(250);

        }

        producer.close();
    }
}