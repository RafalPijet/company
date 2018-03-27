package com.lopez.company.mapper;

import com.lopez.company.domain.Position;
import com.lopez.company.domain.dto.PositionDto;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    public Position mapToPosition(PositionDto positionDto) {
        return new Position(positionDto.getId(), positionDto.getName());
    }
    public PositionDto mapToPositionDto(Position position) {
        return new PositionDto(position.getId(), position.getName());
    }
}
