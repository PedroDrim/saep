/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class TestResolucao {

    private static Resolucao resolucao;
    
    public static void test1() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2*5");

        Regra regra1 = new Regra("B + C", 60 , 0 , dependeDe, "Regra de teste", "A");
        regra1.setTipoRegra(Regra.EXPRESSAO);
        
        List<String> dependeDe2 = new ArrayList<>();
        dependeDe.add("X = 1 + 1");
        dependeDe.add("Y = 2");
        dependeDe.add("C = 2*5");
        
        Regra regra2 = new Regra(Regra.CONDICIONAL, " X > Y", "2*C", "C-2", 0, dependeDe2, 10, 5);
        
        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Date date = Calendar.getInstance().getTime();
        Resolucao resolucao1 = new Resolucao("Res1", "resolucao de teste", date, regras);

        Document document = SaepConversor.convertResolucaoToDocument(resolucao1);
        System.out.println("Resolucao1 : " + document.toJson());
        
        Resolucao resolucao1R = SaepDeconversor.deconvertDocumentToResolucao(document);

        Document documentReso = SaepConversor.convertResolucaoToDocument(resolucao1R);
        System.out.println("Resolucao2 : " + documentReso.toJson());
        
        resolucao = resolucao1;
        
        System.out.println("Fim do teste");
    }
    
    public static Resolucao get(){
        return(resolucao);
    }

}
