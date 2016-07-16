/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.controller;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceParecerDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceRadocDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicParecerDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicRadocDAO;
import com.mongodb.client.MongoDatabase;
import java.util.List;

/**
 * Classe responsável por implementar a interface {@code ParecerRepository}.
 *
 * @author Pedro Henrique Silva Farias
 * @see ParecerRepository
 */
public class BasicParecerRepository implements ParecerRepository {

    /**
     * Instancia de {@code InterfaceRadocDAO} para manipular os Radocs.
     *
     * @see Radoc
     * @see InderfaceRadocDAO
     */
    private InterfaceRadocDAO radocDAO;

    /**
     * Instancia de {@code InterfaceParecerDAO} para manipular as Resoluções.
     *
     * @see Parecer
     * @see InderfaceParecerDAO
     */
    private InterfaceParecerDAO parecerDAO;

    /**
     * Construtor responsável por inicializar a interfaces:
     * {@code InterfaceParecerDAO} e {@code InterfaceRadocDAO}
     *
     * @param mongodatabase Instancia referente ao banco de dados mongo
     * utilizado.
     * @see InterfaceParecerDAO
     * @see InterfaceRadocDAO
     * @see ParecerRepository
     * @see Radoc
     * @see Parecer
     */
    public BasicParecerRepository(MongoDatabase mongodatabase) {
        this.radocDAO = new BasicRadocDAO(mongodatabase, "Radoc");
        this.parecerDAO = new BasicParecerDAO(mongodatabase, "Parecer");
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public void adicionaNota(String id, Nota nota) {
        Parecer parecerInstance = this.parecerDAO.findParecerById(id);
        List<Nota> notas = parecerInstance.getNotas();
        notas.add(nota);
        
        Parecer novoParecer = new Parecer(parecerInstance.getId(), parecerInstance.getResolucao(),
                parecerInstance.getRadocs(), parecerInstance.getPontuacoes(),
                parecerInstance.getFundamentacao(), notas);
        
        this.parecerDAO.updateParecerById(id, novoParecer);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public void removeNota(String id, Avaliavel original) {
        Parecer parecerInstance = this.parecerDAO.findParecerById(id);
        List<Nota> notas = parecerInstance.getNotas();
        
        for(int index = 0; index < notas.size(); index++){
            Nota nota = notas.get(index);
            if(nota.getItemOriginal().equals(original)){
                notas.remove(index);
                break;
            }
        }
        
        Parecer novoParecer = new Parecer(parecerInstance.getId(), parecerInstance.getResolucao(),
                parecerInstance.getRadocs(), parecerInstance.getPontuacoes(),
                parecerInstance.getFundamentacao(), notas);
        
        this.parecerDAO.updateParecerById(id, novoParecer);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public void persisteParecer(Parecer parecer) {
        this.parecerDAO.insertParecer(parecer);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public void atualizaFundamentacao(String parecer, String fundamentacao) {
        Parecer parecerInstance = this.parecerDAO.findParecerById(parecer);
        Parecer novoParecer = new Parecer(parecerInstance.getId(), parecerInstance.getResolucao(),
                parecerInstance.getRadocs(), parecerInstance.getPontuacoes(),
                fundamentacao, parecerInstance.getNotas());
        
        this.parecerDAO.updateParecerById(parecer, novoParecer);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public Parecer byId(String id) {
        Parecer parecer;

        try {
            parecer = this.parecerDAO.findParecerById(id);
        } catch (IdentificadorDesconhecido e) {
            parecer = null;
        }
        return (parecer);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public void removeParecer(String id) {
        this.parecerDAO.deleteParecerById(id);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public Radoc radocById(String identificador) {
        Radoc radoc;

        try {
            radoc = this.radocDAO.findRadocById(identificador);
        } catch (IdentificadorDesconhecido e) {
            radoc = null;
        }
        return (radoc);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public String persisteRadoc(Radoc radoc) {
        String id;

        try {
            this.radocDAO.insertRadoc(radoc);
            id = radoc.getId();
        } catch (IdentificadorExistente e) {
            id = null;
        }
        return (id);
    }

    /**
     * A descrição do método está na interface {@code ParecerRepository}
     *
     * @see ParecerRepository
     */
    @Override
    public void removeRadoc(String identificador) {
        this.radocDAO.deleteRadocById(identificador);
    }

}
