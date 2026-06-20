package com.datapulse.producer.dlq;

import com.datapulse.producer.model.event.PartitionedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class DeadLetterQueue {

    private final BlockingQueue<PartitionedEvent> queue =
            new LinkedBlockingQueue<>();

    public void send(
            PartitionedEvent event)
            throws InterruptedException {

        queue.put(event);
    }

    public PartitionedEvent receive()
            throws InterruptedException {

        return queue.take();
    }
}
