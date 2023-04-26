package com.my.contactbook.util;

import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.entity.UserEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "UserCode", "First Name", "Last Name", "Gender" };
    static String SHEET = "students";

    static String USER_SHEET = "users";

    static String SCHEDULE_SHEET = "schedules";

    static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static ByteArrayInputStream studentsToExcel(List<UserEntity> students) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (UserEntity user : students) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(user.getUserCode() == null ? "" : user.getUserCode());
                row.createCell(1).setCellValue(user.getFirstName() == null ? "" : user.getFirstName());
                row.createCell(2).setCellValue(user.getLastName() == null ? "" : user.getLastName());
                row.createCell(3).setCellValue(user.getGender() == null ? "" : user.getGender());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<UserDTO> excelToUsers(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
            Iterator<Row> rows = sheet.iterator();

            List<UserDTO> users = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                UserDTO user = new UserDTO();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            user.setFirstName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            user.setLastName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            user.setGender(currentCell.getStringCellValue());
                            break;
                        case 3:
                            user.setDob(currentCell.getStringCellValue());
                            break;
                        case 4:
                            user.setAddress(currentCell.getStringCellValue());
                            break;
                        case 5:
                            if(currentCell.getCellType().toString().equals("STRING")) {
                                user.setPhoneNumber(currentCell.getStringCellValue());
                            }
                            else {
                                user.setPhoneNumber(String.valueOf((int) currentCell.getNumericCellValue()));
                            }
                            break;
                        case 6:
                            List<String> roles = new ArrayList<>();
                            roles.add(currentCell.getStringCellValue());
                            user.setRoleName(roles);
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<ScheduleDTO> excelToSchedules(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
            Iterator<Row> rows = sheet.iterator();

            List<ScheduleDTO> schedules = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                ScheduleDTO scheduleDTO = new ScheduleDTO();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            scheduleDTO.setClassName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            scheduleDTO.setSlotName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            scheduleDTO.setSubjectName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            if(currentCell.getCellType().toString().equals("STRING")) {
                                scheduleDTO.setSubjectGrade(currentCell.getStringCellValue());
                            }
                            else {
                                scheduleDTO.setSubjectGrade(String.valueOf((int) currentCell.getNumericCellValue()));
                            }
                            break;
                        case 4:
                            scheduleDTO.setScheduleTime(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                schedules.add(scheduleDTO);
            }
            workbook.close();
            return schedules;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<UserEntity> excelToStudents(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
            Iterator<Row> rows = sheet.iterator();

            List<UserEntity> users = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                UserEntity user = new UserEntity();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            user.setUserCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            user.setFirstName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            user.setLastName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            user.setGender(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
