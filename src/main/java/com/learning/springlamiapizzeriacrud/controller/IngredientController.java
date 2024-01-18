package com.learning.springlamiapizzeriacrud.controller;

import com.learning.springlamiapizzeriacrud.model.Ingredient;
import com.learning.springlamiapizzeriacrud.repository.IngredientRepository;
import com.learning.springlamiapizzeriacrud.repository.OfferRepository;
import com.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        model.addAttribute("ingredientList", ingredientList);
        return "ingredients/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Ingredient ingredient = new Ingredient();
        model.addAttribute("ingredient", ingredient);

        return "ingredients/create";
    }

    @PostMapping("/create")
    public String createIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "ingredients/create";
        } else {
            Ingredient savedIngredient = ingredientRepository.save(ingredient);
            model.addAttribute("redirectMessage", "Ingredient created successfully!");
            return "redirect:/ingredients";
        }
    }


}
