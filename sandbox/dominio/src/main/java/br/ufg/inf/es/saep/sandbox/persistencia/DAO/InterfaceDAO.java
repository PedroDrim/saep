/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.DAO;

import java.util.List;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public interface InterfaceDAO {
    
    void insert(Document object);
    void update(Object id, Document object);
    Document search(Object id);
    boolean remove(Object id);
    List<String> listAll();
}
