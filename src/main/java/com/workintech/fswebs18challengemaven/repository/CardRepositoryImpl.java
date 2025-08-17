package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;


import java.util.List;

@Slf4j
@Repository
public class CardRepositoryImpl implements CardRepository {
    private final EntityManager entityManager;

    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Card save(Card card) {
        CardValidation.cardCheck(card);
        log.info("save started");
        entityManager.persist(card);
        log.info("save ended");
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        CardValidation.textCheck(color, "Color");
        CardValidation.colorCheck(color);
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE color = :color", Card.class);
        query.setParameter("color", color);
        if (query.getResultList().isEmpty()) {
            throw new CardException("Card cannot find!", HttpStatus.NOT_FOUND);
        }
        return query.getResultList();
    }

    @Override
    public List<Card> findAll() {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c", Card.class);
        return query.getResultList();
    }

    @Override
    public List<Card> findByValue(Integer value) {
        CardValidation.valueValid(value);
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.value = :value ORDER BY c.value DESC", Card.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        CardValidation.textCheck(type, "Type");
        Type enumType = Enum.valueOf(Type.class, type.toUpperCase());
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE type = :type", Card.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Card update(Card card) {
        CardValidation.cardCheck(card);
        entityManager.merge(card);
        return card;
    }

    @Transactional
    @Override
    public Card remove(Long id) {
        CardValidation.idValid(id);
        Card card = entityManager.find(Card.class, id);
        CardValidation.cardCheck(card);
        entityManager.remove(card);
        return card;
    }
}
