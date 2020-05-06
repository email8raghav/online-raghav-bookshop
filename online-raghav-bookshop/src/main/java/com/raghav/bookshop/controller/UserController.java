package com.raghav.bookshop.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.raghav.bookshop.entity.Book;
import com.raghav.bookshop.entity.User;
import com.raghav.bookshop.requestModel.UpdateUserRequestModel;
import com.raghav.bookshop.requestModel.UserShippingRequestModel;
import com.raghav.bookshop.service.BookService;
import com.raghav.bookshop.service.UserService;


@Controller
public class UserController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	
	
	
	@GetMapping("/myAccount")
	public String getMyAccountPage(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("user", user);
		return "my-profile";
	}
	
	@GetMapping("/updateUser")
	public String updateUserGetRequest(@AuthenticationPrincipal User userEntity, Model model) {
		UpdateUserRequestModel updateUserRequestModel = new UpdateUserRequestModel();
		BeanUtils.copyProperties(userEntity, updateUserRequestModel);
		model.addAttribute("user", updateUserRequestModel);
		return "update-profile";
	}
	
	@PostMapping("/updateUser")
	public String updateUserPostRequest(@AuthenticationPrincipal User user, Model model) {
		UpdateUserRequestModel updateUserRequestModel = new UpdateUserRequestModel();
		model.addAttribute("user", updateUserRequestModel);
		return "My-profile";
	}
	
	@GetMapping("/addShippingAddress")
	public String getShippingAddressForm(@AuthenticationPrincipal User user, Model model) {
		UserShippingRequestModel userShippingRequestModel = new UserShippingRequestModel();
		model.addAttribute("userShipping", userShippingRequestModel);
		model.addAttribute("user", user);
		return "shipping-address";
	}
	
	
	
	@GetMapping("/test")
	public String test(Model model) {
		String newUserMeassage = "Please verify your email to login !!! check your email!!!";
		model.addAttribute("newUserMeassage", newUserMeassage);
		model.addAttribute("message", "display this message");
		return "test";
	}

	@GetMapping("/bookShelf")
	public String bookshelf(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("user", user);

		List<Book> bookList = bookService.getAllBooks();
		model.addAttribute("bookList", bookList);
		model.addAttribute("activeAll", true);

		return "book-shelf";
	}

	@GetMapping("/bookDetail")
	public String bookDetail(@PathParam("id") Long id, Model model, @AuthenticationPrincipal User user) {

		model.addAttribute("user", user);
		Optional<Book> bookDetail = bookService.bookDetail(id);
		Book book = null;
		if (bookDetail.isPresent()) {
			book = bookDetail.get();
		}
		model.addAttribute("book", book);

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);

		return "book-detail";
	}

}
