package it.jiniux.gdlp.application.mappers;

import it.jiniux.gdlp.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.domain.ReadingStatus;

public class ReadingStatusMapper {
    private static ReadingStatusMapper INSTANCE;
    
    public static synchronized ReadingStatusMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReadingStatusMapper();
        }
        return INSTANCE;
    }

    public ReadingStatus toDomain(it.jiniux.gdlp.application.dtos.ReadingStatusDto dto) {
        if (dto == null) {
            return null;
        }
        switch (dto) {
            case READING:
                return ReadingStatus.READING;
            case READ:
                return ReadingStatus.READ;
            case TO_READ:
                return ReadingStatus.TO_READ;
            case ABANDONED:
                return ReadingStatus.ABANDONED;
            default:
                throw new IllegalArgumentException("Unknown ReadingStatusDto: " + dto);
        }
    }

    public it.jiniux.gdlp.application.dtos.ReadingStatusDto toDto(it.jiniux.gdlp.domain.ReadingStatus domain) {
        if (domain == null) {
            return null;
        }
        switch (domain) {
            case READING:
                return ReadingStatusDto.READING;
            case READ:
                return ReadingStatusDto.READ;
            case TO_READ:
                return ReadingStatusDto.TO_READ;
            case ABANDONED:
                return ReadingStatusDto.ABANDONED;
            default:
                throw new IllegalArgumentException("Unknown ReadingStatus: " + domain);
        }
    }
}
