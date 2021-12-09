import java.util.Arrays;
import java.util.Scanner;

public class FinanceAdviser {

    public static void main(String[] args) {
        showMenu();
    }

    private static final int[][] budget = new int[5][9];

    private static int money = 1000;

    private static final String FOOD = "Продукты";
    private static final String PREPAYMENT = "Аванс";
    private static final String CLOTHES = "Одежда";
    private static final String GIFT = "Подарок";
    private static final String GAMES = "Игры";
    private static final String[] categories = {FOOD, PREPAYMENT, CLOTHES, GIFT, GAMES};

    private static final Scanner scanner = new Scanner(System.in);

    static void showMenu() {
        System.out.println();
        System.out.println("*** Выберете действие ***");
        System.out.println("1. Добавить транзакцию  2. Удалить транзакцию");
        System.out.println("3. Текущий баланс   4. Транзакции");
        System.out.println("5. Транзакции по категории");
        System.out.println("0. Выход");
        System.out.println();
        int input = scanner.nextInt();
        switch (input) {
            case 0: {
                exit();
            }
            case 1: {
                addTransaction();
                break;
            }
            case 2: {
                removeTransaction();
                break;
            }
            case 3: {
                showBalance();
                break;
            }
            case 4: {
                showAll();
                break;
            }
            case 5: {
                transactionsByCategory();
                break;
            }
            default:
                System.out.println("Ошибка. Данное действие не поддерживается.");
        }
    }

    private static void transactionsByCategory() {
        System.out.println();
        System.out.println("*** Транзакции по категории ***");
        System.out.println("*** Выберете категорию ***");
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%d. %s\n", i, categories[i]);
        }
        System.out.println();
        int category = scanner.nextInt();
        showCategory(category);
    }

    private static String categoryAsString(int[] category) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < category.length; i++) {
            int curr = category[i];
            if (curr > 0) {
                sb.append("\n(").append(i).append(") ").append(curr);
            }
        }
        return sb.length() > 0 ? sb.toString() : "отсутствуют\n";
    }

    private static void showBalance() {
        System.out.printf("Баланс: %d\n", money);
        showMenu();
    }

    private static void showAll() {
        int counter = 0;
        for (int i = 0; i < budget.length; i++) {
            System.out.print("\n" + categories[i] + ": ");
            for (int j = 0; j < budget[i].length; j++) {
                if (budget[i][j] != 0) {
                    System.out.print("[" + ++counter + "]" + budget[i][j] + "; ");
                }
            }
            if (Arrays.stream(budget[i]).sum() == 0) {
                System.out.print("отсутствует");
            }
        }
        System.out.println();
        showMenu();
    }

    private static void showCategory(int category) {
        System.out.printf("Транзакции по категории \"%s\": %s\n",
                categories[category],
                categoryAsString(budget[category])
        );
        showMenu();
    }

    static void addTransaction() {
        System.out.println();
        System.out.println("*** Добавьте транзакцию ***");
        System.out.println("*** Например: Продукты 1000 ***");
        String[] input = new Scanner(System.in).nextLine().split(" ");
        if (input.length != 2) {
            System.out.println("Ошибка. Введенные данные некорректны.");
            return;
        }
        int number = Integer.parseInt(input[1]);
        // по буковкам смапить индекс категории
        int categoryIndex = 0;
        for (int i = 0; i < categories.length; i++) {
            if (input[0].equalsIgnoreCase(categories[i])) {
                categoryIndex = i;
                break;
            }
        }
        // найти свободное место и добавить туда транзакцию
        for (int i = 0; i < budget[categoryIndex].length; i++) {
            if (budget[categoryIndex][i] == 0) {
                budget[categoryIndex][i] = number;
                break;
            }
        }
        money = money + number;
        showMenu();

    }

    static void removeTransaction() {
        // набираем номер
        int number = scanner.nextInt();
        int temp = 0;
        // дропаем его
        int counter = 0;
        for (int i = 0; i < budget.length; i++) {
            for (int j = 0; j < budget[i].length; j++) {
                if (budget[i][j] != 0) {
                    counter = counter + 1;
                }
                if (counter == number) {
                    temp = budget[i][j];
                    budget[i][j] = 0;
                    break;
                }
            }
        }
        // минус из денег
        money = money - temp;
        showMenu();
    }

    static void exit() {
        return;
    }
}
