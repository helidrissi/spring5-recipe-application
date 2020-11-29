package guru.springframework.services;


import guru.springframework.controllers.IndexController;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by jt on 6/17/17.
 */
public class RecipeServiceImplTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        controller = new IndexController(recipeService);
    }

    @Test
    public void testMvcMockito() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isBadGateway())
                .andExpect(view().name("index"));
    }

    @Test
    public void getRecipes() throws Exception {

        Recipe recipe = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe2.setId(1L);

        HashSet receipesData = new HashSet();
        receipesData.add(recipe);

        receipesData.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(receipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 2);
        verify(recipeRepository, times(1)).findAll();
    }

}