package com.misispiclix.elementalrealms.controller;

import com.misispiclix.elementalrealms.dto.BeerDTO;
import com.misispiclix.elementalrealms.service.IBeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";

    private final IBeerService beerService;

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> findAll() {
        return beerService.findAll();
    }

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> findById(@PathVariable("id") Integer id) {
        return beerService.findById(id);
    }

    @PostMapping(BEER_PATH)
    public Mono<ResponseEntity<Void>> createBeer(@Validated @RequestBody BeerDTO beerDTO) {
        return beerService.createBeer(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + savedDto.getId())
                        .build().toUri())
                        .build());
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<Void> updateBeer(@PathVariable("id") Integer id, @Validated @RequestBody BeerDTO beerDTO) {
        beerService.updateBeer(id, beerDTO).subscribe();
        return ResponseEntity.noContent().build();
    }

    @PutMapping(BEER_PATH_ID + "/2")
    public Mono<ResponseEntity<Void>> updateBeer2(@PathVariable("id") Integer id, @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.updateBeer(id, beerDTO).map(savedDto -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(BEER_PATH_ID)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") Integer id, @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.deleteById(id).thenReturn(ResponseEntity.noContent().build());
    }

}
