package com.escapenest.repository;

import com.escapenest.entity.Booking;
import com.escapenest.model.dto.*;
import com.escapenest.model.enums.StatusBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAllByUser_IdOrderByCreateAtDesc(Integer id, Pageable pageable);

    List<Booking> findAllByHotel_Id(Integer hotelId);
    List<Booking> findByStatusBooking(StatusBooking statusBooking);
    List<Booking> findByHotel_IdAndStatusBooking(Integer id , StatusBooking statusBooking);
    List<Booking> findAllByUser_Id(Integer idUser);

    // tổng booking theo ngày tháng
    @Query(value = "SELECT new  com.escapenest.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'COMPLETE'" +
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt) " +
            "ORDER BY YEAR(o.createAt) ASC, MONTH(o.createAt) ASC")
    List<TotalBookingMonthDto> finTotalBookingMonth();

    // tổng doanh thu từng ngày
    @Query(value = "SELECT new com.escapenest.model.dto.RevenueDayDto(DAY(o.checkOut),MONTH(o.checkOut),YEAR(o.checkOut), SUM(o.price)) " +
            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.checkOut), MONTH(o.checkOut), DAY(o.checkOut) " +
            "ORDER BY YEAR(o.checkOut) DESC, MONTH(o.checkOut) DESC, DAY(o.checkOut) DESC")
    List<RevenueDayDto> getTotalRevenueDay();

    // tổng daonh thu từng tháng tháng
    @Query(value = "SELECT new com.escapenest.model.dto.RevenueMonthDto(MONTH(o.checkOut),YEAR(o.checkOut), SUM(o.price)) " +
            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.checkOut), MONTH(o.checkOut) " +
            "ORDER BY YEAR(o.checkOut) DESC, MONTH(o.checkOut) DESC")
    List<RevenueMonthDto> getTotalRevenueByMonth();

    //  doanh thu từng ngay của  tháng
    @Query(value = "SELECT new com.escapenest.model.dto.RevenueDayDto(DAY(o.createAt), MONTH(o.createAt), YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.hotel.id = :id AND o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt), DAY(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC, DAY(o.createAt) DESC")
    List<RevenueDayDto> getTotalRevenueByMonth(@Param("id") Integer id);


    // tổng đơn đặt phongfg theo từng loại phòng của homestay
    @Query(value = "SELECT new com.escapenest.model.dto.TotalBookingByRoomTypeDto(o.room.name, count(o.id)) " +
            "FROM Booking o WHERE o.hotel.id = :id AND YEAR(o.createAt) = :year AND o.statusBooking = 'COMPLETE' " +
            "GROUP BY o.room.name " +
            "ORDER BY count(o.id) DESC")
    List<TotalBookingByRoomTypeDto> getTotalBookingByRoomTypeAndYear(@Param("id") Integer id, @Param("year") int year);

    // tổng doanh thu từng tháng của năm
    @Query(value = "SELECT new com.escapenest.model.dto.RevenueMonthDto(MONTH(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.hotel.id = :id AND YEAR(o.createAt) = :year AND  o.statusBooking = 'COMPLETE'" +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC")
    List<RevenueMonthDto> getTotalRevenueByMonthAndHotelId(@Param("id") Integer id, @Param("year") int year);

    // tổng booking từng tháng trong năm của homestay
    @Query(value = "SELECT new  com.escapenest.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'COMPLETE' AND o.hotel.id = :id AND YEAR(o.createAt) = :year AND MONTH(o.createAt) = :month"   +
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt)")
    Optional<TotalBookingMonthDto> findTotalBookingMonthByHotel(@Param("id") Integer id , @Param("month") int month , @Param("year") int year);
    // tổng booking đang chờ xác nhận
    @Query(value = "SELECT new  " +
            "com.escapenest.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'PENDING' AND o.hotel.id = :id"+
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt)")
    Optional<TotalBookingMonthDto> findTotalBookingPendingMonthByHotel(@Param("id") Integer id );


    // tìm kiếm các booking theo từng phòng và ngày check in
    @Query(value = "SELECT new com.escapenest.model.dto.BookingDto(o.id, o.guests, o.checkIn, o.checkOut, o.numberRoom, o.room.name, o.room.roomType) " +
            "FROM Booking o " +
            "WHERE o.room.id = :id AND o.statusBooking = 'CONFIRM' AND o.checkIn BETWEEN :checkIn AND :checkOut")
    List<BookingDto> findBookingByRoomAndCreateAtBetweenCheckinCheckOut(@Param("id") Integer id, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);


    List<Booking> findAllByRoom_IdAndCheckInBetween(Integer roomId, LocalDate star , LocalDate end );
    List<Booking> findBookingByCreateAtBetweenAndStatusBookingOrderByCreateAtDesc(LocalDate star, LocalDate end, StatusBooking statusBooking);

    int countBookingsByCreateAtBetweenAndStatusBookingNotLike(LocalDate createAtAfter, LocalDate createAtBefore, StatusBooking statusBooking);

    @Query("SELECT b FROM Booking b WHERE " +
            "b.room.id = :roomId AND " +
            "b.statusBooking IN :statuses AND " +
            "b.checkIn <= :adjustedCheckOut AND " +
            "b.checkOut > :checkInDay")
    List<Booking> findOverlappingAndValidBookings(
            @Param("roomId") Integer roomId,
            @Param("checkInDay") LocalDate checkInDay,
            @Param("adjustedCheckOut") LocalDate adjustedCheckOut,
            @Param("statuses") List<StatusBooking> statuses
    );

    @Query("SELECT COALESCE(SUM(b.numberRoom), 0) " +
            "FROM Booking b " +
            "WHERE b.room.id = :roomId " +
            "AND b.statusBooking IN :statuses " +
            "AND b.checkOut > :checkIn " +
            "AND b.checkIn < :checkOut")
    Integer sumBookedRoomsForPeriod(@Param("roomId") Integer roomId,
                                    @Param("statuses") List<StatusBooking> statuses,
                                    @Param("checkIn") LocalDate checkIn,
                                    @Param("checkOut") LocalDate checkOut);
//owner
    @Query("SELECT b FROM Booking b " +
            "WHERE b.room.hotel.id = :hotelId " +
            "AND ((b.checkIn BETWEEN :start AND :end) " +
            "OR (b.checkOut BETWEEN :start AND :end) " +
            "OR (b.checkIn <= :start AND b.checkOut >= :end))")
    List<Booking> findByHotelAndDateRange(
            @Param("hotelId") Integer hotelId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);
//admin
@Query("SELECT b FROM Booking b " +
        "WHERE ((b.checkIn BETWEEN :start AND :end) " +
        "OR (b.checkOut BETWEEN :start AND :end) " +
        "OR (b.checkIn <= :start AND b.checkOut >= :end))")
List<Booking> findByDateRange(
        @Param("start") LocalDate start,
        @Param("end") LocalDate end);

}



