package ma.emsi.firstfx.logic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ma.emsi.firstfx.logic.dao.FilmDao;
import ma.emsi.firstfx.logic.dao.impl.FilmDaoImp;
import ma.emsi.firstfx.logic.entities.Film;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FilmService {
    private FilmDao filmDao = new FilmDaoImp();
    public Film findById(int id){
        return filmDao.findById(id);
    }
    public List<Film> findAll() {
        return filmDao.findAll();
    }

    public Film save(Film film) {

        return filmDao.insert(film);

    }
    public void update(Film film) {

        filmDao.update(film);

    }
    public void remove(Film film) {
        filmDao.deleteById(film.getId());
    }



    public void exporterVersExcel() {
        List<Film> films = filmDao.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Films");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Title");
            headerRow.createCell(2).setCellValue("Category");
            headerRow.createCell(3).setCellValue("Min Age");
            headerRow.createCell(4).setCellValue("Max Age");
            headerRow.createCell(5).setCellValue("Registration Date");

            // Populate data rows
            int rowNum = 1;
            for (Film film : films) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(film.getId());
                row.createCell(1).setCellValue(film.getTitre());
                row.createCell(2).setCellValue(film.getCategory());
                row.createCell(3).setCellValue(film.getMin_age());
                row.createCell(4).setCellValue(film.getMax_age());
                row.createCell(5).setCellValue(film.getRegistrationDate().toString());
            }

            // Save the workbook to a file
            try (FileOutputStream outputStream = new FileOutputStream("src/main/resources/Exporter/film.xlsx")) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during the export process
        }
    }



    public void importerDepuisExcel() {
        // Clear the existing data in the database
        filmDao.deleteAll();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream("src/main/resources/Importer/film.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows starting from the second row (skipping the header)
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);

                // Read the data from each cell
                int id = (int) row.getCell(0).getNumericCellValue();
                String title = row.getCell(1).getStringCellValue();
                String category = row.getCell(2).getStringCellValue();
                int minAge = (int) row.getCell(3).getNumericCellValue();
                int maxAge = (int) row.getCell(4).getNumericCellValue();
                String registrationDate = row.getCell(5).getStringCellValue();

                // Create a Film object
                Film film = new Film(id, title, category, minAge, maxAge, registrationDate);

                // Store the film in the database
                filmDao.insert(film);
            }

            System.out.println("Import completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during the import process
        }
    }



    public void exporterVersJson() {
        List<Film> films = filmDao.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File jsonFile = new File("src/main/resources/Exporter/film.json");
            objectMapper.writeValue(jsonFile, films);
            System.out.println("Export completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during the export process
        }
    }

    public void importDepuisJson() {
        // Clear the existing data in the database
        filmDao.deleteAll();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File jsonFile = new File("src/main/resources/Importer/film.json");
            List<Film> films = objectMapper.readValue(jsonFile, new TypeReference<List<Film>>() {});

            // Insert the films into the database
            for (Film film : films) {
                filmDao.insert(film);
            }

            System.out.println("Import completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during the import process
        }
    }


}
