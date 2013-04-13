package Aufgabe4;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import java.io.*;


import Aufgabe4.Recipes.Recipe;
import Aufgabe4.Recipes.Recipe.Comment.User;
import Aufgabe4.Recipes.Recipe.*;

import java.util.*;

import Aufgabe3.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class Main {
	private static Scanner in;

	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws JAXBException, IOException {



		//Auslesen des Rezeptes
		Recipes recipes = recipesUnmarshaller();

		//Auswahl des Rezeptes
		int choice = choice(recipes)-1;

		//Gewaehltes Rezept jetzt benutzbar machen
		Recipe recipe = recipes.getRecipe().get(choice);

		//Auswahl was mit dem Rezept passieren soll
				int choice_2_aktion = choice_2(recipe);

				switch(choice_2_aktion)
				{
			/**case 2:
					//Verfassen eines neuen Kommentares
					Comments comments = commentList(recipe);
					//Anfügen des Kommentares an das Rezept
					rez.setComments(comments);
					//Übergabe an die XML-File
					rezeptMarshaller("src/Schoko_Schema_2.xsd" , "src/Schokokuchen.xml", cobo);**/

				case 1:
					//Ausgabe des Rezeptes
					plotRecipe(recipe);
					break;

				default:
					//Wenn Programm nicht ausführbar, wird es beendet
					System.out.print("Das Programm wird beendet!");
					System.exit(0);
				}
	}

	public static void plotRecipe(Recipe recipe) {
		System.out.println(recipe.getTitle() + "\n\n");
		System.out.println(recipe.getSubtitle() + "\n\n");

		System.out.println("Fotos:\n");
		for(Picture a : recipe.getPicture())
		{
			System.out.print("Foto_Id: " + a.getPId() + '\t');
			System.out.print("Quelle: " + a.getScr() + '\t');
			System.out.print("User: " + a.getUsr() + '\t');
		}

		System.out.println("\n\nZutaten:\n");
		for(Ingredient a : recipe.getIngredient())
		{
			System.out.print(a.getAmount() + "\t");
			System.out.print(a.getUnit() + "\t");
			System.out.print(a.getName() + "\n");
		}

		System.out.print("\n\nZubereitung:\n\n");
		System.out.print("Arbeitszeit: " + recipe.getWorkTime() + "\t/ ");
		System.out.print("Schwierigkeit: " + recipe.getComplexity() + "\t/ ");
		System.out.print("Brennwert: " + recipe.getCaloricValue() + "\n\n");
		System.out.print(recipe.getDecription());

		System.out.print("\n\nKommentare\n\n");
		for(Comment a : recipe.getComment())
		{


			//System.out.print("User: " + a.getUser().getUsername() + "\t");
			//System.out.print("Avatar: " + a.getUser().getUsername() + "\n");
			System.out.print(a.getTimestamp() + "\n");
			System.out.print(a.getContent() + "\n\n");
		}
	}

	/**
	 * @param recipe
	 * @return choice
	 */
	public static int choice_2(Recipe recipe) {
		in = new Scanner(System.in);
		int choice = -1;

			//Abfrage der Möglichkeiten des aktuellen Rezeptes 
			System.out.println("Das Rezept " + recipe.getTitle() + " anzeigen? (1)");
			System.out.println("Ein Kommentar zu " + recipe.getTitle() + " abgeben? (2)");
			System.out.println("Möchten Sie das Programm beenden? (3)");

			choice = in.nextInt();
			if ((choice >= 1) && (choice < 4)){
				return choice;
			}
			return choice;
	}

	public static int choice(Recipes recipes) throws JAXBException{
		in = new Scanner(System.in);

		do{
			//Abfrage über alle Rezepte an den Users
			for (Recipe r : recipes.getRecipe()) {
				System.out.println("\"" + r.getTitle() + "\" (" + r.getRId()	+ ")");					
			}
			//Auswahl wird in choice gespeichert
			int choice = in.nextInt();
			if ((choice >= 1) && (choice <= recipes.getRecipe().size())) {
				return choice;
			}
		} while (true);

	}

	public static Comment newComment () {
			in = new Scanner(System.in);
			
		Comment comment = new Comment();
		

		XMLGregorianCalendar date = null;
		
		try {
			System.out.println("Wie ist Ihr Name?");
			User user = new User();
			String uer = in.next();
			user.setUsername(uer);
//		System.out.println("");
//			user.setAvatar("kein Bild vorhanden");
			 
			System.out.println("Ihr Kommentar: ");
			String comm = in.next();
			comment.setContent(comm);

		
				date = DatatypeFactory.newInstance().newXMLGregorianCalendar( new GregorianCalendar() );
			} catch  ( DatatypeConfigurationException e ) {
				e.printStackTrace();
			}
			comment.setTimestamp( date );

			
			
		return  comment;
		}
	

	
/**	public static Comments commentList(Recipes rez) throws  JAXBException{

		Comments comments = new Comments();
		Comment comment = newComment();
		for (Comment a : rez.getComments().getComment())
		{
			comments.getComment().add(a);
		}
		comments.getComment().add(comment);

		return comments;
	}
	**/
	public static Recipes recipesUnmarshaller() throws JAXBException {
		JAXBContext jaxContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmar = jaxContext.createUnmarshaller();
		Recipes recipes = (Recipes) unmar.unmarshal(new File("src/Aufgabe3/chefkochbeispieldaten.xml"));
		return recipes;
	}

}