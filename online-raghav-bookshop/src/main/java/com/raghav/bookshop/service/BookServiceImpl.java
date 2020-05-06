package com.raghav.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raghav.bookshop.entity.Book;
import com.raghav.bookshop.repository.BookRepository;
import com.raghav.bookshop.requestModel.AddBookRequestModel;
import com.raghav.bookshop.requestModel.UpdateBookRequestModel;


@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book saveBook(AddBookRequestModel addBookRequestModel) {
		Book bookEntity = new Book();
		BeanUtils.copyProperties(addBookRequestModel, bookEntity);
		return bookRepository.saveAndFlush(bookEntity);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> bookDetail(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public Book findOne(Long id) {
		return bookRepository.getOne(id);
	}

	@Override
	public Book updateBook(UpdateBookRequestModel updateBookRequestModel) {
		Book bookEntity = new ModelMapper().map(updateBookRequestModel, Book.class);
		return bookRepository.saveAndFlush(bookEntity);
	}

	
}
