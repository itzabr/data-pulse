package com.datapulse.producer.offset;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PartitionOffsetManager {

    private final Map<Integer, AtomicLong> offsets =
            new ConcurrentHashMap<>();

    public PartitionOffsetManager() {

        for (int i = 0; i < 4; i++) {

            offsets.put(
                    i,
                    new AtomicLong(0)
            );
        }
    }

    public long nextOffset(int partition) {

        return offsets
                .get(partition)
                .getAndIncrement();

    }

    public long currentOffset(int partition) {

        return offsets
                .get(partition)
                .get();

    }
}
