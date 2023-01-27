/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.commonlib.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ivano
 */
public class Tutor implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Language> languages;

    public Tutor(String firstName, String lastName, List<Language> languages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.languages = languages;
    }

    public Tutor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
