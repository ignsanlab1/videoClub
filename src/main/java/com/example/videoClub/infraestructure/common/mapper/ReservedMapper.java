package com.example.videoClub.infraestructure.common.mapper;

import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;
import com.example.videoClub.domain.model.Reserved;
import com.example.videoClub.infraestructure.entity.ReservedEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {FilmMapper.class, UserMapper.class})
public interface ReservedMapper {

    Reserved toReserved(ReservedEntity reservedEntity);

    @InheritInverseConfiguration
    ReservedEntity toReservedEntity(Reserved reserved);

    ReservedDto toReservedDto(Reserved reserved);

    Reserved toReservedFromDto(ReservedDto reservedDto);

    ReservedDto toReservedDtoFromEntity(ReservedEntity reservedEntity);

    @InheritInverseConfiguration
    ReservedEntity toReservedEntityFromDto(ReservedDto reservedDto);

    ReservedRequest toReservedRequest(Reserved reserved);

    Reserved toReservedFromRequest(ReservedRequest reservedRequest);

    default ReservedEntity map(Long reservedId) {
        if (reservedId == null) {
            return null;
        }
        ReservedEntity reservedEntity = new ReservedEntity();
        reservedEntity.setId(reservedId);
        return reservedEntity;
    }

    default Long map(ReservedEntity reservedEntity) {
        if (reservedEntity == null) {
            return null;
        }
        return reservedEntity.getId();
    }
}
