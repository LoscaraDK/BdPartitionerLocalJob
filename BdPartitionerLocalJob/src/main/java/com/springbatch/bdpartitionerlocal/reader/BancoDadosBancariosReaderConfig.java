package com.springbatch.bdpartitionerlocal.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.springbatch.bdpartitionerlocal.dominio.DadosBancarios;

@Configuration
public class BancoDadosBancariosReaderConfig {
	@StepScope
	@Bean
	public JdbcPagingItemReader<DadosBancarios> dadosBancariosReader(@Qualifier("appDataSource") DataSource dataSource,
			@Qualifier("queryProviderDadosBancarios") PagingQueryProvider queryProviderDadosBancarios) {
		return new JdbcPagingItemReaderBuilder<DadosBancarios>().name("dadosBancariosReader").dataSource(dataSource)
				.queryProvider(queryProviderDadosBancarios).pageSize(2000)
				.rowMapper(new BeanPropertyRowMapper<DadosBancarios>(DadosBancarios.class)).build();
	}

	@StepScope
	@Bean
	public SqlPagingQueryProviderFactoryBean queryProviderDadosBancarios(
			@Value("#{stepExecutionContext['minValue']}") Long minValue,
			@Value("#{stepExecutionContext['maxValue']}") Long maxValue,
			@Qualifier("appDataSource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean bean = new SqlPagingQueryProviderFactoryBean();
		bean.setSelectClause("*");
		bean.setFromClause("from dados_bancarios_origem");
		bean.setWhereClause("where id >= " + minValue + " and " + " id <= " + maxValue);
		bean.setSortKey("id");
		bean.setDataSource(dataSource);
		return bean;
	}
}
