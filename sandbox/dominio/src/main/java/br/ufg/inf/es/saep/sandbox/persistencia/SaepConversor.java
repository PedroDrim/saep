/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.SaepException;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class SaepConversor {

    public static Document convertValorToDocument(Valor valor, int tipo) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String valorJson = mapper.writeValueAsString(valor);
            Document document = Document.parse(valorJson);
            document.append("tipo", tipo);

            return (document);

        } catch (IOException e) {
            throw new SaepException("Nao foi possivel converter o valor para Document.");
        }
    }

    public static Document convertRelatoToDocument(Relato relato, Tipo tipo) {

        Document document;

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
            Document documentValor = convertValorToDocument(valor, atributo.getTipo());

            documentAtributoCompleto.append(atributo.getNome(), documentValor);
        }

        document.append("valorPorNome", documentAtributoCompleto);

        return (document);
    }

    public static Document convertAtributoToDocument(Atributo atributo) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String atributoJson = mapper.writeValueAsString(atributo);
            Document document = Document.parse(atributoJson);

            return (document);

        } catch (IOException e) {
            throw new SaepException("Nao foi possivel converter o atributo " + atributo.getNome() + " para Document.");
        }
    }

    public static Document converterRadocToDocument(Radoc radoc, List<String> tipos) {

        Document document = new Document();
        
        return (document);

    }
}
