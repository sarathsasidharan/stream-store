package eventhub.consumer;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.axual.beb.client.BebClient;
import io.axual.beb.client.BebClientConfig;
import io.axual.beb.client.general.config.GenericConsumerConfig;
import io.axual.beb.client.general.consumer.Consumer;
import io.axual.beb.client.general.consumer.ConsumerStrategy;
import io.axual.beb.client.general.consumer.Processor;

public class BEBConsumerGeneric {
    private static final Logger LOG = LoggerFactory.getLogger(BEBConsumerGeneric.class);

    public static void main(String[] args) {
        // BebClient initialization
        BebClientConfig clientConfig = BebClientConfig.builder()
                .setConfigurationProviderUrl("https://distributioneh.servicebus.windows.net:9093")
               // .set
                .setApplicationId("io.axual.test")
                .setApplicationVersion("3.0.0-SNAPSHOT")
                .setEnvironmentSuffix("")
               // .setSslKeyPassword("notsecret")
                .setSslKeystoreLocation("C:\\Users\\sasasid\\Documents\\rabo-poc\\stream-store\\src\\main\\java\\beb.client.keystore.jks")
                .setSslKeystorePassword("notsecret")
                .setSslTruststoreLocation("C:\\Users\\sasasid\\Documents\\rabo-poc\\stream-store\\src\\main\\java\\beb.client.truststore.jks")
                .setSslKeyPassword("Endpoint=sb://distributioneh.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=Cf5HUD43dQ4vcaF5GbJsZih+epbxCJKiOTYrKZ0cq0E=")
                .setSslTruststorePassword("notsecret")
                .build();

        try (final BebClient bebClient = new BebClient(clientConfig);) {

            GenericConsumerConfig genericConsumerConfig = GenericConsumerConfig.builder()
                    .setConsumerStrategy(ConsumerStrategy.AT_LEAST_ONCE)
                    .setTopic("general-applicationlog")
                    .build();

            // Create a processor
            Processor<GenericRecord, GenericRecord> genericRecordProcessor = new GenericLoggingProcessor(bebClient.getJsonConverter());
            try (Consumer consumer = bebClient.buildGenericConsumer(genericConsumerConfig, genericRecordProcessor)) {
                consumer.startConsuming();
                while (consumer.isConsuming()) {
                    LOG.info("Still consuming...");
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                LOG.error("Exception when starting to consume", e);
            }
        }
    }
}
