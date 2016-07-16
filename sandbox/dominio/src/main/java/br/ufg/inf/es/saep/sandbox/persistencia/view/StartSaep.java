/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.view;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceParecerDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceRadocDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceResolucaoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceTipoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicParecerDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicRadocDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicResolucaoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicTipoDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro Henrique Silva Farias
 */
public class StartSaep {

    /**
     * Método de inicialização do sistema.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MongoClient cliente = new MongoClient();
        MongoDatabase mongoDatabase = cliente.getDatabase("Saep");

        InterfaceRadocDAO radocDAO = new BasicRadocDAO(mongoDatabase, "Radoc");
        InterfaceTipoDAO tipoDAO = new BasicTipoDAO(mongoDatabase, "Tipo");
        InterfaceResolucaoDAO resolucaoDAO = new BasicResolucaoDAO(mongoDatabase, "Resolucao");
        InterfaceParecerDAO parecerDAO = new BasicParecerDAO(mongoDatabase, "Parecer");
        
        mongoDatabase.drop();
        System.out.println("Fim do programa");
    }

}
