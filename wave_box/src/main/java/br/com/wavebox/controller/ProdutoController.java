package br.com.wavebox.controller;

public class ProdutoController {

	package br.com.wavebox.controller;

	import com.wave_box.model.Produto;
	import com.wave_box.repository.ProdutoRepository;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;
	import java.util.Optional;

	@Controller
	@RequestMapping("/produtos") // Definindo o caminho base "/produtos" para todas as rotas
	public class ProdutoController {

	    @Autowired
	    private ProdutoRepository produtoRepository;

	    // Listar todos os produtos
	    @GetMapping
	    public String listarProdutos(Model model) {
	        List<Produto> produtos = produtoRepository.findAll();
	        model.addAttribute("produtos", produtos);
	        return "produtos/lista"; // Refere-se ao template 'lista.html' que listará os produtos
	    }

	    // Exibir detalhes de um produto específico
	    @GetMapping("/{id}")
	    public String verProduto(@PathVariable Long id, Model model) {
	        Optional<Produto> produto = produtoRepository.findById(id);
	        if (produto.isPresent()) {
	            model.addAttribute("produto", produto.get());
	            return "produtos/detalhes"; // Refere-se ao template 'detalhes.html'
	        } else {
	            return "error"; // Caso o produto não seja encontrado
	        }
	    }

	    // Exibir formulário de criação de produto
	    @GetMapping("/novo")
	    public String mostrarFormularioCriacao(Model model) {
	        model.addAttribute("produto", new Produto());
	        return "produtos/formulario"; // Refere-se ao template 'formulario.html' para criar/editar produto
	    }

	    // Criar um novo produto
	    @PostMapping
	    public String criarProduto(@ModelAttribute Produto produto) {
	        produtoRepository.save(produto);
	        return "redirect:/produtos"; // Redireciona para a página de listagem de produtos após a criação
	    }

	    // Exibir formulário de edição de produto
	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
	        Optional<Produto> produto = produtoRepository.findById(id);
	        if (produto.isPresent()) {
	            model.addAttribute("produto", produto.get());
	            return "produtos/formulario"; // O mesmo template de criação servirá para a edição
	        } else {
	            return "error"; // Caso o produto não seja encontrado
	        }
	    }

	    // Atualizar produto existente
	    @PostMapping("/editar/{id}")
	    public String editarProduto(@PathVariable Long id, @ModelAttribute Produto produtoAtualizado) {
	        Optional<Produto> produtoExistente = produtoRepository.findById(id);
	        if (produtoExistente.isPresent()) {
	            Produto produto = produtoExistente.get();
	            produto.setNome(produtoAtualizado.getNome());
	            produto.setDescricao(produtoAtualizado.getDescricao());
	            produto.setPreco(produtoAtualizado.getPreco());
	            produtoRepository.save(produto);
	            return "redirect:/produtos";
	        } else {
	            return "error";
	        }
	    }

	    // Deletar produto
	    @PostMapping("/deletar/{id}")
	    public String deletarProduto(@PathVariable Long id) {
	        produtoRepository.deleteById(id);
	        return "redirect:/produtos"; // Redireciona após deletar
	    }
	}
	
}
