package br.senai.sp.todolist.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.todolist.dao.ListaDAO;
import br.senai.sp.todolist.modelo.ItemLista;
import br.senai.sp.todolist.modelo.Lista;

@RestController
public class ListaRestController {
	
	@Autowired
	private ListaDAO listaDAO;
	
	//Insert
	@RequestMapping(
			value="/lista", 
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Lista> inserir(@RequestBody String strLista){
		
		Lista lista = new Lista();
		List<ItemLista> itens = new ArrayList<>();
		
		try {
			JSONObject jobj = new JSONObject(strLista);
			lista.setTitulo(jobj.getString("titulo"));
	        JSONArray jsonArray = jobj.getJSONArray("itens");
	        for (int i = 0; i < jsonArray.length(); i++) {
				ItemLista item = new ItemLista();
				item.setDescricao(jsonArray.getString(i));
				item.setLista(lista);
				itens.add(item);
			}
			lista.setItens(itens);
			listaDAO.inserir(lista);
			URI location = new URI("/lista/"+lista.getId());
			return  ResponseEntity.created(location).body(lista);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}

	//Select
	@RequestMapping(
			value="/lista",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Lista> listar(){
		List<Lista> listar = listaDAO.listar();
		return listar;
	}
	
	//Select Uma lista
	@RequestMapping(
			value="/lista/{id}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Lista listar(@PathVariable("id")long id){
		Lista listar = listaDAO.listar(id);
		return listar;
	}

	//Delete
	@RequestMapping(
			value="/lista/{id}",
			method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") long idLista){
		listaDAO.excluir(idLista);

		return ResponseEntity.noContent().build();
	
	}
	//Delete item
	@RequestMapping(
			value="/item/{idItem}",
			method=RequestMethod.DELETE)
	public ResponseEntity<Void> excluirItem(@PathVariable("idItem") long idItem){
		listaDAO.excluirItem(idItem);

		return ResponseEntity.noContent().build();		
	}
	
}
