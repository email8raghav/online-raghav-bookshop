package com.raghav.bookshop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.raghav.bookshop.entity.Book;
import com.raghav.bookshop.requestModel.AddBookRequestModel;
import com.raghav.bookshop.requestModel.UpdateBookRequestModel;
import com.raghav.bookshop.service.BookService;


@Controller
public class AdminController {
	
	@Autowired
	private BookService bookService;
	
	
	@GetMapping("/addBook")
	public String addBook(Model model) {
		AddBookRequestModel book = new AddBookRequestModel();
		model.addAttribute("book", book);
		return "admin/add-book";
	}
	
	@PostMapping("/addBook")
	public String addNewBook(@ModelAttribute AddBookRequestModel book, Model model) {
		
		/*
		 * save to database
		 */
		Book bookEntity = bookService.saveBook(book);
		
		MultipartFile bookImage = book.getBookImage();

		try {
			byte[] bytes = bookImage.getBytes();
			String name = bookEntity.getId() + ".png";
			
			Path path = Paths.get("src/main/resources/static/images/book/"+name);
			if(Files.exists(path)) {
				Files.delete(path);
			}
			
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/images/book/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return "redirect:bookList";
	}
	
	@GetMapping("/bookList")
	public String bookList(Model model){
		List<Book> bookList = bookService.getAllBooks();
		model.addAttribute("bookList", bookList);
		return "admin/book-list";
	}
	
	@GetMapping("/bookInfo")
	public String bookInfo(@RequestParam("id") Long id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		
		return "admin/book-info";
	}
	
	@GetMapping("/updateBook")
	public String updateBook(@RequestParam("id") Long id, Model model) {
		
		Book book = bookService.findOne(id);
		
		ModelMapper modelMapper = new ModelMapper();
		UpdateBookRequestModel updateBookRequestModel = modelMapper.map(book, UpdateBookRequestModel.class);
		
		model.addAttribute("book", updateBookRequestModel);
		
		return "admin/update-book";
	}
	
	@PostMapping(value="/updateBook")
	public String updateBookPost(@ModelAttribute("book") UpdateBookRequestModel updateBookRequestModel, HttpServletRequest request) {
		
		MultipartFile bookImage = updateBookRequestModel.getBookImage();
		
		if(!bookImage.isEmpty()) {
			try {
				byte[] bytes = bookImage.getBytes();
				String name = updateBookRequestModel.getId() + ".png"; // 1_0, 1_1, 1_2 for multiple books
				
				Path path = Paths.get("src/main/resources/static/images/book/"+name);
				if(Files.exists(path)) {
					Files.delete(path);
				}
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/images/book/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		bookService.updateBook(updateBookRequestModel);
		/*
		 * return "redirect:/book-info?id="+updateBookRequestModel.getId(); is a method call not a page return heheee
		 */
		return "redirect:/bookInfo?id="+updateBookRequestModel.getId();
	}
		
}
