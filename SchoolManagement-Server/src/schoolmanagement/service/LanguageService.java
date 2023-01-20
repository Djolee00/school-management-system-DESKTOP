/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package schoolmanagement.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import schoolmanagement.commonlib.model.Language;

/**
 *
 * @author ivano
 */
public interface LanguageService {

    public List<Language> getAllLanguages() throws IOException,SQLException;
    
}
