/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import java.util.HashSet;
import java.util.Set;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class TestTipo {

    private static Tipo tipo;
    
    public static void test1() {

        Atributo atributo1 = new Atributo("T_att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("T_att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("T_att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("Teste", "T_tipo1", "Um tipo de teste", atributos);

        Document tipoDoc = SaepConversor.convertTipoToDocument(tipo1);
        System.out.println("Tipo1 : " + tipoDoc.toJson());

        Tipo tipoR = SaepDeconversor.deconvertDocumentToTipo(tipoDoc);

        tipo = tipo1;
        
        System.out.println("Fim do teste");
    }
    
    public static Tipo get(){
        return(tipo);
    }
}
