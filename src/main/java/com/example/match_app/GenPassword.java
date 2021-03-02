package com.example.match_app;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
public class GenPassword {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println(new Pbkdf2PasswordEncoder().encode("demo"));
	}

}
