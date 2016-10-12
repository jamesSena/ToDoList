package br.senai.sp.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.modelo.ItemLista;
import br.senai.sp.todolist.modelo.Lista;

/**
 * @author jamesson
 *
 */
@Repository
public class ItemDAO {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void marcarFeito(Long idItem, Boolean valor){
		ItemLista item = manager.find(ItemLista.class, idItem);
		item.setFeito(valor);
		manager.merge(item);
	}
	@Transactional
	public void inserir(Long idLista, ItemLista item){
		item.setLista(manager.find(Lista.class,idLista));
		manager.persist(item);
	}

	public ItemLista listar(Long id){
			ItemLista itemLista = manager.find(ItemLista.class, id);
		return itemLista;
		}
}
