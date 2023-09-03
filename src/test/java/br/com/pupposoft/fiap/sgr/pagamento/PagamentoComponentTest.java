package br.com.pupposoft.fiap.sgr.pagamento;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomDouble;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomLong;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import br.com.pupposoft.fiap.SgrService;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.ItemEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json.PagamentoJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SgrService.class)
@TestPropertySource(locations = "classpath:application-componenttest.properties")
@ActiveProfiles("componenttest")
class PagamentoComponentTest extends ComponentTestBase {

	
	@BeforeEach
	public void initEach(){
		cleanAllDatabase();
	}

	@Test
	void test() {
		
		PagamentoEntity pagamentoEntity = createObterByIdentificadorPagamentoData();
		
		PagamentoJson responseJson = pagamentoApiController.obterByIdentificadorPagamento(pagamentoEntity.getIdentificadorPagamentoExterno());
		System.out.println(responseJson);
	}

	private PagamentoEntity createObterByIdentificadorPagamentoData() {
		ClienteEntity cliente = ClienteEntity.builder()
				.id(null)
				.nome(getRandomString())
				.cpf(getRandomString())
				.email(getRandomString())
				.build();
		clienteEntityRepository.save(cliente);
		
		ProdutoEntity produto = ProdutoEntity.builder()
				.id(null)
				.nome(getRandomString())
				.descricao(getRandomString())
				.imagem(null)
				.valor(new BigDecimal(getRandomDouble()))
				.categoriaId(0L)
				.itens(null)
				.build();
		produtoEntityRepository.save(produto);
		

		PedidoEntity pedido = PedidoEntity.builder()
				.id(null)
				.statusId(0L)
				.dataCadastro(LocalDate.now())
				.dataConclusao(null)
				.observacao(getRandomString())
				.cliente(cliente)
				.itens(null)
				.pagamentos(null)
				.build();
		pedidoEntityRepository.save(pedido);
		
		
		ItemEntity item = ItemEntity.builder()
				.id(null)
				.quantidade(getRandomLong())
				.valorUnitario(getRandomDouble())
				.produto(produto)
				.pedido(pedido)
				.build();
		itemEntityRepository.save(item);

		
		PagamentoEntity pagamentoEntity = PagamentoEntity
				.builder()
				.id(null)
				.identificadorPagamentoExterno(getRandomString())
				.valor(getRandomDouble())
				.pedido(pedido)
				.build();
		
		pagamentoEntityRepository.save(pagamentoEntity);
		return pagamentoEntity;
	}
	
}
