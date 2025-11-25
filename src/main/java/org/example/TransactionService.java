package org.example;

import java.util.HashSet;
import java.util.Set;

/**
 * Сервис отвечает за управление платежами и переводами
 */

public class TransactionService {

    public static final Set<String> TRANSACTION_CATEGORIES = Set.of(
            "Sport", "Alcohol", "Food");

    public Boolean isValidCategory(String category) {
        return TRANSACTION_CATEGORIES.contains(category);
    }

    public Set<String> validateCategories(Set<String> categories) {
        Set<String> validCategories = new HashSet<String>();

        for (String category : categories) {
            if (isValidCategory(category)) {
                validCategories.add(category);
            }
        }
        return validCategories;
    }
}
