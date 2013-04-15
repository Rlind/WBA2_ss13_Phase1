package Aufgabe4;

import java.io.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;


import Aufgabe4.Recipes.Recipe;
import Aufgabe4.Recipes.Recipe.Comment;
import Aufgabe4.Recipes.Recipe.Comment.User;
import Aufgabe4.Recipes.Recipe.*;

import java.util.*;



 
 
public class Main {
	private static Scanner in;

	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws SAXException 
	 * @throws DatatypeConfigurationException 
	 */
	public static void main(String[] args) throws JAXBException, SAXException, DatatypeConfigurationException {



		//Auslesen des Rezeptes
		Recipes recipes = recipesUnmarshaller();

		//Auswahl des Rezeptes
		int choice = choice(recipes)-1;

		//Gewaehltes Rezept jetzt benutzbar machen
		Recipe recipe = recipes.getRecipe().get(choice);

		//Auswahl, was mit dem Rezept passieren soll
				int choice_2_aktion = choice_2(recipe);

				switch(choice_2_aktion)
				{
				case 2:
					//Neuer Kommentar anlegen
					Comment comment = newComment();
					//Kommentar ins Rezeptobjekt legen
					recipe.comment.add(comment);
					//Speichern in die XML-Datei
					recipesMarshaller("src/Aufgabe_3d.xsd" , "src/Aufgabe_3d_beispiel.xml", recipes);

				case 1:
					//Ausgabe des Rezeptes
					plotRecipe(recipe);
					break;

				default:
					//Wenn Programm nicht ausf�hrbar, wird es beendet
					System.out.print("Das Programm wird beendet!");
					System.exit(0);
				}
	}



	/**
	 * @return comment
	 * @throws DatatypeConfigurationException 
	 */
	public static Comment newComment() throws DatatypeConfigurationException {
        in = new Scanner(System.in);
        Comment comment = new Comment();
        User user = new User();
        System.out.println("Wie hei�en Sie?");
        user.setUsername(in.nextLine());
        System.out.println("Wo liegt ihr Bild?");
        user.setAvatar(in.nextLine());
        comment.setUser(user);
        
        System.out.println("Was wollen Sie sagen?");
        comment.setContent(in.nextLine());
        
        GregorianCalendar c = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        c.setTime(date);
        XMLGregorianCalendar now = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        comment.setTimestamp(now);
        
        return comment;
        
    }

	/**
	 * @param recipe
	 */
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
			System.out.print("User: " + a.getUser().getUsername() + "\t");
			System.out.print("Avatar: " + a.getUser().getAvatar() + "\n");
			System.out.print(a.getTimestamp() + "\n");
			System.out.print(a.getContent() + "\n\n");
			System.out.print("Kommentar hilfreich?:  " + a.isUsefull() + "\n");
		}
	}

	/**
	 * @param recipe
	 * @return choice
	 */
	public static int choice_2(Recipe recipe) {
		in = new Scanner(System.in);
		int choice = -1;

			//Abfrage der M�glichkeiten des aktuellen Rezeptes 
			System.out.println("Das Rezept " + recipe.getTitle() + " anzeigen? (1)");
			System.out.println("Ein Kommentar zu " + recipe.getTitle() + " abgeben? (2)");
			System.out.println("M�chten Sie das Programm beenden? (3)");

			choice = in.nextInt();
			if ((choice >= 1) && (choice < 4)){
				return choice;
			}
			return choice;
	}

	/**
	 * @param recipes
	 * @return choice
	 * @throws JAXBException
	 */
	public static int choice(Recipes recipes) throws JAXBException{
		in = new Scanner(System.in);

		do{
			System.out.println("Die Rezepte:" + "\n");
			//Abfrage �ber alle Rezepte an den Users
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

	/**
	 * @return
	 * @throws JAXBException
	 */
	public static Recipes recipesUnmarshaller() throws JAXBException {
		JAXBContext jaxContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmar = jaxContext.createUnmarshaller();
		Recipes recipes = (Recipes) unmar.unmarshal(new File("src/Aufgabe3/chefkochbeispieldaten.xml"));
		return recipes;
	}

    /**
     * 
     * @param xsdSchema
     * @param xmlDatei
     * @param jaxbElement
     * @throws SAXException
     * @throws JAXBException
     */
    private static void recipesMarshaller(String xsdSchema, String xmlDatei, Recipes jaxbElement) throws SAXException, JAXBException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdSchema));
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(jaxbElement, new File(xmlDatei));
    }

}
