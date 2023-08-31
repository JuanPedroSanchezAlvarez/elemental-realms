package com.misispiclix.elementalrealms.repository;

import com.misispiclix.elementalrealms.domain.Beer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBeerRepository extends ReactiveMongoRepository<Beer, String> {
    Mono<Beer> findFirstByBeerName(String beerName);
    Flux<Beer> findByBeerStyle(String beerStyle);
}
