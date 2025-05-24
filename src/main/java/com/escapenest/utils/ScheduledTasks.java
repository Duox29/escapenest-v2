package com.escapenest.utils;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.RoomPrice;
import com.escapenest.repository.RoomPriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final RoomPriceRepository roomPriceRepository;

    public void deleteRoomPrice() {
        LocalDate localDate = LocalDate.now();
        List<RoomPrice> roomPriceList = roomPriceRepository.findAllByDateAfter(localDate);
        roomPriceRepository.deleteAll(roomPriceList);
        roomPriceRepository.deleteAll(roomPriceList);
    }

}
