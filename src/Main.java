import model.Account;
import model.TransferOperation;
import service.TransferService;
import service.ReportService;
import util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String ACCOUNTS_DIR = "accounts";
    private static final String INPUT_DIR = "input";
    private static final String REPORT_FILE = "report.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Account> accounts = new HashMap<>();
        List<TransferOperation> allOperations = new ArrayList<>();

        try {
            loadAccounts(accounts);
            System.out.println("Загруженные учетные записи:");
            for (Account acc : accounts.values()) {
                System.out.println(acc.getAccountNumber() + " : " + acc.getBalance());
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки аккаунтов: " + e.getMessage());
            return;
        }

        TransferService transferService = new TransferService(accounts);
        ReportService reportService = new ReportService(REPORT_FILE);

        while (true) {
            System.out.println("Введите 1 для анализа передаваемых файлов, 2 для отображения отчета, 0 для выхода:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (choice == 0) break;

            if (choice == 1) {
                try {
                    List<String> files = Files.list(java.nio.file.Paths.get(INPUT_DIR))
                            .filter(p -> Files.isRegularFile(p) && FileUtils.isTxtFile(p.toString()))
                            .map(p -> p.toString())
                            .collect(Collectors.toList());

                    if (files.isEmpty()) {
                        System.out.println("Нет подходящих файлов для обработки в папке input.");
                        continue;
                    }

                    for (String filePath : files) {
                        List<TransferOperation> ops = transferService.processTransferFile(filePath);
                        allOperations.addAll(ops);
                        reportService.appendOperations(ops);
                        System.out.println("Обработан файл: " + filePath);
                    }

                } catch (IOException e) {
                    System.out.println("Ошибка при обработке файлов: " + e.getMessage());
                }
            } else if (choice == 2) {
                try {
                    List<String> reportLines = Files.readAllLines(Paths.get(REPORT_FILE));
                    System.out.println("Отчет:");
                    reportLines.forEach(System.out::println);
                } catch (IOException e) {
                    System.out.println("Ошибка при чтении отчета: " + e.getMessage());
                }
            } else {
                System.out.println("Некорректный выбор, повторите.");
            }
        }

        try {
            saveAccounts(accounts);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения аккаунтов: " + e.getMessage());
        }
        System.out.println("Программа завершена.");
    }

    private static void loadAccounts(Map<String, Account> accounts) throws IOException {
        List<String> lines = FileUtils.readLines(ACCOUNTS_DIR);
        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 2) {
                String accNum = parts[0];
                int balance = Integer.parseInt(parts[1]);
                accounts.put(accNum, new Account(accNum, balance));
            }
        }
    }

    private static void saveAccounts(Map<String, Account> accounts) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Account acc : accounts.values()) {
            lines.add(acc.getAccountNumber() + " " + String.format("%.2f", acc.getBalance()));
        }
        FileUtils.writeLines(ACCOUNTS_DIR, lines);
    }
}