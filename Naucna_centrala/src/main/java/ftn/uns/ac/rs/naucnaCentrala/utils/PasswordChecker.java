package ftn.uns.ac.rs.naucnaCentrala.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PasswordChecker {

	public static boolean checkNewPass(String pass) throws IOException {
		if (!pass.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$")) {
			return false;
		}
		if(!check10000FrequentlyUsed(pass)) {
			return false;
		}
		return true;
	}

	public static boolean check10000FrequentlyUsed(String pass) throws IOException {
		/*
		 * Provera da li je password medju 10000 najkoriscenijih sifara
		 */
		File file = new File("src/main/resources/passwords.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		while ((st = br.readLine()) != null) {
			System.out.println(st);
			if(st.equals(pass)) {
				return false;
			}
		}
		
		return true;
	}

	/*public static void main(String[] args) throws IOException {

		String pass = "Kuhinja007-";
		Boolean b = checkNewPass(pass);
		System.out.println("Password is valid? - " + b);

	}*/
}
