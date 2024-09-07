package com.notes.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.notes.entity.Notes;
import com.notes.entity.UserDtls;
import com.notes.repository.NotesRepository;
import com.notes.repository.UserRepository;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	NotesRepository notesRepository;
	@Autowired
	private UserRepository userRepository;

	@ModelAttribute
	public void addCommnData(Principal p, Model m) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);
		m.addAttribute("user", user);
	}
	
	@GetMapping("/addNotes")
	public String home() {
		return "user/add_notes";
	}

	@GetMapping("/viewNotes/{page}")
	public String view(@PathVariable int page, Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);

		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		Page<Notes> notes = notesRepository.findByNotesByUser(user.getId(), pageable);
		m.addAttribute("pageNo", page);
		m.addAttribute("totalPage", notes.getTotalPages());
		m.addAttribute("Notes", notes);
		m.addAttribute("totalElements", notes.getTotalElements());
		return "user/view_notes";
	}

	@GetMapping("/editNotes/{id}")
	public String editNotes(@PathVariable int id, Model m) {
		Optional<Notes> n = notesRepository.findById(id);
		if (n != null) {
			Notes notes = n.get();
			m.addAttribute("notes", notes);
		}
		return "user/edit_notes";
	}

	@PostMapping("/updateNotes")
	public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal p) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);
		notes.setUserDtls(user);
		Notes updateNotes = notesRepository.save(notes);
		if (updateNotes != null) {
			session.setAttribute("msg", "Notes Updated Successfully");
		} else {
			session.setAttribute("msg", "Something went wrong on server");
		}

		return "redirect:/user/viewNotes/0";
	}

	@GetMapping("/deleteNotes/{id}")
	public String deleteNotes(@PathVariable int id, HttpSession session) {
		Optional<Notes> notes = notesRepository.findById(id);
		if (notes != null) {
			notesRepository.delete(notes.get());
			session.setAttribute("msg", "Notes Deleted Successfully");
		} else {
			session.setAttribute("msg", "Something went wrong on server");
		}
		return "redirect:/user/viewNotes/0";
	}

	@GetMapping("/viewProfile")
	public String viewProfile() {
		return "user/view_profile";
	}

	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute UserDtls user, HttpSession session, Model m) {
		Optional<UserDtls> olduser = userRepository.findById(user.getId());
		if (olduser != null) {
			user.setPassword(olduser.get().getPassword());
			user.setRole(olduser.get().getRole());
			user.setEmail(olduser.get().getEmail());
			UserDtls updateUser = userRepository.save(user);
			if (updateUser != null) {
				m.addAttribute("user", updateUser);
				session.setAttribute("msg", "Profile Updated Successfully");
			} else {
				session.setAttribute("msg", "Something went wrong on server");
			}
		}

		return "redirect:/user/viewProfile";
	}

	@PostMapping("/saveNotes")
	public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p) {
		String email = p.getName();
		UserDtls u = userRepository.findByEmail(email);
		notes.setUserDtls(u);

		Notes n = notesRepository.save(notes);

		if (n != null) {
			session.setAttribute("msg", "Notes Added Successfully");
		} else {
			session.setAttribute("msg", "Something went wrong on server");
		}
		System.out.println(notes);
		return "redirect:/user/addNotes";
	}
}