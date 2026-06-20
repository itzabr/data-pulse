package com.datapulse.producer.ack;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AcknowledgementManager {

    private final Map<Integer, AtomicLong> acknowledgements =
            new ConcurrentHashMap<>();

    public AcknowledgementManager() {

        for (int i = 0; i < 4; i++) {

            acknowledgements.put(
                    i,
                    new AtomicLong(-1)
            );
        }
    }

    public void acknowledge(
            int partition,
            long offset) {

        acknowledgements
                .get(partition)
                .set(offset);

    }

    public long getAcknowledgedOffset(
            int partition) {

        return acknowledgements
                .get(partition)
                .get();

    }
}
