package br.com.pupposoft.fiap.sgr.pagamento;

import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomDouble;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomInteger;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomLong;
import static br.com.pupposoft.fiap.test.databuilder.DataBuilderBase.getRandomString;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import br.com.pupposoft.fiap.SgrService;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.ItemEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PlataformaPagamentoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pedido.repository.PlataformaPagamentoEntityRepository;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.PagamentoApiController;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.ClienteGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.starter.http.HttpConnect;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SgrService.class)
@TestPropertySource(locations = "classpath:application-componenttest.properties")
@ActiveProfiles("componenttest")
@WireMockTest
@Disabled
class PagamentoComponentTest extends ComponentTestBase {

	@LocalServerPort
    private int randomServerPort;

	@Autowired
	private PagamentoApiController pagamentoApiController;
	
    @Autowired
    private PedidoGateway pedidoGateway;
    
    @Autowired
    private ClienteGateway clienteGateway;
    
    @Autowired
    @Qualifier("plataformaPagamentoMercadoPagoGateway")
    PlataformaPagamentoGateway plataformaPagamentoGateway;
    
    @Autowired
    private PlataformaPagamentoEntityRepository plataformaPagamentoEntityRepository;
    
    @BeforeEach
    public void initEach(){
    	cleanAllDatabase();
    }

    @Test
	void efetuarWithMercadoPagoTest(WireMockRuntimeInfo wmRuntimeInfo) {
		setField(pedidoGateway, "baseUrl", "http://localhost:" + randomServerPort);
		setField(clienteGateway, "baseUrl", "http://localhost:" + randomServerPort);
		
		final Integer idPagamentoExterno = getRandomInteger();
		final String responseBodyStr = "{\"id\": "+ idPagamentoExterno +"  }";
		final String accessToken = getRandomString();
		final String path = "/v1/payments";
		
		stubFor(post(path).willReturn(okJson(responseBodyStr)));

		setField(plataformaPagamentoGateway, "baseUrl", wmRuntimeInfo.getHttpBaseUrl());
		setField(plataformaPagamentoGateway, "accessToken", accessToken);
		setField(plataformaPagamentoGateway, "httpConnectGateway", new HttpConnect());
		setField(plataformaPagamentoGateway, "objectMapper", new ObjectMapper());
		
		createPlataformPagamentoMP();
		
		PedidoEntity pedidoEntity = createPedido();
		
		PagamentoJson pagamentoJsonRequest = PagamentoJson.builder().pedidoId(pedidoEntity.getId()).forma("PIX").build();
		PagamentoJson pagamentoJsonResponse = pagamentoApiController.efetuar(pagamentoJsonRequest);
		
		assertEquals(idPagamentoExterno+"", pagamentoJsonResponse.getPagamentoExternoId());

		List<PagamentoEntity> allPaymentsfindAll = pagamentoEntityRepository.findAll();
		assertFalse(allPaymentsfindAll.isEmpty());
		assertEquals(idPagamentoExterno+"", allPaymentsfindAll.get(0).getIdentificadorPagamentoExterno());
	}

	private void createPlataformPagamentoMP() {
		PlataformaPagamentoEntity plataformaPagamentoEntity = PlataformaPagamentoEntity.builder()
				.id(getRandomLong())
				.code("MERCADO_PAGO")
				.status(0L)
				.build();
		plataformaPagamentoEntityRepository.save(plataformaPagamentoEntity);
	}
	
	@Test
	void obterByIdentificadorPagamentoTest() {
		
		
		PagamentoEntity pagamentoEntity = createObterByIdentificadorPagamentoData();
		
		PagamentoJson pagamentoJson = pagamentoApiController.obterByIdentificadorPagamento(pagamentoEntity.getIdentificadorPagamentoExterno());
		assertEquals(pagamentoEntity.getId(), pagamentoJson.getId());
	}

	private PagamentoEntity createObterByIdentificadorPagamentoData() {
		PedidoEntity pedido = createPedido();

		
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

	private PedidoEntity createPedido() {
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
		return pedido;
	}
	
}
