package service;

import model.TransferOperation;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    private String reportFilePath;

    public ReportService(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }

    public void appendOperations(List<TransferOperation> operations) throws IOException {
        List<String> lines = operations.stream()
                .map(TransferOperation::toString)
                .collect(Collectors.toList());
        Files.write(Paths.get(reportFilePath), lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
