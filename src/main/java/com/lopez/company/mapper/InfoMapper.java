package com.lopez.company.mapper;

import com.lopez.company.domain.Info;
import com.lopez.company.domain.dto.InfoDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class InfoMapper {

    public Info mapToInfo(InfoDto infoDto) {
        return new Info(infoDto.getId(), infoDto.getFirstname(), infoDto.getLastname(), infoDto.getPesel(), infoDto.getSex(), infoDto.getPid(), infoDto.getPname(), infoDto.getRid(), infoDto.getName(), infoDto.getDescription(), dateUtility(infoDto.getDate()), infoDto.getValue());
    }

    public InfoDto mapToInfoDto(Info info) {
        return new InfoDto(info.getId(), info.getFirstname(), info.getLastname(), info.getPesel(), info.getSex(), info.getPid(), info.getPname(), info.getRid(), info.getName(), info.getDescription(), info.getDate().toString().substring(0, 10), info.getValue());
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
