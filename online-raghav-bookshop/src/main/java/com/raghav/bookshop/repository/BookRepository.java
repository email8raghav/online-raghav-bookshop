package com.raghav.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raghav.bookshop.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
