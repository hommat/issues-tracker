package com.mateuszziomek.cqrs.event.store;

import com.mateuszziomek.cqrs.domain.AggregateConcurrencyException;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.domain.AggregateId;
import com.mateuszziomek.cqrs.event.EventModel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class EventStore<T extends AggregateRoot> {
    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;
    private final Class<T> clazz;

    /**
     * @throws AggregateConcurrencyException if latest version of aggregate is different from excepted
     */
    public void saveEvents(AggregateId aggregateId, Iterable<BaseEvent> events, int exceptedVersion) {
        var persistedEvents = eventStoreRepository.findByAggregateId(aggregateId);

        if (exceptedVersion != -1 && persistedEvents.get(persistedEvents.size() - 1).version() != exceptedVersion) {
            throw new AggregateConcurrencyException(aggregateId);
        }

        var version = exceptedVersion;
        for (var event: events) {
            version += 1;
            event.setVersion(version);
            var eventModel = new EventModel(
                    UUID.randomUUID(),
                    new Date(),
                    aggregateId,
                    clazz.getTypeName(),
                    version,
                    event.getClass().getSimpleName(),
                    event
            );

            eventStoreRepository.save(eventModel);
            eventProducer.produce(event.getClass().getSimpleName(), event);
        }
    }

    public List<BaseEvent> getEvents(AggregateId aggregateId) {
        var events = eventStoreRepository.findByAggregateId(aggregateId);

        return events
                .stream()
                .map(EventModel::eventData)
                .toList();
    }
}