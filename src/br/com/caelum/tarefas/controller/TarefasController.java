package br.com.caelum.tarefas.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefasController {
  
  @SuppressWarnings("unused")
  private final JdbcTarefaDao dao;
  
  @Autowired
  public TarefasController(JdbcTarefaDao dao) {
    this.dao = dao;
  }
  
  @RequestMapping("/novaTarefa")
  public String form() {
    return "tarefa/formulario";
  }
  
  @RequestMapping("/adicionaTarefa")
  public String adiciona(@Valid Tarefa tarefa, BindingResult result) {

    if(result.hasFieldErrors("descricao"))
      return "tarefa/formulario";   
      
    dao.adiciona(tarefa);
    return "tarefa/adicionada";
  }

  @RequestMapping("/listaTarefas")
  public String lista(Model model) {
    model.addAttribute("tarefas", dao.lista());
    return "tarefa/lista";
  }
  
  @RequestMapping("/removeTarefa")
  public void remove(long id, HttpServletResponse response) {
	System.out.println("Chamou com id " + id);
    dao.remove(dao.buscaPorId(id));
    response.setStatus(200);
  }
  
  @RequestMapping("/mostraTarefa")
  public String mostra(Long id, Model model) {
    model.addAttribute("tarefa", dao.buscaPorId(id));
    return "tarefa/mostra";
  }
  
  @RequestMapping("/alteraTarefa")
  public String altera(Tarefa tarefa) {
    dao.altera(tarefa);
    return "redirect:listaTarefas";
  }
  
  @RequestMapping("finalizaTarefa")
  public String finaliza(Long id, Model model) {
    dao.finaliza(id);
    model.addAttribute("tarefa", dao.buscaPorId(id));
    return "tarefa/finalizada";
  }
  
}