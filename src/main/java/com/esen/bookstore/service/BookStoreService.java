package com.esen.bookstore.service;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.BookStore;
import com.esen.bookstore.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
