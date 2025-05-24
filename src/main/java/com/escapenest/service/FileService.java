package com.escapenest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.escapenest.entity.Booking;
import com.escapenest.entity.Hotel;
import com.escapenest.repository.BookingRepository;
import com.escapenest.repository.HotelRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    public ByteArrayInputStream exportExcel(Hotel hotel, List<Booking> bookingList) throws IOException {
        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Thống kê");
            Row titleRow = sheet.createRow(0);

            //set title
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Báo cáo thống kê hoạt động homestay "+ hotel.getName());
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

            //set font
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);

            //header
            String[] headers = {
                    "ID", "Tên Khách Hàng", "Số Điện Thoại", "Email", "Phòng", "Số Khách",
                    "Check-in", "Check-out", "Thành tiền", "Trạng thái", "Thời gian tạo"
            };

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(1);
            for(int i = 0; i < headers.length; i++) {
                Cell cellHeader = headerRow.createCell(i);
                cellHeader.setCellValue(headers[i]);
                cellHeader.setCellStyle(headerStyle);
            }
            //data

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            //format số tiền
            CellStyle currencyStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            currencyStyle.setDataFormat(format.getFormat("#,##0"));

            int rowIndex = 2;
            for(Booking b: bookingList){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(b.getId());
                row.createCell(1).setCellValue(b.getNameCustomer());
                row.createCell(2).setCellValue(b.getPhoneCustomer());
                row.createCell(3).setCellValue(b.getEmailCustomer());
                row.createCell(4).setCellValue(b.getRoom().getName());
                row.createCell(5).setCellValue(b.getGuests());
                row.createCell(6).setCellValue(String.valueOf(b.getCheckIn()));
                row.createCell(7).setCellValue(String.valueOf(b.getCheckOut()));
                row.createCell(8).setCellValue(b.getPrice());
                row.getCell(8).setCellStyle(currencyStyle);
                row.createCell(9).setCellValue(String.valueOf(b.getStatusBooking()));
                row.createCell(10).setCellValue(String.valueOf(b.getCreateAt()));
                for(int i = 0; i <=10; i++){
                    if(i == 8)
                        continue;
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
            //summary style
            CellStyle summaryStyle = workbook.createCellStyle();
            Font summaryFont = workbook.createFont();
            summaryFont.setBold(true);
            summaryFont.setFontHeightInPoints((short) 15);
            summaryStyle.setFont(summaryFont);
            summaryStyle.setAlignment(HorizontalAlignment.RIGHT);
            //summary section
            int lastRow = rowIndex+1;
            Row totalRow = sheet.createRow(lastRow);
            Cell labelCell = totalRow.createCell(0);
            labelCell.setCellValue("Tổng doanh thu các đơn đã hoàn thành: ");
            sheet.addMergedRegion(new CellRangeAddress(lastRow,lastRow,0,7));
            labelCell.setCellStyle(summaryStyle);

            String formula = String.format("SUMIF(J3:J%d, \"COMPLETE\", I3:I%d)", rowIndex,rowIndex);
            Cell totalCell = totalRow.createCell(8);
            totalCell.setCellFormula(formula);
            totalCell.setCellStyle(currencyStyle);

            //fee
            CellStyle percentStyle = workbook.createCellStyle();
            DataFormat cellFormat = workbook.createDataFormat();
            percentStyle.setDataFormat(cellFormat.getFormat("0.00%"));

            Row feeRow = sheet.createRow(lastRow+1);
            Cell feeLabel = feeRow.createCell(0);
            feeLabel.setCellValue("Chiết khấu: ");
            sheet.addMergedRegion(new CellRangeAddress(lastRow+1, lastRow+1,0,7 ));
            feeLabel.setCellStyle(summaryStyle);
            Cell feeCell = feeRow.createCell(8);
            feeCell.setCellValue(0.15);
            feeCell.setCellStyle(percentStyle);

            //thực nhận
            Row afterTotalRow = sheet.createRow(lastRow+2);
            Cell afterTotalLabel = afterTotalRow.createCell(0);
            afterTotalLabel.setCellValue("Tổng doanh thu sau chiết khấu: ");
            sheet.addMergedRegion(new CellRangeAddress(lastRow+2, lastRow+2,0,7 ));
            afterTotalLabel.setCellStyle(summaryStyle);
            String formula2 = String.format("I%d*(1-I%d)",lastRow+1, lastRow+2);
            Cell afterTotalCell = afterTotalRow.createCell(8);
            afterTotalCell.setCellFormula(formula2);
            afterTotalCell.setCellStyle(currencyStyle);

            for(int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            sheet.setAutoFilter(new CellRangeAddress(1,1,0, headers.length -1));
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
    //admin export

    public ByteArrayInputStream adminExportExcel(List<Booking> bookingList) throws IOException {
        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Thống kê");
            Row titleRow = sheet.createRow(0);

            //set title
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Báo cáo thống kê hoạt động Website EscapeNest ");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

            //set font
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);

            //header
            String[] headers = {
                    "ID", "Tên Khách Hàng", "Số Điện Thoại", "Email", "Phòng", "Số Khách",
                    "Check-in", "Check-out", "Thành tiền", "Trạng thái", "Thời gian tạo", "Homestay"
            };

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(1);
            for(int i = 0; i < headers.length; i++) {
                Cell cellHeader = headerRow.createCell(i);
                cellHeader.setCellValue(headers[i]);
                cellHeader.setCellStyle(headerStyle);
            }
            //data

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            //format số tiền
            CellStyle currencyStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            currencyStyle.setDataFormat(format.getFormat("#,##0"));

            int rowIndex = 2;
            for(Booking b: bookingList){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(b.getId());
                row.createCell(1).setCellValue(b.getNameCustomer());
                row.createCell(2).setCellValue(b.getPhoneCustomer());
                row.createCell(3).setCellValue(b.getEmailCustomer());
                row.createCell(4).setCellValue(b.getRoom().getName());
                row.createCell(5).setCellValue(b.getGuests());
                row.createCell(6).setCellValue(String.valueOf(b.getCheckIn()));
                row.createCell(7).setCellValue(String.valueOf(b.getCheckOut()));
                row.createCell(8).setCellValue(b.getPrice());
                row.getCell(8).setCellStyle(currencyStyle);
                row.createCell(9).setCellValue(String.valueOf(b.getStatusBooking()));
                row.createCell(10).setCellValue(String.valueOf(b.getCreateAt()));
                row.createCell(11).setCellValue(b.getHotel().getName());
                for(int i = 0; i <=11; i++){
                    if(i == 8)
                        continue;
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
            //summary style
            CellStyle summaryStyle = workbook.createCellStyle();
            Font summaryFont = workbook.createFont();
            summaryFont.setBold(true);
            summaryFont.setFontHeightInPoints((short) 15);
            summaryStyle.setFont(summaryFont);
            summaryStyle.setAlignment(HorizontalAlignment.RIGHT);
            //summary section
            int lastRow = rowIndex+1;
            Row totalRow = sheet.createRow(lastRow);
            Cell labelCell = totalRow.createCell(0);
            labelCell.setCellValue("Tổng doanh thu các đơn đã hoàn thành: ");
            sheet.addMergedRegion(new CellRangeAddress(lastRow,lastRow,0,7));
            labelCell.setCellStyle(summaryStyle);

            String formula = String.format("SUMIF(J3:J%d, \"COMPLETE\", I3:I%d)", rowIndex,rowIndex);
            Cell totalCell = totalRow.createCell(8);
            totalCell.setCellFormula(formula);
            totalCell.setCellStyle(currencyStyle);

            //fee
            CellStyle percentStyle = workbook.createCellStyle();
            DataFormat cellFormat = workbook.createDataFormat();
            percentStyle.setDataFormat(cellFormat.getFormat("0.00%"));

            Row feeRow = sheet.createRow(lastRow+1);
            Cell feeLabel = feeRow.createCell(0);
            feeLabel.setCellValue("Chiết khấu: ");
            sheet.addMergedRegion(new CellRangeAddress(lastRow+1, lastRow+1,0,7 ));
            feeLabel.setCellStyle(summaryStyle);
            Cell feeCell = feeRow.createCell(8);
            feeCell.setCellValue(0.15);
            feeCell.setCellStyle(percentStyle);

            //thực nhận
            Row afterTotalRow = sheet.createRow(lastRow+2);
            Cell afterTotalLabel = afterTotalRow.createCell(0);
            afterTotalLabel.setCellValue("Tổng thu : ");
            sheet.addMergedRegion(new CellRangeAddress(lastRow+2, lastRow+2,0,7 ));
            afterTotalLabel.setCellStyle(summaryStyle);
            String formula2 = String.format("I%d*I%d",lastRow+1, lastRow+2);
            Cell afterTotalCell = afterTotalRow.createCell(8);
            afterTotalCell.setCellFormula(formula2);
            afterTotalCell.setCellStyle(currencyStyle);

            for(int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            sheet.setAutoFilter(new CellRangeAddress(1,1,0, headers.length -1));
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
