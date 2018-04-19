package com.lopez.company.mapper;

import com.lopez.company.domain.Remuneration;
import com.lopez.company.domain.dto.RemunerationDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RemunerationMapper {

    public Remuneration mapToRemuneration(RemunerationDto remunerationDto) {
        if (remunerationDto.getId() == null) {
            return new Remuneration(dateUtility(remunerationDto.getDate()), remunerationDto.getName(), remunerationDto.getDescription(), remunerationDto.getValue());
        }
        return new Remuneration(remunerationDto.getId(), dateUtility(remunerationDto.getDate()),remunerationDto.getName(), remunerationDto.getDescription(), remunerationDto.getValue());
    }

    public RemunerationDto mapToRemuneartionDto(Remuneration remuneration) {
        return new RemunerationDto(remuneration.getId(), remuneration.getDate().toString().substring(0, 10), remuneration.getName(), remuneration.getDescription(), remuneration.getValue());
    }
    private Date dateUtility(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
