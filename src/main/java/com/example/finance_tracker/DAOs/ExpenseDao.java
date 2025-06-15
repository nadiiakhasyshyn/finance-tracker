package com.example.finance_tracker.DAOs;

import com.example.finance_tracker.entities.Expense;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ExpenseDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public ExpenseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createExpense(Expense expense) {
        sessionFactory.getCurrentSession().persist(expense);
    }

    public Optional<Expense> getExpenseById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Expense WHERE id=:id", Expense.class)
                .setParameter("id",id)
                .uniqueResultOptional();
    }

    public List<Expense> getAllExpenses() {
        return sessionFactory.getCurrentSession().createQuery("FROM Expense ", Expense.class)
                .getResultList();
    }

    public void deleteExpenseById(Long id) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Expense WHERE id=:id")
                .setParameter("id",id)
                .executeUpdate();
    }

    public List<Expense> getAllExpensesByUserID(Long userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Expense e WHERE e.user.id=:userId", Expense.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    public List<Expense> getAllExpensesByCategory(String category) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Expense WHERE category=:category", Expense.class)
                .setParameter("category",category)
                .getResultList();
    }

}
