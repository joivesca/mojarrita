package com.onndoo.mojarrita.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.github.javafaker.Faker;
import com.onndoo.mojarrita.model.Profiles;

public class FakeUserGenerator {
    private final Faker faker;
    private static Set<String> generatedUsernames = new HashSet<>();
    private Supplier<String> usernameGenerator;

    public FakeUserGenerator() {
        faker = new Faker();
        setUsernameGenerator("dragonBall");
    }
    
    //JAVA 17
    /*
    public void setUsernameGenerator(String input) {
        switch (input) {
            case "dragonBall" -> usernameGenerator = () -> faker.dragonBall().character();
            case "harryPotter" -> usernameGenerator = () -> faker.harryPotter().character();
            case "rickandMorty" -> usernameGenerator = () -> faker.rickAndMorty().character();

            default -> usernameGenerator = () -> faker.hobbit().character();
        }
    }
    */
    
    //Java 11
    public void setUsernameGenerator(String input) {
        switch (input) {
            case "dragonBall":
                usernameGenerator = () -> faker.dragonBall().character();
                break;
            case "harryPotter":
                usernameGenerator = () -> faker.harryPotter().character();
                break;
            case "rickandMorty":
                usernameGenerator = () -> faker.rickAndMorty().character();
                break;
            default:
                usernameGenerator = () -> faker.hobbit().character();
                break;
        }
    }
    
    private String generateUniqueUsername() {
        String username;
        do {
            username = usernameGenerator.get();
        } while (generatedUsernames.contains(username));

        generatedUsernames.add(username);
        return username;
    }
    
    public Profiles generateFakeUserProfile(String groupName) {
        Profiles profile = new Profiles();
        profile.setUsername(generateUniqueUsername());
        profile.setPassword(faker.internet().password());
        profile.setGroupName(groupName);
        return profile;
    }
}