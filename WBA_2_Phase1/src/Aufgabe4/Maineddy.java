package Aufgabe4;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import Aufgabe4.*;
import Aufgabe3.*;


public class Maineddy {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws JAXBException, SAXException {


		//Auslesen der Daten des Rezeptes
		Recipes cobo = rezeptUnmarshaller();

		//Menue zur Auswahl des Rezeptes
		int menuWahl = menu(cobo)-1;

		//Zuweisung des gewaehlten Rezeptes
		Recipes rez = cobo.getRecipe().get(menuWahl);

		//Menue zur Verarbeitung des Rezeptes (Drucken, Kommentar, etc)
		int subMenuWahl = subMenu(rez);

		switch(subMenuWahl)
		{
		case 2:
			//Verfassen eines neuen Kommentares
			Comments comments = commentList(rez);
			//Anfügen des Kommentares an das Rezept
			rez.setComments(comments);
			//Übergabe an die XML-File
			rezeptMarshaller("src/Schoko_Schema_2.xsd" , "src/Schokokuchen.xml", cobo);

		case 1:
			//Ausgabe des Rezeptes
			ausgabeRezept(rez);
			break;

		default:
			//Wenn Programm nicht ausführbar, wird es beendet
			System.out.print("Das Programm wird beendet!");
			System.exit(0);
		}
	}

	/**
	 * Erzeugen eines Menues, Abfrage nach dem nächsten Schritt.
	 * @throws JAXBException
	 */
	public static int menu (Recipes cobo) throws JAXBException {

		BufferedReader console = new BufferedReader (new InputStreamReader(System.in));

		do {

			try {
				//Abfrage welches Rezept angezeigt oder bearbeitet werden soll
				for (Recipe r : cobo.getRecipe()) {
					System.out.println("\"" + r.getTitle() + "\" (" + r.getRId()	+ ")");
				}
				//Die Wahl wird in einer Variable gespeichert
				int wahl = Integer.parseInt(console.readLine());
				if ((wahl >= 1) && (wahl <= cobo.getRecipes().size())) {
					return wahl;
				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}
		} while (true);

	}

	/**
	 * Erzeugen eines Untermenues, Abfrage nach dem nächsten Schritt.
	 * @throws JAXBException
	 */
	public static int subMenu (Recipes rez) throws JAXBException {

		BufferedReader console = new BufferedReader (new InputStreamReader(System.in));

		try {
			//Abfrage welche Option gewünscht ist
			System.out.println("Möchten Sie das Rezept " + rez.getTitle() + " anzeigen lassen? (1)");
			System.out.println("Möchten Sie ein Kommentar zu " + rez.getTitle() + " abgeben? (2)");
			System.out.println("Möchten Sie das Programm beenden? (3)");

			int wahl = Integer.parseInt(console.readLine());
			if ((wahl >= 1) && (wahl < 4)){
				return wahl;
			}
		}

			catch (IOException e) {
				e.printStackTrace();
			}


		return -1;
	}

	/**
	 * Erzeugen eines neuen Kommentars
	 * Abfrage des Namen
	 * Bild wird gesetzt
	 * Zeitstempel wird gesetzt
	 * Kommentar wird eingefügt
	 * 
	 * @return neuer Kommentar
	 */
	public static Comment newComment () {

		Comment comment = new Comment();
		BufferedReader console = new BufferedReader (new InputStreamReader(System.in));

		try {
			System.out.println("Wie ist Ihr Name?");
			User user = new User();
			user.setUsername(console.readLine());
			user.setAvatar("kein Bild vorhanden");
			comment.setUser(user);
			SimpleDateFormat date = new SimpleDateFormat ("dd.MM.yyyy");
			comment.setDate(date.format(new Date()));
			System.out.println("Ihr Kommentar: ");
			comment.setContent(console.readLine());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return comment;
	}

	/**
	 * Erzeugen einer weiteren Kommentarliste
	 * Neuer Kommentar wird an die alte Liste angefügt
	 * 
	 * @return Kommentarliste vom Typ Comments
	 * @throws JAXBException
	 */
	public static Comments commentList(Recipes rez) throws  JAXBException{

		Comments comments = new Comments();
		Comment comment = newComment();
		for (Comment a : rez.getComments().getComment())
		{
			comments.getComment().add(a);
		}
		comments.getComment().add(comment);

		return comments;
	}

	/**
	 * Ausgabe des Rezeptes.
	 * Alle Daten des Rezeptes werden ausgegeben
	 */
	public static void ausgabeRezept (Recipes rez) throws JAXBException {


		System.out.println(rez.getTitle() + "\n\n");

		System.out.println("Bilder:\n");
		for(Picture a : rez.getPicture().getPic())
		{
			System.out.print("User: " + a.getUser() + '\t');
			System.out.print("url: " + a.getUrl() +'\n');
		}

		System.out.println("\n\nZutaten:\n");
		for(Ingredient a : rez.getIngredient().getZutat())
		{
			System.out.print(a.getAmount());
			System.out.print(a.getUnit() + "\t");
			System.out.print(a.getName() + "\n");
		}

		System.out.print("\n\nZubereitung:\n\n");
		System.out.print("Arbeitszeit: " + rez.getRecipe().getWorkTime() + "\t/ ");
		System.out.print("Schwierigkeit: " + rez.getRecipe().getComplexity() + "\t/ ");
		System.out.print("Brennwert: " + rez.getRecipe().getCaloric_value() + "\n");
		System.out.print(rez.getRecipe().getDescription());

		System.out.print("\n\nKommentare\n\n");
		for(Comment a : rez.getComments().getComment())
		{
			System.out.print("User: " + a.getUser() + "\n");
			System.out.print(a.getTimestamp() + "\n");
			System.out.print(a.getContent() + "\n\n");
		}

	}


	/**
	 * Methode zum Einlesen der Daten aus der XML (unmarshalling von Daten)
	 * @return 
	 * @throws JAXBException
	 */
	public static Recipes rezeptUnmarshaller() throws JAXBException {
		JAXBContext jaxCon = JAXBContext.newInstance("_Aufgabe4");
		Unmarshaller unMar = jaxCon.createUnmarshaller();
		Recipes cobo = (Recipes) unMar.unmarshal(new File("src/Schokokuchen.xml"));

		return cobo;
	}

	/**
	 * Vorbereitungsklasse fuer das Schreiben in ein XML-File
	 * 
	 * @param xsdSchema zugehoeriges XSD-Schema
	 * @param xmlDatei zu erzeugendes XML-File
	 * @param jaxbElement Element, welches in das XML-File gesetzt werden soll
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static void rezeptMarshaller (String xsdSchema, String xmlDatei, Object jaxbElement) throws JAXBException, SAXException {

		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = (xsdSchema == null || xsdSchema.trim().length() == 0) ? null
				: schemaFactory.newSchema(new File(xsdSchema));

		JAXBContext jaxbContext = JAXBContext.newInstance(jaxbElement
				.getClass().getPackage().getName());

		marshal(jaxbContext, schema, xmlDatei, jaxbElement);
	}

	/**
	 * Hilfsklasse zum Schreiben in XML-File
	 * 
	 * @param jaxbContext Package des Objektes
	 * @param schema XSD-File
	 * @param xmlDatei XML-Filename
	 * @param jaxbElement Objekt
	 * @throws JAXBException
	 */
	public static void marshal(JAXBContext jaxbContext, Schema schema,
			String xmlDatei, Object jaxbElement) throws JAXBException {

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(jaxbElement, new File(xmlDatei));
	}

	}