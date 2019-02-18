// package eventhub.consumer;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import io.axual.beb.client.BebClient;
// import io.axual.beb.client.BebClientConfig;
// import io.axual.beb.client.general.config.SpecificConsumerConfig;
// import io.axual.beb.client.general.consumer.Consumer;
// import io.axual.beb.client.general.consumer.ConsumerStrategy;
// import io.axual.beb.client.general.consumer.Processor;

// public class BEBConsumer {
//     private static final Logger LOG = LoggerFactory.getLogger(BEBConsumer.class);

//     public static void main(String[] args) {
//         // BebClient initialization
//         BebClientConfig clientConfig = BebClientConfig.builder()
//                 .setConfigurationProviderUrl("distributioneh.servicebus.windows.net:9093")
//                 .setApplicationId("io.axual.test")
//                 .setApplicationVersion("3.0.0-SNAPSHOT")
//                 .setEnvironmentSuffix("")
//                 //.setSslKeyPassword("notsecret")
//                 //.setSslKeystoreLocation("beb.client.keystore.jks")
//                 //.setSslKeystorePassword("notsecret")
//                 //.setSslTruststoreLocation("beb.client.truststore.jks")
//                 //.setSslTruststorePassword("notsecret")
//                 .setSslKeyPassword("Endpoint=sb://distributioneh.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=Cf5HUD43dQ4vcaF5GbJsZih+epbxCJKiOTYrKZ0cq0E=")
//                 .build();

//         try (final BebClient bebClient = new BebClient(clientConfig);) {

//             SpecificConsumerConfig specificConsumerConfig = SpecificConsumerConfig.builder()
//                     .setConsumerStrategy(ConsumerStrategy.AT_LEAST_ONCE)
//                     .setTopic("distribution")
//                     .build();

//             // Create a processor
//             Processor<ApplicationInstance, ApplicationLogEvent> applicationLogEventProcessor = new ApplicationLogEventProcessor();
//             try (Consumer consumer = bebClient.buildSpecificConsumer(specificConsumerConfig, applicationLogEventProcessor)) {
//                 consumer.startConsuming();
//                 while (consumer.isConsuming()) {
//                     LOG.info("Still consuming...");
//                     Thread.sleep(1000);
//                 }
//             } catch (Exception e) {
//                 LOG.error("Exception when starting to consume", e);
//             }
//         }
//     }
// }

