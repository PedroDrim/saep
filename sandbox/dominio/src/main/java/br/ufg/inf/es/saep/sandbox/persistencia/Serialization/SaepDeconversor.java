/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.Serialization;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Grupo;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.SaepException;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class SaepDeconversor {

    public static Valor deconvertDocumentToValor(Document document) {

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

        return(valor);
    }

    public static Relato deconvertDocumentToRelato(Document document) {

        /*Document document;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String relatoJson = mapper.writeValueAsString(relato);
            document = Document.parse(relatoJson);

        } catch (IOException e) {
            throw new SaepException("Nao foi possivel converter o relato  para Document.");
        }

        Document documentAtributoCompleto = new Document();

        for (Atributo atributo : tipo.getAtributos()) {

            Valor valor = relato.get(atributo.getNome());
            Document documentValor = deconvertValorToDocument(valor, atributo.getTipo());

            documentAtributoCompleto.append(atributo.getNome(), documentValor);
        }

        document.append("valorPorNome", documentAtributoCompleto);
         */
        Relato relato = new Relato("1", null);
        return (relato);
    }

    public static Atributo deconvertDocumentToAtributo(Document document) {

        String nome = document.getString("nome");
        String descricao = document.getString("descricao");
        Integer tp = document.getInteger("tipo");

        Atributo atributo = new Atributo(nome, descricao, tp.intValue());
        return (atributo);

    }

    public static Radoc deconvertDocumentToRadoc(Document document) {

        Radoc radoc = new Radoc(null);

        return (radoc);
    }

    public static Tipo deconvertDocumentToTipo(Document document) {

        document.remove("_id");

        String nome = document.getString("nome");
        String codigo = document.getString("codigo");
        String descricao = document.getString("descricao");

        List<Document> listaDocument = (ArrayList<Document>) document.get("atributos");
        Set<Atributo> atributos = new HashSet<>();

        for (Document docAtributo : listaDocument) {
            atributos.add(SaepDeconversor.deconvertDocumentToAtributo(docAtributo));
        }

        Tipo tipo = new Tipo(nome, codigo, descricao, atributos);

        return (tipo);
    }

    public static Grupo deconvertDocumentToGrupo(Document document) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Grupo grupo = mapper.readValue(document.toJson(), Grupo.class);

            return (grupo);

        } catch (IOException e) {
            throw new SaepException("Nao foi possivel converter o Document para grupo.");
        }
    }

    public static Regra deconvertDocumentToRegra(Document document) {

        Regra regra = new Regra(null, 0, 0, null, null, null);

        List<String> dependeDe = (ArrayList<String>) document.get("dependeDe");

        regra.setDependeDe(dependeDe);
        regra.setDescricao(document.getString("descricao"));
        regra.setExpressao(document.getString("expressao"));
        regra.setEntao(document.getString("entao"));
        regra.setSenao(document.getString("senao"));

        Double ppr = (Double) document.get("pontosPorRelato");
        Double vmax = (Double) document.get("valorMaximo");
        Double vmin = (Double) document.get("valorMinimo");
        regra.setPontosPorRelato(ppr.intValue());
        regra.setValorMaximo(vmax.floatValue());
        regra.setValorMaximo(vmin.floatValue());

        regra.setTipo(document.getString("tipo"));
        regra.setTipoRegra(document.getInteger("tipoRegra"));
        regra.setVariavel(document.getString("variavel"));

        return (regra);
    }

    public static Resolucao deconvertDocumentToResolucao(Document document) {

        document.remove("_id");
        List<Document> listaDocument = (ArrayList<Document>) document.get("regras");
        List<Regra> listaRegras = new ArrayList<>();

        for (Document docRegras : listaDocument) {
            listaRegras.add(SaepDeconversor.deconvertDocumentToRegra(docRegras));
        }

        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataAprovacao = formato.parse(document.getString("dataAprovacao"));

            Resolucao resolucao = new Resolucao(document.getString("identificador"),
                    document.getString("descricao"), dataAprovacao, listaRegras);

            return (resolucao);

        } catch (ParseException ex) {
            throw new SaepException("Nao foi possivel converter o Document para resolucao.");
        }

    }
}
