package com.misispiclix.elementalrealms.repository;

import com.misispiclix.elementalrealms.domain.card.TestCard;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TestCardRepositoryImplTest {

    ITestCardRepository testCardRepository = new TestCardRepositoryImpl();

    @Test
    void findAll() {
        Flux<TestCard> testCardFlux = testCardRepository.findAll();
        testCardFlux.subscribe(testCard -> {
            System.out.println(testCard.toString());
        });
    }

    @Test
    void findAllMap() {
        Flux<TestCard> testCardFlux = testCardRepository.findAll();
        testCardFlux.map(testCard -> {
            return testCard.getName();
        }).subscribe(name -> {
            System.out.println(name);
        });
    }

    @Test
    void findAllToList() {
        Flux<TestCard> testCardFlux = testCardRepository.findAll();
        Mono<List<TestCard>> listMono = testCardFlux.collectList();
        listMono.subscribe(list -> {
            list.forEach(testCard -> System.out.println(testCard.getName()));
        });
    }

    @Test
    void findAllFilter() {
        testCardRepository.findAll()
                .filter(testCard -> testCard.getName().equals("Card2"))
                .subscribe(testCard -> System.out.println(testCard.getName()));
    }

    @Test
    void findById() {
        Mono<TestCard> testCardMono = testCardRepository.findById(UUID.randomUUID());
        testCardMono.subscribe(testCard -> {
            System.out.println(testCard.toString());
        });
    }

    @Test
    void findByIdStepVerifier() {
        Mono<TestCard> testCardMono = testCardRepository.findById(UUID.randomUUID());
        StepVerifier.create(testCardMono).expectNextCount(1).verifyComplete();
        testCardMono.subscribe(testCard -> {
            System.out.println(testCard.toString());
        });
    }

    @Test
    void findByIdMap() {
        Mono<TestCard> testCardMono = testCardRepository.findById(UUID.randomUUID());
        testCardMono.map(testCard -> {
            return testCard.getName();
        }).subscribe(name -> {
            System.out.println(name);
        });
    }

    @Test
    void findByIdFilter() {
        Mono<TestCard> testCardMono = testCardRepository.findAll().filter(testCard -> testCard.getName().equals("Card2")).next();
        testCardMono.subscribe(testCard -> {
            System.out.println(testCard.getName());
        });
    }

    @Test
    void findByIdNotFoundException() {
        Flux<TestCard> testCardFlux = testCardRepository.findAll();
        final String testName = "Card5";
        Mono<TestCard> testCardMono = testCardFlux.filter(testCard -> testCard.getName().equals(testName)).single()
                .doOnError(throwable -> {
                    System.out.println("Error occurred in flux.");
                    System.out.println(throwable.toString());
                });
        testCardMono.subscribe(testCard -> {
            System.out.println(testCard.getName());
        }, throwable -> {
            System.out.println("Error occurred in mono.");
            System.out.println(throwable.toString());
        });
    }

}
