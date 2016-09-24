package br.senai.sp.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.modelo.ItemLista;
import br.senai.sp.todolist.modelo.Lista;


@Repository
public class ListaDAO {

	@PersistenceContext
	private EntityManager manager;

	//Inserir informa��es no banco de dados
	@Transactional
	public void inserir(Lista lista){
		manager.persist(lista);
	}

	//select das informa��es no banco de dados
	public List<Lista> listar(){
		TypedQuery<Lista> query= manager.createQuery("select l from Lista l", Lista.class);
		return query.getResultList();
	}
	
	//select das informa��es no banco de dados
	public Lista listar(Long id){
		Lista lista = manager.find(Lista.class, id);
		return lista;
	}

	//Deletando informa��o do banco de dados via id
	@Transactional
	public void excluir(Long id){
		Lista lista = manager.find(Lista.class, id);
		manager.remove(lista);
	}

	//Deletando informa��es do item da lista via id do item
	@Transactional
	public void excluirItem(Long idItem){
		ItemLista item = manager.find(ItemLista.class, idItem);
		Lista lista = item.getLista();
		lista.getItens().remove(item);
		manager.merge(lista);
	}

}
