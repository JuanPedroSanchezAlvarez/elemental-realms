package com.misispiclix.elementalrealms.service;

import com.misispiclix.elementalrealms.dto.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBeerService {
    Flux<BeerDTO> findAll();
    Mono<BeerDTO> findById(Integer id);
    Mono<BeerDTO> createBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO);
    Mono<Void> deleteById(Integer id);
}
