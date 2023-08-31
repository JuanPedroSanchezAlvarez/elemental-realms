package com.misispiclix.elementalrealms.service.implementation;

import com.misispiclix.elementalrealms.dto.BeerDTO;
import com.misispiclix.elementalrealms.mapper.IBeerMapper;
import com.misispiclix.elementalrealms.repository.IBeerRepository;
import com.misispiclix.elementalrealms.service.IBeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements IBeerService {

    private final IBeerRepository beerRepository;
    private final IBeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> findAll() {
        return beerRepository.findAll().map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> findById(String id) {
        return beerRepository.findById(id).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> createBeer(Mono<BeerDTO> beerDTO) {
        //return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)).map(beerMapper::beerToBeerDto);
        return beerDTO.map(beerMapper::beerDtoToBeer).flatMap(beerRepository::save).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(String id, BeerDTO beerDTO) {
        return beerRepository.findById(id)
                .map(foundBeer -> {
                    //update properties
                    foundBeer.setBeerName(beerDTO.getBeerName());
                    foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    foundBeer.setPrice(beerDTO.getPrice());
                    foundBeer.setUpc(beerDTO.getUpc());
                    foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());

                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return beerRepository.deleteById(id);
    }

    @Override
    public Mono<BeerDTO> findFirstByBeerName(String beerName) {
        return beerRepository.findFirstByBeerName(beerName).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Flux<BeerDTO> findByBeerStyle(String beerStyle) {
        return beerRepository.findByBeerStyle(beerStyle).map(beerMapper::beerToBeerDto);
    }

}
