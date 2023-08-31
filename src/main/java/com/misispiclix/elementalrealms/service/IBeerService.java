package com.misispiclix.elementalrealms.service;

import com.misispiclix.elementalrealms.dto.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBeerService {
    Flux<BeerDTO> findAll();
    Mono<BeerDTO> findById(String id);
    Mono<BeerDTO> createBeer(Mono<BeerDTO> beerDTO);
    Mono<BeerDTO> createBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(String id, BeerDTO beerDTO);
    Mono<Void> deleteById(String id);
    Mono<BeerDTO> findFirstByBeerName(String beerName);
    Flux<BeerDTO> findByBeerStyle(String beerStyle);
}
