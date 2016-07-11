/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio;

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

        Regra regra1 = new Regra("A",Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO" , 52 , dependeDe);
        
        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        
        Regra regra2 = new Regra("Z",Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO" , 52 , dependeDe2);
        
        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Date date = Calendar.getInstance().getTime();
        Resolucao resolucao1 = new Resolucao("Res1", "Resolucao1", "resolucao de teste", date, regras);

        Document document = SaepConversor.convertResolucaoToDocument(resolucao1);
        System.out.println("Resolucao1 : " + document.toJson());
        
        Resolucao resolucao1R = SaepDeconversor.deconvertDocumentToResolucao(document);

        Document documentReso = SaepConversor.convertResolucaoToDocument(resolucao1R);
        System.out.println("Resolucao1R : " + documentReso.toJson());
        
        resolucao = resolucao1;
        
        System.out.println("Fim do teste");
    }
    
    public static Resolucao get(){
        return(resolucao);
    }

}
