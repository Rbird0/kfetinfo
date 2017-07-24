/*
 * kfetinfo - Logiciel pour la K'Fet du BDE Info de l'IUT Lyon 1
 *  Copyright (C) 2017 Simon Lecutiez

 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kfetinfo;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class LecteurBase {
	static String path = new File("").getAbsolutePath();
	static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	
	private static JSONObject lireObjet(String path){
		JSONParser parser = new JSONParser();

		Object objet = null;
		try {
		objet = parser.parse(new FileReader(path));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject objetJson = (JSONObject)objet;
		return(objetJson);
	}

	public static Ingredient lireIngredient(String nom){
		JSONObject ingredientJson = new JSONObject();
		ingredientJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\" + nom + ".json");

		Ingredient ingredient = new Ingredient((String)ingredientJson.get("id"), (String)ingredientJson.get("nom"), ((Number)ingredientJson.get("cout")).floatValue(), (boolean)ingredientJson.get("estDisponible"), ((Number)ingredientJson.get("nbUtilisations")).intValue(), ((Number)ingredientJson.get("priorite")).intValue());
		return(ingredient);
	}

	public static Sauce lireSauce(String nom){
		JSONObject sauceJson = new JSONObject();
		sauceJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces\\" + nom + ".json");

		Sauce sauce = new Sauce((String)sauceJson.get("id"), (String)sauceJson.get("nom"), ((Number)sauceJson.get("cout")).floatValue(), (boolean)sauceJson.get("estDisponible"), ((Number)sauceJson.get("nbUtilisations")).intValue(), ((Number)sauceJson.get("priorite")).intValue());
		return(sauce);
	}

	public static Dessert lireDessert(String nom){
		JSONObject dessertJson = new JSONObject();
		dessertJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts\\" + nom + ".json");

		Dessert dessert = new Dessert((String)dessertJson.get("id"), (String)dessertJson.get("nom"), ((Number)dessertJson.get("cout")).floatValue(), (boolean)dessertJson.get("estDisponible"), ((Number)dessertJson.get("nbUtilisations")).intValue(), ((Number)dessertJson.get("priorite")).intValue(), ((Number)dessertJson.get("prix")).floatValue());
		return(dessert);
	}

	public static Boisson lireBoisson(String nom){
		JSONObject boissonJson = new JSONObject();
		boissonJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons\\" + nom + ".json");

		Boisson boisson = new Boisson((String)boissonJson.get("id"), (String)boissonJson.get("nom"), ((Number)boissonJson.get("cout")).floatValue(), (boolean)boissonJson.get("estDisponible"), ((Number)boissonJson.get("nbUtilisations")).intValue(), ((Number)boissonJson.get("priorite")).intValue());
		return(boisson);
	}

	public static Plat lirePlat(String nom){
		JSONObject platJson = new JSONObject();
		platJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats\\" + nom + ".json");

		Plat plat = new Plat((String)platJson.get("id"), (String)platJson.get("nom"), ((Number)platJson.get("cout")).floatValue(), (boolean)platJson.get("estDisponible"), ((Number)platJson.get("nbUtilisations")).intValue(), ((Number)platJson.get("priorite")).intValue(), ((Number)platJson.get("nbMaxIngredients")).intValue(), ((Number)platJson.get("nbMaxSauces")).intValue(), (boolean)platJson.get("utilisePain"), ((Number)platJson.get("prix")).floatValue());
		return(plat);
	}

	public static SupplementBoisson lireSupplementBoisson(String nom){
		JSONObject supplementBoissonJson = new JSONObject();
		supplementBoissonJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson\\" + nom + ".json");

		SupplementBoisson supplementBoisson = new SupplementBoisson((String)supplementBoissonJson.get("id"), (String)supplementBoissonJson.get("nom"), ((Number)supplementBoissonJson.get("cout")).floatValue(), (boolean)supplementBoissonJson.get("estDisponible"), ((Number)supplementBoissonJson.get("nbUtilisations")).intValue(), ((Number)supplementBoissonJson.get("priorite")).intValue(), ((Number)supplementBoissonJson.get("prix")).floatValue());
		return(supplementBoisson);
	}

	public static Membre lireMembre(String nomFichier){
		JSONObject membreJson = new JSONObject();
		membreJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Membres\\" + nomFichier + ".json");

		try {
			Membre membre = new Membre((String)membreJson.get("id"), (String)membreJson.get("nom"), (String)membreJson.get("prenom"), (String)membreJson.get("surnom"), (String)membreJson.get("poste"), formatDate.parse((String)membreJson.get("dateNaissance")), ((Number)membreJson.get("nbCommandes")).intValue(), ((Number)membreJson.get("nbServices")).intValue(), ((Number)membreJson.get("tempsMoyenCommande")).floatValue());
			return(membre);
		}
		catch (Exception e) {
			e.printStackTrace();
			Membre membre = new Membre();
			return(membre);
		}
	}

	public static Commande lireCommande(Date moment, int numero){
		JSONObject commandeJson = new JSONObject();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 10){
			zeros += "0";
			if((int)numero < 100){
				zeros += "0";
			}
		}

		commandeJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(moment) + "\\" + mois.format(moment) + "\\" + jour.format(moment) + "\\" + zeros + numero + ".json");

		try {
			Date momentCommande = new Date(((Number)commandeJson.get("moment")).longValue());
			int numeroCommande = ((Number)commandeJson.get("numero")).intValue();
			Plat platCommande = BaseDonnees.getPlat((String)commandeJson.get("plat"));

			List<Ingredient> ingredientsCommande = new ArrayList<Ingredient>();
			JSONArray ingredientsCommandeJson = (JSONArray) commandeJson.get("ingredients");
			Iterator<String> iterateurIng = ingredientsCommandeJson.iterator();
			while(iterateurIng.hasNext()){
				ingredientsCommande.add(BaseDonnees.getIngredient((String)iterateurIng.next()));
			}

			List<Sauce> saucesCommande = new ArrayList<Sauce>();
			JSONArray saucesCommandeJson = (JSONArray) commandeJson.get("sauces");
			Iterator<String> iterateurSau = saucesCommandeJson.iterator();
			while(iterateurSau.hasNext()){
				saucesCommande.add(BaseDonnees.getSauce((String)iterateurSau.next()));
			}

			Dessert dessertCommande = BaseDonnees.getDessert((String)commandeJson.get("dessert"));
			Boisson boissonCommande = BaseDonnees.getBoisson((String)commandeJson.get("boisson"));
			SupplementBoisson supplementBoissonCommande = BaseDonnees.getSupplementBoisson((String)commandeJson.get("supplementBoisson"));

			Commande commande = new Commande(momentCommande, numeroCommande, platCommande, ingredientsCommande, saucesCommande, dessertCommande, boissonCommande, supplementBoissonCommande);
			return(commande);
		}
		catch (Exception e) {
			e.printStackTrace();
			Commande commande = new Commande(new Date(0), 0, BaseDonnees.getRienPlat(), new ArrayList<Ingredient>(), new ArrayList<Sauce>(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());
			return(commande);
		}
	}

	public static CommandeAssignee lireCommandeAssignee(Date moment, int numero){
		JSONObject commandeJson = new JSONObject();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 10){
			zeros += "0";
			if((int)numero < 100){
				zeros += "0";
			}
		}

		commandeJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(moment) + "\\" + mois.format(moment) + "\\" + jour.format(moment) + "\\" + zeros + numero + ".json");

		try {
			Date momentCommande = new Date(((Number)commandeJson.get("moment")).longValue());
			int numeroCommande = ((Number)commandeJson.get("numero")).intValue();
			Plat platCommande = BaseDonnees.getPlat((String)commandeJson.get("plat"));

			List<Ingredient> ingredientsCommande = new ArrayList();
			JSONArray ingredientsCommandeJson = (JSONArray) commandeJson.get("ingredients");
			Iterator<String> iterateurIng = ingredientsCommandeJson.iterator();
			while(iterateurIng.hasNext()){
				ingredientsCommande.add(BaseDonnees.getIngredient((String)iterateurIng.next()));
			}

			List<Sauce> saucesCommande = new ArrayList<Sauce>();
			JSONArray saucesCommandeJson = (JSONArray) commandeJson.get("sauces");
			Iterator<String> iterateurSau = saucesCommandeJson.iterator();
			while(iterateurSau.hasNext()){
				saucesCommande.add(BaseDonnees.getSauce((String)iterateurSau.next()));
			}

			Dessert dessertCommande = BaseDonnees.getDessert((String)commandeJson.get("dessert"));
			Boisson boissonCommande = BaseDonnees.getBoisson((String)commandeJson.get("boisson"));
			SupplementBoisson supplementBoissonCommande = BaseDonnees.getSupplementBoisson((String)commandeJson.get("supplementBoisson"));

			Membre membreCommande = BaseDonnees.getMembre((String)commandeJson.get("membre"));
			Date momentAssignationCommande = new Date(((Number)commandeJson.get("momentAssignation")).longValue());
			boolean estRealiseeCommande = (boolean)commandeJson.get("estRealisee");
			Date momentRealisationCommande = new Date(((Number)commandeJson.get("momentRealisation")).longValue());
			boolean estDonneeCommande = (boolean)commandeJson.get("estDonnee");

			Commande commande = new Commande(momentCommande, numeroCommande, platCommande, ingredientsCommande, saucesCommande, dessertCommande, boissonCommande, supplementBoissonCommande);
			CommandeAssignee commandeAssignee = new CommandeAssignee(commande, membreCommande, momentAssignationCommande, estRealiseeCommande, momentRealisationCommande, estDonneeCommande);
			
			return(commandeAssignee);
		}
		catch (Exception e) {
			e.printStackTrace();
			Commande commande = new Commande(new Date(0), 0, BaseDonnees.getRienPlat(), new ArrayList<Ingredient>(), new ArrayList<Sauce>(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());
			Membre membre = new Membre("f38aa97b-2c4b-491e-be10-884e48fbb6c2", "", "", "", "", new Date(0), 0, 0, 0);
			CommandeAssignee commandeAssignee = new CommandeAssignee(commande, membre, new Date(0), false, new Date(0), false);
			return(commandeAssignee);
		}
	}

	public static Service lireService(Date date){
		JSONObject serviceJson = new JSONObject();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		serviceJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\" + "_service.json");
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date dateService = formatDate.parse((String)serviceJson.get("date"));
			float nbBaguettesBaseService = ((Number)serviceJson.get("nbBaguettesBase")).floatValue();
			float nbBaguettesUtiliseesService = ((Number)serviceJson.get("nbBaguettesUtilisees")).floatValue();
			Membre ordiService = BaseDonnees.getMembre((String)serviceJson.get("ordi"));

			List<Membre> commisService = new ArrayList<Membre>();
			JSONArray commisServiceJson = (JSONArray) serviceJson.get("commis");
			Iterator<String> iterateurCom = commisServiceJson.iterator();
			while(iterateurCom.hasNext()){
				commisService.add(BaseDonnees.getMembre((String)iterateurCom.next()));
			}

			List<Membre> confectionService = new ArrayList<Membre>();
			JSONArray confectionServiceJson = (JSONArray) serviceJson.get("confection");
			Iterator<String> iterateurCon = confectionServiceJson.iterator();
			while(iterateurCon.hasNext()){
				confectionService.add(BaseDonnees.getMembre((String)iterateurCon.next()));
			}

			List<Commande> commandesService = new ArrayList<Commande>();

			Service service = new Service(dateService, commandesService, nbBaguettesBaseService, nbBaguettesUtiliseesService, ordiService, commisService, confectionService);

			return(service);
		} catch (Exception e){
			e.printStackTrace();

			Membre membre = new Membre("f38aa97b-2c4b-491e-be10-884e48fbb6c2", "", "", "", "", new Date(0), 0, 0, 0);
			Service service = new Service(new Date(0), new ArrayList<Commande>(), 0, 0, membre, new ArrayList<Membre>(), new ArrayList<Membre>());

			return(service);
		}
	}

	public static Parametres lireParametres(){
		JSONObject parametresJson = new JSONObject();

		parametresJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Paramètres\\paramètres.json");

		try {
			float prixIngredientSuppParametres = ((Number)parametresJson.get("prixIngredientSupp")).floatValue();
			float prixBoissonParametres = ((Number)parametresJson.get("prixBoisson")).floatValue();
			float reducMenuParametres = ((Number)parametresJson.get("reducMenu")).floatValue();

			Parametres parametres = new Parametres(prixIngredientSuppParametres, prixBoissonParametres, reducMenuParametres);

			return(parametres);
		} catch (Exception e){
			e.printStackTrace();

			Parametres parametres = new Parametres(0.3f, 0.5f, 0.3f);

			return(parametres);
		}
	}

	public static boolean estAssignee(Date moment, int numero){
		JSONObject commandeJson = new JSONObject();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 10){
			zeros += "0";
			if((int)numero < 100){
				zeros += "0";
			}
		}

		commandeJson = lireObjet(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(moment) + "\\" + mois.format(moment) + "\\" + jour.format(moment) + "\\" + zeros + numero + ".json");
		boolean estAssignee = false;

		try {
			estAssignee = (boolean)commandeJson.get("assignee");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return(estAssignee);
	}
}
