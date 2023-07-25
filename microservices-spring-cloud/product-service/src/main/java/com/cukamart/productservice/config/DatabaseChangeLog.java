package com.cukamart.productservice.config;

import com.github.cloudyrock.mongock.ChangeLog;

@ChangeLog
public class DatabaseChangeLog {

   /* @ChangeSet(order = "001", id = "seedDatabase", author = "Martin")
    public void seedDatabase(ProductRepository expenseRepository) {
        List<Product> expenseList = new ArrayList<>();
        expenseList.add(createNewExpense("Movie Tickets", ENTERTAINMENT, BigDecimal.valueOf(5.99)));
        expenseList.add(createNewExpense("Diiner", RESTAURANT, BigDecimal.valueOf(60)));
        expenseList.add(createNewExpense("Netflix", ENTERTAINMENT, BigDecimal.valueOf(10.99)));
        expenseList.add(createNewExpense("Gym", MISC, BigDecimal.valueOf(12.99)));
        expenseList.add(createNewExpense("Theater", UTILITIES, BigDecimal.valueOf(12)));

        expenseRepository.insert(expenseList);O
    }

    private Product createNewExpense(String expenseName, ExpenseCategory expenseCategory, BigDecimal amount) {
        Product expense = new Product();
        expense.setExpenseName(expenseName);
        expense.setExpenseCategory(expenseCategory);
        expense.setExpenseAmount(amount);
        return expense;
    }*/
}


