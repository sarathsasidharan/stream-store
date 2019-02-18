//Copyright (c) Microsoft Corporation. All rights reserved.
//Licensed under the MIT License.

package eventhub.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TestDataReporter implements Runnable {

    private static final int NUM_MESSAGES = 1000;
    private final String TOPIC;

    private Producer<Long, String> producer;

    public TestDataReporter(final Producer<Long, String> producer, String TOPIC) {
        this.producer = producer;
        this.TOPIC = TOPIC;
    }

    @Override
    public void run() {
        for(int i = 0; i < NUM_MESSAGES; i++) {
            String msg="";                
            long time = System.currentTimeMillis();
            System.out.println("Test Data #" + i + " from thread #" + Thread.currentThread().getId());
            //Create Xml to send for Distribution 
            CreateXml xml = new CreateXml();
            msg=xml.createXml(i);
            final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(TOPIC, time, msg);
            System.out.println(msg);
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println(exception);
                        System.exit(1);
                    }
                }
            });
        }
        System.out.println("Finished sending " + NUM_MESSAGES + " messages from thread #" + Thread.currentThread().getId() + "!");
        System.exit(0);
    }
}