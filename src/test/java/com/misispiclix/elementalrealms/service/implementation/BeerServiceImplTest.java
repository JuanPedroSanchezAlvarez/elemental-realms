package com.misispiclix.elementalrealms.service.implementation;

import com.misispiclix.elementalrealms.domain.Beer;
import com.misispiclix.elementalrealms.dto.BeerDTO;
import com.misispiclix.elementalrealms.mapper.IBeerMapper;
import com.misispiclix.elementalrealms.mapper.IBeerMapperImpl;
import com.misispiclix.elementalrealms.repository.IBeerRepository;
import com.misispiclix.elementalrealms.service.IBeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerServiceImplTest {

    @Autowired
    IBeerService beerService;

    @Autowired
    IBeerRepository beerRepository;

    @Autowired
    IBeerMapper beerMapper;

    BeerDTO beerDTO;

    @BeforeEach
    void setUp() {
        beerDTO = beerMapper.beerToBeerDto(getTestBeer());
    }

    public static Beer getTestBeer() {
        return Beer.builder().beerName("Space Dust").beerStyle("IPA").price(BigDecimal.TEN).quantityOnHand(12).upc("qwe").build();
    }

    public static BeerDTO getTestBeerDto() {
        return new IBeerMapperImpl().beerToBeerDto(getTestBeer());
    }

    public BeerDTO getSavedBeerDto() {
        return beerService.createBeer(Mono.just(getTestBeerDto())).block();
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void createBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Mono<BeerDTO> savedMono = beerService.createBeer(Mono.just(beerDTO));
        savedMono.subscribe(savedDTO -> {
            System.out.println(savedDTO.getId());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void updateBeer() {
    }

    @Test
    void deleteById() {
    }

    @Test
    @DisplayName("Test Save Beer Using Subscriber")
    void saveBeerUseSubscriber() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

        Mono<BeerDTO> savedMono = beerService.createBeer(Mono.just(beerDTO));

        savedMono.subscribe(savedDto -> {
            System.out.println(savedDto.getId());
            atomicBoolean.set(true);
            atomicDto.set(savedDto);
        });

        await().untilTrue(atomicBoolean);

        BeerDTO persistedDto = atomicDto.get();
        assertThat(persistedDto).isNotNull();
        assertThat(persistedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Save Beer Using Block")
    void testSaveBeerUseBlock() {
        BeerDTO savedDto = beerService.createBeer(Mono.just(getTestBeerDto())).block();
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Update Beer Using Block")
    void testUpdateBlocking() {
        final String newName = "New Beer Name";  // use final so cannot mutate
        BeerDTO savedBeerDto = getSavedBeerDto();
        savedBeerDto.setBeerName(newName);

        BeerDTO updatedDto = beerService.createBeer(Mono.just(savedBeerDto)).block();

        //verify exists in db
        BeerDTO fetchedDto = beerService.findById(updatedDto.getId()).block();
        assertThat(fetchedDto.getBeerName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Test Update Using Reactive Streams")
    void testUpdateStreaming() {
        final String newName = "New Beer Name";  // use final so cannot mutate

        AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

        beerService.createBeer(Mono.just(getTestBeerDto()))
                .map(savedBeerDto -> {
                    savedBeerDto.setBeerName(newName);
                    return savedBeerDto;
                })
                .flatMap(beerService::createBeer) // save updated beer
                .flatMap(savedUpdatedDto -> beerService.findById(savedUpdatedDto.getId())) // get from db
                .subscribe(dtoFromDb -> {
                    atomicDto.set(dtoFromDb);
                });

        await().until(() -> atomicDto.get() != null);
        assertThat(atomicDto.get().getBeerName()).isEqualTo(newName);
    }

    @Test
    void testDeleteBeer() {
        BeerDTO beerToDelete = getSavedBeerDto();

        beerService.deleteById(beerToDelete.getId()).block();

        Mono<BeerDTO> expectedEmptyBeerMono = beerService.findById(beerToDelete.getId());

        BeerDTO emptyBeer = expectedEmptyBeerMono.block();

        assertThat(emptyBeer).isNull();

    }

    @Test
    void findFirstByBeerNameTest() {
        BeerDTO beerDto = getSavedBeerDto();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Mono<BeerDTO> foundDto = beerService.findFirstByBeerName(beerDto.getBeerName());

        foundDto.subscribe(dto -> {
            System.out.println(dto.toString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testFindByBeerStyle() {
        BeerDTO beerDto1 = getSavedBeerDto();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerService.findByBeerStyle(beerDto1.getBeerStyle())
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

}
