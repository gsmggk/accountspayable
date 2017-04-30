package com.gsmggk.accountspayable.services;

import org.junit.Test;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.services.util.PasswordHash;

public class PaswordHashTest {
	@Test
	public void passHashTest() {
		try {	
			// Print out 10 hashes
			for (int i = 0; i < 10; i++)
				System.out.println(PasswordHash.createHash("p\r\nassw0Rd!"));

			// Test password validation
		
			System.out.println("Running tests...");
			for (int i = 0; i < 100; i++) {
				String password = "" + i;
				String hash=PasswordHash.createHash(password); ;
			
				
				String secondHash = PasswordHash.createHash(password);
				Assert.isTrue(!hash.equals(secondHash), "FAILURE: TWO HASHES ARE EQUAL!");
				
				String wrongPassword = "" + (i + 1);
				Assert.isTrue(!PasswordHash.validatePassword(wrongPassword, hash), "FAILURE: WRONG PASSWORD ACCEPTED!");
				Assert.isTrue(PasswordHash.validatePassword(password, hash),"FAILURE: GOOD PASSWORD NOT ACCEPTED!");
				}
		}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		
	}


