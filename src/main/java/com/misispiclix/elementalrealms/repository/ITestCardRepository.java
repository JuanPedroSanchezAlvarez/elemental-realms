package com.misispiclix.elementalrealms.repository;

import com.misispiclix.elementalrealms.domain.card.TestCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ITestCardRepository {

    Flux<TestCard> findAll();
    Mono<TestCard> findById(UUID id);

}
