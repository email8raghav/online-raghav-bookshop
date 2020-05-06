package com.raghav.bookshop.service;

import java.util.List;
import java.util.Optional;

import com.raghav.bookshop.entity.Book;
import com.raghav.bookshop.requestModel.AddBookRequestModel;
import com.raghav.bookshop.requestModel.UpdateBookRequestModel;

public interface BookService {
	
	public Book saveBook(AddBookRequestModel addBookRequestModel);
	
	public List<Book> getAllBooks();
	
	public Optional<Book> bookDetail(Long id);

	public Book findOne(Long id);
	
	public Book updateBook(UpdateBookRequestModel updateBookRequestModel);

}
