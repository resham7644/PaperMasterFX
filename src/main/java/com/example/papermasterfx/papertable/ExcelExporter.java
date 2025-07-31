package com.example.papermasterfx.papertable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {

    public static <T> void exportToExcel(TableView<T> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.setInitialFileName("table_data.xlsx");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        File file = fileChooser.showSaveDialog(null);
        if (file == null) return;

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet 1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < tableView.getColumns().size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(tableView.getColumns().get(col).getText());
            }

            // Populate data rows
            ObservableList<T> items = tableView.getItems();
            for (int rowIdx = 0; rowIdx < items.size(); rowIdx++) {
                Row row = sheet.createRow(rowIdx + 1);
                T item = items.get(rowIdx);

                for (int col = 0; col < tableView.getColumns().size(); col++) {
                    Object cellData = tableView.getColumns().get(col).getCellData(item);
                    Cell cell = row.createCell(col);
                    cell.setCellValue(cellData != null ? cellData.toString() : "");
                }
            }

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

            System.out.println("Exported to Excel: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
