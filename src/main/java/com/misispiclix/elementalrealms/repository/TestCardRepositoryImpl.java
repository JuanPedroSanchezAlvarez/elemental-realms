package com.misispiclix.elementalrealms.repository;

import com.misispiclix.elementalrealms.domain.card.TestCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class TestCardRepositoryImpl implements ITestCardRepository {

    TestCard card1 = TestCard.builder().name("Card1").build();
    TestCard card2 = TestCard.builder().name("Card2").build();
    TestCard card3 = TestCard.builder().name("Card3").build();

    @Override
    public Flux<TestCard> findAll() {
        return Flux.just(card1, card2, card3);
    }

    @Override
    public Mono<TestCard> findById(UUID id) {
        return Mono.just(card2);
    }

}
