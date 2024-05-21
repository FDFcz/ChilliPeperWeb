package com.example.Controlers;

import com.example.Structures.Cron;
import com.example.Structures.Plant;
import com.example.Structures.Teracota;
import com.example.Structures.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ChiliPeperApplication {
	private static DatabaseControler dbControler ;
	private static AuthorizationControler aControler = new AuthorizationControler();

	public static void main(String[] args) {
		SpringApplication.run(ChiliPeperApplication.class, args);
		dbControler = new DatabaseControler();
		dbControler.initDB();
	}
	public static String getHTMLTable(String table)
	{
		String[][] myDictionary = dbControler.getAllTAble(table);
		String htmlTag = "<table><tr><th colspan="+(myDictionary[0].length)+">"+table+"</th></tr><tr>";
		if(myDictionary.length>0) {
			for(int j = 0; j < myDictionary[0].length; j++)
			{
				htmlTag += "<th>" + myDictionary[0][j] +"</th>";
			}
			htmlTag += "</tr>";

			for(int i = 1; i < myDictionary.length; i++) {
				htmlTag += "<tr>";
				for(int j = 0; j < myDictionary[0].length; j++) {
					htmlTag += "<td>" + myDictionary[i][j] + "</td>";
				}
				htmlTag += "</tr>";
			}
		}
		htmlTag +="</table>";

		return htmlTag;
	}
//region [teracota]
	public static void addTeracota(Customer user, Teracota teracota) {dbControler.addTeracota(user.getId(), teracota.getNome(),teracota.getPlantID());}
	public static Teracota getTeracota(int teraID) {return dbControler.getTeracota(teraID);}
	public static void deleteTeracota(int teraID){dbControler.deleteTeracota(teraID);}
	public static Plant getPlant(int plantTypeID) {return dbControler.getPlant(plantTypeID);}
	public static List<Cron> getCronsForTeracota(int teraID){return dbControler.getCronToTeracota(teraID);}
	public static void addNewCron(int teraID) {dbControler.addNewCron(teraID);}
	public static void updateCron(Cron cron) {dbControler.updateCron(cron);}
	public static void deleteCron(int cronID){dbControler.deleteCron(cronID);}
	//endregion

//region [Customer]
	public static int getUserID(String userName) {return dbControler.getUserID(userName);}
	public static Customer getUser(int id) {return dbControler.getUser(id);}
	public static Customer getUserWithTeracotas(int id) {return dbControler.getUserWithTeracotas(id);}
	public static int registryNewUser(String userName, String password) {return dbControler.registryNewUser(userName, aControler.getHash(password));}
	//endregion
}
