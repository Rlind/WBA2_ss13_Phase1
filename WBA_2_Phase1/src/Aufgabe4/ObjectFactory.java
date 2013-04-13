//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.04.13 um 06:29:48 PM CEST 
//


package Aufgabe4;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.aufgabe_3d package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.aufgabe_3d
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Recipes }
     * 
     */
    public Recipes createRecipes() {
        return new Recipes();
    }

    /**
     * Create an instance of {@link Recipes.Recipe }
     * 
     */
    public Recipes.Recipe createRecipesRecipe() {
        return new Recipes.Recipe();
    }

    /**
     * Create an instance of {@link Recipes.Recipe.Comment }
     * 
     */
    public Recipes.Recipe.Comment createRecipesRecipeComment() {
        return new Recipes.Recipe.Comment();
    }

    /**
     * Create an instance of {@link Recipes.Recipe.Picture }
     * 
     */
    public Recipes.Recipe.Picture createRecipesRecipePicture() {
        return new Recipes.Recipe.Picture();
    }

    /**
     * Create an instance of {@link Recipes.Recipe.Ingredient }
     * 
     */
    public Recipes.Recipe.Ingredient createRecipesRecipeIngredient() {
        return new Recipes.Recipe.Ingredient();
    }

    /**
     * Create an instance of {@link Recipes.Recipe.Comment.User }
     * 
     */
    public Recipes.Recipe.Comment.User createRecipesRecipeCommentUser() {
        return new Recipes.Recipe.Comment.User();
    }

}
