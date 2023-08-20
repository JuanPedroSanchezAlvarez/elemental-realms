package com.misispiclix.elementalrealms.repository;

import com.misispiclix.elementalrealms.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IBeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
