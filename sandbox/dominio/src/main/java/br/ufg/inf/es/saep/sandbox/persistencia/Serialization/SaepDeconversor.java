/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.Serialization;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Grupo;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class SaepDeconversor {

    public static Valor deconvertDocumentToValor(Document document) {

        try {

            document.remove("_id");
            Integer tp = document.getInteger("tipo");
            Valor valor = null;

            switch (tp.intValue()) {

                case Atributo.LOGICO:
                    valor = new Valor(document.getBoolean("boolean").booleanValue());
                    break;

                case Atributo.REAL:
                    valor = new Valor(document.getDouble("float").floatValue());
                    break;

                case Atributo.STRING:
                    valor = new Valor(document.getString("string"));
                    break;
            }

            return (valor);
        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para resolucao.");
        }

    }

    public static Relato deconvertDocumentToRelato(Document document) {

        try {

            document.remove("_id");

            Document map = (Document) document.get("valorPorNome");

            Map<String, Valor> mapaValores = new HashMap<>();
            for (String key : map.keySet()) {

                Document valorDoc = (Document) map.get(key);
                Valor valor = SaepDeconversor.deconvertDocumentToValor(valorDoc);
                mapaValores.put(key, valor);
            }

            Relato relato = new Relato(document.getString("tipo"), mapaValores);

            return (relato);

        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para relato.");
        }

    }

    public static Atributo deconvertDocumentToAtributo(Document document) {

        try {

            document.remove("_id");

            String nome = document.getString("nome");
            String descricao = document.getString("descricao");
            Integer tp = document.getInteger("tipo");

            Atributo atributo = new Atributo(nome, descricao, tp.intValue());
            return (atributo);

        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para atributo.");
        }
    }

    public static Radoc deconvertDocumentToRadoc(Document document) {

        try {

            document.remove("_id");
            
            int i  = document.getInteger("tamanhoRelato").intValue();
            Document relatosDoc = (Document) document.get("relatos");
            
            List<Relato> listRelatos = new ArrayList<>();
            for(int j = 0; j < i; j++){
                Document relato = (Document) relatosDoc.get(String.valueOf(j));
                Relato rel = SaepDeconversor.deconvertDocumentToRelato(relato);
                listRelatos.add(rel);
            }
            
            Radoc radoc = new Radoc(
                    document.getString("id"),
                    document.getInteger("anoBase").intValue(),
                    listRelatos);
            
            return (radoc);
            
        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para radoc.");
        }
    }

    public static Tipo deconvertDocumentToTipo(Document document) {

        try {

            document.remove("_id");

            String id = document.getString("id");
            String nome = document.getString("nome");
            String descricao = document.getString("descricao");

            List<Document> listaDocument = (ArrayList<Document>) document.get("atributos");
            Set<Atributo> atributos = new HashSet<>();

            for (Document docAtributo : listaDocument) {
                atributos.add(SaepDeconversor.deconvertDocumentToAtributo(docAtributo));
            }

            Tipo tipo = new Tipo(id, nome, descricao, atributos);

            return (tipo);

        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para tipo.");
        }
    }

    public static Grupo deconvertDocumentToGrupo(Document document) {

        try {
            document.remove("_id");

            ObjectMapper mapper = new ObjectMapper();
            Grupo grupo = mapper.readValue(document.toJson(), Grupo.class);

            return (grupo);

        } catch (IOException e) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para grupo.");
        }
    }

    public static Regra deconvertDocumentToRegra(Document document) {

        try {

            document.remove("_id");

            Double ppr = (Double) document.get("pontosPorItem");
            Double vmax = (Double) document.get("valorMaximo");
            Double vmin = (Double) document.get("valorMinimo");

            List<String> dependeDe = (ArrayList<String>) document.get("dependeDe");

            Regra regra = new Regra(
                    document.getString("variavel"),
                    document.getInteger("tipo"),
                    document.getString("descricao"),
                    vmax.floatValue(), vmin.floatValue(),
                    document.getString("expressao"),
                    document.getString("entao"),
                    document.getString("senao"),
                    document.getString("tipoRelato"),
                    ppr.longValue(), dependeDe);

            return (regra);

        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para regra.");
        }
    }

    public static Resolucao deconvertDocumentToResolucao(Document document) {

        try {

            document.remove("_id");

            List<Document> listaDocument = (ArrayList<Document>) document.get("regras");
            List<Regra> listaRegras = new ArrayList<>();

            for (Document docRegras : listaDocument) {
                listaRegras.add(SaepDeconversor.deconvertDocumentToRegra(docRegras));
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            long longdate = document.getLong("dataAprovacao").longValue();
            Date date = new Date(longdate);

            Resolucao resolucao = new Resolucao(
                    document.getString("id"),
                    document.getString("nome"),
                    document.getString("descricao"),
                    date, listaRegras);

            return (resolucao);

        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para resolucao.");
        }

    }

    public static Pontuacao deconvertDocumentToPontuacao(Document document) {

        try {

            document.remove("_id");

            Document valDoc = (Document) document.get("valor");
            Valor valor = SaepDeconversor.deconvertDocumentToValor(valDoc);
            Pontuacao pontuacao = new Pontuacao(document.getString("atributo"), valor);

            return (pontuacao);

        } catch (Exception ex) {
            throw new IdentificadorDesconhecido("Nao foi possivel converter o Document para pontuacao.");
        }

    }
}
