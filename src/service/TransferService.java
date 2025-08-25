package service;

import model.Account;
import model.TransferOperation;
import util.FileUtils;
import util.DateTimeUtils;
import exception.TransferException;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class TransferService {
    private Map<String, Account> accounts;

    public TransferService(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public List<TransferOperation> processTransferFile(String filePath) {
        List<TransferOperation> operations = new ArrayList<>();
        String fileName = Paths.get(filePath).getFileName().toString();
        String timestamp = DateTimeUtils.getCurrentDateTime();

        try {
            List<String> lines = FileUtils.readLines(filePath);
            for (String line : lines) {
                // парсим только нужные поля
                String[] parts = line.split("\\s+");
                String fromAcc = null;
                String toAcc = null;
                Double amount = null;
                Double fromBalance = 0.0;
                Double toBalance = 0.0;

                for (String part : parts) {
                    if (part.matches("\\d{5}-\\d{5}")) {

                        if (fromAcc == null) {
                            fromAcc = part;
                        } else {
                            toAcc = part;
                        }
                    } else {
                        try {
                            amount = Double.parseDouble(part.replace(',', '.'));
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                if (!accounts.containsKey(fromAcc)) {
                    throw new TransferException("Счет отправителя не найден в базе: " + fromAcc);
                }
                if (!accounts.containsKey(toAcc)) {
                    throw new TransferException("Счет получателя не найден в базе: " + toAcc);
                }

                if (fromAcc == null || toAcc == null || amount == null) {
                    throw new TransferException("Некорректный формат файла: " + fileName);
                }

                String status;
                if (amount <= 0) {
                    status = "ошибка: отрицательная или нулевая сумма";
                } else if (!accounts.containsKey(fromAcc) || !accounts.containsKey(toAcc)) {
                    status = "ошибка: счет не найден";
                } else {
                    Account fromAccount = accounts.get(fromAcc);
                    Account toAccount = accounts.get(toAcc);
                    amount = Math.round(amount * 100.0) / 100.0;
                    if (fromAccount.getBalance() < amount) {
                        status = "ошибка: недостаточно средств";
                    } else {
                        fromBalance = fromAccount.getBalance();
                        toBalance = toAccount.getBalance();
                        // перевод
                        fromAccount.setBalance(fromAccount.getBalance() - amount);
                        toAccount.setBalance(toAccount.getBalance() + amount);
                        status = "успешно обработан";
                    }

                }

                operations.add(new TransferOperation(fileName, fromAcc, toAcc, amount, status, timestamp, fromBalance, toBalance));
            }
            Path source = Paths.get(filePath);
            Path archiveDir = Paths.get("archive");
            if (!Files.exists(archiveDir)) {
                Files.createDirectory(archiveDir);
            }
            Files.move(source, archiveDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | TransferException e) {
            operations.add(new TransferOperation(fileName, "N/A", "N/A", (double) 0, "ошибка: " + e.getMessage(), timestamp, 0.0, 0.0));
        }
        return operations;

    }
}