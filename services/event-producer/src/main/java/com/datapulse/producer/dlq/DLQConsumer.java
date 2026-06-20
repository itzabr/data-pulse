package com.datapulse.producer.dlq;

import com.datapulse.producer.model.event.PartitionedEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DLQConsumer {

    private final DeadLetterQueue deadLetterQueue;

    public DLQConsumer(
            DeadLetterQueue deadLetterQueue) {

        this.deadLetterQueue =
                deadLetterQueue;
    }

    @PostConstruct
    public void start() {

        Thread thread =
                new Thread(() -> {

                    while (true) {

                        try {

                            PartitionedEvent event =
                                    deadLetterQueue
                                            .receive();

                            System.out.println(
                                    "DLQ received offset "
                                            + event.getOffset());

                        }
                        catch (Exception e) {

                            e.printStackTrace();

                        }
                    }
                });

        thread.start();
    }
}
