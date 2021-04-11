package com.controller;

import com.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
@RequestMapping("/info/{bookNo}")
    public Book info(@PathVariable Integer bookNo) {
        Book book = new Book();
        book.setBookNo(bookNo);
        book.setBookName("bookName");
        return book;
    }
}
