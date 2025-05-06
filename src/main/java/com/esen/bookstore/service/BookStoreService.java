package com.esen.bookstore.service;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.BookStore;
import com.esen.bookstore.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookStoreService {

    private final BookstoreRepository bookstoreRepository;


    public void removeBookFromInventory(Book book){
        bookstoreRepository.findAll()
                .forEach(bookStore -> {
                    bookStore.getInventory().remove(book);
                    bookstoreRepository.save(bookStore);
                });

    }

    public List<BookStore> findAll(){
        return bookstoreRepository.findAll();
    }

    public void delete(Long id){
        if (!bookstoreRepository.existsById(id)){
            throw new IllegalArgumentException("Cannot find book id" + id);
        }

        var bookstore = bookstoreRepository.findById(id).get();
        bookstoreRepository.delete(bookstore);

    }

    public void save(BookStore bookStore){
        bookstoreRepository.save(bookStore);
    }

    public BookStore update(Long id, String location, Double priceModifier, Double moneyInCashRegister){
        if(Stream.of(location, priceModifier, moneyInCashRegister).allMatch(Objects::isNull)){
            throw new IllegalArgumentException("At least one input is required!" + id);
        }

        if (!bookstoreRepository.existsById(id)){
            throw new IllegalArgumentException("Cannot find book id" + id);
        }

        var book = bookstoreRepository.findById(id).get();

        if (location != null){
            book.setLocation(location);
        }
        if (priceModifier != null){
            book.setPriceModifier(priceModifier);

        }
        if (moneyInCashRegister != null){
            book.setMoneyInCashRegister(moneyInCashRegister);
        }


        return bookstoreRepository.save(book);

    }
}
