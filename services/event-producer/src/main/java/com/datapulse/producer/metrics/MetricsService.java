package com.datapulse.producer.metrics;

import com.datapulse.producer.ack.AcknowledgementManager;
import com.datapulse.producer.offset.PartitionOffsetManager;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final PartitionOffsetManager offsetManager;
    private final AcknowledgementManager acknowledgementManager;

    public MetricsService(
            PartitionOffsetManager offsetManager,
            AcknowledgementManager acknowledgementManager) {

        this.offsetManager = offsetManager;
        this.acknowledgementManager =
                acknowledgementManager;
    }

    public String metrics() {

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0; i < 4; i++) {

            long produced =
                    offsetManager.currentOffset(i);

            long consumed =
                    acknowledgementManager
                            .getAcknowledgedOffset(i);

            long lag =
                    produced - consumed - 1;

            builder.append(
                    "Partition ")
                    .append(i)
                    .append(
                            " Produced=")
                    .append(produced)
                    .append(
                            " Consumed=")
                    .append(consumed)
                    .append(
                            " Lag=")
                    .append(lag)
                    .append("\n");
        }

        return builder.toString();
    }
}
