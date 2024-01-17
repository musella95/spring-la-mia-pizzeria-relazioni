package com.learning.springlamiapizzeriacrud.controller;

import com.learning.springlamiapizzeriacrud.model.Offer;
import com.learning.springlamiapizzeriacrud.model.Pizza;
import com.learning.springlamiapizzeriacrud.repository.OfferRepository;
import com.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId", required = true) Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            Pizza pizzaToOffer = result.get();
            model.addAttribute("pizza", pizzaToOffer);
            Offer newOffer = new Offer();
            newOffer.setPizza(pizzaToOffer);
            newOffer.setStartDate(LocalDate.now());
            newOffer.setExpireDate(LocalDate.now().plusDays(30));
            model.addAttribute("offer", newOffer);
            return "offers/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String storedOffer(Offer formOffer) {
        Offer storedOffer = offerRepository.save(formOffer);
        return "redirect:/pizzas/show/" + storedOffer.getPizza().getId();
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "discounts/edit";
        }
        Offer updateOffer = offerRepository.save(formOffer);
        return "redirect:/pizzas/show/" + updateOffer.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Offer> result = offerRepository.findById(id);
        if (result.isPresent()) {
            Offer offerToDelete = result.get();
            offerRepository.delete(offerToDelete);
            return "redirect:/pizzas/show/" + offerToDelete.getPizza().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer with id " + id + " not found");
        }
    }
}
