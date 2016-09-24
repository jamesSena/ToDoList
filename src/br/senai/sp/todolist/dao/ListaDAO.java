package br.senai.sp.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.modelo.Lista;


@Repository
public class ListaDAO {

	@PersistenceContext
	private EntityManager manager;
	@Transactional
	public void inserir(Lista lista){
		manager.persist(lista);
	}

	public List<Lista> listar(){
		TypedQuery<Lista> query= manager.createQuery("");
	}
}
