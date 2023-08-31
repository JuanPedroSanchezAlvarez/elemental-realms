package com.misispiclix.elementalrealms.mapper;

import com.misispiclix.elementalrealms.domain.Beer;
import com.misispiclix.elementalrealms.dto.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface IBeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);

}
