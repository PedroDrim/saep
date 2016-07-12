/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.DAO;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class ResolucaoPersistence implements ResolucaoRepository{

    ResolucaoDAO daoResolucao;
    TipoDAO daoTipo;
    
    public ResolucaoPersistence(MongoDatabase database){
        this.daoResolucao = new ResolucaoDAO(database);
        this.daoTipo = new TipoDAO(database);
    }
    
    @Override
    public Resolucao byId(String identificador) {
        
        Resolucao resolucao = null;
        
        try{
            Document document = this.daoResolucao.search(identificador);
            resolucao = SaepDeconversor.deconvertDocumentToResolucao(document);
            
        }catch(IdentificadorDesconhecido e){
            e.printStackTrace();
        }    
        
        return(resolucao);
    }

    @Override
    public String persiste(Resolucao resolucao) {
        
        Document document = SaepConversor.convertResolucaoToDocument(resolucao);
        this.daoResolucao.insert(document);
        
        return(resolucao.getId());
    }

    @Override
    public boolean remove(String identificador) {
        this.daoResolucao.remove(identificador);
        return(true);
    }

    @Override
    public List<String> resolucoes() {
        return(this.daoResolucao.listAll());
    }

    @Override
    public void persisteTipo(Tipo tipo) {
        Document document = SaepConversor.convertTipoToDocument(tipo);
        this.daoTipo.insert(document);
    }

    @Override
    public void removeTipo(String codigo) {
        this.daoTipo.remove(codigo);
    }

    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        Document document = this.daoTipo.search(codigo);
        return(SaepDeconversor.deconvertDocumentToTipo(document));
    }

    @Override
    public List<Tipo> tiposPeloNome(String nome) {
    
        List<String> listaNomes = this.daoTipo.listAll();
        List<Tipo> listaTipo = new ArrayList<>();
        
        for(String id : listaNomes){
            
            if(id.contains(nome)){
                
                Document document = this.daoTipo.search(id);
                Tipo tipo = SaepDeconversor.deconvertDocumentToTipo(document);
                listaTipo.add(tipo);
            }
        }
        
        return(listaTipo);
  }
}
