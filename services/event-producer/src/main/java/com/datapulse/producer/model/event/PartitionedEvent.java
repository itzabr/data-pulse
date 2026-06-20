package com.datapulse.producer.model.event;

public class PartitionedEvent {

    private final BaseEvent event;
    private final int partition;
    private final long offset;

    public PartitionedEvent(
            BaseEvent event,
            int partition,
            long offset) {

        this.event = event;
        this.partition = partition;
        this.offset = offset;
    }

    public BaseEvent getEvent() {
        return event;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }
}
