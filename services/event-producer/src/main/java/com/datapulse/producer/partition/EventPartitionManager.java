package com.datapulse.producer.partition;

import com.datapulse.producer.model.event.BaseEvent;
import com.datapulse.producer.model.event.PartitionedEvent;
import com.datapulse.producer.offset.PartitionOffsetManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class EventPartitionManager {

    private static final int PARTITION_COUNT = 4;

    private final List<BlockingQueue<PartitionedEvent>> partitions =
            new ArrayList<>();
            
    private final PartitionOffsetManager partitionOffsetManager;

    public EventPartitionManager(
            PartitionOffsetManager partitionOffsetManager) {
        
        this.partitionOffsetManager = partitionOffsetManager;

        for (int i = 0; i < PARTITION_COUNT; i++) {

            partitions.add(
                    new LinkedBlockingQueue<>()
            );
        }
    }

    public void publish(BaseEvent event)
            throws InterruptedException {

        int partition =
                Math.abs(
                        event.getCustomerId()
                                .hashCode()
                ) % PARTITION_COUNT;
                
        long offset =
                partitionOffsetManager
                        .nextOffset(partition);

        PartitionedEvent partitionedEvent =
                new PartitionedEvent(
                        event,
                        partition,
                        offset
                );

        partitions
                .get(partition)
                .put(partitionedEvent);
    }

    public PartitionedEvent consume(int partition)
            throws InterruptedException {

        return partitions
                .get(partition)
                .take();
    }
}
